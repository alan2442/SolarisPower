package com.example.solarispower.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.solarispower.dto.EmpresaRequestDTO;
import com.example.solarispower.dto.EmpresaResponseDTO;
import com.example.solarispower.dto.LoginEmpresaRequestDTO;
import com.example.solarispower.dto.LoginRequestDTO;
import com.example.solarispower.models.Empresa;
import com.example.solarispower.repository.EmpresaRepository;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public EmpresaResponseDTO cadastrar(EmpresaRequestDTO dto) {
        if (!dto.getSenhaEmpresa().equals(dto.getConfSenhaEmpresa())) {
            throw new IllegalArgumentException("As senhas não coincidem.");
        }

        if (empresaRepository.existsByEmailEmpresa(dto.getEmailEmpresa())) {
            throw new IllegalArgumentException("E-mail corporativo já cadastrado.");
        }

        if (dto.getCnpjEmpresa() != null && !dto.getCnpjEmpresa().trim().isEmpty()
                && empresaRepository.existsByCnpjEmpresa(dto.getCnpjEmpresa())) {
            throw new IllegalArgumentException("CNPJ já cadastrado no sistema.");
        }

        Empresa empresa = new Empresa();
        converteDtoParaEntidade(dto, empresa);

        Empresa salva = empresaRepository.save(empresa);
        return new EmpresaResponseDTO(salva);
    }

    public EmpresaResponseDTO autenticar(LoginEmpresaRequestDTO dto) {
        Empresa empresa = empresaRepository.findByEmailEmpresaAndSenhaEmpresa(
                dto.getEmailEmpresa(),
                dto.getSenhaEmpresa());

        if (empresa == null) {
            throw new IllegalArgumentException("E-mail corporativo ou senha inválidos.");
        }

        return new EmpresaResponseDTO(empresa);
    }

    public List<EmpresaResponseDTO> listarTodas() {
        return empresaRepository.findAll()
                .stream()
                .map(EmpresaResponseDTO::new)
                .collect(Collectors.toList());
    }

    public EmpresaResponseDTO buscarPorId(Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada."));
        return new EmpresaResponseDTO(empresa);
    }

    public EmpresaResponseDTO atualizar(Long id, EmpresaRequestDTO dto) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada."));

        empresa.setNmEmpresa(dto.getNmEmpresa());
        empresa.setCnpjEmpresa(dto.getCnpjEmpresa());
        empresa.setEmailEmpresa(dto.getEmailEmpresa());
        empresa.setSegmentoEmpresa(dto.getSegmentoEmpresa());
        empresa.setCepEmpresa(dto.getCepEmpresa());
        empresa.setRuaEmpresa(dto.getRuaEmpresa());
        empresa.setNumeroRuaEmpresa(dto.getNumeroRuaEmpresa());
        empresa.setComplementoEmpresa(dto.getComplementoEmpresa());

        // Atualiza a senha apenas se ela for fornecida
        if (dto.getSenhaEmpresa() != null && !dto.getSenhaEmpresa().trim().isEmpty()) {
            empresa.setSenhaEmpresa(dto.getSenhaEmpresa());
        }

        Empresa atualizada = empresaRepository.save(empresa);
        return new EmpresaResponseDTO(atualizada);
    }

    public void deletar(Long id) {
        if (!empresaRepository.existsById(id)) {
            throw new IllegalArgumentException("Empresa não encontrada.");
        }
        empresaRepository.deleteById(id);
    }

    private void converteDtoParaEntidade(EmpresaRequestDTO dto, Empresa empresa) {
        empresa.setNmEmpresa(dto.getNmEmpresa());
        empresa.setCnpjEmpresa(dto.getCnpjEmpresa());
        empresa.setEmailEmpresa(dto.getEmailEmpresa());
        empresa.setSegmentoEmpresa(dto.getSegmentoEmpresa());
        empresa.setSenhaEmpresa(dto.getSenhaEmpresa());
        empresa.setCepEmpresa(dto.getCepEmpresa());
        empresa.setRuaEmpresa(dto.getRuaEmpresa());
        empresa.setNumeroRuaEmpresa(dto.getNumeroRuaEmpresa());
        empresa.setComplementoEmpresa(dto.getComplementoEmpresa());
    }
}