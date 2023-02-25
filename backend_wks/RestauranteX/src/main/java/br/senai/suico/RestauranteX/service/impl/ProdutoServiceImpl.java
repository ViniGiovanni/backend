package br.senai.suico.RestauranteX.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.senai.suico.RestauranteX.exception.RegraNegocioException;
import br.senai.suico.RestauranteX.model.entity.Produto;
import br.senai.suico.RestauranteX.model.entity.Produto;
import br.senai.suico.RestauranteX.model.repository.ProdutoRepository;
import br.senai.suico.RestauranteX.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {
	
	private ProdutoRepository repository;
	public ProdutoServiceImpl(ProdutoRepository repository) {
		super();
		this.repository = repository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public void validarDadosObrigatorios(Produto produto) {
		
		if (produto.getNome()== null || produto.getNome().trim().equals("")) {
			throw new RegraNegocioException("Informe um nome válido");
		}
		
		if (produto.getDescricao() == null || produto.getDescricao().trim().equals("")) {
			throw new RegraNegocioException("Informe uma descrição válida");
		}
		
		if (produto.getEnderecoimagem() == null || produto.getEnderecoimagem().trim().equals("")) {
			throw new RegraNegocioException("Informe um endereço de imagem válido");
		}
		
		if (produto.getValor() <= 0) {
			throw new RegraNegocioException("Informe um valor válido");
		}
	}
	
	@Override
	public Produto salvar(Produto produto) {
		validarDadosObrigatorios(produto);
		//validarNome(cliente.getEmail());
		return repository.save(produto);
	}

	@Override
	public Optional<Produto> buscarPorNome(String none) {
		
		return Optional.empty();
	}
	
	@Transactional
	@Override
	public Produto atualizar(Produto Produto) {
		Objects.requireNonNull(Produto.getId());
		validarDadosObrigatorios(Produto);
		
		var ProdutoOptional = repository.findById(Produto.getId());
		if (ProdutoOptional.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return repository.save(Produto);
	}

	@Override
	@Transactional
	public void deletar(long id) {
	Objects.requireNonNull(id);
		
		var ProdutoOptional = repository.findById(id);
		if (ProdutoOptional.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		try {
		    repository.delete(ProdutoOptional.get());
		}
		catch(Exception ex ){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}
		
	@Override
	public List<Produto> buscar() {
		
		return repository.findAll();
	}

	@Override
	public Optional<Produto> buscarPorId(long id) {
		return repository.findById(id);
	}

}
