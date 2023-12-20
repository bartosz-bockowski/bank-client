package com.example.bank.controller;

import com.example.bank.command.BankCommand;
import com.example.bank.domain.Bank;
import com.example.bank.dto.BankDTO;
import com.example.bank.exception.NotFoundException;
import com.example.bank.service.BankService;
import com.example.bank.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bank")
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;

    private final ClientService clientService;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<BankDTO> save(@Valid @RequestBody BankCommand bankCommand) {
        return new ResponseEntity<>(modelMapper
                .map(bankService.save(
                        modelMapper.map(bankCommand, Bank.class)), BankDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<BankDTO> all() {
        return bankService.findAll().stream()
                .map(bank -> modelMapper.map(bank, BankDTO.class))
                .toList();
    }

    @GetMapping("/{bankId}")
    public BankDTO getOneById(@PathVariable Long bankId) {
        return modelMapper.map(bankService.findById(bankId), BankDTO.class);
    }

    @PutMapping("/{bankId}")
    public ResponseEntity<BankDTO> update(@PathVariable Long bankId, @Valid @RequestBody BankCommand bankCommand) {
        if (!bankService.existsById(bankId)) {
            throw new NotFoundException("Bank with id " + bankId + " does not exists");
        }
        Bank bank = modelMapper.map(bankCommand, Bank.class);
        bank.setId(bankId);
        return new ResponseEntity<>(modelMapper.map(bankService.save(bank), BankDTO.class), HttpStatus.OK);
    }

    @DeleteMapping("/{bankId}")
    public HttpStatus delete(@PathVariable Long bankId) {
        bankService.deleteById(bankId);
        return HttpStatus.OK;
    }

}
