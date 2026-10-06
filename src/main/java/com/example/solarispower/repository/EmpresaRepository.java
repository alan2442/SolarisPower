package com.example.solarispower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.solarispower.models.Empresa;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Empresa findByEmailEmpresaAndSenhaEmpresa(String emailEmpresa, String senhaEmpresa);

    boolean existsByEmailEmpresa(String emailEmpresa);

    boolean existsByCnpjEmpresa(String cnpjEmpresa);

    Optional<Empresa> findByEmailEmpresa(String emailEmpresa);
}