package com.example.solarispower.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.solarispower.dto.ProdutoRequestDTO;
import com.example.solarispower.dto.ProdutoResponseDTO;
import com.example.solarispower.models.Empresa;
import com.example.solarispower.models.Produto;
import com.example.solarispower.repository.EmpresaRepository;
import com.example.solarispower.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public ProdutoResponseDTO cadastrar(ProdutoRequestDTO dto, MultipartFile imagem) throws IOException {
        Empresa empresa = empresaRepository.findById(dto.getCdEmpresa())
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada com ID: " + dto.getCdEmpresa()));

        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setQuantidade(dto.getQuantidade());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(dto.getCategoria());
        produto.setEmpresa(empresa);

        if (imagem != null && !imagem.isEmpty()) {
            produto.setImagem(imagem.getBytes());
        }

        Produto salvo = produtoRepository.save(produto);
        return new ProdutoResponseDTO(salvo);
    }

    public List<ProdutoResponseDTO> listarPorEmpresa(Long cdEmpresa) {
        return produtoRepository.findByEmpresaCdEmpresa(cdEmpresa)
                .stream()
                .map(ProdutoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
        return new ProdutoResponseDTO(produto);
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto, MultipartFile imagem) throws IOException {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setQuantidade(dto.getQuantidade());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(dto.getCategoria());

        if (imagem != null && !imagem.isEmpty()) {
            produto.setImagem(imagem.getBytes());
        }

        Produto atualizado = produtoRepository.save(produto);
        return new ProdutoResponseDTO(atualizado);
    }

    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado.");
        }
        produtoRepository.deleteById(id);
    }



    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoResponseDTO::new)
                .collect(Collectors.toList());
    }
}