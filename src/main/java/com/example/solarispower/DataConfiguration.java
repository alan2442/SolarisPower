package com.example.solarispower;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * Classe de configuração do Spring responsável por configurar o acesso ao banco de dados
 * e a integração com JPA/Hibernate.
 * 
 * A anotação @Configuration indica que esta classe contém definições de beans que serão
 * gerenciados pelo Spring.
 */
@Configuration
public class DataConfiguration {
    
    /**
     * Configura o DataSource, que é a fonte de dados utilizada pela aplicação para
     * conectar ao banco de dados MySQL.
     * 
     * @return DataSource configurado com driver, URL, usuário e senha
     */
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver"); // Driver JDBC do MySQL
        dataSource.setUrl("jdbc:mysql://localhost:3306/solarispower"); // URL de conexão
        dataSource.setUsername("root"); // Usuário do banco
        dataSource.setPassword("root"); // Senha do banco
        return dataSource;
    }
    
    /**
     * Configura o JpaVendorAdapter para integração do Spring com o Hibernate.
     * 
     * O JpaVendorAdapter encapsula propriedades específicas do provedor JPA (Hibernate)
     * como dialeto do banco, exibição de SQL e geração de DDL.
     * 
     * @return JpaVendorAdapter configurado para MySQL e Hibernate
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL); // Define o tipo de banco
        adapter.setShowSql(true); // Habilita a exibição de SQL no console
        adapter.setGenerateDdl(true); // Permite a geração automática do schema
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect"); // Dialeto do MySQL
        adapter.setPrepareConnection(true); // Prepara a conexão antes de cada uso
        return adapter;
    }
}
