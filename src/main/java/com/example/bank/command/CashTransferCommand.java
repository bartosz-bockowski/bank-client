package com.example.bank.command;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
public class CashTransferCommand {

    @NotEmpty(message = "destination account number cannot be empty")
    private String destinationAccountNumber;

    @DecimalMin(value = "0", inclusive = false, message = "cash transfer value cannot be equal or lower than 0")
    private BigDecimal value;

}
