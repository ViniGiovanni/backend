package br.senai.suico.RestauranteX.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.senai.suico.RestauranteX.model.dto.ClienteDto;
import br.senai.suico.RestauranteX.model.entity.Cliente;
import br.senai.suico.RestauranteX.service.impl.ClienteServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.models.media.MediaType;

//@CrossOrigin("*")
//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/Clientes")
public class ClienteController {
	@Autowired
	ClienteServiceImpl servico;
	
	@Operation(summary = "Find All Client")
	@GetMapping
	public List<Cliente> buscar()
	{		
		return  servico.buscar();
	}

	@Operation(summary = "Find Client by Id")
	@GetMapping("/{id}")
	public Optional<Cliente> buscaPorId(@PathVariable long id) {
		return servico.buscarPorId(id);
	}

	@PostMapping("/cadastrar")
	public Cliente createCliente(@RequestBody Cliente cliente) {
		return servico.salvar(cliente);
	}
	

	@Operation(summary = "Find Client by Email")
	@PostMapping("/buscar")
	public Optional<Cliente> findCliente(@RequestBody Cliente cliente) {
		return servico.buscarPorEmail(cliente.getEmail());
	}
	
	@Operation(
			  summary = "Authentication for Bitte 2.0",
			  responses = {
			    @ApiResponse(responseCode = "404", description = "Login not found for support look for Yann")
			  }
			)

	@PostMapping("/autenticar")
	public Optional<ClienteDto> autenticar(
			//@Parameter(cli ={"email"="","senha":"123"}) 
	     @RequestBody Cliente cliente) {
		return servico.autenticar(cliente);		
	}
	
	@PutMapping("/{id}")
	public Cliente updateCliente(@RequestBody Cliente categ, @PathVariable long id) {
		    if (id != categ.getId()) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
	        }
	        categ.setId(id);
	        return servico.atualizar(categ);
	}
	
	@DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void excluirPorId(@PathVariable long id) {   
        servico.deletar(id);
    }


}
