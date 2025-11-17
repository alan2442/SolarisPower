package com.example.solarispower.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class SolarisController {

    /* =========================================
       ROTAS PARA EXIBIÇÃO DAS PÁGINAS
       ========================================= */

    // ===============================
    // ROTAS DO MENU DE NAVEGAÇÃO
    // ===============================

    /* Rota principal do site */
    @GetMapping("/")
    public String index() {
        return "Index/index"; // Retorna a view da página inicial
    }

    /* Segunda rota para index, possivelmente para compatibilidade ou redirecionamento */
    @GetMapping("/index2")
    public String index2() {
        return "Index/index"; // Retorna a mesma view da página inicial
    }

    /* Rota para página de suporte */
    @GetMapping("/suporte")
    public String suporte() {
        return "Suporte/suporte"; // Retorna a view da página de suporte
    }

    /* Processa o envio do formulário da página de suporte */
    @PostMapping("/suporte")
    public String enviarMensagem(
            @RequestParam("assunto") String assunto, // Captura o assunto enviado pelo usuário
            @RequestParam("duvida") String duvida,   // Captura a dúvida enviada pelo usuário
            Model model) {

        try {
            // Aqui é possível integrar envio de e-mail ou persistência no banco futuramente
            System.out.println("Assunto: " + assunto);
            System.out.println("Dúvida: " + duvida);

            // Mensagem de sucesso exibida na página
            model.addAttribute("msg", "💬 Sua mensagem foi enviada com sucesso! Nossa equipe responderá em breve.");

        } catch (Exception e) {
            // Mensagem de erro exibida na página
            model.addAttribute("erro", "❌ Não foi possível enviar sua mensagem. Tente novamente mais tarde.");
        }

        // Retorna à mesma página de suporte para exibir mensagem
        return "Suporte/suporte";
    }

    /* Rota para exibir página de planos */
    @GetMapping("/planos")
    public String planos() {
        return "Planos/planos"; // Retorna a view da página de planos
    }

    /* Rota para página de dúvidas frequentes */
    @GetMapping("/duvidas")
    public String duvidas() {
        return "Duvidas/duvidas"; // Retorna a view da página de dúvidas
    }

    /* Rota para cadastro de produto */
    @GetMapping("/cadastrarProduto")
    public String cadastrarProduto() {
        return "CadastroProduto/CadastrarProduto"; // Retorna a view de cadastro de produto
    }

    /* Rota para calculadora solar */
    @GetMapping("/calculadoraSolar")
    public String calculadoraSolar() {
        return "CalculadoraSolar/calculadora"; // Retorna a view da calculadora solar
    }

    // ===============================
    // FIM DAS ROTAS DO MENU DE NAVEGAÇÃO
    // ===============================

    /* Rota para exibir carrinho de compras */
    @GetMapping("/carrinho")
    public String carrinho() {
        return "Produtos/carrinho"; // Retorna a view do carrinho
    }

    /* Rota para exibir notícias */
    @GetMapping("/noticia")
    public String noticia() {
        return "Noticia/noticia"; // Retorna a view de notícias
    }

    /* Rota para página sobre a empresa */
    @GetMapping("/sobre")
    public String sobre() {
        return "Sobre/sobre"; // Retorna a view sobre
    }

    /* Rota alternativa para cadastro de produto (duplicada, mas mantém funcionalidade) */
    @GetMapping("/cadastrarProd")
    public String cadastrarProd() {
        return "CadastroProduto/CadastrarProduto"; // Retorna a mesma view de cadastro de produto
    }

    /* Rota para logout do usuário */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Encerra a sessão do usuário, removendo todos os atributos
        return "redirect:/loginUsuario"; // Redireciona para a página de login
    }

    /* =========================================
       FIM DAS ROTAS DE PÁGINAS
       ========================================= */
}
