/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.repository;

import com.paulo.rodrigues.librarybookstore.dto.BookDTO;
import com.paulo.rodrigues.librarybookstore.filter.BookFilter;
import com.paulo.rodrigues.librarybookstore.utils.PagedResult;

/**
 *
 * @author paulo.rodrigues
 */
public interface BookRepositoryCustom {
    
    PagedResult<BookDTO> findPageble(BookFilter filter);
}
