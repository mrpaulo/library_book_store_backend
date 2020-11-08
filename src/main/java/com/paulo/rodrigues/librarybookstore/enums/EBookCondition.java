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
public enum EBookCondition {
    USED(0, "Usado"),
    NEW(1, "Novo"),
    COLLECTABLE(2, "Colecionável");

    int cod;
    String description;

    private EBookCondition(int cod, String description) {
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

    public EBookCondition getBookConditionByCod(int cod) {
        for (EBookCondition value : EBookCondition.values()) {
            if (cod == value.getCod()) {
                return value;
            }
        }
        return null;
    }
}
