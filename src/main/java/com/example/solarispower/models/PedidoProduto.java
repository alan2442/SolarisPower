package com.example.solarispower.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade PedidoProduto mapeada para a tabela "PedidosProduto" no banco de dados.
 * Representa um pedido feito por uma pessoa contendo uma lista de produtos.
 *
 * Anotações:
 * - @Entity: Marca a classe como uma entidade JPA.
 * - @Table: Define o nome da tabela no banco de dados.
 * - @Getter / @Setter (Lombok): Gera automaticamente métodos getters e setters.
 */
@Entity(name = "PedidosProduto")
@Table(name = "PedidosProduto")
@Getter
@Setter
public class PedidoProduto {

    /** Identificador único do pedido, gerado automaticamente pelo banco */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdPedido;

    /**
     * Relação Many-to-One com a entidade Pessoa.
     * Um pedido é feito por apenas uma pessoa.
     * 
     * - @ManyToOne: Mapeia a associação de muitos pedidos para uma pessoa.
     */
    @ManyToOne
    private Pessoa cdPessoa;
    
    /**
     * Relação One-to-Many com a entidade Produto.
     * Um pedido pode conter vários produtos.
     * 
     * Configurações:
     * - cascade = CascadeType.PERSIST: persiste automaticamente os produtos ao persistir o pedido.
     * - fetch = FetchType.LAZY: carrega a lista de produtos apenas quando acessada, melhorando desempenho.
     */
    @OneToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Produto> produtos;
}
