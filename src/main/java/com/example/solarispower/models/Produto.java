package com.example.solarispower.models;




import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity(name = "Produto")
@Table(name = "Produto")
@Getter
@Setter
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cd_produto;

    private String nm_produto;
    private String descricao_produto;
    private int qtd_produto;
    private String preco_produto; 

    
    @OneToOne
    private PedidoProduto cd_pedido;
     
    
    
    @OneToOne
    private Empresa cd_empresa; 
    
    
}
