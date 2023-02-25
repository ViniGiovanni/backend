package br.senai.suico.RestauranteX.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.suico.RestauranteX.model.entity.Cliente;

public interface  ClienteRepository extends JpaRepository<Cliente,Long> {

	// Query método
	boolean existsByEmail(String emai);
	
	Optional<Cliente>  findByEmail(String email);	
}
