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
import com.paulo.rodrigues.librarybookstore.authentication.model.Token;
import com.paulo.rodrigues.librarybookstore.author.model.Author;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author paulo.rodrigues
 */
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1/authentiations")
public class authenticationController {
    
    @PostMapping()
    public Token login(@RequestBody Login login) {
        
        String aString="JUST_A_TEST_STRING";
        String token = UUID.nameUUIDFromBytes(aString.getBytes()).toString();
        Token response = new Token(login.getUserName(), token);
        
        return response;
    }
    
    @PostMapping("/valid")
    public boolean isTokenValid(@RequestBody Token token) {
        System.out.println("isTokenValid");
        System.out.println(token);
        
        return true;
    }
    
    @GetMapping("/{token}")
    public boolean logout(@PathVariable(value = "token") String token) {
        System.out.println("Logout");
        System.out.println(token);
        
        
        return true;
    }
    
}
