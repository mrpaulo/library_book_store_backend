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

import com.paulo.rodrigues.librarybookstore.address.AddressService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.paulo.rodrigues.librarybookstore.book.BookDTO;
import com.paulo.rodrigues.librarybookstore.author.PersonDTO;
import com.paulo.rodrigues.librarybookstore.utils.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.author.PersonFilter;
import com.paulo.rodrigues.librarybookstore.author.Author;
import com.paulo.rodrigues.librarybookstore.book.Book;
import com.paulo.rodrigues.librarybookstore.author.Person;
import com.paulo.rodrigues.librarybookstore.book.BookRepository;
import com.paulo.rodrigues.librarybookstore.address.CityRepository;
import com.paulo.rodrigues.librarybookstore.address.CountryRepository;
import com.paulo.rodrigues.librarybookstore.author.PersonRepository;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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
    
    @Autowired 
    private BookRepository bookRepository;

    public List<PersonDTO> findAll() {
        return toListDTO(personRepository.findAll());
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

    public PersonDTO create(Person person) throws LibraryStoreBooksException {
        if(person != null && person.getAddress() != null){
            addressService.create(person.getAddress());
        }

        return toDTO(save(person));
    }

    public Person save(Person person) throws LibraryStoreBooksException {
        person.personValidation();
        person.persistAt();

        return personRepository.saveAndFlush(person);
    }

    public PersonDTO edit(Long personId, PersonDTO personDetalhes) throws LibraryStoreBooksException {
        Person personToEdit = findById(personId);
       // modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(PersonDTO.class, Person.class);
        modelMapper.validate();
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
                .birthCity(person.getBirthCity() != null ? person.getBirthCity().toDTO() : null)
                .birthCountry(person.getBirthCountry() != null ? person.getBirthCountry().toDTO() : null)
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
                .birthCity(dto.getBirthCity() != null ? cityRepository.getById(dto.getBirthCity().getId()) : null)
                .birthCountry(dto.getBirthCountry() != null ? countryRepository.getById(dto.getBirthCountry().getId()) : null)
                .build();
    }

    

    public List<PersonDTO> toListDTO(List<Person> people) {
        return people.stream().map(b -> toDTO(b)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<PersonDTO> getListAuthorsDTO(List<Author> authors) {
        return authors.stream().map(b -> toDTO(b)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<Author> getListAuthorsByListDTO(List<PersonDTO> listDTOs) throws LibraryStoreBooksException {
        List<Author> result = new ArrayList<>();

        if (!FormatUtils.isEmpty(listDTOs)) {
            for (PersonDTO dto : listDTOs) {
                result.add(authorFromDTO(dto));
            }
        }

        return result;
    }

    public Person saveBookAuthor(Book book, PersonDTO dto) throws LibraryStoreBooksException {
        Person author = authorFromDTO(dto);
        
        Set<Book> booksFromAuthor = Sets.newHashSet(bookRepository.getBooksFromAuthor(author.getName()));
        booksFromAuthor.add(book);
        
        author.setBooks(Lists.newArrayList(booksFromAuthor));
        authorRepository.saveAndFlush(author);
        return author;
    }
    
    public Set<Person> saveBookAuthorFromListBooksDTO(Book book, List<PersonDTO> authorsDTO) throws LibraryStoreBooksException {
        Set<Person> authors = new HashSet<>();
        for(PersonDTO author: authorsDTO){
            authors.add(saveBookAuthor(book, author));
        }
        return authors;
    }

}
