/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bring.models;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author leonard
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class Transaction implements Serializable {
    
    private static final long serialVersionUID = -4947847275584951761L;
    
    private String id;
    private String accountId;
    private String counterPartyAccount;
    private String counterPartyName;
    private String counterPartyLogoPath;
    private double instructedAmount;
    private String instructedCurrency;
    private double transactionAmount;
    private String transactionCurrency;
    private String transactionType;
    private String description;
    
}
