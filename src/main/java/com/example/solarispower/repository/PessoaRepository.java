package com.example.solarispower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.solarispower.models.Pessoa;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Pessoa findByEmailPessoaAndSenhaPessoa(String emailPessoa, String senhaPessoa);

    Optional<Pessoa> findByEmailPessoa(String emailPessoa);

    boolean existsByEmailPessoa(String emailPessoa);
}