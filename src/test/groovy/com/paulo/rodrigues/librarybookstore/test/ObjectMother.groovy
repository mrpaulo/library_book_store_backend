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
package com.paulo.rodrigues.librarybookstore.test

import com.fasterxml.jackson.databind.ObjectMapper
import com.paulo.rodrigues.librarybookstore.address.dto.AddressDTO
import com.paulo.rodrigues.librarybookstore.address.dto.CityDTO
import com.paulo.rodrigues.librarybookstore.address.dto.CountryDTO
import com.paulo.rodrigues.librarybookstore.address.dto.StateDTO
import com.paulo.rodrigues.librarybookstore.address.enums.ETypePublicPlace
import com.paulo.rodrigues.librarybookstore.address.model.Address
import com.paulo.rodrigues.librarybookstore.address.model.City
import com.paulo.rodrigues.librarybookstore.address.model.Country
import com.paulo.rodrigues.librarybookstore.address.model.StateCountry
import com.paulo.rodrigues.librarybookstore.authentication.dto.UpdatePassword
import com.paulo.rodrigues.librarybookstore.authentication.dto.UserDTO
import com.paulo.rodrigues.librarybookstore.authentication.filter.UserFilter
import com.paulo.rodrigues.librarybookstore.authentication.model.Role
import com.paulo.rodrigues.librarybookstore.authentication.model.User
import com.paulo.rodrigues.librarybookstore.author.dto.AuthorDTO
import com.paulo.rodrigues.librarybookstore.author.filter.AuthorFilter
import com.paulo.rodrigues.librarybookstore.author.model.Author
import com.paulo.rodrigues.librarybookstore.book.dto.BookDTO
import com.paulo.rodrigues.librarybookstore.book.enums.EBookCondition
import com.paulo.rodrigues.librarybookstore.book.enums.EBookFormat
import com.paulo.rodrigues.librarybookstore.book.filter.BookFilter
import com.paulo.rodrigues.librarybookstore.book.model.Book
import com.paulo.rodrigues.librarybookstore.book.model.BookSubject
import com.paulo.rodrigues.librarybookstore.book.model.Language
import com.paulo.rodrigues.librarybookstore.publisher.dto.PublisherDTO
import com.paulo.rodrigues.librarybookstore.publisher.filter.PublisherFilter
import com.paulo.rodrigues.librarybookstore.publisher.model.Publisher
import com.paulo.rodrigues.librarybookstore.utils.ConstantsUtil
import com.paulo.rodrigues.librarybookstore.utils.PagedResult
import groovy.json.JsonSlurper
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

import java.time.LocalDate
import java.util.function.Supplier

class ObjectMother extends Specification {

    /*
    Generic
    */
    static def nameForTest = "GroovySpockTest_gfkgjoemf48"


    def static slurper = new JsonSlurper()

    def static toJson(object) {
        new ObjectMapper().writeValueAsString(object)
    }

    static <T> T applyProperties(props, T object) {
        if (props) {
            props.each { k, v -> object[k] = v }
        }
        object
    }

    static buildRandomString ( length){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)

                .collect({ -> new StringBuilder() },
                        { sb, codePoint -> sb.append((char) codePoint) },
                        { sb1, sb2 -> sb1.append(sb2.toString()) })
                .toString()
    }

    static buildPastDate () {
        return LocalDate.parse("1999-12-30");
    }

    static buildForStartDate () {
        return LocalDate.parse("1999-12-29");
    }

    static buildForFinalDate () {
        return LocalDate.parse("1999-12-31");
    }

    static buildPageable () {
        return PageRequest.of(1, 1);
    }

    static genericId = 99
    static secretPassword = "secret_password"

    /*
    Address
    */
    static buildCountry (props = null) {
        applyProperties(props, new Country(
                id: 2,
                name: "USA"
        ))
    }

    static buildCountryDTO (props = null) {
        applyProperties(props, new CountryDTO(
                id: 2,
                name: "USA"
        ))
    }

    static buildState (props = null) {
        applyProperties(props, new StateCountry(
                id: 3,
                name: "California",
                country: buildCountry()
        ))
    }

    static buildStateDTO (props = null) {
        applyProperties(props, new StateDTO(
                id: 3,
                name: "California"
        ))
    }

    static buildCity (props = null) {
        applyProperties(props, new City(
                id: 6,
                name: "San Francisco",
                state: buildState(),
                country: buildCountry()
        ))
    }

    static buildCityDTO (props = null) {
        applyProperties(props, new CityDTO(
                id: 6,
                name: "San Francisco"
        ))
    }

    static buildAddress (props = null) {
        applyProperties(props, new Address(
                id: genericId,
                logradouro: ETypePublicPlace.AVENUE,
                name: nameForTest,
                number: "123",
                neighborhood: "Downtown",
                cep: "123-123",
                zipCode: "123-123",
                city: buildCity(),
                coordination: buildRandomString(ConstantsUtil.MAX_SIZE_ADDRESS_COORDINATION),
                referentialPoint: buildRandomString(ConstantsUtil.MAX_SIZE_SHORT_TEXT)
        ))
    }

    static buildAddressDTO (props = null) {
        applyProperties(props, new AddressDTO(
                id: genericId,
                logradouro: ETypePublicPlace.AVENUE,
                name: nameForTest,
                number: "321",
                neighborhood: "Downtown",
                cep: "321-321",
                zipCode: "321-321",
                city: buildCity()
        ))
    }

    /*
    Author
    */
    static buildAuthorFilter (props = null) {
        applyProperties(props, new AuthorFilter(
                currentPage: 1,
                rowsPerPage: 10,
                sortColumn: 'name',
                sort: 'asc',
                offset: 0,
                id: null,
                name: "GroovySpockTest",
                startDate: buildForStartDate(),
                finalDate: buildForFinalDate(),
                sex: "M"
        ))
    }

    static buildAuthors (props = null) {
        applyProperties(props, Arrays.asList(buildAuthor()))
    }

    static buildAuthorsPage (props = null) {
        applyProperties(props, new PageImpl<>(buildAuthors(), buildPageable (), 1l))
    }

    static buildAuthor (props = null) {
        applyProperties(props, new Author(
                id: genericId,
                name: nameForTest,
                birthdate: buildPastDate(),
                sex: "M",
                email: "test@test.com",
                birthCity: buildCity(),
                birthCountry: buildCountry(),
                address: buildAddress(),
                description: buildRandomString(ConstantsUtil.MAX_SIZE_LONG_TEXT)
        ))
    }

    static buildAuthorsDTO (props = null) {
        applyProperties(props, Arrays.asList(buildAuthorDTO()))
    }

    static buildAuthorDTO (props = null) {
        applyProperties(props, new AuthorDTO(
                id: genericId,
                name: nameForTest,
                birthdate: buildPastDate(),
                sex: "M",
                email: "test@test.com",
                birthCity: buildCityDTO(),
                birthCountry: buildCountryDTO(),
                address: buildAddressDTO(),
                description: buildRandomString(ConstantsUtil.MAX_SIZE_LONG_TEXT)
        ))
    }

    /*
    Book
    */
    static buildBookFilter (props = null) {
        applyProperties(props, new BookFilter(
                currentPage: 1,
                rowsPerPage: 10,
                sortColumn: 'title',
                sort: 'asc',
                offset: 0,
                id: null,
                name: nameForTest,
                startDate: buildForStartDate(),
                finalDate: buildForFinalDate(),
                title: '',
                author: '',
                publisher: '',
                subjectName: '',
                adultsOnly: null,
                publishDate: buildPastDate()

        ))
    }

    static buildBooks (props = null) {
        applyProperties(props, Arrays.asList(buildBook()))
    }

    static buildBooksPage (props = null) {
        applyProperties(props, new PagedResult<>(buildBooks(), buildBooks().size(), 1l))
    }

    static buildBook (props = null) {
        applyProperties(props, new Book(
                id: genericId,
                title: nameForTest,
                authors: buildAuthors(),
                publisher: buildPublisher(),
                language: buildLanguage(),
                subject: buildBookSubject(),
                subtitle: buildRandomString(ConstantsUtil.MAX_SIZE_NAME),
                link: buildRandomString(ConstantsUtil.MAX_SIZE_NAME),
                format: EBookFormat.AUDIO_BOOK,
                condition: EBookCondition.NEW,
                publishDate: buildPastDate(),
                length: 100,
                edition: 1,
                rating: 5.0,
                review: buildRandomString(ConstantsUtil.MAX_SIZE_LONG_TEXT),
                adultsOnly: false
        ))
    }

    static buildBooksDTO (props = null) {
        applyProperties(props, Arrays.asList(buildBookDTO()))
    }

    static buildBookDTO (props = null) {
        applyProperties(props, new BookDTO(
                id: genericId,
                title: nameForTest,
                authors: buildAuthorsDTO(),
                publisher: buildPublisherDTO(),
                subtitle: '',
                link: '',
                format: EBookFormat.AUDIO_BOOK,
                condition: EBookCondition.NEW,
                publishDate: buildPastDate(),
                length: 100,
                edition: 1,
                rating: 5.0,
                review: buildRandomString(ConstantsUtil.MAX_SIZE_LONG_TEXT),
                adultsOnly: false
        ))
    }

    static buildLanguage (props = null) {
        applyProperties(props, new Language(
                id: 1,
                name: "Português"
        ))
    }

    static buildBookSubject (props = null) {
        applyProperties(props, new BookSubject(
                id: 1,
                name: "Romance",
                description: buildRandomString(ConstantsUtil.MAX_SIZE_LONG_TEXT)
        ))
    }

    /*
   Publisher
   */
    static cnpjToTest = '77072141000144'

    static buildPublisherFilter (props = null) {
        applyProperties(props, new PublisherFilter(
                currentPage: 1,
                rowsPerPage: 10,
                sortColumn: 'name',
                sort: 'asc',
                offset: 0,
                id: null,
                name: nameForTest,
                startDate: buildForStartDate(),
                finalDate: buildForFinalDate(),
                cnpj: '',
                foundationDate: buildPastDate()

        ))
    }

    static buildPublishers (props = null) {
        applyProperties(props, Arrays.asList(buildPublisher()))
    }

    static buildPublishersPage (props = null) {
        applyProperties(props, new PageImpl<>(buildPublishers(), buildPageable (), 1l))
    }

    static buildPublisher (props = null) {
        applyProperties(props, new Publisher(
                id: genericId,
                name: nameForTest,
                cnpj: cnpjToTest,
                foundationDate: buildPastDate(),
                address: buildAddress(),
                description: buildRandomString(ConstantsUtil.MAX_SIZE_LONG_TEXT)
        ))
    }

    static buildPublishersDTO (props = null) {
        applyProperties(props, Arrays.asList(buildPublisherDTO()))
    }

    static buildPublisherDTO (props = null) {
        applyProperties(props, new PublisherDTO(
                id: genericId,
                name: nameForTest,
                cnpj: cnpjToTest,
                foundationDate: buildPastDate(),
                address: buildAddressDTO(),
                description: buildRandomString(ConstantsUtil.MAX_SIZE_LONG_TEXT)
        ))
    }

    /*
    User
    */
    static buildUserFilter (props = null) {
        applyProperties(props, new UserFilter(
                currentPage: 1,
                rowsPerPage: 10,
                sortColumn: 'name',
                sort: 'asc',
                offset: 0,
                id: null,
                name: "GroovySpockTest",
                startDate: buildForStartDate(),
                finalDate: buildForFinalDate()
        ))
    }

    static buildUsers (props = null) {
        applyProperties(props, Arrays.asList(buildUser()))
    }

    static buildUsersPage (props = null) {
        applyProperties(props, new PageImpl<>(buildUsers(), buildPageable (), 1l))
    }

    static buildUser (props = null) {
        applyProperties(props, new User(
                id: genericId,
                name: nameForTest + "b",
                username: nameForTest + "b",
                password: secretPassword,
                birthdate: buildPastDate(),
                cpf: "50069735018",
                sex: "M",
                email: "test@test.com",
                address: buildAddress(),
                roles: buildRoles()
        ))
    }

    static buildUsersDTO (props = null) {
        applyProperties(props, Arrays.asList(buildUserDTO()))
    }

    static buildUserDTO (props = null) {
        applyProperties(props, new UserDTO(
                id: genericId,
                name: nameForTest + "b",
                username: nameForTest + "b",
                birthdate: buildPastDate(),
                sex: "M",
                email: "test@test.com"
        ))
    }

    static buildRoles (props = null) {
        applyProperties(props, Arrays.asList(buildRole()))
    }

    static buildRole (props = null) {
        applyProperties(props, new Role(
                id: 1,
                name: "ADMIN",
        ))
    }

    static buildUpdatePassword (props = null) {
        applyProperties(props, new UpdatePassword(
                newPassword: secretPassword + "b",
                currentPassword: secretPassword
        ))
    }
}
