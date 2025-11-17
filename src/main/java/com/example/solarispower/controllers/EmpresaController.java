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

    // Injeta automaticamente o repositório de Empresa,
    // permitindo o acesso às operações de banco de dados (CRUD)
    @Autowired
    private EmpresaRepository er;

    // =====================================================
    // =============== ROTAS DE CADASTRO ====================
    // =====================================================

    /* Exibe a página de cadastro de empresas */
    @RequestMapping(value = "/cadastrarEmpresa", method = RequestMethod.GET)
    public String cadastroEmpresa(Model model) {
        // Cria um novo objeto Empresa e adiciona ao modelo para ser usado no formulário
        model.addAttribute("empresa", new Empresa());
        return "CadastroEmpresa/CadastroEmpresa"; // Retorna a view de cadastro
    }

    /* Recebe os dados do formulário e realiza o cadastro da empresa */
    @RequestMapping(value = "/cadastroEmpresa", method = RequestMethod.POST)
    public String formEmpresa(
            Empresa empresa,
            @RequestParam("confSenhaEmpresa") String confSenha,
            RedirectAttributes redirectAttributes) {

        // Verifica se as senhas são iguais
        if (!empresa.getSenhaEmpresa().equals(confSenha)) {
            redirectAttributes.addFlashAttribute("erroCadastro", "As senhas não coincidem!");
            return "redirect:/cadastrarEmpresa";
        }

        // Salva a empresa no banco
        er.save(empresa);

        redirectAttributes.addFlashAttribute(
                "mensagemCadastroEmpresa",
                "Cadastro realizado com sucesso! Faça login para continuar.");

        return "redirect:/loginEmpresa";
    }

    // =====================================================
    // ================ ROTAS DE LOGIN =====================
    // =====================================================

    /* Exibe a página de login da empresa */
    @GetMapping("/loginEmpresa")
    public String loginEmpresa() {
        return "LoginEmpresa/loginEmpresa";
    }

    /* Processa o login da empresa */
    @PostMapping("/loginEmpresa")
    public String loginEmpresa(
            @RequestParam("emailEmpresa") String email,
            @RequestParam("senhaEmpresa") String senha,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        // Busca a empresa no banco com base no e-mail e senha informados
        Empresa empresa = er.findByEmailEmpresaAndSenhaEmpresa(email, senha);

        if (empresa != null) {
            // Se a empresa for encontrada, salva o objeto na sessão
            session.setAttribute("empresaLogado", empresa);

            // Redireciona para o perfil da empresa
            return "redirect:/perfilEmpresa";
        } else {
            // Caso as credenciais estejam incorretas, exibe mensagem de erro profissional
            redirectAttributes.addFlashAttribute(
                    "erro",
                    "Não foi possível acessar sua conta empresarial. Verifique o e-mail e a senha e tente novamente.");

            // Retorna para a tela de login
            return "redirect:/loginEmpresa";
        }
    }

    // =====================================================
    // ========== ROTAS DE PERFIL DA EMPRESA ===============
    // =====================================================

    /* Exibe a página de perfil da empresa logada */
    @GetMapping("/perfilEmpresa")
    public String perfilEmpresa(HttpSession session, Model model) {
        // Recupera a empresa armazenada na sessão
        Empresa empresaLogada = (Empresa) session.getAttribute("empresaLogado");

        // Se não houver empresa logada, redireciona para a tela de login
        if (empresaLogada == null) {
            return "redirect:/loginEmpresa";
        }

        System.out.println("Empresa logada: " + empresaLogada.getNmEmpresa());

        // Envia os dados da empresa para a view
        model.addAttribute("empresa", empresaLogada);
        return "Perfil/PerfilEmpresa";
    }

    // =====================================================
    // ================ CRUD DE EMPRESAS ===================
    // =====================================================

    /* Lista todas as empresas cadastradas */
    @GetMapping("/empresas")
    public String listaEmpresas(Model model) {
        Iterable<Empresa> empresas = er.findAll();
        model.addAttribute("empresas", empresas);
        return "Empresa/listaEmpresas"; // Retorna a view com a lista
    }

    /* Exibe o formulário de edição de uma empresa específica */
    @GetMapping("/editarEmpresa/{id}")
    public String editarEmpresa(@PathVariable("id") Long id, Model model) {
        Optional<Empresa> empresa = er.findById(id);

        // Se a empresa existir, adiciona ao modelo e exibe o formulário de edição
        if (empresa.isPresent()) {
            model.addAttribute("empresa", empresa.get());
            return "Cadastro/editarEmpresa";
        } else {
            // Caso o ID não seja encontrado, redireciona para a lista de empresas
            return "redirect:/empresas";
        }
    }

    /* Atualiza os dados da empresa logada */
    @PostMapping("/atualizarEmpresa")
    public String atualizarEmpresa(Empresa empresa, HttpSession session, RedirectAttributes redirectAttributes) {

        // Recupera a empresa logada da sessão
        Empresa empresaLogada = (Empresa) session.getAttribute("empresaLogado");

        // Se não houver empresa logada, redireciona para login
        if (empresaLogada == null) {
            return "redirect:/loginEmpresa";
        }

        // Garante que o ID salvo será o mesmo da empresa logada
        empresa.setCdEmpresa(empresaLogada.getCdEmpresa());

        // Salva as alterações no banco de dados
        er.save(empresa);

        // Atualiza os dados da empresa também na sessão
        session.setAttribute("empresaLogado", empresa);

        // Adiciona mensagem flash confirmando a atualização
        redirectAttributes.addFlashAttribute("mensagemAtualizacaoE", "Dados atualizados com sucesso!");

        // Redireciona para o perfil da empresa
        return "redirect:/perfilEmpresa";
    }

    /* Exclui uma empresa pelo ID informado */
    @GetMapping("/deletarEmpresa/{id}")
    public String deletarEmpresa(@PathVariable("id") Long id) {
        // Remove a empresa do banco de dados
        er.deleteById(id);
        return "redirect:/empresas"; // Redireciona para a lista atualizada
    }

}
