package com.myProject.service.command;

import com.myProject.employee.Admin;
import com.myProject.employee.Cashier;
import com.myProject.employee.Employee;
import com.myProject.entitie.Role;
import com.myProject.entitie.User;
import com.myProject.service.UserManager;
import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NewUserTest {

    @Test
    void addUserSuccessTest() throws DaoException, AppException {
        UserManager manager = mock(UserManager.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        ServletContext context = mock(ServletContext.class);
        HttpSession session = mock(HttpSession.class);
        Employee employee = new Admin(User.builder()
                                        .login("admin")
                                        .role(Role.builder()
                                                .id(1)
                                                .name("test_admin")
                                                .build())
                                        .build());
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
        when(req.getSession().getAttribute("employee")).thenReturn(employee);
        when(req.getServletContext().getAttribute("UserManager")).thenReturn(manager);
        String result = Receiver.runCommand(req, resp,"command.add_user");
        assertEquals(result,"jsp/user_details.jsp");
        assertEquals(attr.get("title"),"command.add_user");
        assertEquals(attr.get("roles"), list);
    }
    @Test
    void unauthorizedAccessTest() throws DaoException {
        UserManager manager = mock(UserManager.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        ServletContext context = mock(ServletContext.class);
        HttpSession session = mock(HttpSession.class);
        Employee employee = new Cashier(User.builder()
                .login("test_cashier")
                .role(Role.builder()
                        .id(2)
                        .name("test_cashier")
                        .build())
                .build());
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
        when(req.getServletContext()).thenReturn(context);
        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("employee")).thenReturn(employee);
        when(req.getServletContext().getAttribute("UserManager")).thenReturn(manager);
        assertThrows(AppException.class,
                () -> Receiver.runCommand(req, resp,"command.add_user"),
                "Unauthorised access attempt");
    }
}