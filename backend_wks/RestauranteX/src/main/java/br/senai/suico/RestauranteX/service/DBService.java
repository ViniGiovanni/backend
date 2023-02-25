package br.senai.suico.RestauranteX.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senai.suico.RestauranteX.model.entity.Categoria;
import br.senai.suico.RestauranteX.model.entity.Cliente;
import br.senai.suico.RestauranteX.model.entity.Produto;
import br.senai.suico.RestauranteX.model.repository.CategoriaRepository;
import br.senai.suico.RestauranteX.model.repository.ClienteRepository;
import br.senai.suico.RestauranteX.model.repository.ProdutoRepository;

@Service
public class DBService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	public void instanciaDB() {
       //******************************** Carga dos usuários  ********************************
		Cliente c1 = new Cliente(null, "Regina Costa", "regina@gmail.com", "123", "USER",true);
		Cliente c2 = new Cliente(null, "Luan Silva", "luan@gmail.com", "123", "USER",true);
		Cliente c3 = new Cliente(null, "Administrador", "admin@gmail.com", "123", "ADMIN",true);
		
		var c4 = Cliente.builder().nome("Tarrou").email("tarrou@gmail.com").senha("123").roles("ADMIN,USER").ativo(true).build();

		clienteRepository.saveAll(Arrays.asList(c1, c2, c3, c4));
		
		//******************************** Carga das categorias  ********************************
		Categoria cat1 = Categoria.builder().nome("Bebidas").build();
		Categoria cat2 = Categoria.builder().nome("Sobremesas").build();
		Categoria cat3 = Categoria.builder().nome("Lanches").build();
		Categoria cat4 = Categoria.builder().nome("Porções").build();
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4));
		
		//******************************** Carga das produtos  ********************************
		Produto prod1 = Produto.builder().nome("Coca Cola").descricao("Refrigerante em Lata").valor(3.5f).enderecoimagem("cocacola.png").build();
		prod1.setCategoria(cat1);
		
		Produto prod2 = Produto.builder().nome("Fanta Laranja").descricao("Refrigerante em Lata").valor(3.5f).enderecoimagem("fantalaranja.png").build();
		prod2.setCategoria(cat1);
		
		Produto prod3 = Produto.builder().nome("Pudim").descricao("Pudim de Leite Condensado").valor(7.5f).enderecoimagem("pudimleite.png").build();
		prod3.setCategoria(cat2);
		produtoRepository.saveAll(Arrays.asList(prod1, prod2 ,prod3));
		
		
	}
}
