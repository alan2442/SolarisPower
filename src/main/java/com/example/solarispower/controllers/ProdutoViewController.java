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
    private ProdutoService produtoService; // Injeção do serviço de Produto para operações de negócio

    // ===============================
    // EXIBIR PÁGINA DE CADASTRO
    // ===============================
    /**
     * Exibe a página de cadastro de produtos para a empresa logada.
     * Adiciona à model a lista de produtos e um DTO vazio para o formulário.
     * 
     * @param empresa Empresa logada obtida da sessão.
     * @param model   Objeto Model para passar atributos para a view.
     * @return Nome da view "CadastroProduto/CadastrarProduto".
     */
    @GetMapping("/paginaCadastroProdutos")
    public String mostrarPaginaCadastro(@SessionAttribute("empresaLogado") Empresa empresa, Model model) {
        List<Produto> listaProdutos = produtoService.listarPorEmpresa(empresa);

        model.addAttribute("produtos", listaProdutos); // Lista de produtos da empresa
        model.addAttribute("produtoDTO", new ProdutoDTO()); // DTO vazio para formulário
        model.addAttribute("idProdutoEdicao", null); // Indica que não está editando

        return "CadastroProduto/CadastrarProduto";
    }

    // ===============================
    // CADASTRAR OU ATUALIZAR PRODUTO (FORM TRADICIONAL)
    // ===============================
    /**
     * Cadastra um novo produto ou atualiza um existente, baseado no idProdutoEdicao.
     * 
     * @param produtoDTO      DTO com dados do formulário.
     * @param empresa         Empresa logada da sessão.
     * @param idProdutoEdicao ID do produto para edição (opcional).
     * @param model           Model para passar mensagens e atributos para view.
     * @return Nome da view "CadastroProduto/CadastrarProduto" com feedback.
     */
    @PostMapping("/paginaCadastroProdutos")
    public String cadastrarOuAtualizarProduto(
            @ModelAttribute ProdutoDTO produtoDTO,
            @SessionAttribute("empresaLogado") Empresa empresa,
            @RequestParam(name = "idProdutoEdicao", required = false) Long idProdutoEdicao,
            Model model) {

        try {
            Produto produto;

            // Verifica se é atualização ou novo cadastro
            if (idProdutoEdicao != null) {
                Optional<Produto> opt = produtoService.buscarPorId(idProdutoEdicao);
                if (opt.isEmpty())
                    throw new RuntimeException("Produto não encontrado para atualização.");
                produto = opt.get(); // Produto existente
            } else {
                produto = new Produto(); // Novo produto
            }

            // Preenche os campos do produto com os dados do DTO
            produto.setNome(produtoDTO.getNome());
            produto.setDescricao(produtoDTO.getDescricao());
            produto.setQuantidade(produtoDTO.getQuantidade());
            produto.setPreco(new BigDecimal(produtoDTO.getPreco()));
            produto.setCategoria(produtoDTO.getCategoria());

            MultipartFile imagem = produtoDTO.getImagem();
            if (imagem != null && !imagem.isEmpty()) {
                produto.setImagem(imagem.getBytes()); // Converte imagem para byte array
            }

            produto.setEmpresa(empresa); // Define empresa dona do produto
            produtoService.salvarProduto(produto); // Salva ou atualiza produto no banco

            // Mensagem de sucesso
            model.addAttribute("msg", idProdutoEdicao != null
                    ? "✅ O produto foi atualizado com sucesso!"
                    : "✅ Produto cadastrado com sucesso!");

        } catch (Exception e) {
            // Mensagem de erro em caso de exceção
            model.addAttribute("erro", "❌ Não foi possível salvar o produto. Detalhes: " + e.getMessage());
        }

        // Recarrega lista de produtos e formulário limpo
        model.addAttribute("produtos", produtoService.listarPorEmpresa(empresa));
        model.addAttribute("produtoDTO", new ProdutoDTO());
        model.addAttribute("idProdutoEdicao", null);

        return "CadastroProduto/CadastrarProduto";
    }

    // ===============================
    // LISTAR PRODUTOS DA EMPRESA PARA AJAX
    // ===============================
    /**
     * Retorna a lista de produtos da empresa logada em formato JSON.
     * Utilizado para requisições AJAX.
     * 
     * @param empresa Empresa logada da sessão.
     * @return Lista de ProdutoDTOs convertida a partir de Produtos.
     */
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
    /**
     * Retorna os dados de um produto específico para edição via AJAX.
     * 
     * @param id ID do produto.
     * @return ProdutoDTO com os dados do produto ou null se não encontrado.
     */
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
    /**
     * Atualiza um produto existente via AJAX.
     * 
     * @param produtoDTO      DTO com os novos dados.
     * @param idProdutoEdicao ID do produto a ser atualizado.
     * @param empresa         Empresa logada da sessão.
     * @return Mensagem de sucesso ou erro.
     * @throws Exception Em caso de erro ao processar imagem.
     */
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
    /**
     * Exclui um produto via AJAX.
     * 
     * @param id ID do produto a ser excluído.
     * @return Mensagem de sucesso ou erro.
     */
    @PostMapping("/produto/excluir-ajax/{id}")
    @ResponseBody
    public String excluirProdutoAjax(@PathVariable Long id) {
        try {
            produtoService.deletarProduto(id);
            return "✅ O produto foi excluído com sucesso!";
        } catch (Exception e) {
            return "❌ Não foi possível excluir o produto. Tente novamente.";
        }
    }

    // ===============================
    // EXIBIR IMAGEM
    // ===============================
    /**
     * Retorna a imagem de um produto como array de bytes.
     * 
     * @param id ID do produto.
     * @return Array de bytes da imagem ou array vazio se não existir.
     */
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
    /**
     * Lista produtos para a página de clientes, com opção de busca por nome.
     * 
     * @param busca Termo de busca opcional.
     * @param model Model para passar atributos para view.
     * @return View "Produtos/produtos".
     */
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
        model.addAttribute("busca", busca); // Para manter valor no input
        return "Produtos/produtos";
    }

    // ===============================
    // EXCLUIR PRODUTO VIA DELETE
    // ===============================
    /**
     * Exclui um produto pelo ID via requisição DELETE.
     * 
     * @param id ID do produto.
     * @return ResponseEntity com status OK.
     */
    @DeleteMapping("/paginaCadastroProdutos/excluir/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id) {
        produtoService.excluirProduto(id);
        return ResponseEntity.ok().build();
    }

    // ===============================
    // BUSCAR PRODUTOS AJAX COM FILTRO
    // ===============================
    /**
     * Busca produtos de uma empresa via AJAX com filtro por nome.
     * 
     * @param nome    Nome para filtrar (opcional).
     * @param empresa Empresa logada da sessão.
     * @return Lista de ProdutoDTOs filtrada.
     */
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
