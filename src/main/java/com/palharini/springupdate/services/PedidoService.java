package com.palharini.springupdate.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.palharini.springupdate.domain.ItemPedido;
import com.palharini.springupdate.domain.PagamentoComBoleto;
import com.palharini.springupdate.domain.Pedido;
import com.palharini.springupdate.domain.enums.EstadoPagamento;
import com.palharini.springupdate.repositories.ItemPedidoRepository;
import com.palharini.springupdate.repositories.PagamentoRepository;
import com.palharini.springupdate.repositories.PedidoRepository;
import com.palharini.springupdate.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		
		return obj.orElseThrow(() ->  new ObjectNotFoundException("Objeto não encontrado! ID: " + id 
					+ ", Tipo: " + Pedido.class.getName()));		
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setDataPedido(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getDataPedido());			
		}
		
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for(ItemPedido ip: obj.getItens()) {
			ip.setDesconto(0.00);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);			
		}
		
		itemPedidoRepository.saveAll(obj.getItens());	
		
		return obj;
	}
	
	
}