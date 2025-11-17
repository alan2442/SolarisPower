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

    // Injeção automática do repositório PessoaRepository,
    // responsável pelas operações de persistência (CRUD) no banco de dados.
    @Autowired
    private PessoaRepository pr;

    // ==========================================================
    // =========== ROTAS DE LOGIN E CADASTRO DE USUÁRIO =========
    // ==========================================================

    /* Exibe a página de cadastro de usuário */
    @RequestMapping(value = "/cadastrarUsuario", method = RequestMethod.GET)
    public String cadastroUsuario(Model model) {
        // Adiciona um novo objeto Pessoa ao modelo, usado para preencher o formulário
        model.addAttribute("pessoa", new Pessoa());
        return "Cadastro/CadastroUsuario"; // Retorna a view de cadastro do usuário
    }

    /* Recebe os dados do formulário e salva o novo usuário no banco */
    @RequestMapping(value = "/cadastroUsuario", method = RequestMethod.POST)
    public String form(
            Pessoa pessoa,
            @RequestParam("confSenha") String confSenha,
            RedirectAttributes redirectAttributes) {

        // VALIDAÇÃO: senha deve ser igual à confirmação
        if (!pessoa.getSenhaPessoa().equals(confSenha)) {
            redirectAttributes.addFlashAttribute("erroCadastro", "❌ As senhas não coincidem.");
            return "redirect:/cadastrarUsuario";
        }

        // SALVA se estiver tudo OK
        pr.save(pessoa);

        redirectAttributes.addFlashAttribute(
                "mensagemCadastroUsuario",
                "Cadastro realizado com sucesso! Faça login para continuar.");

        return "redirect:/loginUsuario";
    }

    /* Exibe a página de login de usuário */
    @GetMapping("/loginUsuario")
    public String loginUsuario(HttpSession session) {

        // Recupera, caso existam, usuários ou empresas logadas na sessão
        Pessoa pessoaLogada = (Pessoa) session.getAttribute("usuarioLogado");
        Empresa empresaLogada = (Empresa) session.getAttribute("empresaLogado");

        // Verifica se há um usuário ou empresa autenticado e redireciona para o perfil
        // correspondente
        if (pessoaLogada != null) {
            return "redirect:/perfilUsuario";
        } else if (empresaLogada != null) {
            return "redirect:/perfilEmpresa";
        } else {
            // Caso ninguém esteja logado, exibe a tela de login
            return "Login/login";
        }
    }

    /* Processa o login do usuário com base em e-mail e senha */
    @PostMapping("/loginUsuario")
    public String loginUsuario(
            @RequestParam("emailPessoa") String email,
            @RequestParam("senhaPessoa") String senha,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        // Busca o usuário no banco de dados pelo e-mail e senha informados
        Pessoa pessoa = pr.findByEmailPessoaAndSenhaPessoa(email, senha);

        if (pessoa != null) {
            // Caso o login seja válido, armazena o usuário na sessão
            session.setAttribute("usuarioLogado", pessoa);
            return "redirect:/perfilUsuario"; // Redireciona para o perfil do usuário
        } else {
            // Caso contrário, define uma mensagem de erro profissional
            redirectAttributes.addFlashAttribute(
                    "erro",
                    "Não foi possível acessar sua conta. Verifique seu e-mail e senha e tente novamente.");

            // Retorna para a tela de login
            return "redirect:/loginUsuario";
        }
    }

    // ==========================================================
    // ================== PERFIL DO USUÁRIO =====================
    // ==========================================================

    /* Exibe o perfil do usuário autenticado */
    @GetMapping("/perfilUsuario")
    public String perfilUsuario(HttpSession session, Model model) {
        // Recupera o usuário logado da sessão
        Pessoa pessoaLogada = (Pessoa) session.getAttribute("usuarioLogado");

        // Caso não haja sessão ativa, redireciona para o login
        if (pessoaLogada == null) {
            return "redirect:/loginUsuario";
        }

        // Exibe no console o nome do usuário logado (para fins de debug)
        System.out.println("Usuário logado: " + pessoaLogada.getNmPessoa());

        // Envia o objeto da pessoa para ser exibido na view
        model.addAttribute("pessoa", pessoaLogada);
        return "Perfil/PerfilUsuario";
    }

    // ==========================================================
    // =================== CRUD DE USUÁRIO ======================
    // ==========================================================

    /* Exibe uma lista de todos os usuários cadastrados */
    @GetMapping("/usuarios")
    public String listaUsuarios(Model model) {
        // Recupera todos os registros da tabela Pessoa
        Iterable<Pessoa> pessoas = pr.findAll();
        model.addAttribute("pessoas", pessoas);
        return "Usuario/listaUsuarios"; // Retorna a view com a listagem
    }

    /* Exibe o formulário de edição de um usuário específico */
    @GetMapping("/editarUsuario/{id}")
    public String editarUsuario(@PathVariable("id") Long id, Model model) {
        // Busca o usuário pelo ID informado
        Optional<Pessoa> pessoa = pr.findById(id);

        // Se encontrado, adiciona ao modelo e retorna a view de edição
        if (pessoa.isPresent()) {
            model.addAttribute("pessoa", pessoa.get());
            return "Cadastro/editarUsuario";
        } else {
            // Caso o ID não exista, redireciona para a lista de usuários
            return "redirect:/usuarios";
        }
    }

    /* Atualiza os dados do usuário logado */
    @PostMapping("/atualizarUsuario")
    public String atualizarUsuario(Pessoa pessoa, HttpSession session, RedirectAttributes redirectAttributes) {

        // Recupera o usuário logado da sessão
        Pessoa pessoaLogada = (Pessoa) session.getAttribute("usuarioLogado");

        // Se não estiver logado, redireciona para a tela de login
        if (pessoaLogada == null) {
            return "redirect:/loginUsuario";
        }

        // Garante que o ID do usuário a ser atualizado é o mesmo do logado
        pessoa.setCdPessoa(pessoaLogada.getCdPessoa());

        // Atualiza os dados no banco de dados
        pr.save(pessoa);

        // Atualiza também a sessão com os novos dados
        session.setAttribute("usuarioLogado", pessoa);

        // Define uma mensagem flash de sucesso
        redirectAttributes.addFlashAttribute("mensagemAtualizacaoU", "Dados atualizados com sucesso!");

        // Redireciona para o perfil atualizado
        return "redirect:/perfilUsuario";
    }

    /* Exclui um usuário com base no ID informado */
    @GetMapping("/deletarUsuario/{id}")
    public String deletarUsuario(@PathVariable("id") Long id) {
        // Remove o registro do banco de dados
        pr.deleteById(id);
        // Redireciona para a lista de usuários atualizada
        return "redirect:/usuarios";
    }

    // ==========================================================
    // =================== FIM DO CRUD USUÁRIO ==================
    // ==========================================================

}
