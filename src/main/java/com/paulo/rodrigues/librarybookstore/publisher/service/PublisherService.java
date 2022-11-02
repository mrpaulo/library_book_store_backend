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
import com.paulo.rodrigues.librarybookstore.utils.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.publisher.model.Publisher;
import com.paulo.rodrigues.librarybookstore.publisher.dto.PublisherDTO;
import com.paulo.rodrigues.librarybookstore.publisher.filter.PublisherFilter;
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import com.paulo.rodrigues.librarybookstore.publisher.repository.PublisherRepository;

/**
 *
 * @author paulo.rodrigues
 */
@Service
@Transactional
public class PublisherService {
    
    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    private AddressService addressService;

    public List<PublisherDTO> findAll() {
        return toListDTO(publisherRepository.findAll());
    }

    public Page<Publisher> findPageble(PublisherFilter filter, Pageable pageable) {
        return publisherRepository.findPageble(
                filter.getId(),
                filter.getName(),
                filter.getCnpj(),
                filter.getStartDate(),
                filter.getFinalDate(),
                pageable);
    }

    public Publisher findById(Long publisherId) throws LibraryStoreBooksException {
        Publisher publisher = publisherRepository.findById(publisherId).orElse(null);

        if (publisher == null) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("PUBLISHER_NOT_FOUND") + " ID: " + publisherId);
        }

        return publisher;
    }
    
    public List<PublisherDTO> findByName(String name) {
        return toListDTO(publisherRepository.findByName(name));
    }

    public PublisherDTO create(Publisher publisher) throws LibraryStoreBooksException {
        if(publisher != null && publisher.getAddress() != null){
            addressService.create(publisher.getAddress());
        }
        
        return toDTO(save(publisher));
    }

    public Publisher save(Publisher publisher) throws LibraryStoreBooksException {
        publisher.validation();
        publisher.persistAt();

        return publisherRepository.saveAndFlush(publisher);
    }

    public PublisherDTO edit(Long publisherId, PublisherDTO publisherDetail) throws LibraryStoreBooksException {
        Publisher publisherToEdit = findById(publisherId);
        String createBy = publisherToEdit.getCreateBy();
        var createAt = publisherToEdit.getCreateAt();
        Address address = publisherToEdit.getAddress();
        
        ModelMapper mapper = new ModelMapper();
        publisherToEdit = mapper.map(publisherDetail, Publisher.class);
        publisherToEdit.setAddress(address);
        publisherToEdit.setCreateAt(createAt);
        publisherToEdit.setCreateBy(createBy);
        
        
        return toDTO(save(publisherToEdit));
    }

    public void erase(Long publisherId) throws LibraryStoreBooksException {
        Publisher publisherToDelete = findById(publisherId);

        if(publisherToDelete.getAddress() != null){
            addressService.erase(publisherToDelete.getAddress().getId());
        }

        publisherRepository.delete(publisherToDelete);
    }

    public PublisherDTO toDTO(Publisher publisher) {
        
        return PublisherDTO.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .cnpj(publisher.getCnpj())
                .createDate(publisher.getCreateDate())
                .address(addressService.toDTO(publisher.getAddress()))
                .description(publisher.getDescription())
                .build();
                
    }

    public Publisher fromDTO(PublisherDTO dto) throws LibraryStoreBooksException {
        return Publisher.builder()
                .name(dto.getName())
                .cnpj(dto.getCnpj())
                .createDate(dto.getCreateDate())
                .address(dto.getAddress() != null ? addressService.findById(dto.getAddress().getId()) : null)
                .build();
    }

    public List<PublisherDTO> toListDTO(List<Publisher> publishers) {
        return publishers.stream().map(b -> toDTO(b)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
}
