const CrudUsuario = require("../services/CrudUsuario");

class UserController {
  createUser = async (req, res) => {
    try {
      const result = await CrudUsuario.createUser(req.body);
      res
        .status(201)
        .json({ message: "Usuário criado com sucesso", data: result });
    } catch (error) {
      res.status(500).json({ error: error.message });
    }
  };

  getAllUsers = async (req, res) => {
    try {
      const users = await CrudUsuario.getAllUsers();
      res.status(200).json(users);
    } catch (error) {
      res.status(500).json({ error: error.message });
    }
  };

  getUserById = async (req, res) => {
    try {
      const user = await CrudUsuario.getUserById(req.params.cd_pessoa);
      res.status(200).json(user);
    } catch (error) {
      res.status(404).json({ error: error.message });
    }
  };

  loginUser = async (req, res) => {
    const { email_pessoa, senha_pessoa } = req.body;
    try {
      const user = await CrudUsuario.loginUser(email_pessoa, senha_pessoa);
      res.status(200).json({ userId: user.cd_pessoa });
    } catch (error) {
      res.status(201).json({ error: error.message });
    }
  };

  updateUser = async (req, res) => {
    try {
      const result = await CrudUsuario.updateUser(
        req.params.cd_pessoa,
        req.body
      );
      res
        .status(200)
        .json({ message: "Usuário atualizado com sucesso", data: result });
    } catch (error) {
      res.status(404).json({ error: error.message });
    }
  };

  deleteUser = async (req, res) => {
    try {
      const result = await CrudUsuario.deleteUser(req.params.cd_pessoa);
      res
        .status(200)
        .json({ message: "Usuário deletado com sucesso", data: result });
    } catch (error) {
      res.status(404).json({ error: error.message });
    }
  };
}

module.exports = new UserController();
