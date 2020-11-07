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

/**
 *
 * @author paulo.rodrigues
 */
public interface BookRepositoryCustom {
    
    Page<Book> findPageble(String title, String author, String publisher, Date startDate, Date finalDate, Pageable page);
}
