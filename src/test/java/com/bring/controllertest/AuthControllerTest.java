/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bring.controllertest;

import com.bring.controller.AuthController;
import com.bring.models.User;
import com.bring.service.AuthService;
import com.bring.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author leonard
 */
@WebMvcTest(controllers = AuthController.class)
@ActiveProfiles("test")
public class AuthControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;
    
    @WithMockUser(username="spring")
    @Test
    public void shouldFailGenerateToken() throws Exception {
        User user = new User("admin@123456", "password@2020");
        String token = "";

        when(authService.generateToken(any(User.class))).thenReturn(token);

        this.mockMvc.perform(post("/auth/generate_token")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isForbidden());
    }
    
//    @Test
//    public void shouldGenerateToken() throws Exception {
//        User user = new User("admin@2020", "password@2020");
//
//        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/generate_token")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(user)))
//                .andExpect(status().isCreated()).andReturn();
//
//        String token = result.getResponse().getContentAsString();
//
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/transaction/list")
//                .header("Authorization", "Bearer " + token))
//                .andExpect(status().isOk());
//
//    }
    
}
