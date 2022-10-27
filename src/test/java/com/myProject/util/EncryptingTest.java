package com.myProject.util;

import com.myProject.service.exception.DaoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class EncryptingTest {
    @Test
    void stringEncryptTest() throws DaoException {
        String source = "DBrae53";
        String result1 = EncryptPassword.encrypt(source);
        assertNotEquals(result1, source);
        String result2 = EncryptPassword.encrypt("DBrae53");
        assertEquals(result1, result2);
    }
}
