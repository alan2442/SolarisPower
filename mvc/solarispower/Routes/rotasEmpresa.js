const EnterpriseController = require("../controllers/ControllersEmpresa");

const empresaRotas = async (server) => {
  /*Começo Rotas de Usuario */

  server.post("/empresa", EnterpriseController.createEnterprise);

  server.get("/empresas", EnterpriseController.getAllEnterprise);

  // Rota para pegar um usuário por ID
  server.get("/empresas/:cd_empresa", EnterpriseController.getEnterpriseById);

  // Rota para fazer login
  server.post("/loginEmpresa", EnterpriseController.loginEnterprise);

  // Rota para atualizar um usuário
  server.put("/empresas/:cd_empresa", EnterpriseController.updateEnterprise);

  // Rota para deletar um usuário
  server.delete("/empresas/:cd_empresa", EnterpriseController.deleteEnterprise);

  /*Fim Rotas de Usuario */
};

module.exports = empresaRotas;
