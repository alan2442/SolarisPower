package com.example.solarispower.dto;

import java.math.BigDecimal;
import java.util.Base64;

import com.example.solarispower.models.Produto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProdutoResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private int quantidade;
    private BigDecimal preco;
    private String categoria;
    private String imagemBase64;
    private Long cdEmpresa;

    public ProdutoResponseDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.quantidade = produto.getQuantidade();
        this.preco = produto.getPreco();
        this.categoria = produto.getCategoria();
        
        if (produto.getEmpresa() != null) {
            this.cdEmpresa = produto.getEmpresa().getCdEmpresa();
        }

        if (produto.getImagem() != null && produto.getImagem().length > 0) {
            this.imagemBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(produto.getImagem());
        }
    }
}