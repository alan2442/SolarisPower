package com.example.solarispower.dto;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

/**
 * DTO (Data Transfer Object) para a entidade Produto.
 * 
 * Essa classe é usada para transportar dados entre a camada de visualização (HTML/Formulário)
 * e a camada de backend (Controller/Service) sem expor diretamente a entidade Produto.
 * 
 * Benefícios:
 * - Separa a lógica de apresentação da lógica de persistência.
 * - Evita alterações diretas na entidade.
 * - Facilita validação e manipulação dos dados enviados pelo usuário.
 */
@Data
public class ProdutoDTO {

    /** Identificador único do produto (opcional para novos produtos, obrigatório para edições) */
    private Long id;

    /** Nome do produto */
    private String nome;

    /** Descrição detalhada do produto */
    private String descricao;

    /** Quantidade disponível em estoque */
    private int quantidade;

    /** Preço do produto (em formato String para facilitar envio via formulário) */
    private String preco;

    /** Categoria do produto */
    private String categoria;

    /** Arquivo de imagem do produto enviado pelo usuário */
    private MultipartFile imagem;
}
