package com.example.solarispower.models;

import java.util.List;

import jakarta.persistence.CascadeType;
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
 * Entidade Empresa mapeada para a tabela "Empresa" no banco de dados.
 * Representa uma empresa que pode ter vários produtos.
 * 
 * Anotações:
 * - @Entity: Marca a classe como uma entidade JPA.
 * - @Table: Define o nome da tabela no banco de dados.
 * - @Getter / @Setter (Lombok): Gera automaticamente métodos getters e setters.
 */
@Entity(name = "Empresa")
@Table(name = "Empresa")
@Getter
@Setter
public class Empresa {

    /** Identificador único da empresa, gerado automaticamente pelo banco */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cdEmpresa;

    /** Nome da empresa */
    private String nmEmpresa;

    /** CNPJ da empresa (idealmente deve ser único) */
    private String cnpjEmpresa;

    /** E-mail de contato da empresa */
    private String emailEmpresa;

    /** Segmento de atuação da empresa */
    private String segmentoEmpresa;

    /** CEP da empresa */
    private String cepEmpresa;

    /** Rua onde a empresa está localizada */
    private String ruaEmpresa;

    /** Número da rua */
    private int numeroRuaEmpresa;

    /** Senha de acesso da empresa (armazenar de forma segura/hashing) */
    private String senhaEmpresa;

    /** Complemento do endereço da empresa */
    private String complementoEmpresa;

    /**
     * Relação One-to-Many com a entidade Produto.
     * Uma empresa pode ter vários produtos.
     * 
     * Configurações:
     * - cascade = CascadeType.PERSIST: persistir produtos automaticamente ao persistir a empresa.
     * - fetch = FetchType.LAZY: carrega a lista de produtos apenas quando acessada.
     */
    @OneToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Produto> produtos;
}
