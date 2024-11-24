const UserRepository = require("../repository/userRepository");

class CrudUsuario {
  createUser = async (userData) => {
    return await UserRepository.create(userData);
  };

  getAllUsers = async () => {
    return await UserRepository.findAll();
  };

  getUserById = async (cd_pessoa) => {
    const user = await UserRepository.findById(cd_pessoa);
    if (!user) {
      throw new Error("Usuário não encontrado");
    }
    return user;
  };

  loginUser = async (email_pessoa, senha_pessoa) => {
    const user = UserRepository.findByEmailAndPassword(
      email_pessoa,
      senha_pessoa
    );

    if (!user) {
      throw new Error("Email ou senha incorreto!");
    }
    return user;
  };

  updateUser = async (cd_pessoa, userData) => {
    const result = await UserRepository.update(cd_pessoa, userData);
    if (result.affectedRows === 0) {
      throw new Error("Usuário não encontrado para atualização");
    }
    return result;
  };

  deteleUser = async (cd_pessoa) => {
    const result = await UserRepository.delete(cd_pessoa);

    if (result.affectedRows === 0) {
      throw new Error("Usuário não encontrado para deleção");
    }

    return result;
  };
}

module.exports = new CrudUsuario();
