package com.example.solarispower.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "Produto")
@Table(name = "Produto")
@Getter
@Setter
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cd_produto;

    private String nm_produto;
    private String descricao_produto;
    private int qtd_produto;
    private BigDecimal preco_produto;
    private String categoria;

    @Lob
    @Column(name = "imagem", columnDefinition = "LONGBLOB")
    private byte[] imagem;

    @OneToOne
    private PedidoProduto cd_pedido;

    @ManyToOne
    @JoinColumn(name = "cd_empresa")
    private Empresa empresa;

}
