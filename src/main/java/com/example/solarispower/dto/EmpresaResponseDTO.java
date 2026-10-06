package com.example.solarispower.dto;

import com.example.solarispower.models.Empresa;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmpresaResponseDTO {
    private long cdEmpresa;
    private String nmEmpresa;
    private String cnpjEmpresa;
    private String emailEmpresa;
    private String segmentoEmpresa;
    private String cepEmpresa;
    private String ruaEmpresa;
    private int numeroRuaEmpresa;
    private String complementoEmpresa;

    public EmpresaResponseDTO(Empresa empresa) {
        this.cdEmpresa = empresa.getCdEmpresa();
        this.nmEmpresa = empresa.getNmEmpresa();
        this.cnpjEmpresa = empresa.getCnpjEmpresa();
        this.emailEmpresa = empresa.getEmailEmpresa();
        this.segmentoEmpresa = empresa.getSegmentoEmpresa();
        this.cepEmpresa = empresa.getCepEmpresa();
        this.ruaEmpresa = empresa.getRuaEmpresa();
        this.numeroRuaEmpresa = empresa.getNumeroRuaEmpresa();
        this.complementoEmpresa = empresa.getComplementoEmpresa();
    }
}