package com.example.bank.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CashTransferDTO implements Serializable {

    private ClientDTO sourceClient;

    private String destinationAccountNumber;

    private BigDecimal value;

}
