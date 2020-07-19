package com.palharini.springupdate.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.palharini.springupdate.domain.Categoria;
import com.palharini.springupdate.dto.CategoriaDTO;
import com.palharini.springupdate.repositories.CategoriaRepository;
import com.palharini.springupdate.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		
		return obj.orElseThrow(() ->  new ObjectNotFoundException("Objeto não encontrado! ID: " + id 
					+ ", Tipo: " + Categoria.class.getName()));		
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria categoria) {
		Categoria newCategoria = this.find(categoria.getId());
		updateData(newCategoria, categoria);
		 
		return repo.save(newCategoria);
	}
	
	public void delete(Integer id) {
		this.find(id);
		try {
		
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new com.palharini.springupdate.services.exceptions.DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
		
	}
	
	public List<Categoria> findAll() {		
		return repo.findAll();		
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderby, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderby);
		
		return repo.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO categoriaDTO) {
		return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
	}
	
	private void updateData(Categoria novaCategoria, Categoria categoria) {
		novaCategoria.setNome(categoria.getNome());
	}
}
