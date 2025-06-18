package com.example.solarispower.controllers;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.solarispower.models.Empresa;
import com.example.solarispower.models.Pessoa;
import com.example.solarispower.repository.PessoaRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PessoaController {


    @Autowired
    private PessoaRepository pr;



    // Começo Rotas de Login e de Cadastro --------------------------------

    /* Rota cadastroUsuario */
    @RequestMapping(value = "/cadastrarUsuario", method = RequestMethod.GET)
    public String cadastroUsuario(Model model) {
        model.addAttribute("pessoa", new Pessoa());
        return "Cadastro/CadastroUsuario";
    }

    @RequestMapping(value = "/cadastroUsuario", method = RequestMethod.POST)
    public String form(Pessoa pessoa, RedirectAttributes redirectAttributes) {

        pr.save(pessoa);
        redirectAttributes.addFlashAttribute("mensagemCadastroUsuario", "Cadastro realizado com sucesso! Faça login para continuar.");
        return "redirect:/loginUsuario";
    }

    /* Rota loginUsuario */
    @GetMapping("/loginUsuario")
    public String loginUsuario(HttpSession session) {

        Pessoa pessoaLogada = (Pessoa) session.getAttribute("usuarioLogado");
        Empresa empresaLogada = (Empresa) session.getAttribute("empresaLogado");

        if (pessoaLogada != null) {
            // Redireciona para perfilUsuario se estiver logado
            return "redirect:/perfilUsuario";
        } else if (empresaLogada != null) {
            // Redireciona para perfilEmpresa se estiver logado
            return "redirect:/perfilEmpresa";
        } else {
            // Redireciona para login se não estiver logado
            return "Login/login";
        }

    }

    /* Rota loginUsuario */
    @PostMapping("/loginUsuario")
    public String loginUsuario(
            @RequestParam("emailPessoa") String email,
            @RequestParam("senhaPessoa") String senha,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        Pessoa pessoa = pr.findByEmailPessoaAndSenhaPessoa(email, senha);

        if (pessoa != null) {
            // Salvando o usuário logado na sessão
            session.setAttribute("usuarioLogado", pessoa);
            return "redirect:/perfilUsuario";
        } else {
            redirectAttributes.addFlashAttribute("erro", "Email ou senha inválidos!");
            return "redirect:/loginUsuario";
        }
    }




    /* Rota PerfilUsuario */
    @GetMapping("/perfilUsuario")
    public String perfilUsuario(HttpSession session, Model model) {
        Pessoa pessoaLogada = (Pessoa) session.getAttribute("usuarioLogado");

        if (pessoaLogada == null) {
            // Redireciona para login se não estiver logado
            return "redirect:/loginUsuario";
        }

        System.out.println("Usuário logado: " + pessoaLogada.getNmPessoa());

        model.addAttribute("pessoa", pessoaLogada);
        return "Perfil/PerfilUsuario";
    }




    /* Começo CRUD Usuario */

    // Lista todos os usuários
    @GetMapping("/usuarios")
    public String listaUsuarios(Model model) {
        Iterable<Pessoa> pessoas = pr.findAll();
        model.addAttribute("pessoas", pessoas);
        return "Usuario/listaUsuarios";
    }

    // Formulário de edição
    @GetMapping("/editarUsuario/{id}")
    public String editarUsuario(@PathVariable("id") Long id, Model model) {
        Optional<Pessoa> pessoa = pr.findById(id);
        if (pessoa.isPresent()) {
            model.addAttribute("pessoa", pessoa.get());
            return "Cadastro/editarUsuario";
        } else {
            return "redirect:/usuarios";
        }
    }

    // Atualiza os dados do usuário
    @PostMapping("/atualizarUsuario")
    public String atualizarUsuario(Pessoa pessoa, HttpSession session, RedirectAttributes redirectAttributes) {
        Pessoa pessoaLogada = (Pessoa) session.getAttribute("usuarioLogado");

        if (pessoaLogada == null) {
            return "redirect:/loginUsuario"; // Usuário não está logado
        }

        // Garante que o ID da pessoa que será salva é o mesmo da sessão
        pessoa.setCdPessoa(pessoaLogada.getCdPessoa());

        // Atualiza a pessoa no banco de dados
        pr.save(pessoa);

        // Atualiza os dados da sessão também
        session.setAttribute("usuarioLogado", pessoa);

        redirectAttributes.addFlashAttribute("mensagemAtualizacaoU", "Dados atualizados com sucesso!");

        return "redirect:/perfilUsuario";
    }

    // Deleta usuário
    @GetMapping("/deletarUsuario/{id}")
    public String deletarUsuario(@PathVariable("id") Long id) {
        pr.deleteById(id);
        return "redirect:/usuarios";
    }

    

    /* Fim CRUD Usuario */
}
