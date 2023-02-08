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
package com.paulo.rodrigues.librarybookstore.author.controller;

import com.paulo.rodrigues.librarybookstore.author.dto.AuthorDTO;
import com.paulo.rodrigues.librarybookstore.author.service.AuthorService;
import com.paulo.rodrigues.librarybookstore.utils.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.author.model.Author;
import com.paulo.rodrigues.librarybookstore.author.filter.AuthorFilter;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paulo.rodrigues.librarybookstore.utils.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

/**
 *
 * @author paulo.rodrigues
 */
@Log4j2
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/all")
    public ResponseEntity<List<AuthorDTO>> getAll() {
        try {
            List<AuthorDTO> authors = authorService.findAll();
            List<AuthorDTO> authorsSorted = authors.stream().sorted(Comparator.comparing(AuthorDTO::getName)).collect(Collectors.toList());
            return ResponseEntity.ok().body(authorsSorted);
        } catch (Exception e) {
            log.error("Exception on getAll message={}", e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @PostMapping("/fetch")
    public ResponseEntity<List<AuthorDTO>> findPageable(@RequestBody AuthorFilter filter, HttpServletRequest req, HttpServletResponse res) {
        try {
            Pageable pageable = FormatUtils.getPageRequest(filter);
            Page<Author> authors = authorService.findPageable(filter, pageable);
            res.addHeader("totalCount", String.valueOf(authors.getTotalElements()));
            List<AuthorDTO> authorsSorted = authorService.authorsToDTOs(authors.getContent()).stream().sorted(Comparator.comparing(AuthorDTO::getName)).collect(Collectors.toList());
            return ResponseEntity.ok().body(authorsSorted);
        } catch (Exception e) {
            log.error("Exception on findPageable message={}", e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getById(@PathVariable(value = "id") Long authorId) throws LibraryStoreBooksException, NotFoundException {
        try {
            return ResponseEntity.ok().body(authorService.findById(authorId));
        } catch (Exception e) {
            log.error("Exception on getById authorId={}, message={}", authorId, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }
    
    @GetMapping("/fetch/{name}")
    public ResponseEntity<List<AuthorDTO>> getByName(@PathVariable(value = "name") String authorName) throws LibraryStoreBooksException {
        try {
            return ResponseEntity.ok().body(authorService.findByName(authorName));
        } catch (Exception e) {
            log.error("Exception on getByName authorName={}, message={}", authorName, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @PostMapping()
    public ResponseEntity<AuthorDTO> create(@RequestBody Author author) throws LibraryStoreBooksException {
        try {
            return new ResponseEntity<>(authorService.create(author), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Exception on create author={}, message={}", author, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> update(@PathVariable(value = "id") Long authorId, @RequestBody AuthorDTO authorDTO) throws LibraryStoreBooksException, NotFoundException {
        try {
            return ResponseEntity.ok().body(authorService.edit(authorId, authorDTO));
        } catch (Exception e) {
            log.error("Exception on getByName authorId={}, message={}", authorId, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public Map<String, Long> delete(@PathVariable(value = "id") Long authorId) throws LibraryStoreBooksException, NotFoundException {
        try {
            authorService.delete(authorId);
            Map<String, Long> response = new HashMap<>();
            response.put("id", authorId);
            return response;
        } catch (Exception e) {
            log.error("Exception on getByName authorId={}, message={}", authorId, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }
}
