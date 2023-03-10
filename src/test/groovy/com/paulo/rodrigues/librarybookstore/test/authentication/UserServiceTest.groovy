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

import com.paulo.rodrigues.librarybookstore.address.service.AddressService
import com.paulo.rodrigues.librarybookstore.authentication.repository.RoleRepository
import com.paulo.rodrigues.librarybookstore.authentication.repository.UserRepository
import com.paulo.rodrigues.librarybookstore.authentication.service.UserService
import com.paulo.rodrigues.librarybookstore.utils.*
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import static com.paulo.rodrigues.librarybookstore.test.ObjectMother.*

class UserServiceTest extends Specification {

    UserService service

    void setup() {
        service = new UserService()
        service.userRepository = Mock(UserRepository)
        service.addressService = Mock(AddressService)
        service.passwordEncoder = Mock(PasswordEncoder)
        service.roleRepository = Mock(RoleRepository)
    }

    def "User - findAll - happy path"() {
        given:
        def users = buildUsers()

        when:
        def response = service.findAll()

        then:
        1 * service.userRepository.findAll() >> users

        and:
        response.size() == 1
    }

    def "User - findPageable - happy path"() {
        given:
        def usersPage = buildUsersPage()
        def filter = buildUserFilter()
        def pageable = FormatUtils.getPageRequest(filter)

        when:
        def response = service.findPageable(filter, pageable)

        then:
        1 * service.userRepository.findPageable(*_) >> usersPage

        and:
        response.size() == 1
    }

    def "User - findById - happy path"() {
        given:
        def user = buildUser()
        def id = 1

        when:
        def response = service.findById(id)

        then:
        1 * service.userRepository.findById(id) >> Optional.of(user)

        and:
        response == user
    }

    def "User - findById - should throw an exception"() {
        given:
        def id = 1

        when:
        service.findById(id)

        then:
        def e = thrown(NotFoundException)

        and:
        e.getMessage() != null
        e.getMessage() == MessageUtil.getMessage("USER_NOT_FOUND") + " ID: " + id
    }

    def "User - create - happy path"() {
        given:
        def user = buildUser()
        service.passwordEncoder.encode(_) >> "test"

        when:
        def response = service.create(user)

        then:
        1 * service.userRepository.saveAndFlush(user) >> user

        and:
        response != null
        response.getName() == buildUserDTO().getName()
    }

    def "User - create - should throw a assertion error"() {
        given:
        def user = null

        when:
        def response = service.create(user)

        then:
        def e = thrown(AssertionError)

        and:
        response == null

        and:
        e.getMessage() == MessageUtil.getMessage("USER_IS_NULL")
    }

    def "User - save - happy path"() {
        given:
        def user = buildUser()

        when:
        service.save(user)

        then:
        1 * service.userRepository.saveAndFlush(user) >>
                {
                    arguments -> def createAt = arguments.get(0).getCreateAt()
                        assert createAt != null
                } >> user
    }

    def "User - save validation - should throw an exception when username #scenario"() {
        given:
        def user = buildUser(username: value)

        when:
        service.save(user)

        then:
        def e = thrown(LibraryStoreBooksException)
        e.getMessage() == message

        where:
        scenario                    | value                                                | message
        'is null'                   | null                                                 | MessageUtil.getMessage("USERNAME_NOT_INFORMED")
        'is longer than max size'   | buildRandomString(ConstantsUtil.MAX_SIZE_NAME + 1)   | MessageUtil.getMessage("USERNAME_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + "")
    }

    def "User - save validation - should throw an exception when #scenario"() {
        when:
        service.save(user)

        then:
        def e = thrown(LibraryStoreBooksException)
        e.getMessage() == message

        where:
        scenario                               | user                                                                             | message
        'name is longer than max size'         | buildUser(name: buildRandomString(ConstantsUtil.MAX_SIZE_NAME + 1))              | MessageUtil.getMessage("USER_NAME_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + "")
        'email is longer than max size'        | buildUser(email: buildRandomString(ConstantsUtil.MAX_SIZE_NAME + 1))             | MessageUtil.getMessage("USER_EMAIL_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + "")
        'password is null'                     | buildUser(password: null)                                                        | MessageUtil.getMessage("USER_PASSWORD_NOT_INFORMED")
        'password is longer than max size'     | buildUser(password: buildRandomString(ConstantsUtil.MAX_SIZE_NAME + 1))          | MessageUtil.getMessage("USER_PASSWORD_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + "")
        'sex is different of F, M, O or N'     | buildUser(sex: 'X')                                                              | MessageUtil.getMessage("USER_SEX_INVALID")
        'sex is bigger than max size'          | buildUser(sex: buildRandomString(2))                                             | MessageUtil.getMessage("USER_SEX_INVALID")
        'cpf is longer than max size'          | buildUser(cpf: buildRandomString(ConstantsUtil.MAX_SIZE_CPF + 1))                | MessageUtil.getMessage("USER_CPF_INVALID")
        'cpf is invalid'                       | buildUser(cpf: '12345678900')                                                    | MessageUtil.getMessage("USER_CPF_INVALID")
    }

    def "User - edit - happy path"() {
        given:
        def user = buildUser()
        def userDTO = buildUserDTO()
        def id = 99
        service.userRepository.findById(id) >> Optional.of(user)

        when:
        service.edit(id, userDTO)

        then:
        1 * service.userRepository.saveAndFlush(_) >> user
    }

    def "User - delete - happy path"() {
        given:
        def user = buildUser()
        def id = 99
        service.userRepository.findById(id) >> Optional.of(user)

        when:
        service.delete(id)

        then:
        1 * service.userRepository.delete(_)
    }

    def "User - userToDTO - happy path"() {
        given:
        def user = buildUser()

        when:
        def response = service.userToDTO(user)

        then:
        response.getName() == buildUserDTO().getName()
    }

    def "User - userFromDTO - happy path"() {
        given:
        def user = buildUserDTO()

        when:
        def response = service.userFromDTO(user)

        then:
        response.getName() == buildUser().getName()
    }

    def "User - usersToDTOs - happy path"() {
        given:
        def users = buildUsers()

        when:
        def response = service.usersToDTOs(users)

        then:
        response.size() == 1
    }

    def "User - getAllRoles - happy path"() {
        when:
        service.getAllRoles()

        then:
        1 * service.roleRepository.findAll()
    }

    def "User - changeUserPassword - happy path"() {
        given:
        def updatePassword = buildUpdatePassword()
        def user = buildUser()
        service.userRepository.findByEmail(_) >> user
        service.passwordEncoder.matches(*_) >> true

        when:
        service.changeUserPassword(updatePassword)

        then:
        1 * service.userRepository.save(_) >> user
    }

    def "User - changeUserPassword - should thrown an exception"() {
        given:
        def updatePassword = buildUpdatePassword()
        def user = buildUser()
        service.userRepository.findByEmail(_) >> user
        service.passwordEncoder.matches(*_) >> false

        when:
        service.changeUserPassword(updatePassword)

        then: "should thrown an exception the passwords don't match"
        def e = thrown(LibraryStoreBooksException)
        e.getMessage() == MessageUtil.getMessage("INCORRECT_PASSWORD")
    }
}
