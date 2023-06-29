package com.example.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import io.quarkiverse.rabbitmqclient.RabbitMQClient;
import io.quarkus.runtime.StartupEvent;
import com.example.models.dto.PayloadDTO;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped

public class MessageService {
    private static final Logger log = LoggerFactory.getLogger(MessageService.class);
    @Inject
    RabbitMQClient rabbitMQClient;
    private Channel channel;
    @ConfigProperty(name = "rabbitmq.exchange")
    private String exchange;

    @ConfigProperty(name = "rabbitmq.queue")
    private String queue;
    public void onApplicationStart(@Observes StartupEvent event) {
        setupQueues();
        setupReceiving();
    }

    private void setupQueues() {
        try {
            Connection connection = rabbitMQClient.connect();
            channel = connection.createChannel();
            channel.exchangeDeclare(exchange, BuiltinExchangeType.TOPIC, true);
            channel.queueDeclare(queue, true, false, false, null);
            channel.queueBind(queue, exchange, "#");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void setupReceiving() {
        try {
            channel.basicConsume(queue, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    log.info("Received: " + new String(body, StandardCharsets.UTF_8));
                }
            });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void send(PayloadDTO payloadDTO) {
        try {
            var jsonWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            var json = jsonWriter.writeValueAsString(payloadDTO);
            channel.basicPublish(exchange, "#", null, json.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
