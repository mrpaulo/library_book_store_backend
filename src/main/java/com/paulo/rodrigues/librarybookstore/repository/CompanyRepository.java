/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.repository;

import com.paulo.rodrigues.librarybookstore.model.Company;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author paulo.rodrigues
 */
public interface CompanyRepository extends JpaRepository<Company, Long>{
    
    public Company findByCnpj (String cnpj);

    @Query("SELECT c "
            + " FROM Company c "
            + " WHERE (:id IS NULL OR c.id = :id) "
            + " AND (:name IS NULL OR :name = '' OR c.name LIKE CONCAT('%',:name,'%')) "
            + " AND (:cnpj IS NULL OR :cnpj = '' OR c.cnpj LIKE CONCAT('%',:cnpj,'%')) "
//            + " AND ((:startDate IS NULL AND :finalDate IS NULL) OR (c.createDate BETWEEN :startDate AND :finalDate)) "
            + " AND ((coalesce(:startDate, null) is null AND coalesce(:finalDate, null) is null) OR (c.createDate BETWEEN :startDate AND :finalDate)) "
            + "")
    public Page<Company> findPageble(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("cnpj") String cnpj,                        
            @Param("startDate") Date startDate,
            @Param("finalDate") Date finalDate,
            Pageable page);
    
}
