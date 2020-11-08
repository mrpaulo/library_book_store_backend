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
public enum ETypePublicPlace {
    STREET(0, "Rua"),
    AVENUE(1, "Avenida"),
    SQUARE(2, "Praça"),
    SV(3, "Servidão"),
    BC(4, "Beco"),
    ROD(5, "Rodovia"),
    EST(6, "Estrada");
    
    int cod;
    String description;

    private ETypePublicPlace(int cod, String description) {
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
    
    public ETypePublicPlace getBookCFormatByCod(int cod) {
        for (ETypePublicPlace value : ETypePublicPlace.values()) {
            if (cod == value.getCod()) {
                return value;
            }
        }
        return null;
    }
}
