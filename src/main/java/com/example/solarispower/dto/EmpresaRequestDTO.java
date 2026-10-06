package com.example.solarispower.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaRequestDTO {
    private String nmEmpresa;
    private String cnpjEmpresa;
    private String emailEmpresa;
    private String segmentoEmpresa;
    private String senhaEmpresa;
    private String confSenhaEmpresa;
    private String cepEmpresa;
    private String ruaEmpresa;
    private int numeroRuaEmpresa;
    private String complementoEmpresa;
}