package com.palharini.springupdate.services;

import org.springframework.mail.SimpleMailMessage;

import com.palharini.springupdate.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
