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
 * @user paulo.rodrigues
 */
package com.paulo.rodrigues.librarybookstore.test.authentication

import com.paulo.rodrigues.librarybookstore.test.AbstractLBSSpecification
import groovyx.net.http.HttpResponseException
import spock.lang.Unroll

import static com.paulo.rodrigues.librarybookstore.test.ObjectMother.*
import static com.paulo.rodrigues.librarybookstore.utils.ConstantsUtil.*
import static groovyx.net.http.ContentType.JSON
import static org.apache.http.HttpStatus.SC_CREATED
import static org.apache.http.HttpStatus.SC_OK

class UserControllerTest extends AbstractLBSSpecification {
    
    String baseAPI = USERS_V1_BASE_API
    def idNotExist = 99999

    @Unroll
    def "User - create - happy path"() {
        given: "an user object"
        def user = buildUser()

        when: "a rest POST call is performed to create an user"
        def response = client.post(path : baseAPI,
                requestContentType : JSON,
                body : objectMapper.writeValueAsString(user)
        )

        then: "the correct 201 status is expected"
        response.status == SC_CREATED

        and: "the response content is not null"
        response.responseData != null

        cleanup:
        def idToDelete = getIdCreatedFromTest(baseAPI, buildUser().getName())
        deleteItemOnDb(baseAPI, idToDelete)
    }

    @Unroll
    def "User - create - should throw an exception"() {
        given: "an user object with invalid field value"
        def user = buildUser(username: null)

        when: "a rest POST call is performed to create an user"
        def response = client.post(path : baseAPI,
                requestContentType : JSON,
                body : objectMapper.writeValueAsString(user)
        )

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "User - getById - happy path"() {
        given: "creating a user to be used on this test"
        createItemOnDb(baseAPI, buildUser())

        and: "an id from the user created"
        def idToGet = getIdCreatedFromTest(baseAPI, buildUser().getName())

        when: "a rest GET call is performed to get an user by id"
        def response = client.get(path : baseAPI + "/" + idToGet)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null

        cleanup: "deleting the user"
        deleteItemOnDb(baseAPI, idToGet)
    }

    @Unroll
    def "User - getById - should throw an exception"() {
        given: "id that I hope not exist"
        def idToGet = idNotExist

        when: "a rest GET call is performed to get an user by id"
        def response = client.get(path : baseAPI + "/" + idToGet)

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "User - getAll - happy path"() {
        when: "a rest GET call is performed to get all users"
        def response = client.get(path : baseAPI + GET_ALL_PATH)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "User - findPageable - happy path"() {
        given: "creating a user to be used on this test"
        createItemOnDb(baseAPI, buildUser())

        and: "an user filter"
        def filter = buildUserFilter()

        when: "a rest POST call is performed to get all users by filter"
        def response = client.post(path : baseAPI + FIND_PAGEABLE_PATH,
                requestContentType : JSON,
                body : objectMapper.writeValueAsString(filter)
        )

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null

        cleanup: "deleting the user"
        def idToDelete = getIdCreatedFromTest(baseAPI, buildUser().getName())
        deleteItemOnDb(baseAPI, idToDelete)
    }

    @Unroll
    def "User - getByName - happy path"() {
        given: "creating a user to be used on this test"
        createItemOnDb(baseAPI, buildUser())

        and: "a name to search"
        def nameToSearch = buildUser().getName()

        when: "a rest GET call is performed to get a list of users by name"
        def response = client.get(path : baseAPI + "/fetch/" + nameToSearch)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null

        cleanup: "deleting the user"
        def idToDelete = getIdCreatedFromTest(baseAPI, buildUser().getName())
        deleteItemOnDb(baseAPI, idToDelete)
    }

    @Unroll
    def "User - update - happy path"() {
        given: "creating a user to be used on this test"
        createItemOnDb(baseAPI, buildUser())

        and: "an id and an user object"
        def idToEdit = getIdCreatedFromTest(baseAPI, buildUser().getName())
        def user = buildUserDTO(sex: "O", id: idToEdit)

        when: "a rest PUT call is performed to update an user by id"
        def response = client.put(path : baseAPI+ "/" + idToEdit,
                requestContentType : JSON,
                body : objectMapper.writeValueAsString(user)
        )

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null

        cleanup: "deleting the user"
        deleteItemOnDb(baseAPI, idToEdit)
    }

    @Unroll
    def "User - update - should throw an exception"() {
        given: "creating a user to be used on this test"
        createItemOnDb(baseAPI, buildUser())

        and: "an id and an user object"
        def idToEdit = getIdCreatedFromTest(baseAPI, buildUser().getName())
        def user = buildUserDTO(username: null, id: idToEdit)

        when: "a rest PUT call is performed to update an user by id"
        def response = client.put(path : baseAPI+ "/" + idToEdit,
                requestContentType : JSON,
                body : objectMapper.writeValueAsString(user)
        )

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null

        cleanup: "deleting the user"
        deleteItemOnDb(baseAPI, idToEdit)
    }

    @Unroll
    def "User - delete - happy path"() {
        given: "creating a user to be used on this test"
        createItemOnDb(baseAPI, buildUser())

        and: "an id just created on method create"
        def idToDelete = getIdCreatedFromTest(baseAPI, buildUser().getName())

        when: "a rest DELETE call is performed to delete an user by id"
        def response = client.delete(path : baseAPI + "/" + idToDelete)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null

        and: "the deleted id is the expected"
        response.responseData.id == idToDelete
    }

    @Unroll
    def "User - delete - should throw an exception"() {
        given: "id just created on method create"
        def idToDelete = idNotExist

        when: "a rest DELETE call is performed to delete an user by id"
        def response = client.delete(path : baseAPI + "/" + idToDelete)

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }
}

