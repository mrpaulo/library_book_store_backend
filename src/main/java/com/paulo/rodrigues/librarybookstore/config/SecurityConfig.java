/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.session.SessionManagementFilter;

/**
 *
 * @author paulo
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    FilterAcesso filterAcesso() {
        FilterAcesso filter = new FilterAcesso();
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(filterAcesso(), SessionManagementFilter.class)
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/source/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll().anyRequest().authenticated()
                .and().httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("teste").password("teste").roles("USER");
    }
}
