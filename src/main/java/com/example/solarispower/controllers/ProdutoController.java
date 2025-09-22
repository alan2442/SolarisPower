package com.example.solarispower.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.solarispower.models.Produto;
import com.example.solarispower.services.ProdutoService;

@RestController
@RequestMapping("/cadastroProdutos")
public class ProdutoController {
    
    @Autowired
    private ProdutoService produtoService;

    // Listar todos os produtos
    @GetMapping
    public ResponseEntity<Iterable<Produto>> listarTodos() {
        return ResponseEntity.ok(produtoService.listarTodosProdutos());
    }

    // Buscar produto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.buscarPorId(id);
        return produto.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    /*  Buscar produto por cdProduto (método customizado)
    @GetMapping("/codigo/{cdProduto}")
    public ResponseEntity<Produto> buscarPorCdProduto(@PathVariable Long cdProduto) {
        Produto produto = produtoService.buscarPorCdProduto(cdProduto);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

    /*  Criar novo produto
    @PostMapping
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
        Produto novoProduto = produtoService.salvarProduto(produto);
        return ResponseEntity.ok(novoProduto);
    } */

    // Atualizar produto existente
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        Produto atualizado = produtoService.atualizarProduto(id, produtoAtualizado);
        if (atualizado != null) {
            return ResponseEntity.ok(atualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar produto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }



}
