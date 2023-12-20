package com.example.bank.config;

import com.example.bank.dto.CashTransferDTO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

@Component
public class RabbitMQReceiver {

    @RabbitListener(queues = Config.QUEUE_NAME)
    public void receiveMessage(Message message) throws IOException, ClassNotFoundException {
        CashTransferDTO cashTransferDTO = (CashTransferDTO) (
                new ObjectInputStream(
                        new ByteArrayInputStream(message.getBody())
                ).readObject()
        );
        System.out.println(cashTransferDTO);
    }

}
