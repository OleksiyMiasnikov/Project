package com.myProject.service.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.SQLException;

public class DaoException extends SQLException {
    private static final Logger logger = (Logger) LogManager.getLogger(DaoException.class);

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
    }

    public DaoException(String reason, String SQLState) {
        super(reason, SQLState);
    }

    public DaoException(String reason) {
        super(reason);
    }

    public DaoException() {
        super();
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(String reason, String sqlState, Throwable cause) {
        super(reason, sqlState, cause);
    }

    public DaoException(String reason, String sqlState, int vendorCode, Throwable cause) {
        super(reason, sqlState, vendorCode, cause);
    }
}
