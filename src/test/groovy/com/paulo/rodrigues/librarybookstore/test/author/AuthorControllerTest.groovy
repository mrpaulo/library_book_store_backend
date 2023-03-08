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

import com.fasterxml.jackson.databind.ObjectMapper
import com.paulo.rodrigues.librarybookstore.test.AbstractLBSSpecification
import groovyx.net.http.HttpResponseException
import spock.lang.Stepwise
import spock.lang.Unroll

import static com.paulo.rodrigues.librarybookstore.test.ObjectMother.buildAuthor
import static com.paulo.rodrigues.librarybookstore.test.ObjectMother.buildAuthorDTO
import static com.paulo.rodrigues.librarybookstore.test.ObjectMother.buildAuthorFilter
import static groovyx.net.http.ContentType.JSON
import static org.apache.http.HttpStatus.SC_BAD_REQUEST
import static org.apache.http.HttpStatus.SC_CREATED
import static org.apache.http.HttpStatus.SC_OK

@Stepwise
class AuthorControllerTest extends AbstractLBSSpecification {
    
    String baseAPI = "/api/v1/authors"
    def idNotExist = 99999

    @Unroll
    def "Author - create - happy path"() {
        given: "an author object"
        def author = buildAuthor()

        when: "a rest POST call is performed to create an author"
        def response = client.post(path : baseAPI,
                requestContentType : JSON,
                body : objectMapper.writeValueAsString(author)
        )

        then: "the correct 201 status is expected"
        response.status == SC_CREATED

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Author - create - should throw an exception"() {
        given: "an author object with invalid field value"
        def author = buildAuthor(name: null)

        when: "a rest POST call is performed to create an author"
        def response = client.post(path : baseAPI,
                requestContentType : JSON,
                body : objectMapper.writeValueAsString(author)
        )

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "Author - getById - happy path"() {
        given: "id just created on method create"
        def idToGet = getIdCreatedFromTest()

        when: "a rest GET call is performed to get an author by id"
        def response = client.get(path : baseAPI + "/" + idToGet)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Author - getById - should throw an exception"() {
        given: "id that I hope not exist"
        def idToGet = idNotExist

        when: "a rest GET call is performed to get an author by id"
        def response = client.get(path : baseAPI + "/" + idToGet)

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "Author - update - happy path"() {
        given: "an id and an author object"
        def idToEdit = getIdCreatedFromTest()
        def author = buildAuthorDTO(description: "test2")

        when: "a rest PUT call is performed to update an author by id"
        def response = client.put(path : baseAPI+ "/" + idToEdit,
                requestContentType : JSON,
                body : objectMapper.writeValueAsString(author)
        )

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Author - update - should throw an exception"() {
        given: "an id and an author object"
        def idToEdit = getIdCreatedFromTest()
        def author = buildAuthorDTO(name: null)

        when: "a rest PUT call is performed to update an author by id"
        def response = client.put(path : baseAPI+ "/" + idToEdit,
                requestContentType : JSON,
                body : objectMapper.writeValueAsString(author)
        )

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "Author - getAll - happy path"() {
        when: "a rest GET call is performed to get all authors"
        def response = client.get(path : baseAPI + "/all")

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Author - findPageable - happy path"() {
        given: "a author filter"
        def filter = buildAuthorFilter()

        when: "a rest POST call is performed to get all authors by filter"
        def response = client.post(path : baseAPI + "/fetch",
                requestContentType : JSON,
                body : objectMapper.writeValueAsString(filter)
        )

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Author - getByName - happy path"() {
        given: "a name to search"
        def nameToSearch = buildAuthor().getName()

        when: "a rest GET call is performed to get a list of authors by name"
        def response = client.get(path : baseAPI + "/fetch/" + nameToSearch)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Author - delete - happy path"() {
        given: "id just created on method create"
        def idToDelete = getIdCreatedFromTest()

        when: "a rest DELETE call is performed to delete an author by id"
        def response = client.delete(path : baseAPI + "/" + idToDelete)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
    }

    @Unroll
    def "Author - delete - should throw an exception"() {
        given: "id just created on method create"
        def idToDelete = idNotExist

        when: "a rest DELETE call is performed to delete an author by id"
        def response = client.delete(path : baseAPI + "/" + idToDelete)

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    Long getIdCreatedFromTest() {
        def nameToSearch = buildAuthor().getName()
        def response = client.get(path : baseAPI + "/fetch/" + nameToSearch)

        return response.responseData[0].id
    }
}

