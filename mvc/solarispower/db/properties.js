const mysql = require("mysql2/promise"); // Usando a versão "promise" para async/await

// Criando uma pool de conexões com o banco de dados
const connection = mysql.createPool({
  host: "localhost",
  user: "root",
  password: "root",
  database: "SolarisPower",
  waitForConnections: true,
  connectionLimit: 10, // Limite de conexões simultâneas
  queueLimit: 0, // Sem limite para a fila de espera
});

// Testando a conexão com o banco de dados
const connectionDataBase = async () => {
  try {
    await connection.query("SELECT 1"); // Consulta simples para verificar conexão
    console.log("Conexão com banco de dados efetuada com sucesso");
  } catch (err) {
    console.error("Erro ao conectar ao banco de dados:", err);
    throw err;
  }
};

// Exportando a conexão para ser usada no código
module.exports = { connectionDataBase, connection };
