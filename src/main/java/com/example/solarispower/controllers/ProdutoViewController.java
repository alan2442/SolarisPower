package com.example.solarispower.controllers;



// Importações necessárias
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.example.solarispower.dto.ProdutoDTO;
import com.example.solarispower.models.Empresa;
import com.example.solarispower.models.Produto;
import com.example.solarispower.services.ProdutoService;

@Controller
public class ProdutoViewController {

    @Autowired
    private ProdutoService produtoService; // Injeta o serviço responsável por lidar com lógica de Produto



    // ===============================
    // EXIBIR A TELA DE CADASTRO
    // ===============================
    @GetMapping("/paginaCadastroProdutos")
    public String mostrarPaginaCadastro(
            @SessionAttribute("empresaLogado") Empresa empresa,
            Model model) {
        
        // Busca produtos da empresa logada
        List<Produto> listaProdutos = produtoService.listarPorEmpresa(empresa);


        // Adiciona a lista e um objeto DTO vazio ao modelo
        model.addAttribute("produtos", listaProdutos);
        model.addAttribute("produtoDTO", new ProdutoDTO());

        // Retorna o template da tela de cadastro
        return "CadastroProduto/CadastrarProduto";
    }

    


    // ===============================
    // EXIBIR IMAGEM DO PRODUTO
    // ===============================
    @GetMapping("imagem/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> exibirImagem(@PathVariable Long id) {

        // Busca o produto pelo ID
        Optional<Produto> produtoOpt = produtoService.buscarPorId(id);


        // Se encontrado e tiver imagem, retorna a imagem
        if (produtoOpt.isPresent() && produtoOpt.get().getImagem() != null) {
            return ResponseEntity.ok().body(produtoOpt.get().getImagem());
        } else {
            // Caso contrário, retorna 404
            return ResponseEntity.notFound().build();
        }

    }




    // ===============================
    // EXCLUIR PRODUTO
    // ===============================
    @PostMapping("/produto/excluir/{id}")
    public String excluirProduto(
            @PathVariable Long id,
            @SessionAttribute("empresaLogado") Empresa empresa,
            Model model) {
        
        // Remove o produto do banco
        produtoService.deletarProduto(id);
        
        
        // Atualiza lista de produtos no modelo
        List<Produto> listaAtualizada = produtoService.listarPorEmpresa(empresa);
        model.addAttribute("produtos", listaAtualizada);
        model.addAttribute("produtoDTO", new ProdutoDTO());
        model.addAttribute("msg", "Produto excluido com sucesso!");
        

        // Retorna à mesma tela de cadastro
        return "CadastroProduto/CadastrarProduto";
    }




    // ===============================
    // MOSTRAR FORMULÁRIO DE EDIÇÃO
    // ===============================
    @GetMapping("/produto/editar/{id}")
    public String mostrarFormularioEdicao(
            @PathVariable Long id,
            @SessionAttribute("empresaLogado") Empresa empresa,
            Model model) {
        
        // Busca o produto pelo ID
        Optional<Produto> produtoOpt = produtoService.buscarPorId(id);

        if (produtoOpt.isPresent()) {

            Produto produto = produtoOpt.get();

            // Cria um DTO e preenche com os dados do produto
            ProdutoDTO produtoDTO = new ProdutoDTO();
            produtoDTO.setNm_produto(produto.getNm_produto());
            produtoDTO.setDescricao_produto(produto.getDescricao_produto());
            produtoDTO.setQtd_produto(produto.getQtd_produto());
            produtoDTO.setPreco_produto(produto.getPreco_produto().toString());
            produtoDTO.setCategoria(produto.getCategoria());

            // Adiciona o DTO e o ID para saber que é uma edição
            model.addAttribute("produtoDTO", produtoDTO);
            model.addAttribute("idProdutoEdicao", id);

            // Adiciona a lista de produtos para manter a tabela visível
            List<Produto> listaProdutos = produtoService.listarPorEmpresa(empresa);
            model.addAttribute("produtos", listaProdutos);


            // Volta para a mesma tela, mas agora em modo de edição
            return "CadastroProduto/CadastrarProduto";
        } else {
            // Se o produto não for encontrado, redireciona
            return "redirect:/paginaCadastroProdutos";
        }
    }

    



    // ===============================
    // CADASTRAR OU ATUALIZAR PRODUTO
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
                // Se for edição, busca o produto
                Optional<Produto> opt = produtoService.buscarPorId(idProdutoEdicao);
                if (opt.isEmpty())
                    throw new RuntimeException("Produto não encontrado");
                produto = opt.get();
            } else {
                // Caso contrário, cria novo
                produto = new Produto();
            }


            // Preenche os dados do produto com os dados do DTO
            produto.setNm_produto(produtoDTO.getNm_produto());
            produto.setDescricao_produto(produtoDTO.getDescricao_produto());
            produto.setQtd_produto(produtoDTO.getQtd_produto());
            produto.setPreco_produto(new BigDecimal(produtoDTO.getPreco_produto()));
            produto.setCategoria(produtoDTO.getCategoria());


            // Trata imagem, se enviada
            MultipartFile imagem = produtoDTO.getImagem();
            if (imagem != null && !imagem.isEmpty()) {
                produto.setImagem(imagem.getBytes());
            }

            // Associa o produto à empresa da sessão
            produto.setEmpresa(empresa);
            // Salva ou atualiza no banco
            produtoService.salvarProduto(produto);

            // Mensagem de sucesso para o usuário
            model.addAttribute("msg", idProdutoEdicao != null 
            ? "Produto atualizado com sucesso!" 
            : "Produto cadastrado com sucesso!");

        } catch (Exception e) {
            // Em caso de erro, exibe mensagem
            model.addAttribute("msg", "Erro "+ e.getMessage());
        }


        // Recarrega os dados para exibir na tela
        model.addAttribute("produtos", produtoService.listarPorEmpresa(empresa));
        model.addAttribute("produtoDTO", new ProdutoDTO());

        // Retorna para a mesma tela
        return "CadastroProduto/CadastrarProduto";
    }

}
