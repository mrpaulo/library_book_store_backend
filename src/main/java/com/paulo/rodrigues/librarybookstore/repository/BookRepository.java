/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.repository;

import com.paulo.rodrigues.librarybookstore.model.Book;
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
public interface BookRepository extends JpaRepository<Book, Long>{

    @Query("SELECT new com.paulo.rodrigues.librarybookstore.model.Book(b) "
            + " FROM Book b "
            + " WHERE "
            + " b.title like CONCAT('%',:title,'%') "
            + " AND (:author IS NULL OR :author = '' OR :author LIKE CONCAT('%',:author,'%')) "
            + " AND ((:startDate IS NULL AND :finalDate IS NULL) OR (b.publishDate BETWEEN :startDate AND :finalDate)) "
            + "")
    public Page<Book> findPageble(
            @Param("title") String title,
            @Param("author") String author,            
            @Param("publisher") String publisher,
            @Param("startDate") Date startDate,
            @Param("finalDate") Date finalDate,
            Pageable page);
    
}
