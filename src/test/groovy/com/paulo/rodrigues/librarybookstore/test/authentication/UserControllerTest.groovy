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
        def response = postRestCall(baseAPI, user)

        then: "the correct 201 status is expected"
        response.status == SC_CREATED

        and: "the response content is not null"
        response.responseData != null

        cleanup:
        def idToDelete = getIdCreatedFromTest(baseAPI, buildUser().getName())
        deleteByIdRestCall(baseAPI, idToDelete)
    }

    @Unroll
    def "User - create - should throw an exception"() {
        given: "an user object with invalid field value"
        def user = buildUser(username: null)

        when: "a rest POST call is performed to create an user"
        def response = postRestCall(baseAPI, user)

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
        def response = getByIdRestCall(baseAPI, idToGet)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null

        cleanup: "deleting the user"
        deleteByIdRestCall(baseAPI, idToGet)
    }

    @Unroll
    def "User - getById - should throw an exception"() {
        given: "id that I hope not exist"
        def idToGet = idNotExist

        when: "a rest GET call is performed to get an user by id"
        def response = getByIdRestCall(baseAPI, idToGet)

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "User - getAll - happy path"() {
        when: "a rest GET call is performed to get all users"
        def response = getRestCall(baseAPI + GET_ALL_PATH)

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
        def response = postRestCall(baseAPI + FIND_PAGEABLE_PATH, filter)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null

        cleanup: "deleting the user"
        def idToDelete = getIdCreatedFromTest(baseAPI, buildUser().getName())
        deleteByIdRestCall(baseAPI, idToDelete)
    }

    @Unroll
    def "User - getByName - happy path"() {
        given: "creating a user to be used on this test"
        createItemOnDb(baseAPI, buildUser())

        and: "a name to search"
        def nameToSearch = buildUser().getName()

        when: "a rest GET call is performed to get a list of users by name"
        def response = getByNameRestCall(baseAPI, nameToSearch)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null

        cleanup: "deleting the user"
        def idToDelete = getIdCreatedFromTest(baseAPI, buildUser().getName())
        deleteByIdRestCall(baseAPI, idToDelete)
    }

    @Unroll
    def "User - update - happy path"() {
        given: "creating a user to be used on this test"
        createItemOnDb(baseAPI, buildUser())

        and: "an id and an user object"
        def idToEdit = getIdCreatedFromTest(baseAPI, buildUser().getName())
        def user = buildUserDTO(sex: "O", id: idToEdit)

        when: "a rest PUT call is performed to update an user by id"
        def response = putWithIdRestCall(baseAPI, idToEdit,  user)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null

        cleanup: "deleting the user"
        deleteByIdRestCall(baseAPI, idToEdit)
    }

    @Unroll
    def "User - update - should throw an exception"() {
        given: "creating a user to be used on this test"
        createItemOnDb(baseAPI, buildUser())

        and: "an id and an user object"
        def idToEdit = getIdCreatedFromTest(baseAPI, buildUser().getName())
        def user = buildUserDTO(username: null, id: idToEdit)

        when: "a rest PUT call is performed to update an user by id"
        def response = putWithIdRestCall(baseAPI, idToEdit,  user)

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null

        cleanup: "deleting the user"
        deleteByIdRestCall(baseAPI, idToEdit)
    }

    @Unroll
    def "User - delete - happy path"() {
        given: "creating a user to be used on this test"
        createItemOnDb(baseAPI, buildUser())

        and: "an id just created on method create"
        def idToDelete = getIdCreatedFromTest(baseAPI, buildUser().getName())

        when: "a rest DELETE call is performed to delete an user by id"
        def response = deleteByIdRestCall(baseAPI, idToDelete)

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
        def response = deleteByIdRestCall(baseAPI, idToDelete)

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "User - changeUserPassword - should throw an exception"() {
        given: "an user updatePassword object"
        def updatePassword = buildUpdatePassword()

        when: "a rest GET call is performed to update the user password"
        def response = postRestCall(baseAPI + UPDATE_USER_PATH, updatePassword)

        then: "throw an Exception because the logged user is not the same"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "User - getAllRoles - happy path"() {
        when: "a rest GET call is performed to get all user roles"
        def response = getRestCall(baseAPI + GET_ROLES_PATH)

        then: "the correct status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }
}

