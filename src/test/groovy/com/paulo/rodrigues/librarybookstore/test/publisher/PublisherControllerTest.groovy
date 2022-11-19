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
package com.paulo.rodrigues.librarybookstore.test.publisher

import com.paulo.rodrigues.librarybookstore.test.AbstractLBSSpecification
import groovyx.net.http.HttpResponseException
import spock.lang.Stepwise
import spock.lang.Unroll

import static com.paulo.rodrigues.librarybookstore.test.ObjectMother.*
import static groovyx.net.http.ContentType.JSON
import static org.apache.http.HttpStatus.SC_OK

@Stepwise
class PublisherControllerTest extends AbstractLBSSpecification {
    
    String baseAPI = "/api/v1/publishers"
    def idNotExist = 99999

    @Unroll
    def "Publisher - create - happy path"() {
        given: "an publisher object"
        def idToDelete = getIdCreatedFromTest()
        if (idToDelete){
            client.delete(path : baseAPI + "/" + idToDelete)
        }
        def publisher = buildPublisher()

        when: "a rest call is performed to get an publisher by id"
        def response = client.post(path : baseAPI,
                requestContentType : JSON,
                body : publisher
        )

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Publisher - create - should throw an exception"() {
        given: "an publisher object with invalid field value"
        def publisher = buildPublisher(name: null)

        when: "a rest call is performed to get an publisher by id"
        def response = client.post(path : baseAPI,
                requestContentType : JSON,
                body : publisher
        )

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "Publisher - getById - happy path"() {
        given: "id just created on method create"
        def idToGet = getIdCreatedFromTest()

        when: "a rest call is performed to get an publisher by id"
        def response = client.get(path : baseAPI + "/" + idToGet)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Publisher - getById - should throw an exception"() {
        given: "id that I hope not exist"
        def idToGet = idNotExist

        when: "a rest call is performed to get an publisher by id"
        def response = client.get(path : baseAPI + "/" + idToGet)

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "Publisher - update - happy path"() {
        given: "an id and an publisher object"
        def idToEdit = getIdCreatedFromTest()
        def publisher = buildPublisherDTO(description: "test2")

        when: "a rest call is performed to get an publisher by id"
        def response = client.put(path : baseAPI+ "/" + idToEdit,
                requestContentType : JSON,
                body : publisher
        )

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Publisher - update - should throw an exception"() {
        given: "an id and an publisher object"
        def idToEdit = getIdCreatedFromTest()
        def publisher = buildPublisherDTO(name: null)

        when: "a rest call is performed to get an publisher by id"
        def response = client.put(path : baseAPI+ "/" + idToEdit,
                requestContentType : JSON,
                body : publisher
        )

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "Publisher - getAll - happy path"() {
        when: "a rest call is performed to get all publishers"
        def response = client.get(path : baseAPI + "/all")

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Publisher - findPageable - happy path"() {
        given: "a publisher filter"
        def filter = buildPublisherFilter()

        when: "a rest call is performed to get all publishers"
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
    def "Publisher - getByName - happy path"() {
        given: "a name to search"
        def nameToSearch = buildPublisher().getName()

        when: "a rest call is performed to get all publishers"
        def response = client.get(path : baseAPI + "/fetch/" + nameToSearch)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Publisher - delete - happy path"() {
        given: "id just created on method create"
        def idToDelete = getIdCreatedFromTest()

        when: "a rest call is performed to get an publisher by id"
        def response = client.delete(path : baseAPI + "/" + idToDelete)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
    }

    @Unroll
    def "Publisher - delete - should throw an exception"() {
        given: "id just created on method create"
        def idToDelete = idNotExist

        when: "a rest call is performed to get an publisher by id"
        def response = client.delete(path : baseAPI + "/" + idToDelete)

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    Long getIdCreatedFromTest() {
        def nameToSearch = buildPublisher().getName()
        def response = client.get(path : baseAPI + "/fetch/" + nameToSearch)

        return response.responseData.size > 0 ? response.responseData[0].id : null
    }
}

