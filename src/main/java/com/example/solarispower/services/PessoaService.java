package com.example.solarispower.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.solarispower.dto.LoginRequestDTO;
import com.example.solarispower.dto.PessoaRequestDTO;
import com.example.solarispower.dto.PessoaResponseDTO;
import com.example.solarispower.models.Pessoa;
import com.example.solarispower.repository.PessoaRepository;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public PessoaResponseDTO cadastrar(PessoaRequestDTO dto) {
        if (!dto.getSenhaPessoa().equals(dto.getConfSenha())) {
            throw new IllegalArgumentException("As senhas não coincidem.");
        }

        if (pessoaRepository.existsByEmailPessoa(dto.getEmailPessoa())) {
            throw new IllegalArgumentException("E-mail já cadastrado no sistema.");
        }

        Pessoa pessoa = new Pessoa();
        converteDtoParaEntidade(dto, pessoa);

        Pessoa salva = pessoaRepository.save(pessoa);
        return new PessoaResponseDTO(salva);
    }

    public PessoaResponseDTO autenticar(LoginRequestDTO dto) {
        Pessoa pessoa = pessoaRepository.findByEmailPessoaAndSenhaPessoa(
                dto.getEmailPessoa(),
                dto.getSenhaPessoa());

        if (pessoa == null) {
            throw new IllegalArgumentException("Não foi possível acessar sua conta. Verifique seu e-mail e senha.");
        }

        return new PessoaResponseDTO(pessoa);
    }

    public List<PessoaResponseDTO> listarTodos() {
        return pessoaRepository.findAll()
                .stream()
                .map(PessoaResponseDTO::new)
                .collect(Collectors.toList());
    }

    public PessoaResponseDTO buscarPorId(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        return new PessoaResponseDTO(pessoa);
    }

    public PessoaResponseDTO atualizar(Long id, PessoaRequestDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        pessoa.setNmPessoa(dto.getNmPessoa());
        pessoa.setDtNascimento(dto.getDtNascimento());
        pessoa.setEmailPessoa(dto.getEmailPessoa());
        pessoa.setCpfPessoa(dto.getCpfPessoa());
        pessoa.setCepPessoa(dto.getCepPessoa());
        pessoa.setRuaPessoa(dto.getRuaPessoa());
        pessoa.setNumeroRuaPessoa(dto.getNumeroRuaPessoa());
        pessoa.setComplementoPessoa(dto.getComplementoPessoa());

        // Atualiza a senha apenas se ela for informada
        if (dto.getSenhaPessoa() != null && !dto.getSenhaPessoa().trim().isEmpty()) {
            pessoa.setSenhaPessoa(dto.getSenhaPessoa());
        }

        Pessoa atualizada = pessoaRepository.save(pessoa);
        return new PessoaResponseDTO(atualizada);
    }

    public void deletar(Long id) {
        if (!pessoaRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        pessoaRepository.deleteById(id);
    }

    private void converteDtoParaEntidade(PessoaRequestDTO dto, Pessoa pessoa) {
        pessoa.setNmPessoa(dto.getNmPessoa());
        pessoa.setDtNascimento(dto.getDtNascimento());
        pessoa.setEmailPessoa(dto.getEmailPessoa());
        pessoa.setCpfPessoa(dto.getCpfPessoa());
        pessoa.setSenhaPessoa(dto.getSenhaPessoa());
        pessoa.setCepPessoa(dto.getCepPessoa());
        pessoa.setRuaPessoa(dto.getRuaPessoa());
        pessoa.setNumeroRuaPessoa(dto.getNumeroRuaPessoa());
        pessoa.setComplementoPessoa(dto.getComplementoPessoa());
    }
}