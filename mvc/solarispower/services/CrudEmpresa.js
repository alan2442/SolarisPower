const EnterpriseRepository = require("../repository/enterpriseRepository");

class CrudEmpresa {
  createEnterprise = async (enterpriseData) => {
    return await EnterpriseRepository.create(enterpriseData);
  };

  getAllEnterprise = async () => {
    return await EnterpriseRepository.findAll();
  };

  getEnterpriseById = async (cd_empresa) => {
    const enterprise = await EnterpriseRepository.findById(cd_empresa);
    if (!enterprise) {
      throw new Error("Empresa não encontrada");
    }
    return enterprise;
  };

  loginEnterprise = async (email_empresa, senha_empresa) => {
    const enterprise = EnterpriseRepository.findByEmailAndPassword(
      email_empresa,
      senha_empresa
    );

    if (!enterprise) {
      throw new Error("Email ou senha incorreto!");
    }
    return enterprise;
  };

  updateEnterprise = async (cd_empresa, enterpriseData) => {
    const result = await EnterpriseRepository.update(cd_empresa, enterpriseData);
    if (result.affectedRows === 0) {
      throw new Error("Empresa não encontrada para atualização");
    }
    return result;
  };

  deteleEnterprise = async (cd_empresa) => {
    const result = await EnterpriseRepository.delete(cd_empresa);

    if (result.affectedRows === 0) {
      throw new Error("Empresa não encontrada para deleção");
    }

    return result;
  };
}

module.exports = new CrudEmpresa();
