package com.example.solarispower.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaRequestDTO {
    private String nmPessoa;
    private String dtNascimento;
    private String emailPessoa;
    private String cpfPessoa;
    private String senhaPessoa;
    private String confSenha;
    private String cepPessoa;
    private String ruaPessoa;
    private int numeroRuaPessoa;
    private String complementoPessoa;
}