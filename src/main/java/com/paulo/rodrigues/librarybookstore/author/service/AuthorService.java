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
import com.google.common.collect.Sets;
import com.paulo.rodrigues.librarybookstore.address.model.Address;
import com.paulo.rodrigues.librarybookstore.utils.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.book.model.Book;
import com.paulo.rodrigues.librarybookstore.book.repository.BookRepository;
import com.paulo.rodrigues.librarybookstore.author.model.Author;
import com.paulo.rodrigues.librarybookstore.author.dto.AuthorDTO;
import com.paulo.rodrigues.librarybookstore.author.filter.AuthorFilter;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil;

import java.util.*;
import javax.transaction.Transactional;

import com.paulo.rodrigues.librarybookstore.utils.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.paulo.rodrigues.librarybookstore.author.repository.AuthorRepository;

/**
 *
 * @author paulo.rodrigues
 */
@Service
@Transactional
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
      
    @Autowired
    private AddressService addressService;

    @Autowired 
    private BookRepository bookRepository;

    public List<AuthorDTO> findAll() {
        return toListDTO(authorRepository.findAll());
    }

    public Page<Author> findPageable(AuthorFilter filter, Pageable pageable) {
        return authorRepository.findPageble(
                filter.getId(),
                filter.getName(),
                filter.getSex(),
                filter.getStartDate(),
                filter.getFinalDate(),
                pageable);
    }

    public Author findById(Long authorId) throws NotFoundException {
        Optional<Author> author = authorRepository.findById(authorId);

        if (author == null || !author.isPresent()) {
            throw new NotFoundException(MessageUtil.getMessage("AUTHOR_NOT_FOUND") + " ID: " + authorId);
        }

        return author.get();
    }
    
    public List<AuthorDTO> findByName (String name) throws LibraryStoreBooksException {
        return toListDTO(authorRepository.findByName(name));
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

    public AuthorDTO edit(Long authorId, AuthorDTO authorEdited) throws LibraryStoreBooksException, NotFoundException {
        Author authorToEdit = findById(authorId);
        String createBy = authorToEdit.getCreateBy();
        var createAt = authorToEdit.getCreateAt();
        Address address = authorToEdit.getAddress();
        ModelMapper mapper = new ModelMapper();
                    
        authorToEdit = mapper.map(authorEdited, Author.class);
        
        authorToEdit.setAddress(address);        
        authorToEdit.setCreateBy(createBy);
        authorToEdit.setCreateAt(createAt);
        
        return toDTO(save(authorToEdit));
    }
 
    public void erase(Long authorId) throws LibraryStoreBooksException, NotFoundException {
        Author author = findById(authorId);

        if(author.getAddress() != null){
            addressService.erase(author.getAddress().getId());
        }

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
                .address(addressService.getAddressFromDTO(dto.getAddress()))
                .birthdate(dto.getBirthdate())
                .birthCity(addressService.getCityFromDTO(dto.getBirthCity()))
                .birthCountry(addressService.getCountryFromDTO(dto.getBirthCountry()))
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

    public Author saveBookAuthorDTO(Book book, AuthorDTO dto) throws LibraryStoreBooksException {
        Author author = null;
        try {
            author = findById(dto.getId());
        } catch (NotFoundException e) {
            author = fromDTO(dto);
            author = save(author);
        }

        if(author == null){
            throw new LibraryStoreBooksException(MessageUtil.getMessage("SOMETHING_UNEXPECTED_HAPPENED"));
        }

        Set<Book> booksFromAuthor = Sets.newHashSet(bookRepository.getBooksFromAuthorName(author.getName()));
        booksFromAuthor.add(book);
        
        author.setBooks(booksFromAuthor);
        authorRepository.saveAndFlush(author);
        return author;
    }

    public Author saveBookAuthor(Book book, Author author) {
        Set<Book> booksFromAuthor = Sets.newHashSet(bookRepository.getBooksFromAuthorName(author.getName()));
        booksFromAuthor.add(book);

        author.setBooks(booksFromAuthor);
        authorRepository.saveAndFlush(author);
        return author;
    }
    
    public Set<Author> saveBookAuthorFromListBooksDTO(Book book, List<AuthorDTO> authorsDTO) throws LibraryStoreBooksException {
        Set<Author> authors = new HashSet<>();
        for(AuthorDTO author: authorsDTO){
            authors.add(saveBookAuthorDTO(book, author));
        }
        return authors;
    }

    public Set<Author> saveAuthors(Set<Author> authors) throws LibraryStoreBooksException {
        Set<Author> authorsNew =  new HashSet<>();
            if(!FormatUtils.isEmpty(authors)){
                for (Author author: authors) {
                    try {
                        authorsNew.add(findById(author.getId()));
                    } catch (NotFoundException e) {
                        authorsNew.add(save(author));
                    }
                }
            }
        return authorsNew;
    }
}
