package com.example.solarispower.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.solarispower.models.Empresa;

public interface EmpresaRepository extends CrudRepository<Empresa, Long> {
    Empresa findByCdEmpresa(long cdEmpresa);
    Empresa findByEmailEmpresaAndSenhaEmpresa (String emailEmpresa, String senhaEmpresa);
}
