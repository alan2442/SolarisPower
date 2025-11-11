package com.example.solarispower.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

// DTO = Data Transfer Object
// Esta classe serve para transferir os dados do formulário HTML para o backend (controller/service)
// Evita usar diretamente a entidade Produto, ajudando na separação de camadas
@Data
public class ProdutoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private int quantidade;
    private String preco;
    private String categoria;
    private MultipartFile imagem;
}
