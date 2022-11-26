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
        given:
        def books = buildBooks()

        when:
        def response = service.findAll()

        then:
        1 * bookRepository.findAll() >> books

        and:
        response.size() == 1
    }

    def "Book - findPageable - happy path"() {
        given:
        def booksPage = buildBooksPage()
        def filter = buildBookFilter()

        when:
        def response = service.findPageable(filter)

        then:
        1 * bookRepository.findPageable(*_) >> booksPage

        and:
        response != null
    }

    def "Book - findById - happy path"() {
        given:
        def book = buildBook()
        def id = 1
        authorService.authorsFromDTOs(_) >> buildAuthors()

        when:
        def response = service.findById(id)

        then:
        1 * bookRepository.findById(id) >> Optional.of(book)

        and:
        response == book
    }

    def "Book - findById - should throw an exception"() {
        given:
        def id = 1

        when:
        service.findById(id)

        then:
        def e = thrown(NotFoundException)

        and:
        e.getMessage() != null
        e.getMessage() == MessageUtil.getMessage("BOOK_NOT_FOUND") + " ID: " + id
    }

    def "Book - create - happy path"() {
        given:
        def bookDTO = buildBookDTO()
        def book = buildBook()
        authorService.authorsFromDTOs(_) >> buildAuthors()

        when:
        def response = service.create(bookDTO)

        then:
        1 * bookRepository.saveAndFlush(_) >> book

        and:
        response != null
        response.getTitle() == bookDTO.getTitle()
    }

    def "Book - save - happy path"() {
        given:
        def book = buildBook()

        when:
        service.save(book)

        then:
        1 * bookRepository.saveAndFlush(book) >>
                {
                    arguments -> def createAt = arguments.get(0).getCreateAt()
                        assert createAt != null
                } >> book
    }

    def "Book - save validation - should throw an exception when name #scenario"() {
        given:
        def book = buildBook(title: value)

        when:
        service.save(book)

        then:
        def e = thrown(LibraryStoreBooksException)
        e.getMessage() == message

        where:
        scenario                    | value                                                | message
        'is null'                   | null                                                 | MessageUtil.getMessage("BOOK_TITLE_NOT_INFORMED")
        'is bigger than max size'   | buildRandomString(ConstantsUtil.MAX_SIZE_NAME + 1)   | MessageUtil.getMessage("BOOK_TITLE_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + "")
    }

    def "Book - save validation - should throw an exception when #scenario"() {
        when:
        service.save(book)

        then:
        def e = thrown(LibraryStoreBooksException)
        e.getMessage() == message

        where:
        scenario                            | book                                                                       | message
        'subtitle is bigger than max size'  | buildBook(subtitle: buildRandomString(ConstantsUtil.MAX_SIZE_NAME + 1))    | MessageUtil.getMessage("BOOK_SUBTITLE_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + "")
        'link is bigger than max size'      | buildBook(link: buildRandomString(ConstantsUtil.MAX_SIZE_NAME + 1))        | MessageUtil.getMessage("BOOK_LINK_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + "")
        'review is bigger than max size'    | buildBook(review: buildRandomString(ConstantsUtil.MAX_SIZE_LONG_TEXT + 1)) | MessageUtil.getMessage("BOOK_REVIEW_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_LONG_TEXT + "")
    }

    def "Book - checkAndSaveReference - happy path"() {
        given:
        def book = buildBook()

        when:
        def response = service.checkAndSaveReference(book)

        then:
        response.getAuthors().getAt(0).description == book.authors.getAt(0).description

        and:
        response.getPublisher().description == book.publisher.description
    }

    def "Book - edit - happy path"() {
        given:
        def book = buildBook()
        def bookDTO = buildBookDTO()
        def id = 99
        authorService.authorsFromDTOs(_) >> buildAuthors()
        bookRepository.findById(id) >> Optional.of(book)
        modelMapper.map(bookDTO, Book.class) >> book

        when:
        service.edit(id, bookDTO)

        then:
        1 * bookRepository.saveAndFlush(_)
    }

    def "Book - delete - happy path"() {
        given:
        def book = buildBook()
        def id = 99
        authorService.authorsFromDTOs(_) >> buildAuthors()
        bookRepository.findById(id) >> Optional.of(book)

        when:
        service.delete(id)

        then:
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

    //TO DO:
//    def "Book - getBookSubject - happy path"() {
//        given:
//        def book = buildBookDTO()
//        publisherService.fromDTO(_) >> buildPublisher()
//        authorService.getListAuthorsByListDTO(_) >> buildAuthors()
//
//        when:
//        def response = service.fromDTO(book)
//
//        then:
//        response.getTitle() == buildBookDTO().getTitle()
//    }

    //TO DO:
//    def "Book - getAllBookLanguagesSorted - happy path"() {
//        given:
//        languageRepository.findAll() >> Arrays.asList(buildLanguage())
//
//        when:
//        service.getAllBookLanguagesSorted()
//
//        then:
//        1 * languageRepository.findAll()
//    }

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
