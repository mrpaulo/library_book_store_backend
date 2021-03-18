/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.service;

import com.paulo.rodrigues.librarybookstore.dto.BookDTO;
import com.paulo.rodrigues.librarybookstore.dto.CompanyDTO;
import com.paulo.rodrigues.librarybookstore.dto.PersonDTO;
import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.filter.BookFilter;
import com.paulo.rodrigues.librarybookstore.model.Author;
import com.paulo.rodrigues.librarybookstore.model.Book;
import com.paulo.rodrigues.librarybookstore.model.Publisher;
import com.paulo.rodrigues.librarybookstore.repository.BookRepository;
import com.paulo.rodrigues.librarybookstore.repository.BookSubjectRepository;
import com.paulo.rodrigues.librarybookstore.repository.LanguageRepository;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil;
import com.paulo.rodrigues.librarybookstore.utils.PagedResult;
import java.util.ArrayList;
import java.util.List;
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
            @Autowired BookSubjectRepository bookSubjectRepository) {
        this.bookRepository = bookRepository;
        this.personService = personService;
        this.companyService = companyService;
        this.languageRepository = languageRepository;
        this.bookSubjectRepository = bookSubjectRepository;
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

        return toDTO(save(bookToEdit));
    }

    public void erase(Long bookId) throws LibraryStoreBooksException {
        Book bookToErase = findById(bookId);
        bookRepository.delete(bookToErase);
    }

    public BookDTO toDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authors(getListAuthorsDTO(book.getAuthors()))
                .language(book.getLanguage() != null ? book.getLanguage().getName() : null)
                .publisher(getCompanyDTO(book.getPublisher()))
                .subject(book.getSubject() != null ? book.getSubject().getName() : null)
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
                .authors(getListAuthors(dto.getAuthors()))
                .language(languageRepository.findByName(dto.getLanguage()))
                .publisher(getPublisher(dto.getPublisher()))
                .subject(bookSubjectRepository.findByName(dto.getSubject()))
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

    private List<PersonDTO> getListAuthorsDTO(List<Author> authors) {
        return personService.getListAuthorsDTO(authors);
    }

    private List<Author> getListAuthors(List<PersonDTO> authors) throws LibraryStoreBooksException {
        return personService.getListAuthorsbyListDTO(authors);
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
        return bookRepository.getListAuthorsByBookId(bookId);
    }
}
