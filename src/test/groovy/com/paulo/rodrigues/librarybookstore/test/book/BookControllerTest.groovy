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

import com.paulo.rodrigues.librarybookstore.test.AbstractLBSSpecification
import groovyx.net.http.HttpResponseException
import spock.lang.Stepwise
import spock.lang.Unroll

import static com.paulo.rodrigues.librarybookstore.test.ObjectMother.*
import static org.apache.http.HttpStatus.SC_CREATED
import static org.apache.http.HttpStatus.SC_OK
import static com.paulo.rodrigues.librarybookstore.utils.ConstantsUtil.*

@Stepwise
class BookControllerTest extends AbstractLBSSpecification {
    
    String baseAPI = BOOKS_V1_BASE_API
    def idNotExist = 99999

    @Unroll
    def "Book - create - happy path"() {
        given: "a book object"
        def book = buildBookDTO()

        when: "a rest POST call is performed to create a book"
        def response = postRestCall(baseAPI, book)

        then: "the correct 201 status is expected"
        response.status == SC_CREATED

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Book - create - should throw an exception"() {
        given: "a book object with invalid field value"
        def book = buildBook(title: null)

        when: "a rest POST call is performed to create a book"
        def response = postRestCall(baseAPI, book)

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "Book - getById - happy path"() {
        given: "id just created on method create"
        def idToGet = getBookIdCreatedFromTest()

        when: "a rest GET call is performed to get a book by id"
        def response = getByIdRestCall(baseAPI, idToGet)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Book - getById - should throw an exception"() {
        given: "id that I hope not exist"
        def idToGet = idNotExist

        when: "a rest GET call is performed to get a book by id"
        def response = getByIdRestCall(baseAPI, idToGet)

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "Book - update - happy path"() {
        given: "an id and a book object"
        def idToEdit = getBookIdCreatedFromTest()
        def book = buildBookDTO(id: idToEdit, review: "test2")

        when: "a rest PUT call is performed to update a book by id"
        def response = putWithIdRestCall(baseAPI, idToEdit, book)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Book - update - should throw an exception"() {
        given: "an id and a book object"
        def idToEdit = getBookIdCreatedFromTest()
        def book = buildBookDTO(title: null, id: idToEdit)

        when: "a rest PUT call is performed to update a book by id"
        def response = putWithIdRestCall(baseAPI, idToEdit, book)

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "Book - getAll - happy path"() {
        when: "a rest GET call is performed to get all books"
        def response = getRestCall(baseAPI + GET_ALL_PATH)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Book - findPageable - happy path"() {
        given: "a book filter"
        def filter = buildBookFilter()

        when: "a rest POST call is performed to get a list of books by filter"
        def response = postRestCall(baseAPI + FIND_PAGEABLE_PATH, filter)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Book - delete - happy path"() {
        given: "id just created on method create"
        def idToDelete = getBookIdCreatedFromTest()

        when: "a rest DELETE call is performed to delete a book by id"
        def response = deleteByIdRestCall(baseAPI, idToDelete)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
    }

    @Unroll
    def "Book - delete - should throw an exception"() {
        given: "id just created on method create"
        def idToDelete = idNotExist

        when: "a rest DELETE call is performed to delete a book by id"
        def response = deleteByIdRestCall(baseAPI, idToDelete)

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "Book - getBookSubject - happy path"() {
        when: "a rest GET call is performed to get a list of subjects"
        def response = getRestCall(baseAPI + GET_SUBJECTS_PATH)

        then: "the correct status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Book - getLanguages - happy path"() {
        when: "a rest GET call is performed to get a list of languages"
        def response = getRestCall(baseAPI + GET_LANGUAGES_PATH)

        then: "the correct status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Book - getEBookFormat - happy path"() {
        when: "a rest GET call is performed to get all book formats enum"
        def response = getRestCall(baseAPI + GET_FORMATS_PATH)

        then: "the correct status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Book - getEBookCondition - happy path"() {
        when: "a rest GET call is performed to get all book conditions enum"
        def response = getRestCall(baseAPI + GET_CONDITIONS_PATH)

        then: "the correct status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    Long getBookIdCreatedFromTest() {
        def filter = buildBookFilter(title: buildBook().getTitle())

        def response = postRestCall(baseAPI + FIND_PAGEABLE_PATH, filter)
        return response.responseData[0].id
    }
}

