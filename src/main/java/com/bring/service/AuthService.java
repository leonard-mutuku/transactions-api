/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bring.service;

import org.springframework.stereotype.Service;
import com.bring.models.User;
import com.bring.security.JWTUtility;
import com.bring.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author leonard
 */
@Service
public class AuthService {
    
    @Autowired
    private JWTUtility jWTUtility;
    
    @Autowired
    private UserService userService;
    
    public boolean validateCredentials(User user) {
        return ((user.getUsername().equals(Constants.USER)) && (user.getPassword().equals(Constants.PSWD)));
    }
    
    public String generateToken(User user) {
        UserDetails ud = userService.loadUserByUsername(user.getUsername());
        String token = jWTUtility.generateToken(ud);       
        return token;
    }
}
