/*
 * Copyright (C) 2021 paulo.rodrigues
 * Profile: <https://github.com/mrpaulo>
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
package com.paulo.rodrigues.librarybookstore.authentication.controller;

import com.paulo.rodrigues.librarybookstore.authentication.dto.UpdatePassword;
import com.paulo.rodrigues.librarybookstore.authentication.dto.UserDTO;
import com.paulo.rodrigues.librarybookstore.authentication.filter.UserFilter;
import com.paulo.rodrigues.librarybookstore.authentication.model.Login;
import com.paulo.rodrigues.librarybookstore.authentication.model.Role;
import com.paulo.rodrigues.librarybookstore.authentication.model.User;
import com.paulo.rodrigues.librarybookstore.authentication.service.UserService;
import com.paulo.rodrigues.librarybookstore.utils.*;

import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.paulo.rodrigues.librarybookstore.utils.ConstantsUtil.*;
/**
 *
 * @author paulo.rodrigues
 */
@Log4j2
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping(USERS_V1_BASE_API)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(GET_ALL_PATH)
    public List<UserDTO> getAll() {
        try {
            List<UserDTO> usersList = userService.findAll();
            return usersList.stream().sorted(Comparator.comparing(UserDTO::getUsername)).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Exception on findPageable message={}", e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @PostMapping(FIND_PAGEABLE_PATH)
    public List<UserDTO> findPageable(@RequestBody UserFilter filter, HttpServletRequest req, HttpServletResponse res) {
        try {
            Pageable pageable = FormatUtils.getPageRequest(filter);
            Page<User> result = userService.findPageable(filter, pageable);
            res.addHeader("totalCount", String.valueOf(result.getTotalElements()));
            return userService.usersToDTOs(result.getContent()).stream().sorted(Comparator.comparing(UserDTO::getUsername)).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Exception on findPageable message={}", e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @GetMapping(GET_BY_ID_PATH)
    public ResponseEntity<User> getById(@PathVariable(value = "id") Long userId) throws LibraryStoreBooksException, NotFoundException {
        try {
            User user = userService.findById(userId);
            user.setPassword(null);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            log.error("Exception on getById bookId={}, message={}", userId, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @GetMapping(GET_BY_NAME_PATH)
    public ResponseEntity<List<UserDTO>> getByName(@PathVariable(value = "name") String nameOfUser) throws LibraryStoreBooksException {
        try {
            return ResponseEntity.ok().body(userService.findByName(nameOfUser));
        } catch (Exception e) {
            log.error("Exception on getByName nameOfUser={}, message={}", nameOfUser, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @PostMapping()
    public ResponseEntity<UserDTO> create(@RequestBody User user) throws InvalidRequestException, DataValidationException {
        try {
            return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on create user={}, type=DataIntegrityViolationException, message={}", user, e.getMessage());
            String codMessage =
                    Objects.requireNonNull(e.getMessage()).contains("ConstraintViolationException") ?
                            "USER_FIELDS_DUPLICATED" :
                            "DATA_INTEGRITY_VIOLATION";
            throw new DataValidationException(MessageUtil.getMessage(codMessage));
        } catch (Exception e) {
            log.error("Exception on create user={}, message={}", user, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @PutMapping(UPDATE_PATH)
    public ResponseEntity<UserDTO> update(@PathVariable(value = "id") Long userId, @RequestBody UserDTO userDTO) throws InvalidRequestException, NotFoundException, DataValidationException {
        try {
            return ResponseEntity.ok(userService.edit(userId, userDTO));
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on create user={}, type=DataIntegrityViolationException, message={}", userDTO, e.getMessage());
            String codMessage =
                    Objects.requireNonNull(e.getMessage()).contains("ConstraintViolationException") ?
                            "USER_FIELDS_DUPLICATED" :
                            "DATA_INTEGRITY_VIOLATION";
            throw new DataValidationException(MessageUtil.getMessage(codMessage));
        } catch (Exception e) {
            log.error("Exception on update userDTO={}, message={}", userDTO, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @DeleteMapping(DELETE_PATH)
    public Map<String, Long> delete(@PathVariable(value = "id") Long userId) throws LibraryStoreBooksException, NotFoundException {
        try {
            userService.delete(userId);
            Map<String, Long> response = new HashMap<>();
            response.put("id", userId);
            return response;
        } catch (Exception e) {
            log.error("Exception on delete userId={}, message={}", userId, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @PostMapping(UPDATE_USER_PATH)
    public ResponseEntity<String> changeUserPassword(@RequestBody UpdatePassword updatePassword) throws LibraryStoreBooksException {
        try {
            userService.changeUserPassword(updatePassword);
            return ResponseEntity.status(HttpStatus.OK).body("Updated");
        } catch (Exception e) {
            log.error("Exception on changeUserPassword message={}", e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @GetMapping(GET_ROLES_PATH)
    public ResponseEntity<List<Role>> getAllRoles() {
        try {
            return ResponseEntity.ok().body(userService.getAllRoles());
        } catch (Exception e) {
            log.error("Exception on getAllRoles message={}", e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }
}
