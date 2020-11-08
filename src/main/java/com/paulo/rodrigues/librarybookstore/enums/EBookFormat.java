/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.enums;

/**
 *
 * @author paulo.rodrigues
 */
public enum EBookFormat {
    PRINTED_BOOK(0, "Livro impresso"),
    HARDCOVER(1, "Livro capa dura"),    
    KINDLE_EDITION(2, "Kindle"),
    AUDIO_BOOK(3, "Audio livro");
    
    int cod;
    String description;

    private EBookFormat(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public EBookFormat getBookCFormatByCod(int cod) {
        for (EBookFormat value : EBookFormat.values()) {
            if (cod == value.getCod()) {
                return value;
            }
        }
        return null;
    }
}
