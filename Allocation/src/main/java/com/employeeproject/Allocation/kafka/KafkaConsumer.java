package com.employeeproject.Allocation.kafka;

import com.employeeproject.Allocation.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class KafkaConsumer {

    @Autowired
    private EmailService emailService;

    @Value("${email.recipients}")
    private String[] recipients;


    @KafkaListener(topics = "email_topic", groupId = "kafka_mail_group_id")
    public void consume(String message) {
        log.info("Kafka message from email_topic is {}", message);
        List<String> recipientList = Arrays.asList(recipients);
        for (String recipient : recipientList)
            emailService.sendEmail(recipient, "Notification", message);
    }
}