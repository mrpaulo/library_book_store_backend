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

import com.paulo.rodrigues.librarybookstore.address.enums.ETypePublicPlace
import com.paulo.rodrigues.librarybookstore.address.repository.AddressRepository
import com.paulo.rodrigues.librarybookstore.address.repository.CityRepository
import com.paulo.rodrigues.librarybookstore.address.repository.CountryRepository
import com.paulo.rodrigues.librarybookstore.address.repository.StateCountryRepository
import com.paulo.rodrigues.librarybookstore.address.service.AddressService
import com.paulo.rodrigues.librarybookstore.author.repository.AuthorRepository
import com.paulo.rodrigues.librarybookstore.publisher.repository.PublisherRepository
import com.paulo.rodrigues.librarybookstore.utils.ConstantsUtil
import com.paulo.rodrigues.librarybookstore.utils.LibraryStoreBooksException
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil
import com.paulo.rodrigues.librarybookstore.utils.NotFoundException
import spock.lang.Specification

import static com.paulo.rodrigues.librarybookstore.test.ObjectMother.*

class AddressServiceTest extends Specification {

    AddressService service

    void setup() {
        service = new AddressService()
        service.addressRepository = Mock(AddressRepository)
        service.personRepository = Mock(AuthorRepository)
        service.companyRepository = Mock(PublisherRepository)
        service.countryRepository = Mock(CountryRepository)
        service.stateCountryRepository = Mock(StateCountryRepository)
        service.cityRepository = Mock(CityRepository)
    }

    def "Address - findById - happy path"() {
        given:
        def address = buildAddress()
        def id = 1

        when:
        def response = service.findById(id)

        then:
        1 * service.addressRepository.findById(id) >> Optional.of(address)

        and:
        response == address
    }

    def "Address - findById - should throw an exception"() {
        given:
        def id = 1

        when:
        service.findById(id)

        then:
        def e = thrown(NotFoundException)

        and:
        e.getMessage() != null
        e.getMessage() == MessageUtil.getMessage("ADDRESS_NOT_FOUND") + " ID: " + id
    }

    def "Address - create - happy path"() {
        given:
        def address = buildAddress()
        def name = nameForTest

        when:
        def response = service.create(address)

        then:
        1 * service.addressRepository.saveAndFlush(address) >> address

        and:
        response != null
        response.getFmtAddress() == 'Avenida '+ name +', 123. San Francisco - California - USA'
    }

    def "Address - save - happy path"() {
        given:
        def address = buildAddress()

        when:
        service.save(address)

        then:
        1 * service.addressRepository.saveAndFlush(address) >>
                {
                    arguments -> def createAt = arguments.get(0).getCreateAt()
                        assert createAt != null
                } >> address
    }

    def "Address - save validation - should throw an exception when name #scenario"() {
        given:
        def address = buildAddress(name: value)

        when:
        service.save(address)

        then:
        def e = thrown(LibraryStoreBooksException)
        e.getMessage() == message

        where:
        scenario                    | value                                                | message
        'is null'                   | null                                                 | MessageUtil.getMessage("ADDRESS_NAME_NOT_INFORMED")
        'is bigger than max size'   | buildRandomString(ConstantsUtil.MAX_SIZE_NAME + 1)   | MessageUtil.getMessage("ADDRESS_NAME_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + "")
    }

    def "Address - save validation - should throw an exception when #scenario"() {
        when:
        service.save(address)

        then:
        def e = thrown(LibraryStoreBooksException)
        e.getMessage() == message

        where:
        scenario                                    | address                                                                                           | message
        'number is bigger than max size'            | buildAddress(number: buildRandomString(ConstantsUtil.MAX_SIZE_ADDRESS_NUMBER + 1))                | MessageUtil.getMessage("ADDRESS_NUMBER_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_ADDRESS_NUMBER + "")
        'cep is bigger than max size'               | buildAddress(cep: buildRandomString(ConstantsUtil.MAX_SIZE_ADDRESS_CEP + 1))                      | MessageUtil.getMessage("ADDRESS_CEP_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_ADDRESS_CEP + "")
        'zipCode is bigger than max size'           | buildAddress(zipCode: buildRandomString(ConstantsUtil.MAX_SIZE_ADDRESS_ZIPCODE + 1))              | MessageUtil.getMessage("ADDRESS_ZIPCODE_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_ADDRESS_ZIPCODE + "")
        'neighborhood is bigger than max size'      | buildAddress(neighborhood: buildRandomString(ConstantsUtil.MAX_SIZE_NAME + 1))                    | MessageUtil.getMessage("ADDRESS_NEIGHBORHOOD_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + "")
        'coordination is bigger than max size'      | buildAddress(coordination: buildRandomString(ConstantsUtil.MAX_SIZE_ADDRESS_COORDINATION + 1))    | MessageUtil.getMessage("ADDRESS_COORDINATION_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_ADDRESS_COORDINATION + "")
        'referentialPoint is bigger than max size'  | buildAddress(referentialPoint: buildRandomString(ConstantsUtil.MAX_SIZE_SHORT_TEXT + 1))          | MessageUtil.getMessage("ADDRESS_REFERENTIAL_POINT_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_SHORT_TEXT + "")
    }

    def "Address - edit - happy path"() {
        given:
        def address = buildAddress()
        def addressDTO = buildAddressDTO()
        def id = 99
        service.addressRepository.findById(id) >> Optional.of(address)

        when:
        service.edit(id, addressDTO)

        then:
        1 * service.addressRepository.saveAndFlush(_)
    }

    def "Address - erase - happy path"() {
        given:
        def address = buildAddress()
        def id = 99
        service.addressRepository.findById(id) >> Optional.of(address)

        when:
        service.delete(id)

        then:
        1 * service.personRepository.deleteAddressReference(_)
        1 * service.companyRepository.deleteAddressReference(_)
        1 * service.addressRepository.delete(_)
    }

    def "Address - toDTO - happy path"() {
        given:
        def address = buildAddress()
        def name = nameForTest

        when:
        def response = service.toDTO(address)

        then:
        response.getFmtAddress() == 'Avenida '+ name +', 123. San Francisco - California - USA'
    }

    def "Address - getETypePublicPlace - happy path"() {
        given:
        def Street = ETypePublicPlace.STREET

        when:
        def response = service.getETypePublicPlace()

        then:
        response != null

        and:
        response.get(0).label == Street.description
        response.get(0).value == Street.name
    }

    def "Address - getAllCountries - happy path"() {
        when:
        service.getAllCountries()

        then:
        1 * service.countryRepository.findAll()
    }

    def "Address - getAllStates - happy path"() {
        given:
        def country = buildCountry()
        service.countryRepository.findById(_) >> Optional.of(country)

        when:
        service.getAllStates(country.id)

        then:
        1 * service.stateCountryRepository.findByCountry(country)
    }

    def "Address - getAllCities - happy path"() {
        given:
        def country = buildCountry()
        def state = buildState()
        service.countryRepository.findById(_) >> Optional.of(country)
        service.stateCountryRepository.findById(_) >> Optional.of(state)

        when:
        service.getAllCities(country.id, state.id)

        then:
        1 * service.cityRepository.findByCountryAndState(country, state)
    }
}
