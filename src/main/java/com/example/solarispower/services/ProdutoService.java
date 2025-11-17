package com.example.solarispower.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.solarispower.models.Empresa;
import com.example.solarispower.models.Produto;
import com.example.solarispower.repository.ProdutoRepository;

/**
 * Serviço responsável pela lógica de negócios relacionada à entidade Produto.
 * 
 * A anotação @Service indica que esta classe é um componente do Spring
 * que pode ser injetado em controllers ou outros serviços.
 */
@Service
public class ProdutoService {

    // Injeta automaticamente o repositório de produtos
    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Busca um produto pelo seu ID.
     *
     * @param id ID do produto a ser buscado
     * @return Optional contendo o produto, ou vazio caso não encontrado
     */
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    /**
     * Salva um novo produto ou atualiza um produto existente.
     *
     * @param produto Produto a ser salvo ou atualizado
     * @return Produto persistido no banco de dados
     */
    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    /**
     * Atualiza um produto existente com novos dados.
     *
     * @param id ID do produto a ser atualizado
     * @param produtoAtualizado Objeto com os novos dados do produto
     * @return Produto atualizado ou null se o produto não existir
     */
    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);

        if (produtoExistente.isPresent()) {
            Produto produto = produtoExistente.get();

            // Atualiza todos os campos do produto existente
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

    /**
     * Deleta um produto pelo seu ID.
     *
     * @param id ID do produto a ser deletado
     */
    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    /**
     * Lista todos os produtos existentes no banco de dados.
     *
     * @return Lista de produtos
     */
    public List<Produto> listarTodosProdutos() {
        return (List<Produto>) produtoRepository.findAll();
    }

    /**
     * Lista produtos pertencentes a uma empresa específica.
     *
     * @param empresa Empresa cujos produtos serão listados
     * @return Lista de produtos da empresa
     */
    public List<Produto> listarPorEmpresa(Empresa empresa) {
        return produtoRepository.findByEmpresa(empresa);
    }

    /**
     * Remove um produto pelo seu ID.
     *
     * @param id ID do produto a ser removido
     */
    public void excluirProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    /**
     * Lista produtos cujo nome contém a string fornecida, ignorando maiúsculas/minúsculas.
     *
     * @param nome Nome parcial do produto
     * @return Lista de produtos correspondentes ao critério
     */
    public List<Produto> listarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }
}
