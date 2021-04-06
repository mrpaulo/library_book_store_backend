/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
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
    private static final String ERROR_MESSAGE_NOT_FOUND = "Error message not found";

    public static String getMessage(String... args) {

        if (args.length == 1) {
            
            return getRB(args[0]);
            
        } else if (args.length > 1) {

            List<String> params = new ArrayList<>();
            for (int i = 1; i < args.length; i++) {
                params.add(args[i]);
            }

            String[] newParams = params.stream().toArray(String[]::new);

            return MessageFormat.format(getRB(args[0]), newParams);
        }

        return ERROR_MESSAGE_NOT_FOUND;
    }

    public static String getRB(String message) {
        if (messagesRB.containsKey(message)) {
            return messagesRB.getString(message);
        }
        return ERROR_MESSAGE_NOT_FOUND;
    }

}
