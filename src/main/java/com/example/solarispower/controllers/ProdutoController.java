package com.example.solarispower.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.solarispower.dto.MessageResponseDTO;
import com.example.solarispower.dto.ProdutoRequestDTO;
import com.example.solarispower.dto.ProdutoResponseDTO;
import com.example.solarispower.services.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // POST: /api/produtos (Suporta Envio de Arquivo com Multipart Data)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> cadastrar(
            @RequestPart("dados") ProdutoRequestDTO dto,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem) {
        try {
            ProdutoResponseDTO response = produtoService.cadastrar(dto, imagem);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage()));
        }
    }

    // GET: /api/produtos/empresa/{cdEmpresa}
    @GetMapping("/empresa/{cdEmpresa}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarPorEmpresa(@PathVariable Long cdEmpresa) {
        return ResponseEntity.ok(produtoService.listarPorEmpresa(cdEmpresa));
    }

    // GET: /api/produtos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(produtoService.buscarPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO(e.getMessage()));
        }
    }

    // PUT: /api/produtos/{id}
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @RequestPart("dados") ProdutoRequestDTO dto,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem) {
        try {
            ProdutoResponseDTO response = produtoService.atualizar(id, dto, imagem);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage()));
        }
    }

    // DELETE: /api/produtos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            produtoService.deletar(id);
            return ResponseEntity.ok(new MessageResponseDTO("Produto removido com sucesso."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO(e.getMessage()));
        }
    }


    // GET: /api/produtos (Lista TODOS os produtos cadastrados)
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

}