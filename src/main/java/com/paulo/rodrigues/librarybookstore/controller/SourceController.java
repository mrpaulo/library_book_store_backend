/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author paulo
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/source")
public class SourceController {
	
	@GetMapping()
	public Map<String, String> getSouce() {
		Map<String, String> retorno = new HashMap<>();
		retorno.put("Projeto frontend ReactJS", "https://github.com/mrpaulo/front_person_crud");
		retorno.put("Projeto backend Java Spring Boot", "https://github.com/mrpaulo/PersonCRUD");
		
		return retorno;
	}

}
