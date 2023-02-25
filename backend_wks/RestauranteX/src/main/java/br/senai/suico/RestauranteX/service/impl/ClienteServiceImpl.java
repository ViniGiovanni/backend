package br.senai.suico.RestauranteX.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.senai.suico.RestauranteX.exception.RegraNegocioException;
import br.senai.suico.RestauranteX.model.dto.ClienteDto;
import br.senai.suico.RestauranteX.model.entity.Cliente;
import br.senai.suico.RestauranteX.model.repository.ClienteRepository;
import br.senai.suico.RestauranteX.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

	
	private ClienteRepository repository;
	public ClienteServiceImpl(ClienteRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);		
		if(existe) {
			throw new ResponseStatusException(HttpStatus.FOUND);
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public void validarDadosObrigatorios(Cliente Cliente) {
		if (Cliente.getNome() == null || Cliente.getNome().trim().equals("")) {
			throw new RegraNegocioException("Informe um nome válido");
		}
		
		if (Cliente.getEmail() == null || Cliente.getEmail().trim().equals("")) {
			throw new RegraNegocioException("Informe um email válido");
		}
		
		if (Cliente.getSenha() == null || Cliente.getSenha().trim().equals("")) {
			throw new RegraNegocioException("Informe um email válido");
		}
	}
	
	@Override
	@Transactional
	public Cliente salvar(Cliente cliente) {
		validarDadosObrigatorios(cliente);
		validarEmail(cliente.getEmail());	
		
		//cliente.setSenha(passwordEncoder.encode(cliente.getSenha()));
	    return repository.save(cliente);		
	}

	@Override
	public Optional<Cliente> buscarPorEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	public Optional<ClienteDto> autenticar(Cliente cliente) {
		Optional<Cliente> result = repository.findByEmail(cliente.getEmail());
		
		//cliente.setSenha(passwordEncoder.encode(cliente.getSenha()));
	    
		if (result.isPresent()) 		{		
			if (result.get().getSenha().equals(cliente.getSenha() )) {
				ClienteDto cli = new ClienteDto();
				cli.setNome(result.get().getNome());
				cli.setEmail(result.get().getEmail());
				cli.setRoles(result.get().getRoles());
				cli.setToken("token123");
				return Optional.of(cli);
			}
			else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);						
			}
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}	
	}

	@Override
	@Transactional
	public Cliente atualizar(Cliente Cliente) {
		Objects.requireNonNull(Cliente.getId());
		validarDadosObrigatorios(Cliente);
		
		var ClienteOptional = repository.findById(Cliente.getId());
		if (ClienteOptional.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return repository.save(Cliente);
	}

	
	@Override
	@Transactional
	public void deletar(long id) {
		Objects.requireNonNull(id);
		
		var ClienteOptional = repository.findById(id);
		if (ClienteOptional.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		try {
		    repository.delete(ClienteOptional.get());
		}
		catch(Exception ex ){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public List<Cliente> buscar() {
		return repository.findAll();
	}

	@Override
	public Optional<Cliente> buscarPorId(long id) {
		return repository.findById(id);
	}	
	
}
