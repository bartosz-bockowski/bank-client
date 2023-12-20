package com.example.bank.repository;

import com.example.bank.domain.CashTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashTransferRepository extends JpaRepository<CashTransfer, Long> {
}
