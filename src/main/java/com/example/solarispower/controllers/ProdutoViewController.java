package com.example.solarispower.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.solarispower.dto.ProdutoDTO;
import com.example.solarispower.models.Empresa;
import com.example.solarispower.models.Produto;
import com.example.solarispower.services.ProdutoService;

@Controller
public class ProdutoViewController {

    @Autowired
    private ProdutoService produtoService;

    // ===============================
    // EXIBIR PÁGINA DE CADASTRO
    // ===============================
    @GetMapping("/paginaCadastroProdutos")
    public String mostrarPaginaCadastro(@SessionAttribute("empresaLogado") Empresa empresa, Model model) {
        List<Produto> listaProdutos = produtoService.listarPorEmpresa(empresa);

        model.addAttribute("produtos", listaProdutos);
        model.addAttribute("produtoDTO", new ProdutoDTO());
        model.addAttribute("idProdutoEdicao", null);

        return "CadastroProduto/CadastrarProduto";
    }

    // ===============================
    // CADASTRAR OU ATUALIZAR PRODUTO (FORM TRADICIONAL)
    // ===============================
    @PostMapping("/paginaCadastroProdutos")
    public String cadastrarOuAtualizarProduto(
            @ModelAttribute ProdutoDTO produtoDTO,
            @SessionAttribute("empresaLogado") Empresa empresa,
            @RequestParam(name = "idProdutoEdicao", required = false) Long idProdutoEdicao,
            Model model) {

        try {
            Produto produto;

            if (idProdutoEdicao != null) {
                Optional<Produto> opt = produtoService.buscarPorId(idProdutoEdicao);
                if (opt.isEmpty())
                    throw new RuntimeException("Produto não encontrado");
                produto = opt.get();
            } else {
                produto = new Produto();
            }

            produto.setNome(produtoDTO.getNome());
            produto.setDescricao(produtoDTO.getDescricao());
            produto.setQuantidade(produtoDTO.getQuantidade());
            produto.setPreco(new BigDecimal(produtoDTO.getPreco()));
            produto.setCategoria(produtoDTO.getCategoria());

            MultipartFile imagem = produtoDTO.getImagem();
            if (imagem != null && !imagem.isEmpty()) {
                produto.setImagem(imagem.getBytes());
            }

            produto.setEmpresa(empresa);
            produtoService.salvarProduto(produto);

            model.addAttribute("msg", idProdutoEdicao != null
                    ? "Produto atualizado com sucesso!"
                    : "Produto cadastrado com sucesso!");

        } catch (Exception e) {
            model.addAttribute("msg", "Erro: " + e.getMessage());
        }

        model.addAttribute("produtos", produtoService.listarPorEmpresa(empresa));
        model.addAttribute("produtoDTO", new ProdutoDTO());
        model.addAttribute("idProdutoEdicao", null);

        return "CadastroProduto/CadastrarProduto";
    }

    // ===============================
    // LISTAR PRODUTOS DA EMPRESA PARA AJAX
    // ===============================
    @GetMapping("/produto/listar-ajax")
    @ResponseBody
    public List<ProdutoDTO> listarProdutosAjax(@SessionAttribute("empresaLogado") Empresa empresa) {
        List<Produto> produtos = produtoService.listarPorEmpresa(empresa);
        return produtos.stream().map(p -> {
            ProdutoDTO dto = new ProdutoDTO();
            dto.setId(p.getId());
            dto.setNome(p.getNome());
            dto.setDescricao(p.getDescricao());
            dto.setQuantidade(p.getQuantidade());
            dto.setPreco(p.getPreco().toString());
            dto.setCategoria(p.getCategoria());
            return dto;
        }).collect(Collectors.toList());
    }

    // ===============================
    // BUSCAR PRODUTO PARA EDIÇÃO (AJAX)
    // ===============================
    @GetMapping("/produto/editar-ajax/{id}")
    @ResponseBody
    public ProdutoDTO buscarProdutoParaEdicaoAjax(@PathVariable("id") Long id) {
        return produtoService.buscarPorId(id)
                .map(p -> {
                    ProdutoDTO dto = new ProdutoDTO();
                    dto.setId(p.getId());
                    dto.setNome(p.getNome());
                    dto.setDescricao(p.getDescricao());
                    dto.setQuantidade(p.getQuantidade());
                    dto.setPreco(p.getPreco().toString());
                    dto.setCategoria(p.getCategoria());
                    return dto;
                })
                .orElse(null);
    }

    // ===============================
    // ATUALIZAR PRODUTO (AJAX)
    // ===============================
    @PostMapping("/produto/atualizar-ajax")
    @ResponseBody
    public String atualizarProdutoAjax(@ModelAttribute ProdutoDTO produtoDTO,
            @RequestParam Long idProdutoEdicao,
            @SessionAttribute("empresaLogado") Empresa empresa) throws Exception {
        Optional<Produto> opt = produtoService.buscarPorId(idProdutoEdicao);
        if (opt.isEmpty())
            return "Produto não encontrado";

        Produto produto = opt.get();
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setQuantidade(produtoDTO.getQuantidade());
        produto.setPreco(new BigDecimal(produtoDTO.getPreco()));
        produto.setCategoria(produtoDTO.getCategoria());

        MultipartFile imagem = produtoDTO.getImagem();
        if (imagem != null && !imagem.isEmpty()) {
            produto.setImagem(imagem.getBytes());
        }

        produtoService.salvarProduto(produto);
        return "Produto atualizado com sucesso!";
    }

    // ===============================
    // EXCLUIR PRODUTO (AJAX)
    // ===============================
    @PostMapping("/produto/excluir-ajax/{id}")
    @ResponseBody
    public String excluirProdutoAjax(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return "Produto excluído com sucesso!";
    }

    // ===============================
    // EXIBIR IMAGEM
    // ===============================
    @GetMapping("/imagem/{id}")
    @ResponseBody
    public byte[] exibirImagem(@PathVariable Long id) {
        return produtoService.buscarPorId(id)
                .map(p -> p.getImagem() != null ? p.getImagem() : new byte[0])
                .orElse(new byte[0]);
    }

    // ===============================
    // LISTAR PRODUTOS PARA CLIENTES
    // ===============================
    @GetMapping("/produtos")
    public String listarProdutosParaClientes(
            @RequestParam(value = "busca", required = false) String busca,
            Model model) {

        List<Produto> produtos;

        if (busca == null || busca.trim().isEmpty()) {
            produtos = produtoService.listarTodosProdutos();
        } else {
            produtos = produtoService.listarPorNome(busca);
        }

        model.addAttribute("produtos", produtos);
        model.addAttribute("busca", busca); // Para manter o valor no input
        return "Produtos/produtos";
    }






    @DeleteMapping("/paginaCadastroProdutos/excluir/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id) {
        produtoService.excluirProduto(id);
        return ResponseEntity.ok().build();
    }







    @GetMapping("/buscar-ajax")
    @ResponseBody
    public List<ProdutoDTO> buscarProdutosAjax(
            @RequestParam(value = "nome", required = false) String nome,
            @SessionAttribute("empresaLogado") Empresa empresa) {

        List<Produto> produtos;

        if (nome == null || nome.trim().isEmpty()) {
            produtos = produtoService.listarPorEmpresa(empresa);
        } else {
            produtos = produtoService.listarPorEmpresa(empresa).stream()
                    .filter(p -> p.getNome().toLowerCase().contains(nome.toLowerCase()))
                    .collect(Collectors.toList());
        }

        return produtos.stream().map(p -> {
            ProdutoDTO dto = new ProdutoDTO();
            dto.setId(p.getId());
            dto.setNome(p.getNome());
            dto.setDescricao(p.getDescricao());
            dto.setQuantidade(p.getQuantidade());
            dto.setPreco(p.getPreco().toString());
            dto.setCategoria(p.getCategoria());
            return dto;
        }).collect(Collectors.toList());
    }


    

}
