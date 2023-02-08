/*
 * Copyright (C) 2021 paulo.rodrigues
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
package com.paulo.rodrigues.librarybookstore.authentication.service;

import com.paulo.rodrigues.librarybookstore.address.model.Address;
import com.paulo.rodrigues.librarybookstore.address.service.AddressService;
import com.paulo.rodrigues.librarybookstore.authentication.dto.UpdatePassword;
import com.paulo.rodrigues.librarybookstore.authentication.dto.UserDTO;
import com.paulo.rodrigues.librarybookstore.authentication.filter.UserFilter;
import com.paulo.rodrigues.librarybookstore.authentication.model.Login;
import com.paulo.rodrigues.librarybookstore.authentication.model.Role;
import com.paulo.rodrigues.librarybookstore.authentication.model.User;
import com.paulo.rodrigues.librarybookstore.authentication.repository.RoleRepository;
import com.paulo.rodrigues.librarybookstore.authentication.repository.UserRepository;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import com.paulo.rodrigues.librarybookstore.utils.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

import com.paulo.rodrigues.librarybookstore.utils.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author paulo.rodrigues
 */
@Service
@Transactional
@Log4j2
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<UserDTO> findAll() {
        return toListDTO(userRepository.findAll());
    }

    public Page<User> findPageable(UserFilter filter, Pageable pageable) {
        return userRepository.findPageable(
                filter.getId(),
                filter.getName(),
                filter.getCpf(),
                filter.getStartDate(),
                filter.getFinalDate(),
                pageable);
    }

    public User findById(Long userId) throws LibraryStoreBooksException {
        log.info("Finding user by userId={}", userId);
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            log.error("User not found by userId={}", userId);
            throw new LibraryStoreBooksException(MessageUtil.getMessage("USER_NOT_FOUND") + " ID: " + userId);
        }
        user.setPassword(null);
        return user;
    }

    public List<UserDTO> findByName(String name) {
        log.info("Finding user by name={}", name);
        return toListDTO(userRepository.findByName(name));
    }

    public UserDTO create(User user) throws LibraryStoreBooksException {
        if (user == null) {
            return null;
        }
        if (FormatUtils.isEmpty(user.getRoles())) {
            Role role = roleRepository.findByName(Login.ROLE_CLIENT);
            if (role == null) {
                role = new Role(Login.ROLE_CLIENT);
                role = roleRepository.save(role);
            }

            user.setRoles(Arrays.asList(role));
        }
        if (user.getAddress() != null) {
            addressService.create(user.getAddress());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Creating user name={}", user.getName());
        return toDTO(save(user));
    }

    public User save(User user) throws LibraryStoreBooksException {
        user.validation();
        user.persistAt();
        log.info("Saving id={}, userName={}", user.getId(), user.getName());
        return userRepository.saveAndFlush(user);
    }

    public UserDTO edit(Long userId, UserDTO userDetail) throws LibraryStoreBooksException {
        User userToEdit = findById(userId);
        //User volta com o pass null, encontrar forma de retornar não sobreescrever isso
        String pw = userToEdit.getPassword();
        Date createAt = userToEdit.getCreateAt();
        String createBy = userToEdit.getCreateBy();
        Address address = userToEdit.getAddress();
        
        ModelMapper mapper = new ModelMapper();
        userToEdit = mapper.map(userDetail, User.class);
        userToEdit.setAddress(address);
        userToEdit.setCreateAt(createAt);
        userToEdit.setCreateBy(createBy);
        userToEdit.setPassword(pw);
        log.info("Updating id={}, userName={}", userToEdit.getId(), userToEdit.getName());
        return toDTO(save(userToEdit));
    }

    public void delete(Long userId) throws LibraryStoreBooksException, NotFoundException {
        User userToDelete = findById(userId);
        if(userToDelete.getAddress() != null){
            addressService.delete(userToDelete.getAddress().getId());
        }
        log.info("Deleting id={}, userName={}", userToDelete.getId(), userToDelete.getName());
        userRepository.delete(userToDelete);
    }

    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .cpf(user.getCpf())
                .birthdate(user.getBirthdate())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }

    public User fromDTO(UserDTO dto) throws LibraryStoreBooksException {
        return User.builder()
                .name(dto.getName())
                .username(dto.getUsername())
                .cpf(dto.getCpf())
                .birthdate(dto.getBirthdate())
                .email(dto.getEmail())
                // .address(dto.getAddress() != null ? addressService.findById(dto.getAddress().getId()) : null)
                .build();
    }

    public List<UserDTO> toListDTO(List<User> users) {
        return users.stream().map(this::toDTO).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void changeUserPassword(UpdatePassword updatePassword) throws LibraryStoreBooksException {
        User user = userRepository.findByEmail(FormatUtils.getUsernameLogged());
        if (!checkIfValidOldPassword(user, updatePassword.getCurrentPassword())) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("INCORRECT_PASSWORD"));
        }
        user.setPassword(passwordEncoder.encode(updatePassword.getNewPassword()));
        userRepository.save(user);
    }

    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }
}
