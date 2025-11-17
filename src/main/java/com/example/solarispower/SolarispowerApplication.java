package com.example.solarispower;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot.
 * 
 * A anotação @SpringBootApplication indica que esta é a classe de configuração principal,
 * habilitando automaticamente:
 *  - @Configuration: permite que a classe defina beans do Spring.
 *  - @EnableAutoConfiguration: ativa a configuração automática do Spring Boot.
 *  - @ComponentScan: habilita a busca de componentes, serviços e repositórios no pacote.
 */
@SpringBootApplication
public class SolarispowerApplication {

    /**
     * Método principal (main) que inicia a aplicação Spring Boot.
     * 
     * @param args argumentos de linha de comando (opcional)
     */
    public static void main(String[] args) {
        SpringApplication.run(SolarispowerApplication.class, args); // Inicializa o contexto do Spring
    }

}
