
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

import com.paulo.rodrigues.librarybookstore.address.repository.AddressRepository
import com.paulo.rodrigues.librarybookstore.address.service.AddressService
import spock.lang.*

import static ObjectMother.*

class AddressServiceTest extends Specification {

    AddressService service

    void setup() {
        service = new AddressService()
        service.addressRepository = Mock(AddressRepository)
    }

    def "Address - findById - happy path"() {
        given:
        def address = buildAddress()
        def id = 1
        //repository.findById(id) >> address

        when:
        def response = service.findById(id)

        then:
        1 * service.addressRepository.findById(id) >> Optional.of(address)

        and:
        response == address
    }
}
