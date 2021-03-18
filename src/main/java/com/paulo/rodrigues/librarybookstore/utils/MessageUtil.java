/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author paulo.rodrigues
 */
public class MessageUtil {
    
    //@Value("${app.language}")
    //private static final String appLanguage;
    
    private static final Locale locale = new Locale("pt");
    private static final ResourceBundle messagesRB = ResourceBundle.getBundle("resourcebundle.messages", locale);
    
    
    public static String getMessage(String mensagem) {
        if (messagesRB.containsKey(mensagem)) {
            return messagesRB.getString(mensagem);
        }
        //Logar no console uma mensagem indicando que não achou o rotulo
        return "Errou!!!";
    }
    
}
