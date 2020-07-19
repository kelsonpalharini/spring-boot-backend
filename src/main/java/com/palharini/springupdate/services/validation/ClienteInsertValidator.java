package com.palharini.springupdate.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.palharini.springupdate.domain.Cliente;
import com.palharini.springupdate.domain.enums.TipoCliente;
import com.palharini.springupdate.dto.ClienteNewDTO;
import com.palharini.springupdate.repositories.ClienteRepository;
import com.palharini.springupdate.resources.exception.FieldMessage;
import com.palharini.springupdate.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getTipo().equals(TipoCliente.PESSOA_FISICA.getCod()) && !BR.isValidCPF(objDto.getCpf_cnpj())) {
			list.add(new FieldMessage("cpf_cnpj", "CPF Inválido"));
		}
		
		if (objDto.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpf_cnpj())) {
			list.add(new FieldMessage("cpf_cnpj", "CNPJ Inválido"));
		}
		
		Cliente aux = clienteRepository.findByEmail(objDto.getEmail());		
		if (aux != null) {
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}

		return list.isEmpty();
	}

}
