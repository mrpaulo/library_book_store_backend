/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.service;

import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.model.Person;
import com.paulo.rodrigues.librarybookstore.repository.PersonRepository;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

    public List<Person> findAll() {
        return personRepository.findAll();
}

    public Person findById(Long personId) throws LibraryStoreBooksException {
        Person person = personRepository.findById(personId).get();
        checkPersonIsNull(personId, person);

        return person;
}

    public Person findByCPF(String cpf) {

        return personRepository.findByCpf(cpf);
    }

    public Person save(Person person) throws LibraryStoreBooksException {
        person.personValidation();
        person.setCreateAt(new Date());

        return personRepository.saveAndFlush(person);
    }

    public Person edit(Long personId, Person personDetalhes) throws LibraryStoreBooksException {
        personDetalhes.personValidation();

        Person person = findById(personId);        
        person.setName(personDetalhes.getName());
        person.setSex(personDetalhes.getSex());
        person.setEmail(personDetalhes.getEmail());
        person.setBirthdate(personDetalhes.getBirthdate());
        person.setBirthplace(personDetalhes.getBirthplace());
        person.setNationality(personDetalhes.getNationality());
        person.setCpf(personDetalhes.getCpf());
        person.setUpdateAt(new Date());
        return personRepository.saveAndFlush(person);
    }

    public void excluir(Long personId) throws LibraryStoreBooksException {
        Person person = findById(personId);        

        personRepository.delete(person);
    }

    public void checkPersonIsNull(Long personId, Person person) throws LibraryStoreBooksException {
        if (person == null) {
            throw new LibraryStoreBooksException("Pessoa não encontrada para o id: " + personId);
        }
    }

}
