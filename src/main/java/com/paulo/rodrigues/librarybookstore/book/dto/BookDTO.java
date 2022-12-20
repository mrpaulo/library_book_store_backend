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
package com.paulo.rodrigues.librarybookstore.book.dto;

import com.paulo.rodrigues.librarybookstore.book.enums.EBookCondition;
import com.paulo.rodrigues.librarybookstore.book.enums.EBookFormat;
import com.paulo.rodrigues.librarybookstore.publisher.dto.PublisherDTO;
import com.paulo.rodrigues.librarybookstore.author.dto.AuthorDTO;
import com.paulo.rodrigues.librarybookstore.book.enums.EBookCondition;
import com.paulo.rodrigues.librarybookstore.book.enums.EBookFormat;

import java.time.LocalDate;
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
    private List<AuthorDTO> authors;
    private String languageName;
    private PublisherDTO publisher;
    private String subjectName;
    private String subtitle;
    private String review;
    private String link;
    private EBookFormat format;
    private EBookCondition condition;
    private int edition;
    private LocalDate publishDate;
    private Double rating;    
    private int length;
}
