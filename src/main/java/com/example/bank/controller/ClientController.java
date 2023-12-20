package com.example.bank.controller;

import com.example.bank.command.CashTransferCommand;
import com.example.bank.command.ClientCommand;
import com.example.bank.domain.Client;
import com.example.bank.dto.CashTransferDTO;
import com.example.bank.dto.ClientDTO;
import com.example.bank.service.CashTransferService;
import com.example.bank.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    private final ModelMapper modelMapper;

    private final CashTransferService cashTransferService;

    @PostMapping
    public ResponseEntity<ClientDTO> save(@Valid @RequestBody ClientCommand clientCommand) {
        return new ResponseEntity<>(modelMapper
                .map(clientService.save(
                        modelMapper.map(clientCommand, Client.class)), ClientDTO.class), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClientDTO>> all() {
        return new ResponseEntity<>(clientService.findAll().stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .toList(), HttpStatus.OK);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDTO> getOneById(@PathVariable Long clientId) {
        return new ResponseEntity<>(modelMapper
                .map(clientService.findById(clientId), ClientDTO.class), HttpStatus.OK);
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<ClientDTO> update(@PathVariable Long clientId,
                                            @Valid @RequestBody ClientCommand clientCommand) {
        return new ResponseEntity<>(modelMapper
                .map(clientService.update(
                        clientId, modelMapper.map(clientCommand, Client.class)), ClientDTO.class), HttpStatus.OK);
    }

    @DeleteMapping("/{clientId}")
    public HttpStatus deleteById(@PathVariable Long clientId) {
        clientService.deleteById(clientId);
        return HttpStatus.OK;
    }

    @PatchMapping("/{clientId}/setBank/{bankId}")
    public ResponseEntity<ClientDTO> setBank(@PathVariable Long clientId, @PathVariable Long bankId) {
        return new ResponseEntity<>(modelMapper.map(
                clientService.setBankOfClient(clientId, bankId), ClientDTO.class), HttpStatus.OK);
    }

    @PatchMapping("/{clientId}/addMoney")
    public ResponseEntity<ClientDTO> addMoney(@PathVariable Long clientId, @RequestParam BigDecimal amount) {
        return new ResponseEntity<>(modelMapper.map(clientService.addMoney(clientId, amount), ClientDTO.class),
                HttpStatus.OK);
    }

    @PostMapping("/{clientId}/saveTransfer")
    public ResponseEntity<CashTransferDTO> saveTransfer(@PathVariable Long clientId, @Valid @RequestBody CashTransferCommand cashTransferCommand) {
        CashTransferDTO cashTransferDTO = modelMapper.map(
                cashTransferService.createAndSave(
                        clientService.subtractMoneyAndSave(clientId, cashTransferCommand.getValue()),
                        cashTransferCommand
                ),
                CashTransferDTO.class);
        return new ResponseEntity<>(cashTransferDTO, HttpStatus.OK);
    }

}
