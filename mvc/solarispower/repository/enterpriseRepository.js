const { connection } = require("../db/properties");

class EnterpriseRepository {
  create = async (enterpriseData) => {
    const { nm_empresa, cnpj_empresa, email_empresa, segmento_empresa, senha_empresa } =
      enterpriseData;

    const sql = `
      INSERT INTO Empresa 
      (nm_empresa, cnpj_empresa, email_empresa, segmento_empresa, senha_empresa) 
      VALUES (?, ?, ?, ?, ?)`;

    const [result] = await connection.query(sql, [
      nm_empresa,
      cnpj_empresa,
      email_empresa,
      segmento_empresa,
      senha_empresa,
    ]);

    return result;
  };

  findAll = async () => {
    const sql = "SELECT * FROM Empresa";
    const [results] = await connection.query(sql);
    return results;
  };

  findById = async (cd_empresa) => {
    const sql = "SELECT * FROM Empresa WHERE cd_empresa = ?";
    const [results] = await connection.query(sql, [cd_empresa]);
    return results[0];
  };

  findByEmailAndPassword = async (email_empresa, senha_empresa) => {
    const sql = `
    SELECT * 
    FROM Empresa 
    WHERE email_empresa = ? AND senha_empresa = ?`;

    const [results] = await connection.query(sql, [email_empresa, senha_empresa]);
    return results[0];
  };

  update = async (cd_empresa, enterpriseData) => {
    const {
      nm_empresa,
      cnpj_empresa,
      email_empresa,
      segmento_empresa,
      senha_empresa,
      cep_empresa,
      rua_empresa,
      numeroRua_empresa,
      complemento_empresa,
    } = enterpriseData;

    const sql = `
      UPDATE Empresa 
      SET 
        nm_empresa = ?, cnpj_empresa = ?, email_empresa = ?, 
        segmento_empresa = ?, senha_empresa = ?, cep_empresa = ?, 
        rua_empresa = ?, numeroRua_empresa = ?, complemento_empresa = ?
      WHERE cd_empresa = ?`;

    const [result] = await connection.query(sql, [
      nm_empresa,
      cnpj_empresa,
      email_empresa,
      segmento_empresa,
      senha_empresa,
      cep_empresa || null,
      rua_empresa || null,
      numeroRua_empresa || null,
      complemento_empresa || null,
      cd_empresa,
    ]);

    return result;
  };

  delete = async (cd_empresa) => {
    const sql = "DELETE FROM Empresa WHERE cd_empresa = ?";
    const [result] = await connection.query(sql, [cd_empresa]);
    return result;
  };
}

module.exports = new EnterpriseRepository();
