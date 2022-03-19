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
package com.paulo.rodrigues.librarybookstore.authentication.controller;

import com.paulo.rodrigues.librarybookstore.authentication.model.Login;
import com.paulo.rodrigues.librarybookstore.authentication.model.User;
import com.paulo.rodrigues.librarybookstore.authentication.repository.UserRepository;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author paulo.rodrigues
 */
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1/authentications")
public class AuthenticationController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    
   @Resource(name = "tokenServices")
    ConsumerTokenServices tokenServices;
    
    @GetMapping("/logout")
    public boolean logout(HttpServletRequest request) {
                
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            System.out.println("Logout");
            System.out.println(tokenValue);
            tokenServices.revokeToken(tokenValue);
        }
        
        return true;
    }
    
    @Secured({Login.ROLE_ADMIN})    
    @PostMapping("/save")
    public ResponseEntity<User> save(@RequestBody User user){
        user = this.userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Secured({Login.ROLE_ADMIN})
    @PutMapping("/edit")
    public ResponseEntity<User> edit(@RequestBody User user){
        user = this.userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Secured({Login.ROLE_CLIENT, Login.ROLE_ADMIN})
    @GetMapping("/users")
    public ResponseEntity<Page<User>> list(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return new ResponseEntity<>(userRepository.findAll(pageable), HttpStatus.OK);
    }
    
    @ResponseBody
    @Secured({Login.ROLE_CLIENT, Login.ROLE_ADMIN})
    @GetMapping("/user-auth")
    public User user() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}