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
import static groovyx.net.http.ContentType.JSON
import static org.apache.http.HttpStatus.SC_OK

@Stepwise
class BookControllerTest extends AbstractLBSSpecification {
    
    String baseAPI = "/api/v1/books"
    def idNotExist = 99999

    @Unroll
    def "Book - create - happy path"() {
        given: "an book object"
        def book = buildBookDTO()

        when: "a rest call is performed to get an book by id"
        def response = client.post(path : baseAPI,
                requestContentType : JSON,
                body : book
        )

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Book - create - should throw an exception"() {
        given: "an book object with invalid field value"
        def book = buildBook(title: null)

        when: "a rest call is performed to get an book by id"
        def response = client.post(path : baseAPI,
                requestContentType : JSON,
                body : book
        )

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "Book - getById - happy path"() {
        given: "id just created on method create"
        def idToGet = getIdCreatedFromTest()

        when: "a rest call is performed to get an book by id"
        def response = client.get(path : baseAPI + "/" + idToGet)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Book - getById - should throw an exception"() {
        given: "id that I hope not exist"
        def idToGet = idNotExist

        when: "a rest call is performed to get an book by id"
        def response = client.get(path : baseAPI + "/" + idToGet)

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "Book - update - happy path"() {
        given: "an id and an book object"
        def idToEdit = getIdCreatedFromTest()
        def book = buildBookDTO(review: "test2")

        when: "a rest call is performed to get an book by id"
        def response = client.put(path : baseAPI+ "/" + idToEdit,
                requestContentType : JSON,
                body : book
        )

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Book - update - should throw an exception"() {
        given: "an id and an book object"
        def idToEdit = getIdCreatedFromTest()
        def book = buildBookDTO(title: null)

        when: "a rest call is performed to get an book by id"
        def response = client.put(path : baseAPI+ "/" + idToEdit,
                requestContentType : JSON,
                body : book
        )

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "Book - getAll - happy path"() {
        when: "a rest call is performed to get all books"
        def response = client.get(path : baseAPI + "/all")

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Book - findPageable - happy path"() {
        given: "a book filter"
        def filter = buildBookFilter()

        when: "a rest call is performed to get all books"
        def response = client.post(path : baseAPI + "/fetch",
                requestContentType : JSON,
                body : filter
        )

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Book - delete - happy path"() {
        given: "id just created on method create"
        def idToDelete = getIdCreatedFromTest()

        when: "a rest call is performed to get an book by id"
        def response = client.delete(path : baseAPI + "/" + idToDelete)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
    }

    @Unroll
    def "Book - delete - should throw an exception"() {
        given: "id just created on method create"
        def idToDelete = idNotExist

        when: "a rest call is performed to get an book by id"
        def response = client.delete(path : baseAPI + "/" + idToDelete)

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    Long getIdCreatedFromTest() {
        def filter = buildBookFilter(title: buildBook().getTitle())

        def response = client.post(path : baseAPI + "/fetch",
                requestContentType : JSON,
                body : filter
        )

        return response.responseData[0].id
    }
}

