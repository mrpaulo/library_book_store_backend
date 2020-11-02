/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.service;

import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.filter.BookFilter;
import com.paulo.rodrigues.librarybookstore.model.Book;
import com.paulo.rodrigues.librarybookstore.repository.BookRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author paulo.rodrigues
 */
public class BookService {
    
    @Autowired
    public BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
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
        
        if(book == null){
            throw new LibraryStoreBooksException("Livro não encontrado");
        }
        
        return bookRepository.findById(bookId).orElse(null);
    }

    public Book save(Book book) throws LibraryStoreBooksException {
        book.bookValidation();
        
        return bookRepository.saveAndFlush(book);
    }

    public Book edit(Long bookId, Book bookDetail) throws LibraryStoreBooksException {
        Book bookToEdit = findById(bookId);
        //TODO ver o mapeamento automatico
        
        
        return save(bookToEdit);
    }

    public void erase(Long bookId) throws LibraryStoreBooksException {
        Book bookToErase = findById(bookId);
        bookRepository.delete(bookToErase);
    }
    
}
