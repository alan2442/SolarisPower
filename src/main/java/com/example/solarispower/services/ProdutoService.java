package com.example.solarispower.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.solarispower.models.Empresa;
import com.example.solarispower.models.Produto;
import com.example.solarispower.repository.ProdutoRepository;

// Anotação @Service indica que esta classe é um componente de serviço do Spring,
// responsável pela lógica de negócios relacionada aos produtos.
@Service
public class ProdutoService {

    // Injeta automaticamente a dependência do repositório de produtos
    @Autowired
    private ProdutoRepository produtoRepository;

    // Busca um produto pelo seu ID, retornando um Optional que pode estar vazio
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    // Salva um novo produto no banco de dados (ou atualiza se já existir)
    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    // Atualiza um produto existente pelo ID com os dados fornecidos em
    // produtoAtualizado
    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);

        if (produtoExistente.isPresent()) {

            Produto produto = produtoExistente.get();

            // Atualiza os campos do produto existente com os dados do produto atualizado
            produto.setNome(produtoAtualizado.getNome());
            produto.setDescricao(produtoAtualizado.getDescricao());
            produto.setQuantidade(produtoAtualizado.getQuantidade());
            produto.setPreco(produtoAtualizado.getPreco());
            produto.setCategoria(produtoAtualizado.getCategoria());
            produto.setPedido(produtoAtualizado.getPedido());
            produto.setEmpresa(produtoAtualizado.getEmpresa());
            produto.setImagem(produtoAtualizado.getImagem());

            return produtoRepository.save(produto);

        } else {
            return null;
        }
    }

    // Deletar produto por ID
    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    // Listar todos os produtos
    public List<Produto> listarTodosProdutos() {
        return (List<Produto>) produtoRepository.findAll();
    }

    public List<Produto> listarPorEmpresa(Empresa empresa) {
        return produtoRepository.findByEmpresa(empresa);
    }

    public void excluirProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    public List<Produto> listarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

}
