package com.example.solarispower.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.solarispower.dto.LoginRequestDTO;
import com.example.solarispower.dto.MessageResponseDTO;
import com.example.solarispower.dto.PessoaRequestDTO;
import com.example.solarispower.dto.PessoaResponseDTO;
import com.example.solarispower.services.PessoaService;

@RestController
@RequestMapping("/api/pessoas")
@CrossOrigin(origins = "*") // Permite requisições do front-end React
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    // POST: /api/pessoas/cadastro
    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody PessoaRequestDTO dto) {
        try {
            PessoaResponseDTO response = pessoaService.cadastrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage()));
        }
    }

    // POST: /api/pessoas/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto) {
        try {
            PessoaResponseDTO usuarioLogado = pessoaService.autenticar(dto);
            return ResponseEntity.ok(usuarioLogado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponseDTO(e.getMessage()));
        }
    }

    // GET: /api/pessoas
    @GetMapping
    public ResponseEntity<List<PessoaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(pessoaService.listarTodos());
    }

    // GET: /api/pessoas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pessoaService.buscarPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO(e.getMessage()));
        }
    }

    // PUT: /api/pessoas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody PessoaRequestDTO dto) {
        try {
            PessoaResponseDTO response = pessoaService.atualizar(id, dto);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage()));
        }
    }

    // DELETE: /api/pessoas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            pessoaService.deletar(id);
            return ResponseEntity.ok(new MessageResponseDTO("Usuário deletado com sucesso."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO(e.getMessage()));
        }
    }
}