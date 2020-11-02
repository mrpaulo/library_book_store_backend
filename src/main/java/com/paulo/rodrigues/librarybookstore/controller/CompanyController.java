/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.controller;

import com.paulo.rodrigues.librarybookstore.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author paulo.rodrigues
 */
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1/company")
public class CompanyController {
    
    @Autowired
    public CompanyRepository companyRepository;
    
    
}
