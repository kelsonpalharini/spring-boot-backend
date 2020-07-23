package com.palharini.springupdate.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.palharini.springupdate.domain.Cidade;
import com.palharini.springupdate.domain.Cliente;
import com.palharini.springupdate.domain.Endereco;
import com.palharini.springupdate.domain.enums.TipoCliente;
import com.palharini.springupdate.dto.ClienteDTO;
import com.palharini.springupdate.dto.ClienteNewDTO;
import com.palharini.springupdate.repositories.ClienteRepository;
import com.palharini.springupdate.repositories.EnderecoRepository;
import com.palharini.springupdate.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		
		return obj.orElseThrow(() ->  new ObjectNotFoundException("Objeto não encontrado! ID: " + id 
					+ ", Tipo: " + Cliente.class.getName()));		
	}
	
	@Transactional	
	public Cliente insert(Cliente obj) {
		obj.setId(null);		
		obj = repo.save(obj);			
		
		enderecoRepository.saveAll(obj.getEnderecos());		
		
		return obj;		 
	}
	
	public Cliente update(Cliente cliente) {
		Cliente newCliente = this.find(cliente.getId());
		updateData(newCliente, cliente);
		 
		return repo.save(newCliente);
	}
	
	public void delete(Integer id) {
		this.find(id);
		try {
		
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new com.palharini.springupdate.services.exceptions.DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
		
	}
	
	public List<Cliente> findAll() {		
		return repo.findAll();		
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderby, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderby);
		
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO clienteNewDTO) {
		Cliente cli = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(), clienteNewDTO.getCpf_cnpj(), TipoCliente.toEnum(clienteNewDTO.getTipo()), pe.encode(clienteNewDTO.getSenha()));
		
		Cidade cid = new Cidade(clienteNewDTO.getCidadeID(), null, null);
		
		Endereco end = new Endereco(null, clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(), clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cli, cid);
		cli.getEnderecos().addAll(Arrays.asList(end));		
		cli.getTelefones().add(clienteNewDTO.getTelefone1());
		
		if (clienteNewDTO.getTelefone2() != null) {
			cli.getTelefones().add(clienteNewDTO.getTelefone2());
		}
		
		if (clienteNewDTO.getTelefone3() != null) {
			cli.getTelefones().add(clienteNewDTO.getTelefone3());
		}
		
		return cli;		
	}
	
	private void updateData(Cliente novoCliente, Cliente cliente) {
		novoCliente.setNome(cliente.getNome());
		novoCliente.setEmail(cliente.getEmail());
	}
}
