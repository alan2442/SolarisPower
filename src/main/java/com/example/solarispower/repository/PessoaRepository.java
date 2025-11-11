package com.example.solarispower.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.solarispower.models.Pessoa;

public interface PessoaRepository extends CrudRepository<Pessoa, Long> {

    // busca por email e senha — usado no login
    Pessoa findByEmailPessoaAndSenhaPessoa(String emailPessoa, String senhaPessoa);

}
