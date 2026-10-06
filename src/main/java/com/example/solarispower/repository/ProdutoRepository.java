package com.example.solarispower.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.solarispower.models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
    // Busca os produtos pertencentes a uma empresa específica pelo cdEmpresa
    List<Produto> findByEmpresaCdEmpresa(Long cdEmpresa);
}