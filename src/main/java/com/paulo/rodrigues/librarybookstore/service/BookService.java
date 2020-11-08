/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.service;

import com.paulo.rodrigues.librarybookstore.dto.BookDTO;
import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.filter.BookFilter;
import com.paulo.rodrigues.librarybookstore.model.Book;
import com.paulo.rodrigues.librarybookstore.repository.BookRepository;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author paulo.rodrigues
 */
@Service
@Transactional
public class BookService {

    @Autowired
    public BookRepository bookRepository;

    private ModelMapper modelMapper;

    public List<BookDTO> findAll() {
        List<Book> books = bookRepository.findAll();

        return toListDTO(books);
    }

    public Page<Book> findPageble(BookFilter filter, Pageable pageable) {
        return bookRepository.findPageble(
                filter.getTitle(),
                filter.getAuthor(),
                filter.getPublisher(),
                filter.getStartDate(),
                filter.getFinalDate(),
                pageable);
    }

    public Book findById(Long bookId) throws LibraryStoreBooksException {
        Book book = bookRepository.findById(bookId).orElse(null);

        if (book == null) {
            throw new LibraryStoreBooksException("Livro não encontrado");
        }

        return book;
    }

    public BookDTO create(BookDTO dto) throws LibraryStoreBooksException {
        Book book = fromDTO(dto);
        return toDTO(save(book));
    }

    public Book save(Book book) throws LibraryStoreBooksException {
        book.bookValidation();
        book.persistAt();

        return bookRepository.saveAndFlush(book);
    }

    public BookDTO edit(Long bookId, BookDTO bookDetail) throws LibraryStoreBooksException {
        Book bookToEdit = findById(bookId);

        bookToEdit = modelMapper.map(bookDetail, Book.class);

        return toDTO(save(bookToEdit));
    }

    public void erase(Long bookId) throws LibraryStoreBooksException {
        Book bookToErase = findById(bookId);
        bookRepository.delete(bookToErase);
    }

    public BookDTO toDTO(Book book) {
        return modelMapper.map(book, BookDTO.class);
    }

    public Book fromDTO(BookDTO dto) {
        return modelMapper.map(dto, Book.class);
    }

    public List<BookDTO> toListDTO(List<Book> books) {
        return books.stream().map(b -> toDTO(b)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}
