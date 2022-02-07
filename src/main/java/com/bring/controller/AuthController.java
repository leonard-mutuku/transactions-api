/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.bring.models.User;
import com.bring.service.AuthService;
import com.bring.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author leonard
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @RequestMapping(path = "generate_token", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity generateToken(@RequestBody User user) {
        Constants.LOGGER.info("Received generate token request for user {}", user.getUsername());
        if (authService.validateCredentials(user)) {
            String token = authService.generateToken(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(token);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized");
        }        
    }
    
}
