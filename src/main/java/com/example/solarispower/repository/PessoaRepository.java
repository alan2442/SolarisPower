package com.example.solarispower.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.solarispower.models.Pessoa;

/**
 * Interface de repositório para a entidade Pessoa.
 * 
 * Extende CrudRepository, que fornece operações básicas de CRUD:
 * - save(S)
 * - findById(ID)
 * - findAll()
 * - deleteById(ID)
 * - delete(entity)
 * 
 * O Spring Data detecta automaticamente essa interface e cria a implementação em tempo de execução.
 */
public interface PessoaRepository extends CrudRepository<Pessoa, Long> {

    /**
     * Busca uma pessoa pelo email e senha.
     * Utilizado principalmente para autenticação/login.
     * 
     * @param emailPessoa Email da pessoa
     * @param senhaPessoa Senha da pessoa
     * @return Pessoa encontrada ou null se não houver correspondência
     */
    Pessoa findByEmailPessoaAndSenhaPessoa(String emailPessoa, String senhaPessoa);

}
