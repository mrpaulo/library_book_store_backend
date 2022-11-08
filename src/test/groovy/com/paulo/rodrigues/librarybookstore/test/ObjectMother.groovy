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

import com.paulo.rodrigues.librarybookstore.address.dto.AddressDTO
import com.paulo.rodrigues.librarybookstore.address.dto.CityDTO
import com.paulo.rodrigues.librarybookstore.address.dto.CountryDTO
import com.paulo.rodrigues.librarybookstore.address.dto.StateDTO
import com.paulo.rodrigues.librarybookstore.address.enums.ETypePublicPlace
import com.paulo.rodrigues.librarybookstore.address.model.Address
import com.paulo.rodrigues.librarybookstore.address.model.City
import com.paulo.rodrigues.librarybookstore.address.model.Country
import com.paulo.rodrigues.librarybookstore.address.model.StateCountry
import com.paulo.rodrigues.librarybookstore.author.dto.AuthorDTO
import com.paulo.rodrigues.librarybookstore.author.filter.AuthorFilter
import com.paulo.rodrigues.librarybookstore.author.model.Author
import com.paulo.rodrigues.librarybookstore.publisher.dto.PublisherDTO
import com.paulo.rodrigues.librarybookstore.publisher.filter.PublisherFilter
import com.paulo.rodrigues.librarybookstore.publisher.model.Publisher
import com.paulo.rodrigues.librarybookstore.utils.ConstantsUtil
import com.paulo.rodrigues.librarybookstore.utils.DateUtils
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification


class ObjectMother extends Specification {

    /*
    Generic
    */
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
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString()
    }

    static buildPastDate () {
        return DateUtils.getDateEnFormat("2022-10-30T00:00:00.000+00:00")
    }

    static buildPageable () {
        return PageRequest.of(1, 1);
    }

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
                id: 99,
                logradouro: ETypePublicPlace.AVENUE,
                name: "GroovySpockTest",
                number: "123",
                neighborhood: "Downtown",
                cep: "123-123",
                zipCode: "123-123",
                city: buildCity(),
                coordination: buildRandomString(ConstantsUtil.MAX_SIZE_ADDRESS_COORDINATION),
                referencialPoint: buildRandomString(ConstantsUtil.MAX_SIZE_SHORT_TEXT)
        ))
    }

    static buildAddressDTO (props = null) {
        applyProperties(props, new AddressDTO(
                logradouro: ETypePublicPlace.AVENUE,
                name: "GroovySpockTest",
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
                //startDate: buildPastDate(),
                //finalDate: buildPastDate(),
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
                id: 99,
                name: "GroovySpockTest",
                //birthdate: buildPastDate(),
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
                id: 99,
                name: "GroovySpockTest",
                //birthdate: buildPastDate(),
                sex: "M",
                email: "test@test.com",
                birthCity: buildCityDTO(),
                birthCountry: buildCountryDTO(),
                address: buildAddressDTO(),
                description: buildRandomString(ConstantsUtil.MAX_SIZE_LONG_TEXT)
        ))
    }

    /*
    Publisher
    */
    static buildPublisherFilter (props = null) {
        applyProperties(props, new PublisherFilter(
                currentPage: 1,
                rowsPerPage: 10,
                sortColumn: 'name',
                sort: 'asc',
                offset: 0,
                id: null,
                name: "GroovySpockTest",
                //startDate: buildPastDate(),
                //finalDate: buildPastDate(),
                cnpj: '',
                //createDate: buildPastDate()

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
                id: 99,
                name: "GroovySpockTest",
                //birthdate: buildPastDate(),
                cnpj: "77072141000144",
                //createDate: "test@test.com",
                address: buildAddress(),
                description: buildRandomString(ConstantsUtil.MAX_SIZE_LONG_TEXT)
        ))
    }

    static buildPublishersDTO (props = null) {
        applyProperties(props, Arrays.asList(buildPublisherDTO()))
    }

    static buildPublisherDTO (props = null) {
        applyProperties(props, new PublisherDTO(
                id: 99,
                name: "GroovySpockTest",
                //birthdate: buildPastDate(),
                cnpj: "77072141000144",
                //createDate: "test@test.com",
                address: buildAddressDTO(),
                description: buildRandomString(ConstantsUtil.MAX_SIZE_LONG_TEXT)
        ))
    }
}
