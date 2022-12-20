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
package com.paulo.rodrigues.librarybookstore;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaRepositories(enableDefaultTransactions=false)
@EnableAutoConfiguration
public class LibraryBookStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryBookStoreApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}

	@PostConstruct
	public void init(){
		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-3:00"));
		System.out.println("Starting Date in GMT: " + new Date());
	}
}
