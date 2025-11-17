package com.example.solarispower.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.solarispower.dto.ProdutoDTO;
import com.example.solarispower.models.Produto;
import com.example.solarispower.services.ProdutoService;

@RestController // Indica que esta classe é um controlador REST (retorna respostas HTTP em formato JSON)
@RequestMapping("/cadastroProdutos") // Define o endpoint base para todas as rotas deste controlador
public class ProdutoController {

    // Injeção automática do serviço de produtos, responsável pela lógica de negócio
    @Autowired
    private ProdutoService produtoService;

    // ==========================================================
    // =================== LISTAR PRODUTOS =======================
    // ==========================================================

    /**
     * Retorna todos os produtos cadastrados no sistema.
     * 
     * @return ResponseEntity contendo uma lista de produtos em formato JSON.
     */
    @GetMapping
    public ResponseEntity<Iterable<Produto>> listarTodos() {
        // Retorna o resultado do serviço dentro de uma resposta HTTP 200 (OK)
        return ResponseEntity.ok(produtoService.listarTodosProdutos());
    }

    // ==========================================================
    // ================== BUSCAR POR ID ==========================
    // ==========================================================

    /**
     * Busca um produto específico pelo seu ID.
     * 
     * @param id identificador do produto.
     * @return ResponseEntity com o produto encontrado ou HTTP 404 (Not Found) se não existir.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.buscarPorId(id);

        // Retorna o produto caso exista, senão retorna 404 (not found)
        return produto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ==========================================================
    // ============== MÉTODOS OPCIONAIS (COMENTADOS) ============
    // ==========================================================

    /*
     * Busca produto pelo código do produto (cdProduto) — exemplo de método customizado.
     * Este método está comentado, podendo ser ativado futuramente se necessário.
     * 
     * @GetMapping("/codigo/{cdProduto}")
     * public ResponseEntity<Produto> buscarPorCdProduto(@PathVariable Long cdProduto) {
     *     Produto produto = produtoService.buscarPorCdProduto(cdProduto);
     *     if (produto != null) {
     *         return ResponseEntity.ok(produto);
     *     } else {
     *         return ResponseEntity.notFound().build();
     *     }
     * }
     */

    /*
     * Criação de um novo produto — método comentado.
     * Pode ser habilitado futuramente para suportar POST de novos registros.
     * 
     * @PostMapping
     * public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
     *     Produto novoProduto = produtoService.salvarProduto(produto);
     *     return ResponseEntity.ok(novoProduto);
     * }
     */

    // ==========================================================
    // =================== ATUALIZAR PRODUTO ====================
    // ==========================================================

    /**
     * Atualiza os dados de um produto existente com base no ID informado.
     * 
     * @param id                identificador do produto a ser atualizado.
     * @param produtoAtualizado objeto contendo os novos dados.
     * @return ResponseEntity com o produto atualizado ou HTTP 404 se o produto não existir.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        Produto atualizado = produtoService.atualizarProduto(id, produtoAtualizado);

        // Retorna 200 (OK) se o produto foi atualizado ou 404 se não encontrado
        if (atualizado != null) {
            return ResponseEntity.ok(atualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ==========================================================
    // =================== DELETAR PRODUTO ======================
    // ==========================================================

    /**
     * Exclui um produto com base no ID informado.
     * 
     * @param id identificador do produto a ser deletado.
     * @return ResponseEntity com status HTTP 204 (No Content) após exclusão bem-sucedida.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        // Retorna HTTP 204 (sem conteúdo) indicando sucesso na exclusão
        return ResponseEntity.noContent().build();
    }

    // ==========================================================
    // ============== BUSCAR PRODUTO PARA EDIÇÃO ================
    // ==========================================================

    /**
     * Busca um produto específico e converte seus dados em um DTO (Data Transfer Object)
     * para ser utilizado na edição (por exemplo, em formulários de front-end).
     * 
     * @param id identificador do produto.
     * @return ResponseEntity contendo um ProdutoDTO com os dados do produto ou 404 caso não encontrado.
     */
    @GetMapping("/editar/{id}")
    public ResponseEntity<ProdutoDTO> buscarProdutoParaEdicao(@PathVariable Long id) {
        Optional<Produto> produtoOpt = produtoService.buscarPorId(id);

        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();

            // Cria um DTO e preenche com os dados do produto encontrado
            ProdutoDTO dto = new ProdutoDTO();
            dto.setNome(produto.getNome());
            dto.setDescricao(produto.getDescricao());
            dto.setQuantidade(produto.getQuantidade());
            dto.setPreco(produto.getPreco().toString());
            dto.setCategoria(produto.getCategoria());

            // Retorna o DTO com status 200 (OK)
            return ResponseEntity.ok(dto);
        } else {
            // Caso o produto não seja encontrado, retorna 404
            return ResponseEntity.notFound().build();
        }
    }

}
