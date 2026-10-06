package com.example.solarispower.controllers;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pagamento")
@CrossOrigin(origins = "*")
public class PagamentoController {

    @Value("${mercadopago.access_token}")
    private String accessToken;

    @PostMapping("/criar-preferencia")
    public ResponseEntity<?> criarPreferencia(@RequestBody PaymentDTO dto) {
        try {
            if (accessToken == null || accessToken.trim().isEmpty()) {
                System.err.println("ERRO: Access Token do Mercado Pago não foi configurado no application.properties!");
                return ResponseEntity.status(500).body("Access token do Mercado Pago não configurado.");
            }

            MercadoPagoConfig.setAccessToken(accessToken.trim());

            List<PreferenceItemRequest> items = new ArrayList<>();

            if (dto.getItems() == null || dto.getItems().isEmpty()) {
                return ResponseEntity.badRequest().body("O carrinho não pode estar vazio.");
            }

            for (CartItemDTO item : dto.getItems()) {
                // Tratamento de segurança para ID, Nome e Preço
                String itemId = item.getId() != null ? String.valueOf(item.getId()) : "ITEM-1";
                String itemNome = (item.getNome() != null && !item.getNome().isBlank()) ? item.getNome()
                        : "Produto Solar";
                Integer itemQtd = (item.getQuantidade() != null && item.getQuantidade() > 0) ? item.getQuantidade() : 1;
                Double itemPreco = (item.getPreco() != null) ? item.getPreco() : 0.0;

                PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                        .id(itemId)
                        .title(itemNome)
                        .quantity(itemQtd)
                        .unitPrice(BigDecimal.valueOf(itemPreco))
                        .currencyId("BRL")
                        .build();

                items.add(itemRequest);
            }

            // Adiciona frete se houver valor válido
            if (dto.getFrete() != null && dto.getFrete() > 0) {
                items.add(PreferenceItemRequest.builder()
                        .id("FRETE")
                        .title("Frete e Envio")
                        .quantity(1)
                        .unitPrice(BigDecimal.valueOf(dto.getFrete()))
                        .currencyId("BRL")
                        .build());
            }

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("http://localhost:5173/sucesso") // Obrigatório se usar auto_return
                    .failure("http://localhost:5173/erro")
                    .pending("http://localhost:5173/pendente")
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrls)
                    //.autoReturn("approved") // <- Este carinha exige que backUrls.success NÃO seja nulo!
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            Map<String, String> response = new HashMap<>();
            response.put("initPoint", preference.getInitPoint());

            return ResponseEntity.ok(response);

        } catch (MPApiException e) {
            // Esse log vai mostrar no terminal do Spring o erro EXATO vindo do Mercado Pago
            System.err.println("=== ERRO MERCADO PAGO API ===");
            System.err.println("Status Code: " + e.getApiResponse().getStatusCode());
            System.err.println("Corpo do Erro: " + e.getApiResponse().getContent());
            return ResponseEntity.status(500).body("Erro Mercado Pago: " + e.getApiResponse().getContent());

        } catch (MPException e) {
            System.err.println("=== ERRO SDK MERCADO PAGO ===");
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro SDK: " + e.getMessage());

        } catch (Exception e) {
            System.err.println("=== ERRO INTERNO NO SPRING BOOT ===");
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro interno: " + e.getMessage());
        }
    }
}

// DTOs auxiliares
class PaymentDTO {
    private List<CartItemDTO> items;
    private Double frete;
    private Double desconto;

    // Getters e Setters
    public List<CartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemDTO> items) {
        this.items = items;
    }

    public Double getFrete() {
        return frete;
    }

    public void setFrete(Double frete) {
        this.frete = frete;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }
}

class CartItemDTO {
    private Long id;
    private String nome;
    private Integer quantidade;
    private Double preco;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}