/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.enums;

/**
 *
 * @author paulo
 */
public enum EAuthSituation {
    NAO_AUTORIZADO(0, "Não autorizado"),
    AUTORIZADO(1, "Autorizado");
    
   int cod;
   String description;
   
   private EAuthSituation (int cod, String description) {
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
   
    public EAuthSituation getAuthSituationByCod (int cod){
        if(cod == 0) {
            return NAO_AUTORIZADO;
        } else {
            return AUTORIZADO;
        }
    }
}
