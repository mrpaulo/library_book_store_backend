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
package com.paulo.rodrigues.librarybookstore.book.filter;

import com.paulo.rodrigues.librarybookstore.utils.PageableFilter;

import java.time.LocalDate;
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
    private String subjectName;
    private LocalDate publishDate;
    private Boolean adultsOnly;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("BookFilter{");
        if (title != null && !title.isEmpty()) {
            sb.append("title='").append(title).append('\'').append(", ");
        }
        if (author != null && !author.isEmpty()) {
            sb.append("author='").append(author).append('\'').append(", ");
        }
        if (publisher != null && !publisher.isEmpty()) {
            sb.append("publisher='").append(publisher).append('\'').append(", ");
        }
        if (subjectName != null && !subjectName.isEmpty()) {
            sb.append("subjectName='").append(subjectName).append('\'').append(", ");
        }
        if (publishDate != null) {
            sb.append("publishDate=").append(publishDate).append(", ");
        }
        if (adultsOnly != null) {
            sb.append("adultsOnly=").append(adultsOnly).append(", ");
        }
        return toStringSuper(sb).toString();
    }
}
