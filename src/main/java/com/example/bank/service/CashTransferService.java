package com.example.bank.service;

import com.example.bank.domain.CashTransfer;
import com.example.bank.domain.Client;
import com.example.bank.repository.CashTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CashTransferService {

    private final CashTransferRepository cashTransferRepository;

    public CashTransfer save(CashTransfer cashTransfer) {
        return cashTransferRepository.save(cashTransfer);
    }

    public CashTransfer createAndSave(Client client, String accountNumber, BigDecimal value) {
        CashTransfer cashTransfer = new CashTransfer();
        cashTransfer.setSourceClient(client);
        cashTransfer.setDestinationAccountNumber(accountNumber);
        cashTransfer.setValue(value);
        return save(cashTransfer);
    }

    public void deleteAll(List<CashTransfer> cashTransfers) {
        cashTransferRepository.deleteAll(cashTransfers);
    }
}
