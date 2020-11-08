/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.service;

import com.paulo.rodrigues.librarybookstore.dto.AddressDTO;
import com.paulo.rodrigues.librarybookstore.dto.PersonDTO;
import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.filter.PersonFilter;
import com.paulo.rodrigues.librarybookstore.model.Address;
import com.paulo.rodrigues.librarybookstore.model.Person;
import com.paulo.rodrigues.librarybookstore.repository.AddressRepository;
import com.paulo.rodrigues.librarybookstore.repository.CityRepository;
import com.paulo.rodrigues.librarybookstore.repository.CountryRepository;
import com.paulo.rodrigues.librarybookstore.repository.PersonRepository;
import java.util.ArrayList;
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

    private ModelMapper modelMapper;
    
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressService addressService;
    
    @Autowired
    private CityRepository cityRepository;
    
    @Autowired
    private CountryRepository countryRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Page<Person> findPageble(PersonFilter filter, Pageable pageable) {
        return personRepository.findPageble(
                filter.getId(),
                filter.getName(),
                filter.getCpf(),
                filter.getSex(),                
                pageable);
    }

    public Person findById(Long personId) throws LibraryStoreBooksException {
        Person person = personRepository.findById(personId).orElse(null);

        if (person == null) {
            throw new LibraryStoreBooksException("Pessoa não encontrada para o id: " + personId);
        }

        return person;
    }

    public Person findByCPF(String cpf) {
        return personRepository.findByCpf(cpf);
    }

    public PersonDTO create(PersonDTO dto) throws LibraryStoreBooksException {
        Person person = fromDTO(dto);

        return toDTO(save(person));
    }

    public Person save(Person person) throws LibraryStoreBooksException {
        person.personValidation();
        person.persistAt();

        return personRepository.saveAndFlush(person);
    }

    public PersonDTO edit(Long personId, PersonDTO personDetalhes) throws LibraryStoreBooksException {
        Person personToEdit = findById(personId);
        personToEdit = modelMapper.map(personDetalhes, Person.class);

        return toDTO(save(personToEdit));
    }

    public void erase(Long personId) throws LibraryStoreBooksException {
        Person person = findById(personId);

        personRepository.delete(person);
    }

    public PersonDTO toDTO(Person person) {
        if(person == null){
            return null;
        }
        
        return PersonDTO.builder()
                .id(person.getId())
                .name(person.getName())
                .cpf(person.getCpf())
                .sex(person.getSex())
                .email(person.getEmail())
                .adress(addressService.toDTO(person.getAdress()))
                .birthdate(person.getBirthdate())
                .birthCity(person.getBirthCity() != null ? person.getBirthCity().getName() : null)
                .birthCountry(person.getBirthCountry() != null ? person.getBirthCountry().getName() : null )
                .build();
        
    }

    public Person fromDTO(PersonDTO dto) throws LibraryStoreBooksException {
        if(dto == null){
            return null;
        }
        
        return Person.builder()
                .id(dto.getId())
                .name(dto.getName())
                .cpf(dto.getCpf())
                .sex(dto.getSex())
                .email(dto.getEmail())
                .adress(dto.getAdress() != null ? addressService.findById(dto.getAdress().getId()) : null)
                .birthdate(dto.getBirthdate())
                .birthCity(cityRepository.getByName(dto.getBirthCity()))
                .birthCountry(countryRepository.getByName(dto.getBirthCountry()))
                .build();
    }

    public List<PersonDTO> toListDTO(List<Person> people) {
        return people.stream().map(b -> toDTO(b)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    private AddressDTO getAddressDTOFromModel (Address address) {
        if(address == null){
            return null;
        }
        
        return AddressDTO.builder()
                .id(address.getId())
                .fmtAddress(address.formatAddress())
                .build();
    }

}
