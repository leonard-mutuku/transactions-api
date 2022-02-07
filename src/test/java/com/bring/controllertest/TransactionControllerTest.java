/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bring.controllertest;

import com.bring.controller.TransactionController;
import com.bring.models.Transaction;
import com.bring.service.TransactionService;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author leonard
 */
@WebMvcTest(controllers = TransactionController.class)
@ActiveProfiles("test")
public class TransactionControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;    

    private List<Transaction> transactionList;
    
    private List<Transaction> filteredList;

    @BeforeEach
    public void setUp() {         
        Transaction tnx1 = new Transaction("abah-hcky", "Test-Acc-100", "counterPartyAccount 001", 
                "counterPartyName 001", "img/img-001.jpg", 8.60, "GBP", 8.60, "GBP", "Deposit", "customer deposit");
        Transaction tnx2 = new Transaction("abbh-hcky", "Test-Acc-102", "counterPartyAccount 002", 
                "counterPartyName 002", "img/img-002.jpg", 25.60, "GBP", 25.60, "GBP", "Deposit", "customer deposit");
        Transaction tnx3 = new Transaction("abch-hcky", "Test-Acc-103", "counterPartyAccount 003", 
                "counterPartyName 003", "img/img-003.jpg", 6.40, "GBP", 6.40, "GBP", "Withdraw", "customer deposit");
        
        this.transactionList = new ArrayList<>();
        this.transactionList.add(tnx1);
        this.transactionList.add(tnx2);
        this.transactionList.add(tnx3);
        
        this.filteredList = new ArrayList<>();
        this.filteredList.add(tnx3);
        
    }
    
    @WithMockUser(username="spring")
    @Test    
    void shouldFetchTransactionList() throws Exception {

        given(transactionService.getAllTransactions()).willReturn(transactionList);

        this.mockMvc.perform(get("/transaction/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(transactionList.size())));
    }
    
    @WithMockUser(username="spring")
    @Test
    void shouldFetchtransactionFilter() throws Exception {
        String transactionType = "Withdraw";
        
        when(transactionService.getAllTransactionsByType(transactionType)).thenReturn(filteredList);

        this.mockMvc.perform(get("/transaction/filter/{trans_type}", transactionType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)));
    }
    
    @WithMockUser(username="spring")
    @Test
    public void shouldgetTransactionTotalByType() throws Exception {
        String transactionType = "Deposit";
        double resp = 34.2;

        when(transactionService.getTransactionTotalByType(transactionType)).thenReturn(resp);

        MvcResult mvcResult = this.mockMvc.perform(get("/transaction/total/{trans_type}", transactionType))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        double content = Double.valueOf(mvcResult.getResponse().getContentAsString());
        assertEquals(content, resp);
    }
    
}
