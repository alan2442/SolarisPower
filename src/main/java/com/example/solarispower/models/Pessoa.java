package com.example.solarispower.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade Pessoa mapeada para a tabela "Pessoa" no banco de dados.
 * Representa uma pessoa cadastrada no sistema, que pode realizar pedidos.
 *
 * Anotações:
 * - @Entity: Marca a classe como uma entidade JPA.
 * - @Table: Define o nome da tabela no banco de dados.
 * - @Getter / @Setter (Lombok): Gera automaticamente métodos getters e setters.
 */
@Entity(name = "Pessoa")
@Table(name = "Pessoa")
@Getter
@Setter
public class Pessoa {

    /** Identificador único da pessoa, gerado automaticamente pelo banco */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdPessoa;

    /** Nome completo da pessoa */
    private String nmPessoa;

    /** Data de nascimento da pessoa (pode ser armazenada como String ou LocalDate) */
    private String dtNascimento;

    /** 
     * E-mail da pessoa, único no sistema.
     * - @Column(unique = true): Garante unicidade no banco de dados
     */
    @Column(unique = true)
    private String emailPessoa;
    
    /** CPF da pessoa (deveria ser único, mas a anotação não está aplicada aqui) */
    private String cpfPessoa;

    /** Senha da pessoa para autenticação */
    private String senhaPessoa;

    /** CEP da residência da pessoa */
    private String cepPessoa;

    /** Nome da rua da residência */
    private String ruaPessoa;

    /** Número da residência */
    private int numeroRuaPessoa;

    /** Complemento da residência (ex: apartamento, bloco) */
    private String complementoPessoa;
    
    /**
     * Relação One-to-Many com a entidade PedidoProduto.
     * Uma pessoa pode ter vários pedidos.
     * 
     * Configurações:
     * - cascade = CascadeType.PERSIST: persiste automaticamente os pedidos ao persistir a pessoa.
     * - fetch = FetchType.LAZY: carrega a lista de pedidos apenas quando acessada, melhorando desempenho.
     */
    @OneToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<PedidoProduto> pedidos;
    
}
