
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

package address

import com.paulo.rodrigues.librarybookstore.address.enums.ETypePublicPlace
import com.paulo.rodrigues.librarybookstore.address.repository.AddressRepository
import com.paulo.rodrigues.librarybookstore.address.repository.CityRepository
import com.paulo.rodrigues.librarybookstore.address.repository.CountryRepository
import com.paulo.rodrigues.librarybookstore.address.repository.StateCountryRepository
import com.paulo.rodrigues.librarybookstore.address.service.AddressService
import com.paulo.rodrigues.librarybookstore.author.repository.AuthorRepository
import com.paulo.rodrigues.librarybookstore.publisher.repository.PublisherRepository
import spock.lang.*

import static ObjectMother.*

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

    def "Address - create - happy path"() {
        given:
        def address = buildAddress()

        when:
        def response = service.create(address)

        then:
        1 * service.addressRepository.saveAndFlush(address) >> address

        and:
        response != null
        response.getFmtAddress() == 'Avenida GroovySpockTest, 123. San Francisco - California - USA'
    }

    def "Address - save - happy path"() {
        given:
        def address = buildAddress()

        when:
        service.save(address)

        then:
        1 * service.addressRepository.saveAndFlush(address) >>
                {
                    arguments -> def updateAt = arguments.get(0).getUpdateAt()
                        assert updateAt != null
                } >> address
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
        service.erase(id)

        then:
        1 * service.personRepository.deleteAddressReference(_)
        1 * service.companyRepository.deleteAddressReference(_)
        1 * service.addressRepository.delete(_)
    }

    def "Address - toDTO - happy path"() {
        given:
        def address = buildAddress()

        when:
        def response = service.toDTO(address)

        then:
        response.getFmtAddress() == 'Avenida GroovySpockTest, 123. San Francisco - California - USA'
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
