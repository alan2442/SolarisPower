package com.example.solarispower.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.solarispower.models.Empresa;

/**
 * Interface de repositório para a entidade Empresa.
 * 
 * Extende CrudRepository, que fornece operações básicas de CRUD:
 * - save(S)
 * - findById(ID)
 * - findAll()
 * - deleteById(ID)
 * - delete(entity)
 * 
 * Anotações:
 * - Não precisa de @Repository, pois o Spring Data já detecta automaticamente.
 */
public interface EmpresaRepository extends CrudRepository<Empresa, Long> {

    /**
     * Busca uma empresa pelo seu código (ID) único.
     * 
     * @param cdEmpresa Identificador da empresa
     * @return Empresa encontrada ou null se não existir
     */
    Empresa findByCdEmpresa(long cdEmpresa);

    /**
     * Busca uma empresa pelo email e senha, utilizado para autenticação.
     * 
     * @param emailEmpresa Email da empresa
     * @param senhaEmpresa Senha da empresa
     * @return Empresa encontrada ou null se não houver correspondência
     */
    Empresa findByEmailEmpresaAndSenhaEmpresa(String emailEmpresa, String senhaEmpresa);
}
