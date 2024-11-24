create database SolarisPower;

use SolarisPower;



/*Tabela Pessoa */
create table Pessoa (
	cd_pessoa int not null auto_increment,
    nm_pessoa varchar(50) not null,
    dt_nascimento varchar(10) not null,
    email_pessoa varchar(50) not null unique,
    cpf_pessoa varchar(14) not null unique,
    senha_pessoa varchar(20) not null,
    cep_pessoa varchar(10) null,
    rua_pessoa varchar(50) null,
    numeroRua_pessoa int null,
    complemento_pessoa varchar(30) null,
    CONSTRAINT pk_pessoa PRIMARY KEY (cd_pessoa)
);


/*Tabela Empresa*/ 
create table Empresa (
	cd_empresa int not null auto_increment,
    nm_empresa varchar(30) not null,
    cnpj_empresa varchar(18) not null unique,
    email_empresa varchar(50) not null unique,
    segmento_empresa varchar(30) not null,
    senha_empresa varchar(20) not null,
    cep_empresa varchar(10)  null,
    rua_empresa varchar(50) null,
    numeroRua_empresa int null,
    complemento_empresa varchar(30) null,
    CONSTRAINT pk_empresa PRIMARY KEY (cd_empresa)
);


/*Tabela Produto */ 
create table Produto(
	cd_produto int not null auto_increment,
    nm_produto varchar(50) not null,
    descricao_produto varchar(400) not null,
    qtd_produto int not null,
    preco_produto decimal(10, 2) not null,
    cd_empresa int not null,
    CONSTRAINT fk_empresa FOREIGN KEY (cd_empresa)
    REFERENCES Empresa(cd_empresa),
    CONSTRAINT pk_produto PRIMARY KEY (cd_produto)
);

create table PedidoProduto(
	cd_pedido int not null auto_increment,
    cd_pessoa int not null,
    cd_empresa int not null,
    CONSTRAINT pk_pedidoProduto PRIMARY KEY(cd_pedido),
    FOREIGN KEY (cd_pessoa)
    REFERENCES Pessoa(cd_pessoa),
    FOREIGN KEY (cd_empresa)
    REFERENCES Empresa(cd_empresa)
);

insert into Pessoa(nm_pessoa, dt_nascimento, email_pessoa, cpf_pessoa, senha_pessoa)
 Values(
"ALan Nogueira", "23112000", "alanzinho@hotmail.com", "12345678910",
"senha"
);

insert into Empresa(nm_empresa, cnpj_empresa, email_empresa, segmento_empresa, senha_empresa) 
Values(
"Scania", "12344", "scania@hotmail.com", "Veiculos",
"senha"
);

insert into Produto (nm_produto, descricao_produto, qtd_produto, preco_produto, cd_empresa)
 Values("Caminhão Scania", "caminhão 2024", 12, 120.00, 1
);

insert into PedidoProduto(cd_pessoa, cd_empresa) Values(
	1, 1
);

drop table PedidoProduto;
drop table Produto;
drop table Empresa;
drop table Pessoa;

show tables;
select*from Empresa;
select*from Pessoa;
select*from PedidoProduto;
select*from Produto;

describe Pessoa;