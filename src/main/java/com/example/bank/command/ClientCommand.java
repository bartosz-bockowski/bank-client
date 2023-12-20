package com.example.bank.command;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ClientCommand {

    @NotEmpty(message = "client name cannot be empty")
    private String name;

    @NotEmpty(message = "client surname cannot be empty")
    private String surname;

    @NotEmpty(message = "client account number cannot be empty")
    private String accountNumber;

}
