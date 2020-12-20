package com.paulo.rodrigues.librarybookstore.repository;

import com.paulo.rodrigues.librarybookstore.common.BookEnvironment;
import com.paulo.rodrigues.librarybookstore.config.BaseIntegrationTestCase;
import com.paulo.rodrigues.librarybookstore.dto.BookDTO;
import com.paulo.rodrigues.librarybookstore.filter.BookFilter;
import com.paulo.rodrigues.librarybookstore.filter.PageableFilter;
import com.paulo.rodrigues.librarybookstore.service.BookService;
import com.paulo.rodrigues.librarybookstore.utils.PagedResult;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

/**
 *
 * @author paulo.rodrigues
 */
@ActiveProfiles(profiles = "test")
public class BookRepositoryTest extends BaseIntegrationTestCase {
    
    private BookService bookService;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Before
    public void setUp() {
        bookService = new BookService();
    }
    
    @Test
//    @GivenEnvironment(BookEnvironment.class)
    public void deveRetornarUmaListaBooksAtivas() {
        //when(fonteRepositoryMock.findByFiltro(Mockito.any(FiltroFonteDTO.class))).thenReturn(montaListaResultadoFonteDTO());
//        PagedResult<BookDTO> listBooks = bookService.findPageble(montaFiltroTodasBooks());
//        assertThat(listBooks.getElementos()).isNotEmpty();
//        assertThat(listBooks.getElementos()).hasSize(5);
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
    
    private PageableFilter montaFiltroTodasBooks(){
        return PageableFilter.builder()
                 .currentPage(1)
                .rowsPerPage(10)
                .sortColumn("name")
                .sort("ASC")
                .offset(0)                
                .build();
    }
    
    

}
