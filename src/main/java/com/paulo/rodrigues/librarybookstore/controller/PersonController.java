/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.controller;

import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.model.Person;
import com.paulo.rodrigues.librarybookstore.service.PersonService;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
 * @author paulo
 */
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1/people")
public class PersonController {

    @Autowired
    private PersonService personService;
   
    @GetMapping()
    public List<Person> getTodasPersons() {
        List <Person> peopleList = personService.findAll();
        
        return peopleList.stream().sorted(Comparator.comparing(Person::getName)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonPorId(@PathVariable(value = "id") Long personId) throws LibraryStoreBooksException {
        Person person = personService.findById(personId);
        
        return ResponseEntity.ok().body(person);
    }

    @PostMapping()
    public Person criarPerson(@RequestBody Person person) throws LibraryStoreBooksException {
        Person savePerson = personService.save(person);
        return personService.findById(savePerson.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable(value = "id") Long personId, @RequestBody Person personDetalhes) throws LibraryStoreBooksException {

        final Person updatePerson = personService.edit(personId, personDetalhes);
        return ResponseEntity.ok(updatePerson);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deletePerson(@PathVariable(value = "id") Long personId) throws LibraryStoreBooksException {                
        personService.excluir(personId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    
}
