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
package com.paulo.rodrigues.librarybookstore.author;

import com.paulo.rodrigues.librarybookstore.utils.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.author.Author;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
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
@RequestMapping("/api/v1/people")
public class AuthorController {

    @Autowired
    private AuthorService personService;

    @GetMapping("/all")
    public List<AuthorDTO> getAll() {
        List<AuthorDTO> peopleList = personService.findAll();

        return peopleList.stream().sorted(Comparator.comparing(AuthorDTO::getName)).collect(Collectors.toList());
    }

    @GetMapping()
    public List<AuthorDTO> getAllPageble(@RequestBody AuthorFilter filter, HttpServletRequest req, HttpServletResponse res) {
        Pageable pageable = FormatUtils.getPageRequest(filter);
        Page<Author> result = personService.findPageble(filter, pageable);
        res.addHeader("Total-Count", String.valueOf(result.getTotalElements()));

        return personService.toListDTO(result.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getById(@PathVariable(value = "id") Long personId) throws LibraryStoreBooksException {
        return ResponseEntity.ok().body(personService.findById(personId));
    }
    
    @GetMapping("/fetch/{name}")
    public ResponseEntity<List<AuthorDTO>> getByName(@PathVariable(value = "name") String name) throws LibraryStoreBooksException {
        return ResponseEntity.ok().body(personService.findByName(name));
    }

    @PostMapping()
    public AuthorDTO create(@RequestBody Author person) throws LibraryStoreBooksException {
        return personService.create(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> update(@PathVariable(value = "id") Long personId, @RequestBody AuthorDTO personDetalhes) throws LibraryStoreBooksException {
        return ResponseEntity.ok(personService.edit(personId, personDetalhes));
    }

    @DeleteMapping("/{id}")
    public Map<String, Long> delete(@PathVariable(value = "id") Long personId) throws LibraryStoreBooksException {
        personService.erase(personId);
        Map<String, Long> response = new HashMap<>();
        response.put("id", personId);

        return response;
    }

}
