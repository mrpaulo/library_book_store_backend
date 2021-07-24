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

import com.fasterxml.jackson.annotation.JsonGetter;
import com.google.common.collect.Lists;
import com.paulo.rodrigues.librarybookstore.dto.BookDTO;
import com.paulo.rodrigues.librarybookstore.dto.CompanyDTO;
import com.paulo.rodrigues.librarybookstore.dto.PersonDTO;
import com.paulo.rodrigues.librarybookstore.enums.EBookCondition;
import com.paulo.rodrigues.librarybookstore.enums.EBookFormat;
import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.filter.BookFilter;
import com.paulo.rodrigues.librarybookstore.model.Author;
import com.paulo.rodrigues.librarybookstore.model.Book;
import com.paulo.rodrigues.librarybookstore.model.BookSubject;
import com.paulo.rodrigues.librarybookstore.model.Language;
import com.paulo.rodrigues.librarybookstore.model.Publisher;
import com.paulo.rodrigues.librarybookstore.repository.BookRepository;
import com.paulo.rodrigues.librarybookstore.repository.BookSubjectRepository;
import com.paulo.rodrigues.librarybookstore.repository.LanguageRepository;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil;
import com.paulo.rodrigues.librarybookstore.utils.PagedResult;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author paulo.rodrigues
 */
@Service
@Transactional
public class BookService {

    private ModelMapper modelMapper;
    private final BookRepository bookRepository;
    private final PersonService personService;
    private final CompanyService companyService;
    private final LanguageRepository languageRepository;
    private final BookSubjectRepository bookSubjectRepository;

    public BookService(@Autowired BookRepository bookRepository,
            @Autowired PersonService personService,
            @Autowired CompanyService companyService,
            @Autowired LanguageRepository languageRepository,
            @Autowired BookSubjectRepository bookSubjectRepository,
            @Autowired ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.personService = personService;
        this.companyService = companyService;
        this.languageRepository = languageRepository;
        this.bookSubjectRepository = bookSubjectRepository;
        this.modelMapper = modelMapper;
    }

    public List<BookDTO> findAll() {
        List<Book> books = bookRepository.findAll();

        return toListDTO(books);
    }

    public PagedResult<BookDTO> findPageble(BookFilter filter) {
        return bookRepository.findPageble(filter);
    }

    public Book findById(Long bookId) throws LibraryStoreBooksException {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("BOOK_NOT_FOUND") + " ID: " + bookId);
        }
        book.setAuthors(new HashSet<>(personService.getListAuthorsByListDTO(bookRepository.getListAuthorsDTOByBookId(bookId))));

        return book;
    }

    public BookDTO create(BookDTO dto) throws LibraryStoreBooksException {
        Book book = fromDTO(dto);
        book = save(book);
        saveBookAuthor(book, dto);

        return toDTO(book);
    }

    public Book save(Book book) throws LibraryStoreBooksException {
        book.bookValidation();
        book.persistAt();

        return bookRepository.saveAndFlush(book);
    }

    public BookDTO edit(Long bookId, BookDTO bookDetail) throws LibraryStoreBooksException {
        Book bookToEdit = findById(bookId);

        bookToEdit = modelMapper.map(bookDetail, Book.class);
        bookToEdit.setAuthors(personService.saveBookAuthorFromListBooksDTO(bookToEdit, bookDetail.getAuthors()));
        bookToEdit.setSubject(getSubjectFromName(bookDetail.getSubjectName()));
        bookToEdit.setLanguage(getLanguageFromName(bookDetail.getLanguageName()));
        bookToEdit.setPublisher(getPublisher(bookDetail.getPublisher()));

        return toDTO(save(bookToEdit));
    }

    public void erase(Long bookId) throws LibraryStoreBooksException {
        Book bookToErase = findById(bookId);
        bookToErase.getAuthors().stream()
                .forEach(author -> {
                    bookRepository.deleteBookAuthor(author.getId(), bookToErase.getId());
                });
        bookRepository.delete(bookToErase);
    }

    public BookDTO toDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authors(getListAuthorsDTO(Lists.newArrayList(book.getAuthors())))
                .languageName(book.getLanguage() != null ? book.getLanguage().getName() : null)
                .publisher(getCompanyDTO(book.getPublisher()))
                .subjectName(book.getSubject() != null ? book.getSubject().getName() : null)
                .subtitle(book.getSubtitle())
                .review(book.getReview())
                .link(book.getLink())
                .format(book.getFormat())
                .condition(book.getCondition())
                .edition(book.getEdition())
                .publishDate(book.getPublishDate())
                .rating(book.getRating())
                .length(book.getLength())
                .build();
    }

    public Book fromDTO(BookDTO dto) throws LibraryStoreBooksException {
        return Book.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .authors(new HashSet<>(getListAuthors(dto.getAuthors())))
                .language(getLanguageFromName(dto.getLanguageName()))
                .publisher(getPublisher(dto.getPublisher()))
                .subject(getSubjectFromName(dto.getSubjectName()))
                .subtitle(dto.getSubtitle())
                .review(dto.getReview())
                .link(dto.getLink())
                .format(dto.getFormat())
                .condition(dto.getCondition())
                .edition(dto.getEdition())
                .publishDate(dto.getPublishDate())
                .rating(dto.getRating())
                .length(dto.getLength())
                .build();
    }

    public List<BookDTO> toListDTO(List<Book> books) {
        return books.stream().map(b -> toDTO(b)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<Book> fromListDTO(List<BookDTO> books) throws LibraryStoreBooksException {
        List<Book> result = new ArrayList<>();

        for (BookDTO book : books) {
            result.add(fromDTO(book));
        }
        return result;
    }

    private List<PersonDTO> getListAuthorsDTO(List<Author> authors) {
        return personService.getListAuthorsDTO(authors);
    }

    private List<Author> getListAuthors(List<PersonDTO> authors) throws LibraryStoreBooksException {
        return personService.getListAuthorsByListDTO(authors);
    }

    private CompanyDTO getCompanyDTO(Publisher publisher) {
        return companyService.getCompanyDTOFromPublisher(publisher);
    }

    private Publisher getPublisher(CompanyDTO publisher) throws LibraryStoreBooksException {
        return companyService.getPublisherFromDTO(publisher);
    }

    private void saveBookAuthor(Book book, BookDTO dto) throws LibraryStoreBooksException {
        List<PersonDTO> authors = dto.getAuthors();

        if (!FormatUtils.isEmpty(authors)) {
            for (PersonDTO author : authors) {
                personService.saveBookAuthor(book, author);
            }
        }
    }

    public List<PersonDTO> getListAuthorsByBookId(Long bookId) throws LibraryStoreBooksException {
        return bookRepository.getListAuthorsDTOByBookId(bookId);
    }

    private BookSubject getSubjectFromName(String name) {
        return bookSubjectRepository.findByName(name);
    }

    private Language getLanguageFromName(String name) {
        return languageRepository.findByName(name);
    }

    public List<BookSubject> getBookSubject() {
        return bookSubjectRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(BookSubject::getName))
                .collect(Collectors.toList());
    }

    public List<Language> getBookLanguage() {
        return languageRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Language::getName))
                .collect(Collectors.toList());
    }

    public List<Map<String, String>> getEBookFormat() {
        return Stream.of(EBookFormat.values()).map(temp -> {
            Map<String, String> obj = new HashMap<>();
            obj.put("value", temp.getName());
            obj.put("label", temp.getDescription());
            return obj;
        }).collect(Collectors.toList());
    }

    @JsonGetter
    public List<Map<String, String>> getEBookCondition() {
        return Stream.of(EBookCondition.values()).map(temp -> {
            Map<String, String> obj = new HashMap<>();
            obj.put("value", temp.getName());
            obj.put("label", temp.getDescription());
            return obj;
        }).collect(Collectors.toList());
    }

    List<Book> getBooksFromAuthor(String authorName) {
        return bookRepository.getBooksFromAuthor(authorName);
    }
}
