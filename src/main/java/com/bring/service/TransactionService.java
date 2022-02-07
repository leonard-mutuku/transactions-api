/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bring.service;

import com.bring.models.Transaction;
import com.bring.repository.TransactionRepository;
import com.bring.utils.Constants;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author leonard
 */
@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        List<Transaction> all_tnxs = transactionRepository.fetchAllTnxs();
        
        Constants.LOGGER.info("All Transactions:-");
        all_tnxs.forEach((all_tnx) -> {
            Constants.LOGGER.info(all_tnx.toString());
        });
        
        return all_tnxs;
    }

    public List<Transaction> getAllTransactionsByType(String transactionType) {
        List<Transaction> all_tnxs = transactionRepository.fetchAllTnxs();
        
        List<Transaction> filtered_tnxs = all_tnxs.stream().filter(t -> t.getTransactionType().equals(transactionType)).collect(Collectors.toList());
        
        Constants.LOGGER.info("{} Transactions :-", transactionType);
        all_tnxs.forEach((all_tnx) -> {
            Constants.LOGGER.info(all_tnx.toString());
        });
        
        return filtered_tnxs;
    }
    
    public double getTransactionTotalByType(String transactionType) {
        List<Transaction> all_tnxs = transactionRepository.fetchAllTnxs();
        
        double total_tnx_type = all_tnxs.stream().filter(t -> t.getTransactionType().equals(transactionType)).mapToDouble(t -> t.getTransactionAmount()).sum();
        
        Constants.LOGGER.info("{} Transactions Total : {}", transactionType, total_tnx_type);
        
        return total_tnx_type;
    }
    
}
