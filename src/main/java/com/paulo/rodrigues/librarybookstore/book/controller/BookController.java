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
package com.paulo.rodrigues.librarybookstore.book.controller;

import com.paulo.rodrigues.librarybookstore.book.dto.BookDTO;
import com.paulo.rodrigues.librarybookstore.book.filter.BookFilter;
import com.paulo.rodrigues.librarybookstore.book.service.BookService;
import com.paulo.rodrigues.librarybookstore.utils.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.book.model.Book;
import com.paulo.rodrigues.librarybookstore.book.model.BookSubject;
import com.paulo.rodrigues.librarybookstore.book.model.Language;
import com.paulo.rodrigues.librarybookstore.utils.PagedResult;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/fetch")
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
    public Map<String, Long> delete(@PathVariable(value = "id") Long bookId) throws LibraryStoreBooksException {
        bookService.erase(bookId);
        Map<String, Long> response = new HashMap<>();
        response.put("id", bookId);

        return response;
    }
    
    @GetMapping("/subjects")
    public ResponseEntity<List<BookSubject>> getBookSubject() throws LibraryStoreBooksException {
        return ResponseEntity.ok().body(bookService.getBookSubject());
    }
    
    @GetMapping("/formats")
    public ResponseEntity<List<Map<String, String>>> getEBookFormat() throws LibraryStoreBooksException {
        return ResponseEntity.ok().body(bookService.getEBookFormat());
    }
    
    @GetMapping("/conditions")
    public ResponseEntity<List<Map<String, String>>> getEBookCondition() throws LibraryStoreBooksException {
        return ResponseEntity.ok().body(bookService.getEBookCondition());
    }
    
    @GetMapping("/languages")
    public ResponseEntity<List<Language>> getLanguages() throws LibraryStoreBooksException {
        return ResponseEntity.ok().body(bookService.getBookLanguage());
    }
}
