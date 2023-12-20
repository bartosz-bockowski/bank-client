package com.example.bank.service;

import com.example.bank.domain.Client;
import com.example.bank.exception.NotEnoughMoneyException;
import com.example.bank.exception.NotFoundException;
import com.example.bank.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    private final CashTransferService cashTransferService;

    private final BankService bankService;

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Client update(Long clientId, Client client) {
        if (existsById(clientId)) {
            throw new NotFoundException("Client with id " + clientId + " does not exist");
        }
        client.setId(clientId);
        return clientRepository.save(client);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("client with id " + clientId + " cannot be found"));
    }

    public void deleteById(Long clientId) {
        cashTransferService.deleteAll(findById(clientId).getCashTransfers());
        clientRepository.deleteById(clientId);
    }

    public Boolean existsById(Long clientId) {
        return clientRepository.existsById(clientId);
    }

    public Client setBankOfClient(Long clientId, Long bankId) {
        Client client = findById(clientId);
        client.setBank(bankService.findById(bankId));
        return save(client);
    }

    public Client addMoney(Long clientId, BigDecimal amount) {
        Client client = findById(clientId);
        client.setBalance(client.getBalance().add(amount));
        return save(client);
    }

    public Client subtractMoneyAndSave(Long clientId, BigDecimal value) {
        Client client = findById(clientId);
        if (value.compareTo(client.getBalance()) > 0) {
            throw new NotEnoughMoneyException("Insufficient funds");
        }
        client.setBalance(client.getBalance().subtract(value));
        return save(client);
    }
}
