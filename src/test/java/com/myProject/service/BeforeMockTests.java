package com.myProject.service;

import com.myProject.util.ConnectionPool;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.mockStatic;

public class BeforeMockTests {

    static {
        MockedStatic<ConnectionPool> mocked = mockStatic(ConnectionPool.class);
    }
}
