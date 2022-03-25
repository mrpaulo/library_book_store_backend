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
import com.paulo.rodrigues.librarybookstore.authentication.model.Role;
import com.paulo.rodrigues.librarybookstore.authentication.model.User;
import com.paulo.rodrigues.librarybookstore.authentication.service.UserService;
import com.paulo.rodrigues.librarybookstore.utils.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author paulo.rodrigues
 */
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<UserDTO> getAll() {
        List<UserDTO> usersList = userService.findAll();

        return usersList.stream().sorted(Comparator.comparing(UserDTO::getUsername)).collect(Collectors.toList());
    }

    @PostMapping("/fetch")
    public List<UserDTO> getAllPageble(@RequestBody UserFilter filter, HttpServletRequest req, HttpServletResponse res) {
        Pageable pageable = FormatUtils.getPageRequest(filter);
        Page<User> result = userService.findPageble(filter, pageable);
        res.addHeader("totalCount", String.valueOf(result.getTotalElements()));

        return userService.toListDTO(result.getContent()).stream().sorted(Comparator.comparing(UserDTO::getUsername)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable(value = "id") Long userId) throws LibraryStoreBooksException {
        return ResponseEntity.ok().body(userService.findById(userId));
    }

    @GetMapping("/fetch/{name}")
    public ResponseEntity<List<UserDTO>> getByName(@PathVariable(value = "name") String name) throws LibraryStoreBooksException {
        return ResponseEntity.ok().body(userService.findByName(name));
    }

    @PostMapping()
    public UserDTO create(@RequestBody User user) throws LibraryStoreBooksException {
        return userService.create(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable(value = "id") Long userId, @RequestBody UserDTO userDetalhes) throws LibraryStoreBooksException {
        return ResponseEntity.ok(userService.edit(userId, userDetalhes));
    }

    @DeleteMapping("/{id}")
    public Map<String, Long> delete(@PathVariable(value = "id") Long userId) throws LibraryStoreBooksException {
        userService.erase(userId);
        Map<String, Long> response = new HashMap<>();
        response.put("id", userId);

        return response;
    }

    @PostMapping("/update")
    public ResponseEntity<String> changeUserPassword(@RequestBody UpdatePassword updatePassword) throws LibraryStoreBooksException {
        userService.changeUserPassword(updatePassword);

        return ResponseEntity.status(HttpStatus.OK).body("Updated");
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() throws LibraryStoreBooksException {
        return ResponseEntity.ok().body(userService.getAllRoles());
    }
}
