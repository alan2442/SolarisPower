const CrudEmpresa = require("../services/CrudEmpresa");

class EnterpriseController {
  createEnterprise = async (req, res) => {
    try {
      const result = await CrudEmpresa.createEnterprise(req.body);
      res
        .status(201)
        .json({ message: "Empresa cadastrada com sucesso", data: result });
    } catch (error) {
      res.status(500).json({ error: error.message });
    }
  };

  getAllEnterprise = async (req, res) => {
    try {
      const enterprises = await CrudEmpresa.getAllEnterprise();
      res.status(200).json(enterprises);
    } catch (error) {
      res.status(500).json({ error: error.message });
    }
  };

  getEnterpriseById = async (req, res) => {
    try {
      const enterprises = await CrudEmpresa.getEnterpriseById(req.params.cd_empresa);
      res.status(200).json(enterprises);
    } catch (error) {
      res.status(404).json({ error: error.message });
    }
  };

  loginEnterprise = async (req, res) => {
    const { email_empresa, senha_empresa } = req.body;
    try {
      const enterprise = await CrudEmpresa.loginEnterprise(email_empresa, senha_empresa);
      res.status(200).json({ enterpriseId: enterprise.cd_empresa });
    } catch (error) {
      res.status(201).json({ error: error.message });
    }
  };

  updateEnterprise = async (req, res) => {
    try {
      const result = await CrudEmpresa.updateEnterprise(
        req.params.cd_empresa,
        req.body
      );
      res
        .status(200)
        .json({ message: "Empresa atualizada com sucesso", data: result });
    } catch (error) {
      res.status(404).json({ error: error.message });
    }
  };

  deleteEnterprise = async (req, res) => {
    try {
      const result = await CrudEmpresa.deleteEnterprise(req.params.cd_empresa);
      res
        .status(200)
        .json({ message: "Empresa deletada com sucesso", data: result });
    } catch (error) {
      res.status(404).json({ error: error.message });
    }
  };
}

module.exports = new EnterpriseController();
