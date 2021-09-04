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
package com.paulo.rodrigues.librarybookstore.author.service;

import com.paulo.rodrigues.librarybookstore.address.service.AddressService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.paulo.rodrigues.librarybookstore.book.dto.BookDTO;
import com.paulo.rodrigues.librarybookstore.author.dto.AuthorDTO;
import com.paulo.rodrigues.librarybookstore.utils.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.author.filter.AuthorFilter;
import com.paulo.rodrigues.librarybookstore.author.model.Author;
import com.paulo.rodrigues.librarybookstore.book.model.Book;
import com.paulo.rodrigues.librarybookstore.author.model.Author;
import com.paulo.rodrigues.librarybookstore.book.repository.BookRepository;
import com.paulo.rodrigues.librarybookstore.address.repository.CityRepository;
import com.paulo.rodrigues.librarybookstore.address.repository.CountryRepository;
import com.paulo.rodrigues.librarybookstore.author.model.Author;
import com.paulo.rodrigues.librarybookstore.author.dto.AuthorDTO;
import com.paulo.rodrigues.librarybookstore.author.filter.AuthorFilter;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.paulo.rodrigues.librarybookstore.author.repository.AuthorRepository;
import com.paulo.rodrigues.librarybookstore.author.repository.AuthorRepository;

/**
 *
 * @author paulo.rodrigues
 */
@Service
@Transactional
public class AuthorService {

    private ModelMapper modelMapper;

    @Autowired
    private AuthorRepository authorRepository;
      
    @Autowired
    private AddressService addressService;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;
    
    @Autowired 
    private BookRepository bookRepository;

    public List<AuthorDTO> findAll() {
        return toListDTO(authorRepository.findAll());
    }

    public Page<Author> findPageble(AuthorFilter filter, Pageable pageable) {
        return authorRepository.findPageble(
                filter.getId(),
                filter.getName(),
                filter.getSex(),
                filter.getStartDate(),
                filter.getFinalDate(),
                pageable);
    }

    public Author findById(Long authorId) throws LibraryStoreBooksException {
        Author author = authorRepository.findById(authorId).orElse(null);

        if (author == null) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("PERSON_NOT_FOUND") + " ID: " + authorId);
        }

        return author;
    }
    
    public List<AuthorDTO> findByName (String name) throws LibraryStoreBooksException {
        return toListDTO(authorRepository.findByName(name));
    }

    public Author findByCPF(String cpf) {
        return authorRepository.findByCpf(cpf);
    }

    public AuthorDTO create(Author author) throws LibraryStoreBooksException {
        if(author != null && author.getAddress() != null){
            addressService.create(author.getAddress());
        }

        return toDTO(save(author));
    }

    public Author save(Author author) throws LibraryStoreBooksException {
        author.validation();
        author.persistAt();

        return authorRepository.saveAndFlush(author);
    }

    public AuthorDTO edit(Long authorId, AuthorDTO authorDetalhes) throws LibraryStoreBooksException {
        Author authorToEdit = findById(authorId);
       // modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(AuthorDTO.class, Author.class);
        modelMapper.validate();
        authorToEdit = modelMapper.map(authorDetalhes, Author.class);

        return toDTO(save(authorToEdit));
    }

    public void erase(Long authorId) throws LibraryStoreBooksException {
        Author author = findById(authorId);

        authorRepository.delete(author);
    }

    public AuthorDTO toDTO(Author author) {
        if (author == null) {
            return null;
        }

        return AuthorDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .sex(author.getSex())
                .email(author.getEmail())
                .address(addressService.toDTO(author.getAddress()))
                .birthdate(author.getBirthdate())
                .birthCity(author.getBirthCity() != null ? author.getBirthCity().toDTO() : null)
                .birthCountry(author.getBirthCountry() != null ? author.getBirthCountry().toDTO() : null)
                .build();

    }

    public Author fromDTO(AuthorDTO dto) throws LibraryStoreBooksException {
        if (dto == null) {
            return null;
        }

        return Author.builder()
                .id(dto.getId())
                .name(dto.getName())
                .sex(dto.getSex())
                .email(dto.getEmail())
                .address(dto.getAddress() != null ? addressService.findById(dto.getAddress().getId()) : null)
                .birthdate(dto.getBirthdate())
                .birthCity(dto.getBirthCity() != null ? cityRepository.getById(dto.getBirthCity().getId()) : null)
                .birthCountry(dto.getBirthCountry() != null ? countryRepository.getById(dto.getBirthCountry().getId()) : null)
                .build();
    }

    

    public List<AuthorDTO> toListDTO(List<Author> people) {
        return people.stream().map(b -> toDTO(b)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<AuthorDTO> getListAuthorsDTO(List<Author> authors) {
        return authors.stream().map(b -> toDTO(b)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<Author> getListAuthorsByListDTO(List<AuthorDTO> listDTOs) throws LibraryStoreBooksException {
        List<Author> result = new ArrayList<>();

        if (!FormatUtils.isEmpty(listDTOs)) {
            for (AuthorDTO dto : listDTOs) {
                result.add(fromDTO(dto));
            }
        }

        return result;
    }

    public Author saveBookAuthor(Book book, AuthorDTO dto) throws LibraryStoreBooksException {
        Author author = fromDTO(dto);
        
        Set<Book> booksFromAuthor = Sets.newHashSet(bookRepository.getBooksFromAuthor(author.getName()));
        booksFromAuthor.add(book);
        
        author.setBooks(Lists.newArrayList(booksFromAuthor));
        authorRepository.saveAndFlush(author);
        return author;
    }
    
    public Set<Author> saveBookAuthorFromListBooksDTO(Book book, List<AuthorDTO> authorsDTO) throws LibraryStoreBooksException {
        Set<Author> authors = new HashSet<>();
        for(AuthorDTO author: authorsDTO){
            authors.add(saveBookAuthor(book, author));
        }
        return authors;
    }

}
