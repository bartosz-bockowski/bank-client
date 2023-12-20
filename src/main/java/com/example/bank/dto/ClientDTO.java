package com.example.bank.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ClientDTO implements Serializable {

    private String name;

    private String surname;

    private String accountNumber;

    private BigDecimal balance;

}
