package com.example.solarispower.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.solarispower.models.Empresa;
import com.example.solarispower.repository.EmpresaRepository;


import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class EmpresaController {

    @Autowired
    private EmpresaRepository er;


    /* Rota cadastroEmpresa */
    @RequestMapping(value = "/cadastrarEmpresa", method = RequestMethod.GET)
    public String cadastroEmpresa(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "CadastroEmpresa/CadastroEmpresa";
    }

    @RequestMapping(value = "/cadastroEmpresa", method = RequestMethod.POST)
    public String formEmpresa(Empresa empresa, RedirectAttributes redirectAttributes) {
        System.out.println("Empresa recebida: " + empresa.getNmEmpresa());
        er.save(empresa);
        redirectAttributes.addFlashAttribute("mensagemCadastroEmpresa", "Cadastro realizado com sucesso! Faça login para continuar.");
        return "redirect:/loginEmpresa";
    }

    /* Rota loginEmpresa */
    @GetMapping("/loginEmpresa")
    public String loginEmpresa() {
        return "LoginEmpresa/loginEmpresa";
    }

    /* Rota loginEmpresa */
    @PostMapping("/loginEmpresa")
    public String loginEmpresa(
            @RequestParam("emailEmpresa") String email,
            @RequestParam("senhaEmpresa") String senha,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        Empresa empresa = er.findByEmailEmpresaAndSenhaEmpresa(email, senha);

        if (empresa != null) {
            // Salvando a empresa logada na sessão
            session.setAttribute("empresaLogado", empresa);
            return "redirect:/perfilEmpresa";
        } else {
            redirectAttributes.addFlashAttribute("erro", "Email ou senha inválidos!");
            return "redirect:/loginEmpresa";
        }
    }

    // Fim Rotas de Login e de Cadastro -----------------------------------

    // Começo Rotas de Perfil de Usuário e da Empresa
    // --------------------------------

    

    /* Rota PerfilEmpresa */
    @GetMapping("/perfilEmpresa")
    public String perfilEmpresa(HttpSession session, Model model) {
        Empresa empresaLogada = (Empresa) session.getAttribute("empresaLogado");

        if (empresaLogada == null) {
            // Redireciona para loginEmpresa se não estiver logado
            return "redirect:/loginEmpresa";
        }

        System.out.println("Empresa logada: " + empresaLogada.getNmEmpresa());

        model.addAttribute("empresa", empresaLogada);
        return "Perfil/PerfilEmpresa";
    }

    // Fim Rotas de Perfil de Usuário e da Empresa --------------------------------



    /* Começo CRUD Empresa */

    // Lista todas as empresas
    @GetMapping("/empresas")
    public String listaEmpresas(Model model) {
        Iterable<Empresa> empresas = er.findAll();
        model.addAttribute("empresas", empresas);
        return "Empresa/listaEmpresas";
    }

    // Formulário de edição
    @GetMapping("/editarEmpresa/{id}")
    public String editarEmpresa(@PathVariable("id") Long id, Model model) {
        Optional<Empresa> empresa = er.findById(id);
        if (empresa.isPresent()) {
            model.addAttribute("empresa", empresa.get());
            return "Cadastro/editarEmpresa";
        } else {
            return "redirect:/empresas";
        }
    }

    // Atualiza os dados da empresa
    @PostMapping("/atualizarEmpresa")
    public String atualizarEmpresa(Empresa empresa, HttpSession session, RedirectAttributes redirectAttributes) {

        Empresa empresaLogada = (Empresa) session.getAttribute("empresaLogado");

        if (empresaLogada == null) {
            return "redirect:/loginEmpresa"; // Empresa não está logado
        }

        // Garante que o ID da pessoa que será salva é o mesmo da sessão
        empresa.setCdEmpresa(empresaLogada.getCdEmpresa());

        // Atualiza a empresa no banco de dados
        er.save(empresa);

        // Atualiza os dados da sessão também
        session.setAttribute("empresaLogado", empresa);

        redirectAttributes.addFlashAttribute("mensagemAtualizacaoE", "Dados atualizados com sucesso!");

        return "redirect:/perfilEmpresa";
    }

    // Deleta empresa
    @GetMapping("/deletarEmpresa/{id}")
    public String deletarEmpresa(@PathVariable("id") Long id) {
        er.deleteById(id);
        return "redirect:/empresas";
    }

    /* Fim CRUD Empresa */

}
