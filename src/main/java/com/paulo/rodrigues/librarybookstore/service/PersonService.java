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
package com.paulo.rodrigues.librarybookstore.service;

import com.paulo.rodrigues.librarybookstore.dto.PersonDTO;
import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.filter.PersonFilter;
import com.paulo.rodrigues.librarybookstore.model.Author;
import com.paulo.rodrigues.librarybookstore.model.Book;
import com.paulo.rodrigues.librarybookstore.model.Person;
import com.paulo.rodrigues.librarybookstore.repository.AuthorRepository;
import com.paulo.rodrigues.librarybookstore.repository.CityRepository;
import com.paulo.rodrigues.librarybookstore.repository.CountryRepository;
import com.paulo.rodrigues.librarybookstore.repository.PersonRepository;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil;
import java.util.ArrayList;
import java.util.Arrays;
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
    private AuthorRepository authorRepository;

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
                filter.getStartDate(),
                filter.getFinalDate(),
                pageable);
    }

    public Person findById(Long personId) throws LibraryStoreBooksException {
        Person person = personRepository.findById(personId).orElse(null);

        if (person == null) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("PERSON_NOT_FOUND") + " ID: " + personId);
        }

        return person;
    }
    
    public List<PersonDTO> findByName (String name) throws LibraryStoreBooksException {
        return toListDTO(personRepository.findByName(name));
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
        if (person == null) {
            return null;
        }

        return PersonDTO.builder()
                .id(person.getId())
                .name(person.getName())
                .cpf(person.getCpf())
                .sex(person.getSex())
                .email(person.getEmail())
                .address(addressService.toDTO(person.getAddress()))
                .birthdate(person.getBirthdate())
                .birthCity(person.getBirthCity() != null ? person.getBirthCity().getName() : null)
                .birthCountry(person.getBirthCountry() != null ? person.getBirthCountry().getName() : null)
                .build();

    }

    public Person fromDTO(PersonDTO dto) throws LibraryStoreBooksException {
        if (dto == null) {
            return null;
        }

        return Person.builder()
                .id(dto.getId())
                .name(dto.getName())
                .cpf(dto.getCpf())
                .sex(dto.getSex())
                .email(dto.getEmail())
                .address(dto.getAddress() != null ? addressService.findById(dto.getAddress().getId()) : null)
                .birthdate(dto.getBirthdate())
                .birthCity(cityRepository.getByName(dto.getBirthCity()))
                .birthCountry(countryRepository.getByName(dto.getBirthCountry()))
                .build();
    }

    public Author authorFromDTO(PersonDTO dto) throws LibraryStoreBooksException {
        if (dto == null) {
            return null;
        }
        
        Author author = authorRepository.findById(dto.getId()).orElse(null);        
        if (author == null) {
            return null;
        }
        Person person = findById(dto.getId());
        
        Author result = new Author();
        
        result.setDescription(author.getDescription());
        result.setId(dto.getId());
        result.setName(person.getName());
        result.setCpf(person.getCpf());
        result.setSex(person.getSex());
        result.setEmail(person.getEmail());
        result.setAddress(person.getAddress() != null ? addressService.findById(person.getAddress().getId()) : null);
        result.setBirthCity(person.getBirthCity() != null ? cityRepository.getByName(person.getBirthCity().getName()) : null);
        result.setBirthdate(person.getBirthdate());
        result.setBirthCountry(person.getBirthCountry() != null ? countryRepository.getByName(person.getBirthCountry().getName()) : null);
        

        return result;
    }

    public List<PersonDTO> toListDTO(List<Person> people) {
        return people.stream().map(b -> toDTO(b)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<PersonDTO> getListAuthorsDTO(List<Author> authors) {
        return authors.stream().map(b -> toDTO(b)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<Author> getListAuthorsbyListDTO(List<PersonDTO> listDTOs) throws LibraryStoreBooksException {
        List<Author> result = new ArrayList<>();

        if (!FormatUtils.isEmpty(listDTOs)) {
            for (PersonDTO dto : listDTOs) {
                result.add(authorFromDTO(dto));
            }
        }

        return result;
    }

    void saveBookAuthor(Book book, PersonDTO dto) throws LibraryStoreBooksException {
        Author author = authorFromDTO(dto);
        
        author.setBooks(Arrays.asList(book));
        authorRepository.saveAndFlush(author);
    }
    
    

}
