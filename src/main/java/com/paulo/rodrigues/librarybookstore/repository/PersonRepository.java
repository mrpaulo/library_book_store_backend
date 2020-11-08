/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.repository;

import com.paulo.rodrigues.librarybookstore.model.Person;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 *
 * @author paulo
 */

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    public Person findByCpf(String cpf);
    
    @Query("SELECT p "
            + " FROM Person p "
            + " WHERE (:id IS NULL OR p.id = :id) "
            + " AND (:name IS NULL OR :name = '' OR p.name like CONCAT('%',:name,'%')) "
            + " AND (:cpf IS NULL OR :cpf = '' OR p.cpf LIKE CONCAT('%',:cpf,'%')) "
            + " AND (:sex IS NULL OR :sex = '' OR p.sex = :sex) "
            + "")
    public Page<Person> findPageble(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("cpf") String cpf,            
            @Param("sex") String sex,
            Pageable page);
}
