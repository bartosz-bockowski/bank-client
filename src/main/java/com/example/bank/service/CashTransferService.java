package com.example.bank.service;

import com.example.bank.command.CashTransferCommand;
import com.example.bank.config.Config;
import com.example.bank.domain.CashTransfer;
import com.example.bank.domain.Client;
import com.example.bank.dto.CashTransferDTO;
import com.example.bank.repository.CashTransferRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CashTransferService {

    private final CashTransferRepository cashTransferRepository;

    private final RabbitTemplate rabbitTemplate;

    private final ModelMapper modelMapper;

    public CashTransfer save(CashTransfer cashTransfer) {
        return cashTransferRepository.save(cashTransfer);
    }

    public CashTransfer createAndSave(Client client, CashTransferCommand cashTransferCommand) {
        CashTransfer cashTransfer = new CashTransfer();
        cashTransfer.setSourceClient(client);
        cashTransfer.setDestinationAccountNumber(cashTransferCommand.getDestinationAccountNumber());
        cashTransfer.setValue(cashTransferCommand.getValue());

        rabbitTemplate.convertAndSend(Config.QUEUE_NAME, modelMapper.map(cashTransfer, CashTransferDTO.class));

        return save(cashTransfer);
    }

    public void deleteAll(List<CashTransfer> cashTransfers) {
        cashTransferRepository.deleteAll(cashTransfers);
    }
}
