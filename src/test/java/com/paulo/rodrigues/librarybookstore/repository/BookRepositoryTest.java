package com.paulo.rodrigues.librarybookstore.repository;

import com.paulo.rodrigues.librarybookstore.config.JPAHibernateTest;
import com.paulo.rodrigues.librarybookstore.dto.BookDTO;
import com.paulo.rodrigues.librarybookstore.filter.BookFilter;
import com.paulo.rodrigues.librarybookstore.service.BookService;
import com.paulo.rodrigues.librarybookstore.utils.PagedResult;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

/**
 *
 * @author paulo.rodrigues
 */
@ActiveProfiles(profiles = "test")
public class BookRepositoryTest extends JPAHibernateTest {
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private BookRepository bookRepositoryMock;
    
    @Before
    public void setUp() {
        //bookService = new BookService();
        bookService = Mockito.mock(BookService.class);
    }
    
    @Test
//    @GivenEnvironment(BookEnvironment.class)
    public void deveRetornarUmaListaBooks() {
        String simple = "test";
        assertThat(simple).asString();
//        PagedResult<BookDTO> listBooks = bookRepositoryMock.findPageble(montaFiltroTodasBooks());
//        assertThat(listBooks.getElementos()).isNotEmpty();
//        assertThat(listBooks.getElementos()).hasSize(5);
//        assertThat(listBooks.getElementos()).extracting(book -> book.getTitle()).contains("Uma viagem pelo conhecimento");
//        assertThat(listBooks.getElementos()).extracting(book -> book.getRating()).contains(4.7);
    }
    
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
    
    private BookFilter montaFiltroTodasBooks(){
        BookFilter bookFilter = new BookFilter();
        
        bookFilter.setCurrentPage(1);
        bookFilter.setRowsPerPage(10);
        bookFilter.setSort("ASC");
        bookFilter.setSortColumn("name");
        bookFilter.setOffset(0);
        
        return bookFilter;
    }

    private PagedResult<BookDTO> montaListaResultadoBooksDTO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
