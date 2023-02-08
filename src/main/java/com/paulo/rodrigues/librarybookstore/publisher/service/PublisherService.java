/*
 * Copyright (C) 2021 paulo.rodrigues
 * Profile: <https://github.com/mrpaulo>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.paulo.rodrigues.librarybookstore.publisher.service;

import com.paulo.rodrigues.librarybookstore.address.model.Address;
import com.paulo.rodrigues.librarybookstore.address.service.AddressService;
import com.paulo.rodrigues.librarybookstore.book.model.Book;
import com.paulo.rodrigues.librarybookstore.book.repository.BookRepository;
import com.paulo.rodrigues.librarybookstore.publisher.dto.PublisherDTO;
import com.paulo.rodrigues.librarybookstore.publisher.filter.PublisherFilter;
import com.paulo.rodrigues.librarybookstore.publisher.model.Publisher;
import com.paulo.rodrigues.librarybookstore.publisher.repository.PublisherRepository;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import com.paulo.rodrigues.librarybookstore.utils.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil;
import com.paulo.rodrigues.librarybookstore.utils.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author paulo.rodrigues
 */
@Service
@Transactional
@Log4j2
public class PublisherService {

    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    AddressService addressService;

    @Autowired
    BookRepository bookRepository;

    public List<PublisherDTO> findAll() {
        return publishersToDTOs(publisherRepository.findAll());
    }

    public Page<Publisher> findPageable(PublisherFilter filter, Pageable pageable) {
        return publisherRepository.findPageble(
                filter.getId(),
                filter.getName(),
                filter.getCnpj(),
                filter.getStartDate(),
                filter.getFinalDate(),
                pageable);
    }

    public Publisher findById(Long publisherId) throws NotFoundException {
        Optional<Publisher> publisher = publisherRepository.findById(publisherId);
        log.info("Finding publisher by publisherId={}", publisherId);
        if (publisher == null || !publisher.isPresent()) {
            log.error("Publisher not found by publisherId={}", publisherId);
            throw new NotFoundException(MessageUtil.getMessage("PUBLISHER_NOT_FOUND") + " ID: " + publisherId);
        }
        return publisher.get();
    }

    public Publisher findByCnpj(String cnpj) throws NotFoundException, LibraryStoreBooksException {
        log.info("Finding publisher by cnpj={}", cnpj);
        if (!FormatUtils.isCNPJ(cnpj)) {
            log.error("Publisher cnpj is invalid cnpj={}", cnpj);
            throw new LibraryStoreBooksException(MessageUtil.getMessage("PUBLISHER_CNPJ_INVALID"));
        }
        Publisher publisher = publisherRepository.findByCnpj(cnpj);
        if (publisher == null) {
            log.error("Publisher not found by cnpj={}", cnpj);
            throw new NotFoundException(MessageUtil.getMessage("PUBLISHER_NOT_FOUND") + " CNPJ: " + cnpj);
        }
        return publisher;
    }
    
    public List<PublisherDTO> findByName(String name) {
        log.info("Finding publisher by name={}", name);
        return publishersToDTOs(publisherRepository.findByName(name));
    }

    public PublisherDTO create(Publisher publisher) throws LibraryStoreBooksException {
        assert publisher != null : MessageUtil.getMessage("PUBLISHER_IS_NULL");
        if(publisher.getAddress() != null){
            addressService.create(publisher.getAddress());
        }
        log.info("Creating publisher name={}", publisher.getName());
        return publisherToDTO(save(publisher));
    }

    public Publisher save(Publisher publisher) throws LibraryStoreBooksException {
        publisher.validation();
        publisher.persistAt();
        log.info("Saving publisher={}", publisher);
        return publisherRepository.saveAndFlush(publisher);
    }

    public Publisher checkAndSave(Publisher publisher) throws LibraryStoreBooksException {
        try {
            return findByCnpj(publisher.getCnpj());
        } catch (NotFoundException e) {
            return save(publisher);
        } catch (Exception e) {
            log.error("Exception on checkAndSave publisher={}, message={}", publisher, e.getMessage());
            return null;
        }
    }

    public Publisher checkAndSave(PublisherDTO dto) throws LibraryStoreBooksException {
        try {
            return findByCnpj(dto.getCnpj());
        } catch (NotFoundException e) {
            Publisher publisher = Publisher.builder()
                    .name(dto.getName())
                    .cnpj(dto.getCnpj())
                    .foundationDate(dto.getFoundationDate())
                    .address(addressService.getAddressFromDTO(dto.getAddress()))
                    .build();
            return save(publisher);
        } catch (Exception e) {
            log.error("Exception on checkAndSave dto={}, message={}", dto, e.getMessage());
            return null;
        }
    }

    public PublisherDTO edit(Long publisherId, PublisherDTO publisherDetail) throws LibraryStoreBooksException, NotFoundException {
        Publisher publisherToEdit = findById(publisherId);
        String createBy = publisherToEdit.getCreateBy();
        var createAt = publisherToEdit.getCreateAt();
        Address address = publisherToEdit.getAddress();
        ModelMapper mapper = new ModelMapper();
        publisherToEdit = mapper.map(publisherDetail, Publisher.class);
        publisherToEdit.setAddress(address);
        publisherToEdit.setCreateAt(createAt);
        publisherToEdit.setCreateBy(createBy);
        publisherToEdit.setId(publisherId);
        log.info("Updating publisher id={}, name={}", publisherId, publisherToEdit.getName());
        return publisherToDTO(save(publisherToEdit));
    }

    public void delete(Long publisherId) throws LibraryStoreBooksException, NotFoundException {
        Publisher publisherToDelete = findById(publisherId);
        if(publisherToDelete.getAddress() != null){
            addressService.delete(publisherToDelete.getAddress().getId());
        }
        List<Book> books = bookRepository.getBooksFromPublisherId(publisherId);
        if (!FormatUtils.isEmpty(books)){
            for (Book book : books) {
                bookRepository.delete(book);
            }
        }
        log.info("Deleting publisher id={}, name={}", publisherId, publisherToDelete.getName());
        publisherRepository.delete(publisherToDelete);
    }

    public PublisherDTO publisherToDTO(Publisher publisher) {
        if (publisher == null) {
            return null;
        }
        return PublisherDTO.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .cnpj(publisher.getCnpj())
                .foundationDate(publisher.getFoundationDate())
                .address(addressService.toDTO(publisher.getAddress()))
                .description(publisher.getDescription())
                .build();
    }

    public Publisher publisherFromDTO(PublisherDTO dto) {
        try {
            return checkAndSave(dto);
        } catch (Exception e) {
            return null;
        }
    }

    public List<PublisherDTO> publishersToDTOs(List<Publisher> publishers) {
        return publishers.stream().map(this::publisherToDTO).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}
