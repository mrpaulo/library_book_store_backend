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
import com.paulo.rodrigues.librarybookstore.utils.*;

import java.util.*;
import javax.transaction.Transactional;

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
        return usersToDTOs(userRepository.findAll());
    }

    public Page<User> findPageable(UserFilter filter, Pageable pageable) {
        log.info("Finding pageable users by filter={}", filter);
        return userRepository.findPageable(
                filter.getId(),
                filter.getName(),
                filter.getCpf(),
                filter.getStartDate(),
                filter.getFinalDate(),
                pageable);
    }

    public User findById(Long userId) throws NotFoundException {
        log.info("Finding user by userId={}", userId);
        Optional<User> user = userRepository.findById(userId);
        if (user == null || !user.isPresent()) {
            log.error("User not found by userId={}", userId);
            throw new NotFoundException(MessageUtil.getMessage("USER_NOT_FOUND") + " ID: " + userId);
        }
        return user.get();
    }

    public List<UserDTO> findByName(String name) {
        log.info("Finding user by name={}", name);
        return usersToDTOs(userRepository.findByName(name));
    }

    public UserDTO create(User user) throws InvalidRequestException {
        assert user != null : MessageUtil.getMessage("USER_IS_NULL");
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
        return userToDTO(save(user));
    }

    public User save(User user) throws InvalidRequestException {
        user.validation();
        user.persistAt();
        log.info("Saving id={}, userName={}", user.getId(), user.getName());
        return userRepository.saveAndFlush(user);
    }

    public UserDTO edit(Long userId, UserDTO userDetail) throws InvalidRequestException, NotFoundException {
        User userToEdit = findById(userId);
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
        log.info("Updating user id={}, userName={}", userToEdit.getId(), userToEdit.getName());
        return userToDTO(save(userToEdit));
    }

    public void delete(Long userId) throws LibraryStoreBooksException, NotFoundException {
        User userToDelete = findById(userId);
        if(userToDelete.getAddress() != null){
            addressService.delete(userToDelete.getAddress().getId());
        }
        log.info("Deleting id={}, userName={}", userToDelete.getId(), userToDelete.getName());
        userRepository.delete(userToDelete);
    }

    public UserDTO userToDTO(User user) {
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

    public User userFromDTO(UserDTO dto) {
        return User.builder()
                .name(dto.getName())
                .username(dto.getUsername())
                .cpf(dto.getCpf())
                .birthdate(dto.getBirthdate())
                .email(dto.getEmail())
                .build();
    }

    public List<UserDTO> usersToDTOs(List<User> users) {
        return users.stream().map(this::userToDTO).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
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

    private boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return user!= null && passwordEncoder.matches(oldPassword, user.getPassword());
    }
}
