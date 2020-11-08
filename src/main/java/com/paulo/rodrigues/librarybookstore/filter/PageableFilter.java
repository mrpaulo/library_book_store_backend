/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.filter;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author paulo.rodrigues
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PageableFilter {
    
    private int currentPage = 1;
    private int registerByPage = 10;
    private String SortColumn;
    private String sort = "asc";
    
    private Integer id;
    private String name;
    private Date startDate;
    private Date finalDate;
}
