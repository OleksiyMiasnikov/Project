package com.myProject.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerTest extends Mockito {
    @Test
    void test(){
        final HttpServletRequest req = mock(HttpServletRequest.class);
        final HttpServletResponse resp = mock(HttpServletResponse.class);
    }
}
