package com.example.bank.dto;

import lombok.Data;

import java.util.List;

@Data
public class BankDTO {

    private String name;

    private List<ClientDTO> clients;

}
