const { connection } = require("../db/properties");

class UserRepository {
  create = async (userData) => {
    const { nm_pessoa, dt_nascimento, email_pessoa, cpf_pessoa, senha_pessoa } =
      userData;

    const sql = `
      INSERT INTO Pessoa 
      (nm_pessoa, dt_nascimento, email_pessoa, cpf_pessoa, senha_pessoa) 
      VALUES (?, ?, ?, ?, ?)`;

    const [result] = await connection.query(sql, [
      nm_pessoa,
      dt_nascimento,
      email_pessoa,
      cpf_pessoa,
      senha_pessoa,
    ]);

    return result;
  };

  findAll = async () => {
    const sql = "SELECT * FROM Pessoa";
    const [results] = await connection.query(sql);
    return results;
  };

  findById = async (cd_pessoa) => {
    const sql = "SELECT * FROM Pessoa WHERE cd_pessoa = ?";
    const [results] = await connection.query(sql, [cd_pessoa]);
    return results[0];
  };

  findByEmailAndPassword = async (email_pessoa, senha_pessoa) => {
    const sql = `
    SELECT * 
    FROM Pessoa 
    WHERE email_pessoa = ? AND senha_pessoa = ?`;

    const [results] = await connection.query(sql, [email_pessoa, senha_pessoa]);
    return results[0];
  };

  update = async (cd_pessoa, userData) => {
    const {
      nm_pessoa,
      dt_nascimento,
      email_pessoa,
      cpf_pessoa,
      senha_pessoa,
      cep_pessoa,
      rua_pessoa,
      numeroRua_pessoa,
      complemento_pessoa,
    } = userData;

    const sql = `
      UPDATE Pessoa 
      SET 
        nm_pessoa = ?, dt_nascimento = ?, email_pessoa = ?, 
        cpf_pessoa = ?, senha_pessoa = ?, cep_pessoa = ?, 
        rua_pessoa = ?, numeroRua_pessoa = ?, complemento_pessoa = ?
      WHERE cd_pessoa = ?`;

    const [result] = await connection.query(sql, [
      nm_pessoa,
      dt_nascimento,
      email_pessoa,
      cpf_pessoa,
      senha_pessoa,
      cep_pessoa || null,
      rua_pessoa || null,
      numeroRua_pessoa || null,
      complemento_pessoa || null,
      cd_pessoa,
    ]);

    return result;
  };

  delete = async (cd_pessoa) => {
    const sql = "DELETE FROM Pessoa WHERE cd_pessoa = ?";
    const [result] = await connection.query(sql, [cd_pessoa]);
    return result;
  };
}

module.exports = new UserRepository();
