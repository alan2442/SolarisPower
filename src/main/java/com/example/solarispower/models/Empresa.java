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

@Entity(name = "Empresa")
@Table(name = "Empresa")
@Getter
@Setter
public class Empresa {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long cdEmpresa;

    private String nmEmpresa;
    private String cnpjEmpresa; //unique
    private String emailEmpresa;
    private String segmentoEmpresa;
    private String cepEmpresa; 
    private String ruaEmpresa;
    private int numeroRuaEmpresa;
    private String senhaEmpresa;
    private String complementoEmpresa;

    @OneToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Produto> produtos;
    
}
