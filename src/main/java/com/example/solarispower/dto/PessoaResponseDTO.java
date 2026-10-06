package com.example.solarispower.dto;

import com.example.solarispower.models.Pessoa;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PessoaResponseDTO {
    private Long cdPessoa;
    private String nmPessoa;
    private String dtNascimento;
    private String emailPessoa;
    private String cpfPessoa;
    private String cepPessoa;
    private String ruaPessoa;
    private int numeroRuaPessoa;
    private String complementoPessoa;

    // Construtor utilitário para converter a Entidade em DTO
    public PessoaResponseDTO(Pessoa pessoa) {
        this.cdPessoa = pessoa.getCdPessoa();
        this.nmPessoa = pessoa.getNmPessoa();
        this.dtNascimento = pessoa.getDtNascimento();
        this.emailPessoa = pessoa.getEmailPessoa();
        this.cpfPessoa = pessoa.getCpfPessoa();
        this.cepPessoa = pessoa.getCepPessoa();
        this.ruaPessoa = pessoa.getRuaPessoa();
        this.numeroRuaPessoa = pessoa.getNumeroRuaPessoa();
        this.complementoPessoa = pessoa.getComplementoPessoa();
    }
}