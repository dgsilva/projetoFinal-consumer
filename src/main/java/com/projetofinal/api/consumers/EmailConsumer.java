package com.projetofinal.api.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetofinal.api.dto.EmailMessageDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailConsumer {

	@Value("${spring.mail.username}")
	private String from;

	@Autowired
	private JavaMailSender javaMailSender;
	
	@RabbitListener(queues = { "${queue.name}" })
	public void consume(@Payload String payload) throws Exception {

		// deserializar a mensagem gravada na fila
		ObjectMapper objectMapper = new ObjectMapper();
		EmailMessageDto dto = objectMapper.readValue(payload, EmailMessageDto.class);

		// enviando o email
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(dto.getTo());
		message.setSubject(dto.getSubject());
		message.setText(dto.getBody());

		javaMailSender.send(message);
	}
}
