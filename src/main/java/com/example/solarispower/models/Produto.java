package com.example.solarispower.models;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade Produto mapeada para a tabela "produto" no banco de dados.
 * Representa os produtos disponíveis no sistema, que podem pertencer a uma empresa
 * e ser adicionados a pedidos de clientes.
 *
 * Anotações:
 * - @Entity: Marca a classe como uma entidade JPA.
 * - @Table: Define o nome da tabela no banco de dados.
 * - @Getter / @Setter (Lombok): Gera automaticamente os métodos getters e setters.
 */
@Entity
@Table(name = "produto")
@Getter
@Setter
public class Produto {

    /** Identificador único do produto, gerado automaticamente pelo banco */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_produto")
    private Long id;

    /** Nome do produto, obrigatório, com tamanho máximo de 100 caracteres */
    @Column(name = "nm_produto", nullable = false, length = 100)
    private String nome;

    /** Descrição detalhada do produto, opcional, até 500 caracteres */
    @Column(name = "descricao_produto", length = 500)
    private String descricao;

    /** Quantidade disponível do produto em estoque, obrigatório */
    @Column(name = "qtd_produto", nullable = false)
    private int quantidade;

    /** Preço do produto, obrigatório, com precisão de 10 dígitos e 2 casas decimais */
    @Column(name = "preco_produto", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    /** Categoria do produto, opcional, até 50 caracteres */
    @Column(name = "categoria", length = 50)
    private String categoria;

    /** Imagem do produto armazenada como LONGBLOB no banco de dados */
    @Lob
    @Column(name = "imagem", columnDefinition = "LONGBLOB")
    private byte[] imagem;

    /**
     * Relação Many-to-One com PedidoProduto.
     * Um produto pode estar em vários pedidos, mas aqui o relacionamento é
     * para o pedido específico que contém este produto.
     * - fetch = FetchType.LAZY: carrega o pedido somente quando acessado.
     * - @JoinColumn(name = "cd_pedido"): coluna que referencia a tabela PedidoProduto.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cd_pedido")
    private PedidoProduto pedido;

    /**
     * Relação Many-to-One com Empresa.
     * Um produto pertence obrigatoriamente a uma empresa.
     * - fetch = FetchType.LAZY: carrega a empresa somente quando acessada.
     * - @JoinColumn(name = "cd_empresa", nullable = false): coluna obrigatória no banco.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cd_empresa", nullable = false)
    private Empresa empresa;
}
