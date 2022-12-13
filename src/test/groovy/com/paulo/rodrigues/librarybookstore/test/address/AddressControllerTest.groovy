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
package com.paulo.rodrigues.librarybookstore.test.address

import com.paulo.rodrigues.librarybookstore.test.AbstractLBSSpecification
import groovyx.net.http.HttpResponseException
import spock.lang.Stepwise
import spock.lang.Unroll

import static groovyx.net.http.ContentType.JSON
import static org.apache.http.HttpStatus.SC_OK
import static com.paulo.rodrigues.librarybookstore.test.ObjectMother.*

@Stepwise
class AddressControllerTest extends AbstractLBSSpecification {
    
    String baseAPI = "/api/v1/addresses"
    def idNotExist = 99999

    @Unroll
    def "Address - create - happy path"() {
        given: "an address object"
        def address = buildAddress()

        when: "a rest POST call is performed to create an address"
        def response = client.post(path : baseAPI,
                requestContentType : JSON,
                body : address
        )

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Address - create - should throw an exception"() {
        given: "an address object with invalid field value"
        def address = buildAddress(name: null)

        when: "a rest POST call is performed to create an address"
        def response = client.post(path : baseAPI,
                requestContentType : JSON,
                body : address
        )

        then: "throw an Exception"
        HttpResponseException e = thrown()

        and:
        response == null
    }

    @Unroll
    def "Address - getById - happy path"() {
        given: "id just created on method create"
        def idToGet = getIdCreatedFromTest()

        when: "a rest call is performed to get an address by id"
        def response = client.get(path : baseAPI + "/" + idToGet)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Address - getById - should throw an exception"() {
        given: "id that I hope not exist"
        def idToGet = idNotExist

        when: "a rest call is performed to get an address by id"
        def response = client.get(path : baseAPI + "/" + idToGet)

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "Address - update - happy path"() {
        given: "an id and an address object"
        def idToEdit = getIdCreatedFromTest()
        def address = buildAddressDTO(coordination: "test2")

        when: "a rest PUT call is performed to update an address by id"
        def response = client.put(path : baseAPI+ "/" + idToEdit,
                requestContentType : JSON,
                body : address
        )

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Address - update - should throw an exception"() {
        given: "an id and an address object"
        def idToEdit = getIdCreatedFromTest()
        def address = buildAddressDTO(name: null)

        when: "a rest PUT call is performed to update an address by id"
        def response = client.put(path : baseAPI+ "/" + idToEdit,
                requestContentType : JSON,
                body : address
        )

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "Address - delete - happy path"() {
        given: "id just created on method create"
        def idToDelete = getIdCreatedFromTest()

        when: "a rest DELETE call is performed to delete an address by id"
        def response = client.delete(path : baseAPI + "/" + idToDelete)

        then: "the correct 200 status is expected"
        response.status == SC_OK

        and: "the response content is not null"
    }

    @Unroll
    def "Address - delete - should throw an exception"() {
        given: "id just created on method create"
        def idToDelete = idNotExist

        when: "a rest DELETE call is performed to delete an address by id"
        def response = client.delete(path : baseAPI + "/" + idToDelete)

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "Address - getAllCountries - happy path"() {
        when: "a rest GET call is performed to get a list of countries"
        def response = client.get(path : baseAPI + "/countries")

        then: "the correct status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Address - getAllStates - happy path"() {
        when: "a rest GET call is performed to get all states given a country with id 1"
        def response = client.get(path : baseAPI + "/1/states")

        then: "the correct status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Address - getAllStates - should throw an exception given an invalid input"() {
        when: "a rest GET call is performed with an invalid abc input"
        def response = client.get(path : baseAPI + "/abc/states")

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "Address - getAllCities - happy path"() {
        when: "a rest GET call is performed to get all cities given a country 1 and state 1"
        def response = client.get(path : baseAPI + "/1/1/cities")

        then: "the correct status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    @Unroll
    def "Address - getAllCities - should throw an exception given an invalid input"() {
        when: "a rest GET call is performed with an invalid abc input"
        def response = client.get(path : baseAPI + "1/abc/cities")

        then: "throw an Exception"
        thrown(HttpResponseException)

        and:
        response == null
    }

    @Unroll
    def "Address - getETypePublicPlace - happy path"() {
        when: "a rest GET call is performed to get all types of public place enum"
        def response = client.get(path : baseAPI + "/logradouros")

        then: "the correct status is expected"
        response.status == SC_OK

        and: "the response content is not null"
        response.responseData != null
    }

    Long getIdCreatedFromTest() {
        def nameToSearch = buildAddress().getName()
        def response = client.get(path : baseAPI + "/" + nameToSearch + "/name")

        return response.responseData[0].id
    }
}

