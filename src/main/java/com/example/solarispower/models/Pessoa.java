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

@Entity(name = "Pessoa")
@Table(name = "Pessoa")
@Getter
@Setter
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdPessoa;


    private String nmPessoa;
    private String dtNascimento;
    private String emailPessoa;
    private String cpfPessoa; // unique
    private String senhaPessoa;
    private String cepPessoa;
    private String ruaPessoa;
    private int numeroRuaPessoa;
    private String complementoPessoa;
    

    @OneToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<PedidoProduto> pedidos;
    
}
