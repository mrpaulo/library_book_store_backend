/*
 * Copyright (C) 2022 paulo.rodrigues
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

/**
 *
 * @author paulo.rodrigues
 */

package com.paulo.rodrigues.librarybookstore.test.book


import com.paulo.rodrigues.librarybookstore.author.service.AuthorService
import com.paulo.rodrigues.librarybookstore.book.enums.EBookCondition
import com.paulo.rodrigues.librarybookstore.book.enums.EBookFormat
import com.paulo.rodrigues.librarybookstore.book.model.Book
import com.paulo.rodrigues.librarybookstore.book.repository.BookRepository
import com.paulo.rodrigues.librarybookstore.book.repository.BookSubjectRepository
import com.paulo.rodrigues.librarybookstore.book.repository.LanguageRepository
import com.paulo.rodrigues.librarybookstore.book.service.BookService
import com.paulo.rodrigues.librarybookstore.publisher.service.PublisherService
import com.paulo.rodrigues.librarybookstore.utils.*
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

import static com.paulo.rodrigues.librarybookstore.test.ObjectMother.*

class BookServiceTest extends Specification {

    def bookRepository = Mock(BookRepository)
    def authorService = Mock(AuthorService)
    def publisherService = Mock(PublisherService)
    def languageRepository = Mock(LanguageRepository)
    def bookSubjectRepository = Mock(BookSubjectRepository)
    def modelMapper = Mock(ModelMapper)

    BookService service = new BookService(bookRepository,
            authorService,
            publisherService,
            languageRepository,
            bookSubjectRepository,
            modelMapper
    )

    def "Book - findAll - happy path"() {
        given: "a list of books"
        def books = buildBooks()

        when: "the service method findAll() is called"
        def response = service.findAll()

        then: "the repository method findAll() is called once and returns a list of books"
        1 * bookRepository.findAll() >> books

        and: "the response has same as expected"
        response.size() == books.size()
    }

    def "Book - findPageable - happy path"() {
        given: "a list of books and a book filter"
        def booksPage = buildBooksPage()
        def filter = buildBookFilter()

        when: "the service method findPageable(filter) is called"
        def response = service.findPageable(filter)

        then: "the repository method findPageable() is called once with the filter parameter and returns a Page object containing the list of books"
        1 * bookRepository.findPageable(*_) >> booksPage

        and: "the response is not null"
        response != null
    }

    def "Book - findById - happy path"() {
        given: "a book object, an id, and a method stub for the author service"
        def book = buildBook()
        def id = 1
        authorService.authorsFromDTOs(_) >> buildAuthors()

        when: "the service method findById(id) is called"
        def response = service.findById(id)

        then: "the repository method findById(id) is called once with the id parameter and returns an Optional containing the book object"
        1 * bookRepository.findById(id) >> Optional.of(book)

        and: "the response is equal to the book object"
        response == book
    }

    def "Book - findById - should throw an exception"() {
        given: "an id"
        def id = 1

        when: "the service method findById(id) is called"
        service.findById(id)

        then: "a NotFoundException is thrown"
        def e = thrown(NotFoundException)

        and: "the exception message is not null and is equal to the expected message"
        e.getMessage() != null
        e.getMessage() == MessageUtil.getMessage("BOOK_NOT_FOUND") + " ID: " + id
    }

    def "Book - create - happy path"() {
        given: "a bookDTO object, a book object, and a method stub for the author service"
        def bookDTO = buildBookDTO()
        def book = buildBook()
        authorService.authorsFromDTOs(_) >> buildAuthors()

        when: "the service method create(bookDTO) is called"
        def response = service.create(bookDTO)

        then: "the repository method saveAndFlush() is called once with the book object as parameter"
        1 * bookRepository.saveAndFlush(_) >> book

        and: "the response is not null and has a title equal to the title of the bookDTO object"
        response != null
        response.getTitle() == bookDTO.getTitle()
    }

    def "Book - save - happy path"() {
        given: "a book"
        def book = buildBook()

        when: "we call the save method"
        service.save(book)

        then: "the correct method is called"
        1 * bookRepository.saveAndFlush(book) >>
                {
                    arguments -> def createAt = arguments.get(0).getCreateAt()
                        assert createAt != null
                } >> book
    }

    def "Book - save validation - should throw an exception when name #scenario"() {
        given: "a book with a title that is null or too long"
        def book = buildBook(title: value)

        when: "we call the save method"
        service.save(book)

        then: "a InvalidRequestException is thrown with the correct message"
        def e = thrown(InvalidRequestException)
        e.getMessage() == message

        where:
        scenario                    | value                                                | message
        'is null'                   | null                                                 | MessageUtil.getMessage("BOOK_TITLE_NOT_INFORMED")
        'is longer than max size'   | buildRandomString(ConstantsUtil.MAX_SIZE_NAME + 1)   | MessageUtil.getMessage("BOOK_TITLE_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + "")
    }

    def "Book - save validation - should throw an exception when #scenario"() {
        when: "we call the save method with a custom book object created for different scenarios"
        service.save(book)

        then: "a InvalidRequestException is thrown with the correct message"
        def e = thrown(InvalidRequestException)
        e.getMessage() == message

        where:
        scenario                            | book                                                                       | message
        'subtitle is bigger than max size'  | buildBook(subtitle: buildRandomString(ConstantsUtil.MAX_SIZE_NAME + 1))    | MessageUtil.getMessage("BOOK_SUBTITLE_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + "")
        'link is bigger than max size'      | buildBook(link: buildRandomString(ConstantsUtil.MAX_SIZE_SHORT_TEXT + 1))  | MessageUtil.getMessage("BOOK_LINK_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_SHORT_TEXT + "")
        'review is bigger than max size'    | buildBook(review: buildRandomString(ConstantsUtil.MAX_SIZE_LONG_TEXT + 1)) | MessageUtil.getMessage("BOOK_REVIEW_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_LONG_TEXT + "")
    }

    def "Book - checkAndSaveReference - happy path"() {
        given: "a book"
        def book = buildBook()

        when: "we call the checkAndSaveReference method"
        def response = service.checkAndSaveReference(book)

        then: "the author description from the response must be the same as the author description from book"
        response.getAuthors().getAt(0).description == book.authors.getAt(0).description

        and: "the description from the response must be the same as the book description"
        response.getPublisher().description == book.publisher.description
    }

    def "Book - edit - happy path"() {
        given: "a book, a bookDTO and an id"
        def book = buildBook()
        def bookDTO = buildBookDTO()
        def id = 99

        and: "and a method stub for the author service, book repository and a modelMapper"
        authorService.authorsFromDTOs(_) >> buildAuthors()
        bookRepository.findById(id) >> Optional.of(book)
        modelMapper.map(bookDTO, Book.class) >> book

        when: "we call edit method"
        service.edit(id, bookDTO)

        then: "the repository method saveAndFlush is called once"
        1 * bookRepository.saveAndFlush(_)
    }

    def "Book - delete - happy path"() {
        given: "a book and an id"
        def book = buildBook()
        def id = 99

        and: "and a method stub for the author service, and book repository"
        authorService.authorsFromDTOs(_) >> buildAuthors()
        bookRepository.findById(id) >> Optional.of(book)

        when: "we call the delete method"
        service.delete(id)

        then: "the repository method delete is called once"
        1 * bookRepository.delete(_)
    }

    def "Book - bookToDTO - happy path"() {
        given:
        def book = buildBook()

        when:
        def response = service.bookToDTO(book)

        then:
        response.getTitle() == buildBookDTO().getTitle()
    }

    def "Book - bookFromDTO - happy path"() {
        given:
        def book = buildBookDTO()
        publisherService.publisherFromDTO(_) >> buildPublisher()
        authorService.authorsFromDTOs(_) >> buildAuthors()

        when:
        def response = service.bookFromDTO(book)

        then:
        response.getTitle() == buildBookDTO().getTitle()
    }

    def "Book - booksToDTOs - happy path"() {
        given:
        def books = buildBooks()

        when:
        def response = service.booksToDTOs(books)

        then:
        response.size() == 1
    }

    def "Book - booksFromDTOs - happy path"() {
        given:
        def books = buildBooksDTO()
        publisherService.publisherFromDTO(_) >> buildPublisher()
        authorService.authorsFromDTOs(_) >> buildAuthors()

        when:
        def response = service.booksFromDTOs(books)

        then:
        response.size() == 1
    }

    def "Book - saveBookAuthor - happy path"() {
        given:
        def book = buildBook()

        when:
        service.saveBookAuthor(book)

        then:
        1 * authorService.saveBookAuthor(*_)
    }

    def "Book - getAuthorsDTOByBookId - happy path"() {
        given:
        def id = 1

        when:
        service.getAuthorsDTOByBookId(id)

        then:
        1 * bookRepository.getListAuthorsDTOByBookId(id) >> buildAuthorsDTO()
    }

    def "Book - getSubjectFromName - happy path"() {
        given:
        def name = 'test'

        when:
        service.getSubjectFromName(name)

        then:
        1 * bookSubjectRepository.findByName(name) >> buildBookSubject()
    }

    def "Book - getLanguageFromName - happy path"() {
        given:
        def name = 'test'

        when:
        service.getLanguageFromName(name)

        then:
        1 * languageRepository.findByName(name) >> buildLanguage()
    }

    def "Book - getAllBookSubjectsSorted - happy path"() {
        given:
        def subjects = Arrays.asList(buildBookSubject())

        when:
        def response = service.getAllBookSubjectsSorted()

        then:
        1 * bookSubjectRepository.findAll() >> subjects

        and:
        response.size() == subjects.size()
    }

    def "Book - getAllBookLanguagesSorted - happy path"() {
        given: "A list of languages"
        def languages = Arrays.asList(buildLanguage())

        when: "we call the method and get the result"
        def result = service.getAllBookLanguagesSorted()

        then: "we check if the if the correct method was called"
        1 * languageRepository.findAll() >> languages

        and: "the result is the size as expected"
        result.size() == languages.size()
    }

    def "Book - getAllEBookFormats - happy path"() {
        given:
        def printed = EBookFormat.PRINTED_BOOK

        when:
        def response = service.getAllEBookFormats()

        then:
        response != null

        and:
        response.get(0).label == printed.description
    }

    def "Book - getAllEBookConditions - happy path"() {
        given:
        def used = EBookCondition.USED

        when:
        def response = service.getAllEBookConditions()

        then:
        response != null

        and:
        response.get(0).label == used.description
    }
}
