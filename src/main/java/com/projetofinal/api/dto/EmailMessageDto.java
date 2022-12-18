package com.projetofinal.api.dto;

import lombok.Data;

@Data
public class EmailMessageDto {

	private String to;
	private String subject;
	private String body;
}
