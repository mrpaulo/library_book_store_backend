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

import com.paulo.rodrigues.librarybookstore.address.service.AddressService
import com.paulo.rodrigues.librarybookstore.book.repository.BookRepository
import com.paulo.rodrigues.librarybookstore.publisher.repository.PublisherRepository
import com.paulo.rodrigues.librarybookstore.publisher.service.PublisherService
import com.paulo.rodrigues.librarybookstore.utils.ConstantsUtil
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils
import com.paulo.rodrigues.librarybookstore.utils.InvalidRequestException
import com.paulo.rodrigues.librarybookstore.utils.LibraryStoreBooksException
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil
import com.paulo.rodrigues.librarybookstore.utils.NotFoundException
import spock.lang.Specification

import static com.paulo.rodrigues.librarybookstore.test.ObjectMother.*

class PublisherServiceTest extends Specification {

    PublisherService service

    void setup() {
        service = new PublisherService()
        service.publisherRepository = Mock(PublisherRepository)
        service.addressService = Mock(AddressService)
        service.bookRepository = Mock(BookRepository)
    }

    def "Publisher - findAll - happy path"() {
        given:
        def publishers = buildPublishers()
        service.addressService.toDTO(_) >> buildAddressDTO()

        when:
        def response = service.findAll()

        then:
        1 * service.publisherRepository.findAll() >> publishers

        and:
        response.size() == 1
    }

    def "Publisher - findPageable - happy path"() {
        given:
        def publishersPage = buildPublishersPage()
        def filter = buildPublisherFilter()
        def pageable = FormatUtils.getPageRequest(filter)
        service.addressService.toDTO(_) >> buildAddressDTO()

        when:
        def response = service.findPageable(filter, pageable)

        then:
        1 * service.publisherRepository.findPageble(*_) >> publishersPage

        and:
        response.size() == 1
    }

    def "Publisher - findById - happy path"() {
        given:
        def publisher = buildPublisher()
        def id = 1

        when:
        def response = service.findById(id)

        then:
        1 * service.publisherRepository.findById(id) >> Optional.of(publisher)

        and:
        response == publisher
    }

    def "Publisher - findByCnpj - happy path"() {
        given:
        def publisher = buildPublisher()
        def cnpj = cnpjToTest

        when:
        def response = service.findByCnpj(cnpj)

        then:
        1 * service.publisherRepository.findByCnpj(cnpj) >> publisher

        and:
        response == publisher
    }

    def "Publisher - findByCnpj - should throw an exception"() {
        given:
        def cnpj = cnpjToTest

        when:
        service.findByCnpj(cnpj)

        then:
        def e = thrown(NotFoundException)

        and:
        e.getMessage() != null
        e.getMessage() == MessageUtil.getMessage("PUBLISHER_NOT_FOUND") + " CNPJ: " + cnpj
    }

    def "Publisher - findByName - happy path"() {
        given:
        def publishers = buildPublishers()
        def name = nameForTest

        when:
        def response = service.findByName(name)

        then:
        1 * service.publisherRepository.findByName(name) >> publishers

        and:
        response.size() == 1
    }

    def "Publisher - findById - should throw an exception"() {
        given:
        def id = 1

        when:
        service.findById(id)

        then:
        def e = thrown(NotFoundException)

        and:
        e.getMessage() != null
        e.getMessage() == MessageUtil.getMessage("PUBLISHER_NOT_FOUND") + " ID: " + id
    }

    def "Publisher - create - happy path"() {
        given:
        def publisher = buildPublisher()

        when:
        def response = service.create(publisher)

        then:
        1 * service.publisherRepository.saveAndFlush(publisher) >> publisher

        and:
        response != null
        response.getName() == buildPublisherDTO().getName()
    }

    def "Publisher - save - happy path"() {
        given:
        def publisher = buildPublisher()

        when:
        service.save(publisher)

        then:
        1 * service.publisherRepository.saveAndFlush(publisher) >>
                {
                    arguments -> def createAt = arguments.get(0).getCreateAt()
                        assert createAt != null
                } >> publisher
    }

    def "Publisher - save validation - should throw an exception when name #scenario"() {
        given:
        def publisher = buildPublisher(name: value)

        when:
        service.save(publisher)

        then:
        def e = thrown(InvalidRequestException)
        e.getMessage() == message

        where:
        scenario                    | value                                                | message
        'is null'                   | null                                                 | MessageUtil.getMessage("PUBLISHER_NAME_NOT_INFORMED")
        'is bigger than max size'   | buildRandomString(ConstantsUtil.MAX_SIZE_NAME + 1)   | MessageUtil.getMessage("PUBLISHER_NAME_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + "")
    }

    def "Publisher - save validation - should throw an exception when #scenario"() {
        when:
        service.save(publisher)

        then:
        def e = thrown(InvalidRequestException)
        e.getMessage() == message

        where:
        scenario                               | publisher                                                                            | message
        'cnpj is empty'                        | buildPublisher(cnpj: '')                                                             | MessageUtil.getMessage("PUBLISHER_CNPJ_NOT_INFORMED")
        'cnpj is invalid'                      | buildPublisher(cnpj: buildRandomString(11))                                          | MessageUtil.getMessage("PUBLISHER_CNPJ_INVALID")
        'description is bigger than max size'  | buildPublisher(description: buildRandomString(ConstantsUtil.MAX_SIZE_LONG_TEXT + 1)) | MessageUtil.getMessage("PUBLISHER_DESCRIPTION_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_LONG_TEXT + "")
    }

    def "Publisher - checkAndSave - happy path"() {
        given:
        def publisher = buildPublisher()
        service.publisherRepository.findByCnpj(*_) >> publisher

        when:
        def response = service.checkAndSave(publisher)

        then:
        response == publisher
    }

    def "Publisher - checkAndSave dto - happy path"() {
        given:
        def publisher = buildPublisher()
        def publisherDTO = buildPublisherDTO()
        service.publisherRepository.findByCnpj(*_) >> publisher

        when:
        def response = service.checkAndSave(publisherDTO)

        then:
        response == publisher
    }

    def "Publisher - edit - happy path"() {
        given:
        def publisher = buildPublisher()
        def publisherDTO = buildPublisherDTO()
        def id = 99
        service.publisherRepository.findById(id) >> Optional.of(publisher)

        when:
        service.edit(id, publisherDTO)

        then:
        1 * service.publisherRepository.saveAndFlush(_)
    }

    def "Publisher - delete - happy path"() {
        given:
        def publisher = buildPublisher()
        def id = 99
        service.publisherRepository.findById(id) >> Optional.of(publisher)
        service.bookRepository.getBooksFromPublisherId(id) >> null

        when:
        service.delete(id)

        then:
        1 * service.publisherRepository.delete(_)
    }

    def "Publisher - publisherToDTO - happy path"() {
        given:
        def publisher = buildPublisher()

        when:
        def response = service.publisherToDTO(publisher)

        then:
        response.getName() == buildPublisherDTO().getName()
    }

    def "Publisher - publisherFromDTO - happy path"() {
        given:
        def publisher = buildPublisherDTO()
        service.publisherRepository.findByCnpj(_) >> buildPublisher()

        when:
        def response = service.publisherFromDTO(publisher)

        then:
        response.getName() == buildPublisher().getName()
    }

    def "Publisher - publishersToDTOs - happy path"() {
        given:
        def publishers = buildPublishers()

        when:
        def response = service.publishersToDTOs(publishers)

        then:
        response.size() == 1
    }
}
