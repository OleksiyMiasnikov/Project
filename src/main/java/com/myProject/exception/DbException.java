package com.myProject.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.SQLException;

public class DbException extends SQLException {
    private static final Logger logger = (Logger) LogManager.getLogger(DbException.class);
    public DbException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
    }

    public DbException(String reason, String SQLState) {
        super(reason, SQLState);
    }

    public DbException(String reason) {
        super(reason);
    }

    public DbException() {
        super();
    }

    public DbException(Throwable cause) {
        super(cause);
    }

    public DbException(String reason, String sqlState, Throwable cause) {
        super(reason, sqlState, cause);
    }

    public DbException(String reason, String sqlState, int vendorCode, Throwable cause) {
        super(reason, sqlState, vendorCode, cause);
    }
}
