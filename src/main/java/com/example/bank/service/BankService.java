package com.example.bank.service;

import com.example.bank.domain.Bank;
import com.example.bank.exception.NotFoundException;
import com.example.bank.repository.BankRepository;
import com.example.bank.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankService {

    private final BankRepository bankRepository;

    private final ClientRepository clientRepository;

    public Bank save(Bank bank) {
        return bankRepository.save(bank);
    }

    public List<Bank> findAll() {
        return bankRepository.findAll();
    }

    public Bank findById(Long bankId) {
        return bankRepository.findById(bankId)
                .orElseThrow(() -> new NotFoundException("Bank with id " + bankId + " cannot be found"));
    }

    public void deleteById(Long bankId) {
        Bank bank = findById(bankId);
        bank.getClients().stream()
                .peek(client -> client.setBank(null))
                .forEach(clientRepository::save);
        bankRepository.deleteById(bankId);
    }

    public Boolean existsById(Long bankId) {
        return bankRepository.existsById(bankId);
    }
}
