package com.myProject.service.command;

import com.myProject.entitie.Role;
import com.myProject.service.UserManager;
import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddUserTest {

    @Test
    void execute() throws DaoException, AppException {
        UserManager manager = mock(UserManager.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        ServletContext context = mock(ServletContext.class);
        HttpSession session = mock(HttpSession.class);

        List<Role> list = List.of(Role.builder()
                                        .id(1)
                                        .name("test_admin")
                                        .build(),
                                Role.builder()
                                        .id(2)
                                        .name("test_cashier")
                                        .build());
        when(manager.findAllRoles(0, 1000))
                .thenReturn(list);
        Map<String, Object> attr = new HashMap<>();
        Mockito.doAnswer(aInvocation ->
                {
                    String key   = (String) aInvocation.getArguments()[0];
                    Object value = aInvocation.getArguments()[1];
                    attr.put(key, value);
                    return null;
                })
                .when(session).setAttribute(anyString(), any());
        when(req.getServletContext()).thenReturn(context);
        when(req.getSession()).thenReturn(session);
        when(req.getServletContext().getAttribute("UserManager")).thenReturn(manager);
        String result = Receiver.runCommand(req, resp,"command.add_user");
        assertEquals(result,"jsp/user_details.jsp");
        assertEquals(attr.get("title"),"command.add_user");
        assertEquals(attr.get("roles"), list);
    }
}