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

package com.paulo.rodrigues.librarybookstore.test.author

import com.google.common.collect.Sets
import com.paulo.rodrigues.librarybookstore.address.service.AddressService
import com.paulo.rodrigues.librarybookstore.author.service.AuthorService
import com.paulo.rodrigues.librarybookstore.author.repository.AuthorRepository
import com.paulo.rodrigues.librarybookstore.book.repository.BookRepository
import com.paulo.rodrigues.librarybookstore.utils.ConstantsUtil
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils
import com.paulo.rodrigues.librarybookstore.utils.LibraryStoreBooksException
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil
import com.paulo.rodrigues.librarybookstore.utils.NotFoundException
import spock.lang.Specification

import static com.paulo.rodrigues.librarybookstore.test.ObjectMother.*

class AuthorServiceTest extends Specification {

    AuthorService service

    void setup() {
        service = new AuthorService()
        service.authorRepository = Mock(AuthorRepository)
        service.addressService = Mock(AddressService)
        service.bookRepository = Mock(BookRepository)

    }

    def "Author - findAll - happy path"() {
        given:
        def authors = buildAuthors()
        service.addressService.toDTO(_) >> buildAddressDTO()

        when:
        def response = service.findAll()

        then:
        1 * service.authorRepository.findAll() >> authors

        and:
        response.size() == 1
    }

    def "Author - findPageable - happy path"() {
        given:
        def authorsPage = buildAuthorsPage()
        def filter = buildAuthorFilter()
        def pageable = FormatUtils.getPageRequest(filter)
        service.addressService.toDTO(_) >> buildAddressDTO()

        when:
        def response = service.findPageable(filter, pageable)

        then:
        1 * service.authorRepository.findPageble(*_) >> authorsPage

        and:
        response.size() == 1
    }

    def "Author - findById - happy path"() {
        given:
        def author = buildAuthor()
        def id = 1

        when:
        def response = service.findById(id)

        then:
        1 * service.authorRepository.findById(id) >> Optional.of(author)

        and:
        response == author
    }

    def "Author - findById - should throw an exception"() {
        given:
        def id = 1

        when:
        service.findById(id)

        then:
        def e = thrown(NotFoundException)

        and:
        e.getMessage() != null
        e.getMessage() == MessageUtil.getMessage("AUTHOR_NOT_FOUND") + " ID: " + id
    }

    def "Author - create - happy path"() {
        given:
        def author = buildAuthor()

        when:
        def response = service.create(author)

        then:
        1 * service.authorRepository.saveAndFlush(author) >> author

        and:
        response != null
        response.getName() == buildAuthorDTO().getName()
    }

    def "Author - create - should throw a assertion error"() {
        given:
        def author = null

        when:
        def response = service.create(author)

        then:
        def e = thrown(AssertionError)

        and:
        response == null

        and:
        e.getMessage() == MessageUtil.getMessage("AUTHOR_IS_NULL")
    }

    def "Author - save - happy path"() {
        given:
        def author = buildAuthor()

        when:
        service.save(author)

        then:
        1 * service.authorRepository.saveAndFlush(author) >>
                {
                    arguments -> def createAt = arguments.get(0).getCreateAt()
                        assert createAt != null
                } >> author
    }

    def "Author - save validation - should throw an exception when name #scenario"() {
        given:
        def author = buildAuthor(name: value)

        when:
        service.save(author)

        then:
        def e = thrown(LibraryStoreBooksException)
        e.getMessage() == message

        where:
        scenario                    | value                                                | message
        'is null'                   | null                                                 | MessageUtil.getMessage("AUTHOR_NAME_NOT_INFORMED")
        'is bigger than max size'   | buildRandomString(ConstantsUtil.MAX_SIZE_NAME + 1)   | MessageUtil.getMessage("AUTHOR_NAME_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + "")
    }

    def "Author - save validation - should throw an exception when #scenario"() {
        when:
        service.save(author)

        then:
        def e = thrown(LibraryStoreBooksException)
        e.getMessage() == message

        where:
        scenario                               | author                                                                             | message
        'sex is different of F, M, O or N'     | buildAuthor(sex: 'X')                                                              | MessageUtil.getMessage("AUTHOR_SEX_INVALID")
        'sex is bigger than max size'          | buildAuthor(sex: buildRandomString(2))                                             | MessageUtil.getMessage("AUTHOR_SEX_INVALID")
        'description is bigger than max size'  | buildAuthor(description: buildRandomString(ConstantsUtil.MAX_SIZE_LONG_TEXT + 1)) | MessageUtil.getMessage("AUTHOR_DESCRIPTION_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_LONG_TEXT + "")
    }

    def "Author - edit - happy path"() {
        given:
        def author = buildAuthor()
        def authorDTO = buildAuthorDTO()
        def id = 99
        service.authorRepository.findById(id) >> Optional.of(author)

        when:
        service.edit(id, authorDTO)

        then:
        1 * service.authorRepository.saveAndFlush(_)
    }

    def "Author - delete - happy path"() {
        given:
        def author = buildAuthor()
        def id = 99
        service.authorRepository.findById(id) >> Optional.of(author)

        when:
        service.delete(id)

        then:
        1 * service.authorRepository.delete(_)
    }

    def "Author - authorToDTO - happy path"() {
        given:
        def author = buildAuthor()

        when:
        def response = service.authorToDTO(author)

        then:
        response.getName() == buildAuthorDTO().getName()
    }

    def "Author - authorFromDTO - happy path"() {
        given:
        def author = buildAuthorDTO()
        service.addressService.findById(_) >> buildAddress()
        service.addressService.getCityFromDTO(_) >> buildCity()
        service.addressService.getCountryFromDTO(_) >> buildCountry()


        when:
        def response = service.authorFromDTO(author)

        then:
        response.getName() == buildAuthor().getName()
    }

    def "Author - authorsToDTOs - happy path"() {
        given:
        def authors = buildAuthors()

        when:
        def response = service.authorsToDTOs(authors)

        then:
        response.size() == 1
    }

    def "Author - authorsFromDTOs - happy path"() {
        given:
        def author = buildAuthorsDTO()

        when:
        def response = service.authorsFromDTOs(author)

        then:
        response.size() == 1
    }

    def "Author - saveBookAuthorDTO - happy path"() {
        given:
        def book = buildBook()
        def authorDTO = buildAuthorDTO()
        service.authorRepository.findById(_) >> Optional.of(buildAuthor())
        service.bookRepository.getBooksFromAuthorName(_) >> buildBooks()

        when:
        service.saveBookAuthorDTO(book, authorDTO)

        then:
        1 * service.authorRepository.saveAndFlush(_)
    }

    def "Author - saveBookAuthor - happy path"() {
        given:
        def book = buildBook()
        def author = buildAuthor()
        service.authorRepository.findById(_) >> Optional.of(buildAuthor())
        service.bookRepository.getBooksFromAuthorName(_) >> buildBooks()

        when:
        service.saveBookAuthor(book, author)

        then:
        1 * service.authorRepository.saveAndFlush(_)
    }

    def "Author - saveBookAuthorsFromDTOs - happy path"() {
        given:
        def book = buildBook()
        def authorsDTO = buildAuthorsDTO()
        service.authorRepository.findById(_) >> Optional.of(buildAuthor())
        service.bookRepository.getBooksFromAuthorName(_) >> buildBooks()

        when:
        def response = service.saveBookAuthorsFromDTOs(book, authorsDTO)

        then:
        response.size() == 1

        and:
        1 * service.authorRepository.saveAndFlush(_)
    }

    def "Author - saveAuthors - happy path"() {
        given:
        def authors = Sets.newHashSet(buildAuthors())
        service.authorRepository.findById(_) >> Optional.of(buildAuthor())
        service.bookRepository.getBooksFromAuthorName(_) >> buildBooks()

        when:
        def response = service.saveAuthors(authors)

        then:
        response.size() == 1
    }
}
