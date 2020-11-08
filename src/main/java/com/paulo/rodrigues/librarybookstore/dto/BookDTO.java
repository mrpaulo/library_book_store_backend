/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.dto;

import com.paulo.rodrigues.librarybookstore.enums.EBookCondition;
import com.paulo.rodrigues.librarybookstore.enums.EBookFormat;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author paulo.rodrigues
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {
    
    private long id;
    private String title;
    private List<PersonDTO> authors;
    private String language;
    private CompanyDTO publisher;
    private String subject;
    private String subtitle;
    private String review;
    private String link;
    private EBookFormat format;
    private EBookCondition condition;
    private int edition;
    private Date publishDate;   
    private Double rating;    
    private int length;
}
