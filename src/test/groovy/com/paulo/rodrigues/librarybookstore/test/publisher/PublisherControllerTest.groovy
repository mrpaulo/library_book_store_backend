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
import spock.lang.Unroll

import static com.paulo.rodrigues.librarybookstore.test.ObjectMother.*
import static org.apache.http.HttpStatus.SC_CREATED
import static org.apache.http.HttpStatus.SC_OK
import static com.paulo.rodrigues.librarybookstore.utils.ConstantsUtil.*

class PublisherControllerTest extends AbstractLBSSpecification {
    
    String baseAPI = PUBLISHERS_V1_BASE_API
    def idNotExist = 99999

    @Unroll
    def "Publisher - create - happy path"() {
        given: "a check if we already have an object with same name"
        def idToDelete = getIdCreatedFromTest(baseAPI, buildPublisher().getName())
        if (idToDelete){
            deleteByIdRestCall(baseAPI, idToDelete)
        }

        and: "a publisher object"
        def publisher = buildPublisher()

        when: "a rest POST call is performed to create a publisher"
        def response = postRestCall(baseAPI, publisher)

        then: "the correct 201 status is expected"
        response.status == SC_CREATED

        and: "the response content must be as expected"
        response.responseData.name == publisher.getName()

        cleanup: "deleting the publisher"
        def idToDelete2 = getIdCreatedFromTest(baseAPI, buildPublisher().getName())
        deleteByIdRestCall(baseAPI, idToDelete2)
    }

    @Unroll
    def "Publisher - create - should throw an exception"() {
        given: "a publisher object with invalid field value"
        def publisher = buildPublisher(name: null)

        when: " a rest POST call is performed to create a publisher"
        def response = postRestCall(baseAPI, publisher)

        then: "throw an Exception"
        thrown(HttpResponseException)

        and: "the response content is null"
        response == null
    }

    @Unroll
    def "Publisher - getById - happy path"() {
        given: "creating a publisher to be used on this test"
        createItemOnDb(baseAPI, buildPublisher())

        and: "id just created on method create"
        def idToGet = getIdCreatedFromTest(baseAPI, buildPublisher().getName())

        when: "a rest GET call is performed to get a publisher by id"
        def response = getByIdRestCall(baseAPI, idToGet)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content must be as expected"
        response.responseData.id == idToGet

        cleanup: "deleting the publisher"
        deleteByIdRestCall(baseAPI, idToGet)
    }

    @Unroll
    def "Publisher - getById - should throw an exception"() {
        given: "id that I hope not exist"
        def idToGet = idNotExist

        when: "a rest GET call is performed to get a publisher by id"
        def response = getByIdRestCall(baseAPI, idToGet)

        then: "throw an Exception"
        thrown(HttpResponseException)

        and: "the response content is null"
        response == null
    }

    @Unroll
    def "Publisher - update - happy path"() {
        given: "creating a publisher to be used on this test"
        createItemOnDb(baseAPI, buildPublisher())

        and: "an id and a publisher object"
        def idToEdit = getIdCreatedFromTest(baseAPI, buildPublisher().getName())
        def publisher = buildPublisherDTO(description: "test2")

        when: "a rest PUT call is performed to update a publisher by id"
        def response = putWithIdRestCall(baseAPI, idToEdit, publisher)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content must be as expected"
        response.responseData.description == publisher.getDescription()

        cleanup: "deleting the publisher"
        deleteByIdRestCall(baseAPI, idToEdit)
    }

    @Unroll
    def "Publisher - update - should throw an exception"() {
        given: "creating a publisher to be used on this test"
        createItemOnDb(baseAPI, buildPublisher())

        and: "an id and a publisher object"
        def idToEdit = getIdCreatedFromTest(baseAPI, buildPublisher().getName())
        def publisher = buildPublisherDTO(name: null)

        when: "a rest PUT call is performed to update a publisher by id"
        def response = putWithIdRestCall(baseAPI, idToEdit, publisher)

        then: "throw an Exception"
        thrown(HttpResponseException)

        and: "the response content is null"
        response == null

        cleanup: "deleting the publisher"
        deleteByIdRestCall(baseAPI, idToEdit)
    }

    @Unroll
    def "Publisher - getAll - happy path"() {
        when: "a rest GET call is performed to get all publishers"
        def response = getRestCall(baseAPI + GET_ALL_PATH)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Publisher - findPageable - happy path"() {
        given: "a publisher filter"
        def filter = buildPublisherFilter()

        when: "a rest call is performed to get all publishers by a filter"
        def response = postRestCall(baseAPI + FIND_PAGEABLE_PATH, filter)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Publisher - getByName - happy path"() {
        given: "creating a publisher to be used on this test"
        createItemOnDb(baseAPI, buildPublisher())

        and: "a name to search"
        def nameToSearch = buildPublisher().getName()

        when: "a rest call is performed to get a publishers by name"
        def response = getByNameRestCall(baseAPI, nameToSearch)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content must be as expected"
        response.responseData[0].name == nameToSearch

        cleanup: "deleting the publisher"
        def idToDelete = getIdCreatedFromTest(baseAPI, buildPublisher().getName())
        deleteByIdRestCall(baseAPI, idToDelete)
    }

    @Unroll
    def "Publisher - safeDelete - happy path"() {
        given: "creating a publisher to be used on this test"
        createItemOnDb(baseAPI, buildPublisher())

        and: "id just created on method create"
        def idToDelete = getIdCreatedFromTest(baseAPI, buildPublisher().getName())

        when: "a rest call is performed to delete a publisher by id"
        def response = deleteByIdRestCall(baseAPI + "/safe/", idToDelete)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is zero because this publisher has no books"
        response.responseData.size() == 0

        cleanup: "deleting the publisher"
        deleteByIdRestCall(baseAPI, idToDelete)
    }

    @Unroll
    def "Publisher - delete - happy path"() {
        given: "creating a publisher to be used on this test"
        createItemOnDb(baseAPI, buildPublisher())

        and: "id just created on method create"
        def idToDelete = getIdCreatedFromTest(baseAPI, buildPublisher().getName())

        when: "a rest call is performed to delete a publisher by id"
        def response = deleteByIdRestCall(baseAPI, idToDelete)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the deleted id is the expected"
        response.responseData.id == idToDelete
    }

    @Unroll
    def "Publisher - delete - should throw an exception"() {
        given: "id just created on method create"
        def idToDelete = idNotExist

        when: "a rest call is performed to delete a publisher by id"
        def response = deleteByIdRestCall(baseAPI, idToDelete)

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }
}

