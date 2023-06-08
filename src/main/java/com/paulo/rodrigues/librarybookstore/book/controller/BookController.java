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
import com.paulo.rodrigues.librarybookstore.utils.*;
import com.paulo.rodrigues.librarybookstore.book.model.Book;
import com.paulo.rodrigues.librarybookstore.book.model.BookSubject;
import com.paulo.rodrigues.librarybookstore.book.model.Language;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import static com.paulo.rodrigues.librarybookstore.utils.ConstantsUtil.*;

/**
 *
 * @author paulo.rodrigues
 */
@Log4j2
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping(BOOKS_V1_BASE_API)
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(GET_ALL_PATH)
    public ResponseEntity<List<BookDTO>> getAll() {
        try {
            List<BookDTO> books = bookService.findAll();
            List<BookDTO> booksSorted = books.stream().sorted(Comparator.comparing(BookDTO::getTitle)).collect(Collectors.toList());
            return ResponseEntity.ok().body(booksSorted);
        } catch (Exception e) {
            log.error("Exception on getAll message={}", e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @PostMapping(FIND_PAGEABLE_PATH)
    public ResponseEntity<List<BookDTO>> findPageable(@RequestBody BookFilter filter, HttpServletRequest req, HttpServletResponse res) {
        try {
            PagedResult<BookDTO> books = bookService.findPageable(FormatUtils.setOffSet(filter));
            res.addHeader("totalcount", String.valueOf(books != null ? books.getTotalElementos() : 0));
            assert books != null : MessageUtil.getMessage("BOOKS_NOT_FOUND");
            return ResponseEntity.ok().body(books.getElementos());
        } catch (Exception e) {
            log.error("Exception on findPageable, message={}", e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @GetMapping(GET_BY_ID_PATH)
    public ResponseEntity<Book> getById(@PathVariable(value = "id") Long bookId) throws LibraryStoreBooksException, NotFoundException {
        try {
            return ResponseEntity.ok().body(bookService.findById(bookId));
        } catch (Exception e) {
            log.error("Exception on getById bookId={}, message={}", bookId, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }
    
    @PostMapping()
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO bookDTO) throws InvalidRequestException, NotFoundException {
        try {
            return new ResponseEntity<>(bookService.create(bookDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Exception on create bookDTO={}, message={}", bookDTO, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @PutMapping(UPDATE_PATH)
    public ResponseEntity<BookDTO> update(@PathVariable(value = "id") Long bookId, @RequestBody BookDTO bookDTO) throws InvalidRequestException, LibraryStoreBooksException, NotFoundException {
        try {
            return ResponseEntity.ok(bookService.edit(bookId, bookDTO));
        } catch (Exception e) {
            log.error("Exception on update bookId={}, message={}", bookId, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @DeleteMapping(DELETE_PATH)
    public Map<String, Long> delete(@PathVariable(value = "id") Long bookId) throws LibraryStoreBooksException, NotFoundException {
        try {
            bookService.delete(bookId);
            Map<String, Long> response = new HashMap<>();
            response.put("id", bookId);
            return response;
        } catch (Exception e) {
            log.error("Exception on delete bookId={}, message={}", bookId, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }
    
    @GetMapping(GET_SUBJECTS_PATH)
    public ResponseEntity<List<BookSubject>> getBookSubject() {
        try {
            return ResponseEntity.ok().body(bookService.getAllBookSubjectsSorted());
        } catch (Exception e) {
            log.error("Exception on getBookSubject, message={}", e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }
    
    @GetMapping(GET_FORMATS_PATH)
    public ResponseEntity<List<Map<String, String>>> getEBookFormat() {
        try {
            return ResponseEntity.ok().body(bookService.getAllEBookFormats());
        } catch (Exception e) {
            log.error("Exception on getEBookFormat, message={}", e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }
    
    @GetMapping(GET_CONDITIONS_PATH)
    public ResponseEntity<List<Map<String, String>>> getEBookCondition() {
        try {
            return ResponseEntity.ok().body(bookService.getAllEBookConditions());
        } catch (Exception e) {
            log.error("Exception on getEBookCondition, message={}", e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }
    
    @GetMapping(GET_LANGUAGES_PATH)
    public ResponseEntity<List<Language>> getLanguages() {
        try {
            return ResponseEntity.ok().body(bookService.getAllBookLanguagesSorted());
        } catch (Exception e) {
            log.error("Exception on getEBookCondition, message={}", e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }
}
