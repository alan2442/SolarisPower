package com.example.solarispower.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.example.solarispower.models.Empresa;
import com.example.solarispower.models.Produto;

/**
 * Interface de repositório para a entidade Produto.
 * 
 * Extende CrudRepository, que fornece operações básicas de CRUD:
 * - save(S)
 * - findById(ID)
 * - findAll()
 * - deleteById(ID)
 * - delete(entity)
 * 
 * O Spring Data gera automaticamente a implementação dessa interface em tempo de execução.
 */
public interface ProdutoRepository extends CrudRepository<Produto, Long> {

    /**
     * Busca todos os produtos pertencentes a uma empresa específica.
     * 
     * @param empresa Empresa para a qual os produtos devem ser buscados
     * @return Lista de produtos associados à empresa
     */
    List<Produto> findByEmpresa(Empresa empresa);

    /**
     * Busca produtos cujo nome contenha a string fornecida, ignorando maiúsculas e minúsculas.
     * Útil para funcionalidades de pesquisa/filtragem.
     * 
     * @param nome Parte ou total do nome do produto a ser buscado
     * @return Lista de produtos cujo nome corresponde ao critério de busca
     */
    List<Produto> findByNomeContainingIgnoreCase(String nome);
}
