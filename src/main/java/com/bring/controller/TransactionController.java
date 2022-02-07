/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bring.controller;

import com.bring.models.Transaction;
import com.bring.service.TransactionService;
import com.bring.utils.Constants;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author leonard
 */
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;
    
    @RequestMapping(path = "/list", method = RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity transactionList() {
        Constants.LOGGER.info("Received list transactions request");
        List<Transaction> list = transactionService.getAllTransactions();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    
    @RequestMapping(path = "/filter/{trans_type}", method = RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity transactionFilter(@PathVariable("trans_type") String transactionType) {
        Constants.LOGGER.info("Received filter transactions request. Type Filter : {}", transactionType);
        List<Transaction> filtered_list = transactionService.getAllTransactionsByType(transactionType);
        return ResponseEntity.status(HttpStatus.OK).body(filtered_list);
    }
    
    @RequestMapping(path = "/total/{trans_type}", method = RequestMethod.GET)
    public ResponseEntity transactionTotal(@PathVariable("trans_type") String transactionType) {
        Constants.LOGGER.info("Received sum transactions by type request. Type Filter : {}", transactionType);
        double total = transactionService.getTransactionTotalByType(transactionType);
        return ResponseEntity.status(HttpStatus.OK).body(total);
    }
    
}
