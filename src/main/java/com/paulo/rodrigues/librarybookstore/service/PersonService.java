/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.service;

import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.filter.BookFilter;
import com.paulo.rodrigues.librarybookstore.filter.PersonFilter;
import com.paulo.rodrigues.librarybookstore.model.Person;
import com.paulo.rodrigues.librarybookstore.repository.PersonRepository;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import java.util.Date;
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
public class PersonService {

    @Autowired
    private PersonRepository personRepository;
    
    private ModelMapper modelMapper;

    public List<Person> findAll() {
        return personRepository.findAll();
    }
    
    public Page<Person> findPageble(PersonFilter filter, Pageable pageable) {
        return personRepository.findPageble(
                filter.getId(),                
                filter.getName(),
                filter.getCpf(),
                filter.getSex(),
                filter.getStartDate(),
                filter.getFinalDate(),
                pageable);
    }

    public Person findById(Long personId) throws LibraryStoreBooksException {
        Person person = personRepository.findById(personId).orElse(null);
        
        if(person == null) {
            throw new LibraryStoreBooksException("Pessoa não encontrada para o id: " + personId);
        }

        return person;
    }

    public Person findByCPF(String cpf) {

        return personRepository.findByCpf(cpf);
    }

    public Person save(Person person) throws LibraryStoreBooksException {
        person.personValidation();
        person.persistAt();

        return personRepository.saveAndFlush(person);
    }

    public Person edit(Long personId, Person personDetalhes) throws LibraryStoreBooksException {

        Person personToEdit = findById(personId);
//        personToEdit.setName(personDetalhes.getName());
//        personToEdit.setSex(personDetalhes.getSex());
//        personToEdit.setEmail(personDetalhes.getEmail());
//        personToEdit.setBirthdate(personDetalhes.getBirthdate());
//        personToEdit.setBirthplace(personDetalhes.getBirthplace());
//        personToEdit.setNationality(personDetalhes.getNationality());
//        personToEdit.setCpf(personDetalhes.getCpf());

        personToEdit = modelMapper.map(personDetalhes, Person.class);
        
        return save(personToEdit);
    }

    public void erase(Long personId) throws LibraryStoreBooksException {
        Person person = findById(personId);

        personRepository.delete(person);
    }    

}
