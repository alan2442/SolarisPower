package com.example.solarispower.controllers;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;



@Controller
public class SolarisController {

    

    /* Começo Rota das Páginas */

    // Começo Rotas NavBar -------------------------------------

    /* Rota Index */
    @GetMapping("/")
    public String index() {
        return "Index/index";
    }

    /* Rota Index */
    @GetMapping("/index2")
    public String index2() {
        return "Index/index";
    }

    /* Rota Produtos */
    @GetMapping("/produtos")
    public String produtos() {
        return "Produtos/produtos";
    }

    /* Rota Suporte */
    @GetMapping("/suporte")
    public String suporte() {
        return "Suporte/suporte";
    }

    /* Rota Planos */
    @GetMapping("/planos")
    public String planos() {
        return "Planos/planos";
    }

    /* Rota Duvidas */
    @GetMapping("/duvidas")
    public String duvidas() {
        return "Duvidas/duvidas";
    }


    /* Rota CadastroProduto */
    @GetMapping("/cadastrarProduto")
    public String cadastrarProduto() {
        return "CadastroProduto/CadastrarProduto";
    }



    // Fim Rotas NavBar -------------------------------------

    

    

    /* Rota Carrinho - Corrigir essa rota */
    @GetMapping("/carrinho")
    public String carrinho() {
        return "Produtos/carrinho";
    }

    /* Rota noticia */
    @GetMapping("/noticia")
    public String noticia() {
        return "Noticia/noticia";
    }

    /* Rota sobre */
    @GetMapping("/sobre")
    public String sobre() {
        return "Sobre/sobre";
    }

     /* Rota CadastroProduto */
    @GetMapping("/cadastrarProd")
    public String cadastrarProd() {
        return "CadastroProduto/CadastrarProduto";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Apaga todos os dados da sessão
        return "redirect:/loginUsuario";
    }

    /* Fim Rota das Páginas */

    

    

}
