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
import com.paulo.rodrigues.librarybookstore.utils.*;
import com.paulo.rodrigues.librarybookstore.book.model.Book;
import com.paulo.rodrigues.librarybookstore.book.repository.BookRepository;
import com.paulo.rodrigues.librarybookstore.author.model.Author;
import com.paulo.rodrigues.librarybookstore.author.dto.AuthorDTO;
import com.paulo.rodrigues.librarybookstore.author.filter.AuthorFilter;

import java.util.*;
import javax.transaction.Transactional;

import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
      
    @Autowired
    private AddressService addressService;

    @Autowired 
    private BookRepository bookRepository;

    public List<AuthorDTO> findAll() {
        return authorsToDTOs(authorRepository.findAll());
    }

    public Page<Author> findPageable(AuthorFilter filter, Pageable pageable) {
        log.info("Finding pageable authors by filter={}", filter);
        return authorRepository.findPageble(
                filter.getId(),
                filter.getName(),
                filter.getSex(),
                filter.getStartDate(),
                filter.getFinalDate(),
                pageable);
    }

    public Author findById(Long authorId) throws NotFoundException {
        log.info("Finding author by authorId={}", authorId);
        Optional<Author> author = authorRepository.findById(authorId);
        if (author == null || !author.isPresent()) {
            log.error("Author not found by authorId={}", authorId);
            throw new NotFoundException(MessageUtil.getMessage("AUTHOR_NOT_FOUND") + " ID: " + authorId);
        }
        return author.get();
    }
    
    public List<AuthorDTO> findByName (String name) throws LibraryStoreBooksException {
        return authorsToDTOs(authorRepository.findByName(name));
    }

    public AuthorDTO create(Author author) throws InvalidRequestException {
        assert author != null : MessageUtil.getMessage("AUTHOR_IS_NULL");
        if(author.getAddress() != null){
            addressService.create(author.getAddress());
        }
        log.info("Creating author name={}", author.getName());
        return authorToDTO(save(author));
    }

    public Author save(Author author) throws InvalidRequestException {
        author.validation();
        author.persistAt();
        log.info("Saving author={}", author);
        return authorRepository.saveAndFlush(author);
    }

    public AuthorDTO edit(Long authorId, AuthorDTO authorEdited) throws InvalidRequestException, NotFoundException {
        Author authorToEdit = findById(authorId);
        String createBy = authorToEdit.getCreateBy();
        var createAt = authorToEdit.getCreateAt();
        Address address = authorToEdit.getAddress();
        ModelMapper mapper = new ModelMapper();
        authorToEdit = mapper.map(authorEdited, Author.class);
        authorToEdit.setAddress(address);
        authorToEdit.setCreateBy(createBy);
        authorToEdit.setCreateAt(createAt);
        log.info("Updating author id={}, name={}", authorId, authorToEdit.getName());
        return authorToDTO(save(authorToEdit));
    }
 
    public void delete(Long authorId) throws LibraryStoreBooksException, NotFoundException {
        Author author = findById(authorId);
        if(author.getAddress() != null){
            addressService.delete(author.getAddress().getId());
        }
        Set<Book> books = author.getBooks();
        if (!FormatUtils.isEmpty(books)){
            for (Book book : books) {
                book.getAuthors().stream()
                        .filter(author1 -> author1.getId() == authorId)
                        .forEach(author1 -> {
                            log.info("Deleting author_books authorId={}, bookId={}", author1.getId(), book.getId());
                            bookRepository.deleteBookAuthor(author1.getId(), book.getId());
                        });
                if(book.getAuthors().size() <= 1) {
                    log.info("Deleting book id={}, title={}", book.getId(), book.getTitle());
                    bookRepository.delete(book);
                }
            }
        }
        log.info("Deleting author id={}, name={}", authorId, author.getName());
        authorRepository.delete(author);
    }

    public AuthorDTO authorToDTO(Author author) {
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

    public Author authorFromDTO(AuthorDTO dto) {
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

    public List<AuthorDTO> authorsToDTOs(List<Author> authors) {
        return authors.stream().map(this::authorToDTO).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<Author> authorsFromDTOs(List<AuthorDTO> authorsDTO) {
        List<Author> result = new ArrayList<>();
        if (!FormatUtils.isEmpty(authorsDTO)) {
            for (AuthorDTO dto : authorsDTO) {
                result.add(authorFromDTO(dto));
            }
        }
        return result;
    }

    public Author saveBookAuthorDTO(Book book, AuthorDTO dto) throws InvalidRequestException, LibraryStoreBooksException {
        Author author = null;
        try {
            author = findById(dto.getId());
        } catch (NotFoundException e) {
            author = authorFromDTO(dto);
            author = save(author);
        }
        if(author == null){
            log.error("Author is null when saveBookAuthorDTO was called book={}, authorDTO={}", book, dto);
            throw new LibraryStoreBooksException(MessageUtil.getMessage("SOMETHING_UNEXPECTED_HAPPENED"));
        }
        Set<Book> booksFromAuthor = Sets.newHashSet(bookRepository.getBooksFromAuthorName(author.getName()));
        booksFromAuthor.add(book);
        author.setBooks(booksFromAuthor);
        log.info("Saving book to authorDTO, bookTitle={} bookId={} authorName={} authorId={}", book.getTitle(), book.getId(), author.getName(), author.getId());
        authorRepository.saveAndFlush(author);
        return author;
    }

    public Author saveBookAuthor(Book book, Author author) {
        Set<Book> booksFromAuthor = Sets.newHashSet(bookRepository.getBooksFromAuthorName(author.getName()));
        booksFromAuthor.add(book);
        author.setBooks(booksFromAuthor);
        log.info("Saving book to author, bookTitle={} bookId={} authorName={} authorId={}", book.getTitle(), book.getId(), author.getName(), author.getId());
        authorRepository.saveAndFlush(author);
        return author;
    }
    
    public Set<Author> saveBookAuthorsFromDTOs(Book book, List<AuthorDTO> authorsDTO) throws InvalidRequestException, LibraryStoreBooksException {
        Set<Author> authors = new HashSet<>();
        for(AuthorDTO author: authorsDTO){
            authors.add(saveBookAuthorDTO(book, author));
        }
        return authors;
    }

    public Set<Author> saveAuthors(Set<Author> authors) throws InvalidRequestException {
        Set<Author> newAuthors =  new HashSet<>();
            if(!FormatUtils.isEmpty(authors)){
                for (Author author: authors) {
                    try {
                        newAuthors.add(findById(author.getId()));
                    } catch (NotFoundException e) {
                        newAuthors.add(save(author));
                    }
                }
            }
        return newAuthors;
    }
}
