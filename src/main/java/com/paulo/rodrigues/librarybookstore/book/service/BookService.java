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
package com.paulo.rodrigues.librarybookstore.book.service;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.google.common.collect.Lists;
import com.paulo.rodrigues.librarybookstore.address.model.Address;
import com.paulo.rodrigues.librarybookstore.book.dto.BookDTO;
import com.paulo.rodrigues.librarybookstore.publisher.dto.PublisherDTO;
import com.paulo.rodrigues.librarybookstore.author.dto.AuthorDTO;
import com.paulo.rodrigues.librarybookstore.utils.*;
import com.paulo.rodrigues.librarybookstore.book.filter.BookFilter;
import com.paulo.rodrigues.librarybookstore.author.model.Author;
import com.paulo.rodrigues.librarybookstore.book.model.Book;
import com.paulo.rodrigues.librarybookstore.book.repository.BookRepository;
import com.paulo.rodrigues.librarybookstore.publisher.service.PublisherService;
import com.paulo.rodrigues.librarybookstore.author.service.AuthorService;
import com.paulo.rodrigues.librarybookstore.book.model.BookSubject;
import com.paulo.rodrigues.librarybookstore.book.repository.BookSubjectRepository;
import com.paulo.rodrigues.librarybookstore.book.enums.EBookCondition;
import com.paulo.rodrigues.librarybookstore.book.enums.EBookFormat;
import com.paulo.rodrigues.librarybookstore.book.model.Language;
import com.paulo.rodrigues.librarybookstore.book.repository.LanguageRepository;
import com.paulo.rodrigues.librarybookstore.publisher.model.Publisher;

import java.util.*;
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
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final LanguageRepository languageRepository;
    private final BookSubjectRepository bookSubjectRepository;

    public BookService(@Autowired BookRepository bookRepository,
            @Autowired AuthorService personService,
            @Autowired PublisherService companyService,
            @Autowired LanguageRepository languageRepository,
            @Autowired BookSubjectRepository bookSubjectRepository,
            @Autowired ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.authorService = personService;
        this.publisherService = companyService;
        this.languageRepository = languageRepository;
        this.bookSubjectRepository = bookSubjectRepository;
        this.modelMapper = modelMapper;
    }

    public List<BookDTO> findAll() {
        List<Book> books = bookRepository.findAll();

        return toListDTO(books);
    }

    public PagedResult<BookDTO> findPageable(BookFilter filter) {
        return bookRepository.findPageable(filter);
    }

    public Book findById(Long bookId) throws NotFoundException, LibraryStoreBooksException {

        Optional<Book> book = bookRepository.findById(bookId);
        if (book == null || !book.isPresent()) {
            throw new NotFoundException(MessageUtil.getMessage("BOOK_NOT_FOUND") + " ID: " + bookId);
        }


        book.get().setAuthors(new HashSet<>(authorService.getListAuthorsByListDTO(bookRepository.getListAuthorsDTOByBookId(bookId))));

        return book.get();
    }

    public BookDTO create(BookDTO dto) throws LibraryStoreBooksException, NotFoundException {
        Book book = fromDTO(dto);
        book = save(book);
        saveBookAuthor(book);

        return toDTO(book);
    }

    public Book save(Book book) throws LibraryStoreBooksException {
        book.validation();
        book.persistAt();
        book = checkAndSaveReference(book);

        return bookRepository.saveAndFlush(book);
    }

    public Book checkAndSaveReference(Book book) throws LibraryStoreBooksException {

        Set<Author> authors = authorService.saveAuthors(book.getAuthors());
        if(!FormatUtils.isEmpty(authors)){
            book.setAuthors(authors);
        }

        Publisher publisher = publisherService.checkAndSave(book.getPublisher());
        if(publisher != null){
            book.setPublisher(publisher);
        }

        return book;
    }

    public BookDTO edit(Long bookId, BookDTO bookDetail) throws NotFoundException, LibraryStoreBooksException {
        Book bookToEdit = findById(bookId);
        String createBy = bookToEdit.getCreateBy();
        
        bookToEdit = modelMapper.map(bookDetail, Book.class);
        bookToEdit.setAuthors(authorService.saveBookAuthorFromListBooksDTO(bookToEdit, bookDetail.getAuthors()));
        bookToEdit.setSubject(getSubjectFromName(bookDetail.getSubjectName()));
        bookToEdit.setLanguage(getLanguageFromName(bookDetail.getLanguageName()));
        bookToEdit.setPublisher(publisherService.fromDTO(bookDetail.getPublisher()));
        bookToEdit.setCreateBy(createBy);
        bookToEdit.setId(bookId);
        
        return toDTO(save(bookToEdit));
    }

    public void delete(Long bookId) throws LibraryStoreBooksException, NotFoundException {
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
                .publisher(getPublisherDTO(book.getPublisher()))
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
                .publisher(publisherService.fromDTO(dto.getPublisher()))
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

    private List<AuthorDTO> getListAuthorsDTO(List<Author> authors) {
        return authorService.getListAuthorsDTO(authors);
    }

    private List<Author> getListAuthors(List<AuthorDTO> authors) throws LibraryStoreBooksException {
        return authorService.getListAuthorsByListDTO(authors);
    }

    private PublisherDTO getPublisherDTO(Publisher publisher) {
        return publisherService.toDTO(publisher);
    }


    private void saveBookAuthor(Book book) {
        if (!FormatUtils.isEmpty(book.getAuthors())) {
            for (Author author : book.getAuthors()) {
                authorService.saveBookAuthor(book, author);
            }
        }
    }

    public List<AuthorDTO> getListAuthorsByBookId(Long bookId) throws LibraryStoreBooksException {
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

    List<Book> getBooksFromAuthorName(String authorName) {
        return bookRepository.getBooksFromAuthorName(authorName);
    }
    List<Book> getBooksFromPublisher(String authorName) {
        return bookRepository.getBooksFromAuthorName(authorName);
    }
}
