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
package com.paulo.rodrigues.librarybookstore.controller;

import com.paulo.rodrigues.librarybookstore.dto.PersonDTO;
import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.filter.PersonFilter;
import com.paulo.rodrigues.librarybookstore.model.Person;
import com.paulo.rodrigues.librarybookstore.service.PersonService;
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
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/all")
    public List<Person> getAll() {
        List<Person> peopleList = personService.findAll();

        return peopleList.stream().sorted(Comparator.comparing(Person::getName)).collect(Collectors.toList());
    }

    @GetMapping()
    public List<PersonDTO> getAllPageble(@RequestBody PersonFilter filter, HttpServletRequest req, HttpServletResponse res) {
        Pageable pageable = FormatUtils.getPageRequest(filter);
        Page<Person> result = personService.findPageble(filter, pageable);
        res.addHeader("Total-Count", String.valueOf(result.getTotalElements()));

        return personService.toListDTO(result.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getById(@PathVariable(value = "id") Long personId) throws LibraryStoreBooksException {
        return ResponseEntity.ok().body(personService.findById(personId));
    }
    
    @GetMapping("/fetch/{name}")
    public ResponseEntity<List<PersonDTO>> getByName(@PathVariable(value = "name") String name) throws LibraryStoreBooksException {
        return ResponseEntity.ok().body(personService.findByName(name));
    }

    @PostMapping()
    public PersonDTO create(@RequestBody PersonDTO person) throws LibraryStoreBooksException {
        return personService.create(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> update(@PathVariable(value = "id") Long personId, @RequestBody PersonDTO personDetalhes) throws LibraryStoreBooksException {
        return ResponseEntity.ok(personService.edit(personId, personDetalhes));
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> delete(@PathVariable(value = "id") Long personId) throws LibraryStoreBooksException {
        personService.erase(personId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }

}
