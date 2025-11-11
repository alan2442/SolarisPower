package com.example.solarispower.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.solarispower.models.Empresa;
import com.example.solarispower.models.Produto;



// Interface de repositório para manipulação de dados da entidade Produto
// Estende CrudRepository, que fornece métodos básicos para operações CRUD (Create, Read, Update, Delete)
public interface ProdutoRepository extends CrudRepository<Produto, Long> {

    // Método personalizado para buscar uma lista de produtos pertencentes a uma determinada empresa
    // O Spring Data implementa automaticamente esse método baseado na convenção do nome
    List<Produto> findByEmpresa(Empresa empresa);
    List<Produto> findByNomeContainingIgnoreCase(String nome);
}