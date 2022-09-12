package com.myProject.service.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddUser implements Command{
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("----------Add user-------");
    }
}
