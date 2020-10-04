/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author paulo
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class LibraryStoreBooksException extends Exception { 
    
//    private static final long serialVersionUID = 1L;

    public LibraryStoreBooksException(String message){
        super(message);
    }
}