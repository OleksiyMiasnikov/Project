package com.myProject.util;

import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("resources");
        System.out.println(bundle.getString("index_jsp.form.title"));
        System.out.println(ResourceBundle
                .getBundle("resources", bundle.getLocale())
                .getString("index_jsp.form.title")
        );
    }
}
