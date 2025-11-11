package com.example.solarispower.models;

import java.math.BigDecimal;
import jakarta.persistence.*;
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

@Entity
@Table(name = "produto")
@Getter
@Setter
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_produto")
    private Long id;

    @Column(name = "nm_produto", nullable = false, length = 100)
    private String nome;

    @Column(name = "descricao_produto", length = 500)
    private String descricao;

    @Column(name = "qtd_produto", nullable = false)
    private int quantidade;

    @Column(name = "preco_produto", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(name = "categoria", length = 50)
    private String categoria;

    @Lob
    @Column(name = "imagem", columnDefinition = "LONGBLOB")
    private byte[] imagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cd_pedido")
    private PedidoProduto pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cd_empresa", nullable = false)
    private Empresa empresa;
}