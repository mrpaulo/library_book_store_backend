/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.filter;

import com.paulo.rodrigues.librarybookstore.model.BookSubject;
import java.util.Date;
import lombok.AllArgsConstructor;
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
public class BookFilter extends PageableFilter {

    private String title;
    private String author;
    private String publisher;
    private BookSubject subject;
    private Date publishDate;
}
