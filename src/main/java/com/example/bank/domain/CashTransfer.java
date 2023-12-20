package com.example.bank.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class CashTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "source client of cash transfer must not be empty or null")
    @ManyToOne
    private Client sourceClient;

    @NotEmpty(message = "receiver account number cannot be empty")
    private String destinationAccountNumber;

    @DecimalMin(value = "0", inclusive = false, message = "cash transfer value cannot be equal or lower than 0")
    private BigDecimal value;
}
