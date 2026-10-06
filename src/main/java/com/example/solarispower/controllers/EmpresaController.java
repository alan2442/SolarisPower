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

import com.example.solarispower.dto.EmpresaRequestDTO;
import com.example.solarispower.dto.EmpresaResponseDTO;
import com.example.solarispower.dto.LoginEmpresaRequestDTO;
import com.example.solarispower.dto.MessageResponseDTO;
import com.example.solarispower.services.EmpresaService;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    // POST: /api/empresas/cadastro
    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody EmpresaRequestDTO dto) {
        try {
            EmpresaResponseDTO response = empresaService.cadastrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage()));
        }
    }

    // GET: /api/empresas
    @GetMapping
    public ResponseEntity<List<EmpresaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(empresaService.listarTodas());
    }

    // GET: /api/empresas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(empresaService.buscarPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO(e.getMessage()));
        }
    }

    // PUT: /api/empresas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody EmpresaRequestDTO dto) {
        try {
            EmpresaResponseDTO response = empresaService.atualizar(id, dto);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage()));
        }
    }

    // DELETE: /api/empresas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            empresaService.deletar(id);
            return ResponseEntity.ok(new MessageResponseDTO("Empresa deletada com sucesso."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginEmpresaRequestDTO dto) {
        try {
            EmpresaResponseDTO empresaLogada = empresaService.autenticar(dto);
            return ResponseEntity.ok(empresaLogada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponseDTO(e.getMessage()));
        }
    }
}