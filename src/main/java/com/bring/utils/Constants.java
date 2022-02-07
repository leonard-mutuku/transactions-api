package com.bring.utils;

import org.slf4j.LoggerFactory;

/**
 *
 * @author leonard
 */
public class Constants {
    
    //10 secs request timeout
    public static final int REQUEST_TIMEOUT = 10000;
    
    //1 hr token validity
    public static final long JWT_TOKEN_VALIDITY = 1 * 60 * 60;
    public static final String JWT_SECRET_KEY = "s9#Fk8UJk^@6x2BV";
    public static final String USER = "admin@123";
    public static final String PSWD = "password@123";
    
    public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger("DEBUG_LOG");
    
}
