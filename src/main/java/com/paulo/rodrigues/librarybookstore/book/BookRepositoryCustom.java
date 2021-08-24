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
package com.paulo.rodrigues.librarybookstore.book;

import com.paulo.rodrigues.librarybookstore.book.BookDTO;
import com.paulo.rodrigues.librarybookstore.author.PersonDTO;
import com.paulo.rodrigues.librarybookstore.utils.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.book.BookFilter;
import com.paulo.rodrigues.librarybookstore.book.Book;
import com.paulo.rodrigues.librarybookstore.utils.PagedResult;
import java.util.List;

/**
 *
 * @author paulo.rodrigues
 */
public interface BookRepositoryCustom {
    
    PagedResult<BookDTO> findPageble(BookFilter filter);
    List<PersonDTO> getListAuthorsDTOByBookId(Long bookId) throws LibraryStoreBooksException;
    List<Book> getBooksFromAuthor(String authorName);
    boolean deleteBookAuthor (long authorId, long bookId);
}
