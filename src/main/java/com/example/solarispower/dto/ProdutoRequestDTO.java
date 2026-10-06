package com.example.solarispower.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoRequestDTO {
    private String nome;
    private String descricao;
    private int quantidade;
    private BigDecimal preco;
    private String categoria;
    private Long cdEmpresa;
}