/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.utils;

import java.util.List;

/**
 *
 * @author paulo.rodrigues
 */
public class PagedResult<T> {

    public static final long DEFAULT_OFFSET = 0;
    public static final int DEFAULT_MAX_NO_OF_ROWS = 100;
    private long totalElementos;
    private long totalPaginas;
    private List<T> elementos;

    public PagedResult(List<T> elementos, long totalElementos, long totalPaginas) {
        this.elementos = elementos;
        this.totalElementos = totalElementos;
        this.totalPaginas = totalPaginas;
    }

    public long getTotalElementos() {
        return totalElementos;
    }

    public long getTotalPages() {
        return totalPaginas;
    }

    public List<T> getElementos() {
        return elementos;
    }

}
