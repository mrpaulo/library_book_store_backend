/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.controller;

import com.paulo.rodrigues.librarybookstore.dto.BookDTO;
import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.filter.BookFilter;
import com.paulo.rodrigues.librarybookstore.model.Book;
import com.paulo.rodrigues.librarybookstore.service.BookService;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import com.paulo.rodrigues.librarybookstore.utils.PagedResult;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author paulo.rodrigues
 */
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public List<BookDTO> getAll() {
        List<BookDTO> booksList = bookService.findAll();

        return booksList.stream().sorted(Comparator.comparing(BookDTO::getTitle)).collect(Collectors.toList());
    }

    @GetMapping()
    public List<BookDTO> getAllPageble(@RequestBody BookFilter filter, HttpServletRequest req, HttpServletResponse res) {
        
        PagedResult<BookDTO> result = bookService.findPageble(filter);
        res.addHeader("Total-Count", String.valueOf(result != null ? result.getTotalElementos() : 0));

        return result.getElementos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable(value = "id") Long bookId) throws LibraryStoreBooksException {
        return ResponseEntity.ok().body(bookService.findById(bookId));
    }

    @PostMapping()
    public BookDTO create(@RequestBody BookDTO dto) throws LibraryStoreBooksException {
        return bookService.create(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> update(@PathVariable(value = "id") Long bookId, @RequestBody BookDTO bookDetalhes) throws LibraryStoreBooksException {
        return ResponseEntity.ok(bookService.edit(bookId, bookDetalhes));
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> delete(@PathVariable(value = "id") Long bookId) throws LibraryStoreBooksException {
        bookService.erase(bookId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
