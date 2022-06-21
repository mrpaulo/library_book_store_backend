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
package com.paulo.rodrigues.librarybookstore.repository;

import com.paulo.rodrigues.librarybookstore.book.repository.LanguageRepository;
import com.paulo.rodrigues.librarybookstore.book.repository.BookSubjectRepository;
import com.paulo.rodrigues.librarybookstore.book.repository.BookRepository;
import com.paulo.rodrigues.librarybookstore.config.JPAHibernateTest;
import com.paulo.rodrigues.librarybookstore.book.dto.BookDTO;
import com.paulo.rodrigues.librarybookstore.book.filter.BookFilter;
import com.paulo.rodrigues.librarybookstore.book.model.Book;
import com.paulo.rodrigues.librarybookstore.book.service.BookService;
import com.paulo.rodrigues.librarybookstore.publisher.service.PublisherService;
import com.paulo.rodrigues.librarybookstore.author.service.AuthorService;
import com.paulo.rodrigues.librarybookstore.utils.PagedResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

/**
 *
 * @author paulo.rodrigues
 */
@ActiveProfiles(profiles = "test")
public class BookRepositoryTest extends JPAHibernateTest {
        
    @Autowired
    private BookRepository bookRepositoryMock;
    private ModelMapper modelMapper;
    private AuthorService personServiceMock;
    private PublisherService companyServiceMock;
    private LanguageRepository languageRepositoryMock;
    private BookSubjectRepository bookSubjectRepositoryMock;
    private BookService bookServiceMock;
        
    @Before
    public void setUp() {
        personServiceMock = Mockito.mock(AuthorService.class);
        companyServiceMock = Mockito.mock(PublisherService.class);
        languageRepositoryMock = Mockito.mock(LanguageRepository.class);
        bookSubjectRepositoryMock = Mockito.mock(BookSubjectRepository.class);
        
        bookServiceMock = new BookService(bookRepositoryMock, personServiceMock, companyServiceMock, languageRepositoryMock, bookSubjectRepositoryMock, modelMapper);
    }
    
    @Test
    public void testSimple(){
        String simple = "test";
        assertThat(simple).asString();
    }
    
//    @Test
//    public void testGetObjectById_success() {
//        Book book = em.find(Book.class, 1);        
//        assertNotNull(book);
//    }
    
  //  @Test
//    @GivenEnvironment(BookEnvironment.class)
//    public void deveRetornarUmaListaBooks() {        
//        PagedResult<BookDTO> listBooks = bookServiceMock.findPageble(montaFiltroTodasBooks());
//        assertThat(listBooks.getElementos()).isNotEmpty();
//        assertThat(listBooks.getElementos()).hasSize(5);
//        assertThat(listBooks.getElementos()).extracting(book -> book.getTitle()).contains("Uma viagem pelo conhecimento");
//        assertThat(listBooks.getElementos()).extracting(book -> book.getRating()).contains(4.7);
//    }
    
//    @Test
//    @GivenEnvironment(BookEnvironment.class)
//    public void deveRetornarUmaListaBooks() {
//        PagedResult<BookDTO> listBooks = bookService.consultaBooks(montaFiltroTodasBooks());
//        assertThat(listBooks.getElementos()).isNotEmpty();
//        assertThat(listBooks.getElementos()).hasSize(5);
//        assertThat(listBooks.getElementos()).extracting(pref -> pref.getFlForaUso()).contains("S");
//    }
//    
//    @Test
//    @GivenEnvironment(BookEnvironment.class)
//    public void deveRetornarUmaBookPorCdMunicipio() {
//        PagedResult<BookDTO> listBooks = bookService.consultaBooks(montaFiltroMunicipioPorCdMunicipio());
//        assertThat(listBooks.getElementos()).isNotEmpty();
//        assertThat(listBooks.getElementos()).hasSize(1);
//        assertThat(listBooks.getElementos()).extracting(pref -> pref.getNmMunicipio()).contains("Adamantina");
//    }
    
//    private BookFilter montaFiltroTodasBooks(){
//        BookFilter bookFilter = new BookFilter();
//        
//        bookFilter.setCurrentPage(1);
//        bookFilter.setRowsPerPage(10);
//        bookFilter.setSort("ASC");
//        bookFilter.setSortColumn("name");
//        bookFilter.setOffset(0);
//        
//        return bookFilter;
//    }

    private PagedResult<BookDTO> montaListaResultadoBooksDTO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
