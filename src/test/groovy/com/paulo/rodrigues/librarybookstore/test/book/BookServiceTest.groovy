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
        authorService.getListAuthorsByListDTO(_) >> buildAuthors()

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
//TODO:
//    def "Book - create - happy path"() {
//        given:
//        def bookDTO = buildBookDTO()
//        authorService.getListAuthorsByListDTO(_) >> buildAuthors()
//        bookRepository.saveAndFlush(_) >> buildBook()
//
//        when:
//        def response = service.create(bookDTO)
//
//        then:
//        1 * bookRepository.saveAndFlush(bookDTO) >> bookDTO
//
//        and:
//        response != null
//        response.getTitle() == buildBookDTO().getTitle()
//    }

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

    //TODO:
//    def "Book - edit - happy path"() {
//        given:
//        def book = buildBook()
//        def bookDTO = buildBookDTO()
//        def id = 99
//        bookRepository.findById(id) >> Optional.of(book)
//        service.findById(id) >> book
//        authorService.getListAuthorsByListDTO(_) >> buildAuthors()
//        authorService.saveBookAuthorFromListBooksDTO(_) >> buildAuthors()
//
//        when:
//        service.edit(id, bookDTO)
//
//        then:
//        1 * bookRepository.saveAndFlush(_)
//    }
//TODO:
//    def "Book - erase - happy path"() {
//        given:
//        def book = buildBook()
//        def id = 99
//        bookRepository.findById(id) >> Optional.of(book)
//        //bookRepository.getBooksFromBookId(id) >> null
//
//        when:
//        service.delete(id)
//
//        then:
//        1 * bookRepository.delete(_)
//    }

    def "Book - toDTO - happy path"() {
        given:
        def book = buildBook()

        when:
        def response = service.toDTO(book)

        then:
        response.getTitle() == buildBookDTO().getTitle()
    }

    def "Book - fromDTO - happy path"() {
        given:
        def book = buildBookDTO()
        publisherService.fromDTO(_) >> buildPublisher()
        authorService.getListAuthorsByListDTO(_) >> buildAuthors()

        when:
        def response = service.fromDTO(book)

        then:
        response.getTitle() == buildBookDTO().getTitle()
    }
}
