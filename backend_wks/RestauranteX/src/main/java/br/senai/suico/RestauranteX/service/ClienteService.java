package br.senai.suico.RestauranteX.service;

import java.util.List;
import java.util.Optional;

import br.senai.suico.RestauranteX.model.dto.ClienteDto;
import br.senai.suico.RestauranteX.model.entity.Cliente;

public interface ClienteService {	
	public void validarEmail(String email);	
	public void validarDadosObrigatorios(Cliente cliente);
	public Cliente salvar(Cliente cliente);
	public Optional<Cliente>  buscarPorEmail(String email);		
	public Optional<ClienteDto> autenticar(Cliente cliente);
	
	
	public Cliente atualizar(Cliente cliente);
	public void deletar(long id);
	public List<Cliente> buscar();
	public Optional<Cliente>  buscarPorId(long id);
}