package com.example.bank.command;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BankCommand {

    @NotEmpty(message = "bank name cannot be empty")
    private String name;

}
