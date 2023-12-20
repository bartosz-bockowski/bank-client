package com.example.bank.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "client name cannot be empty")
    private String name;

    @NotEmpty(message = "client surname cannot be empty")
    private String surname;

    @NotEmpty(message = "client account number cannot be empty")
    private String accountNumber;

    private BigDecimal balance = new BigDecimal(0);

    @OneToMany(mappedBy = "sourceClient")
    private List<CashTransfer> cashTransfers;

    @ManyToOne
    private Bank bank;

}
