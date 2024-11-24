const UserController = require("../controllers/ConstrollersUsuario");

const usuarioRotas = async (server) => {
  /*Começo Rotas de Usuario */

  server.post("/usuario", UserController.createUser);

  server.get("/usuarios", UserController.getAllUsers);

  // Rota para pegar um usuário por ID
  server.get("/usuarios/:cd_pessoa", UserController.getUserById);

  // Rota para fazer login
  server.post("/login", UserController.loginUser);

  // Rota para atualizar um usuário
  server.put("/usuarios/:cd_pessoa", UserController.updateUser);

  // Rota para deletar um usuário
  server.delete("/usuarios/:cd_pessoa", UserController.deleteUser);

  /*Fim Rotas de Usuario */
};

module.exports = usuarioRotas;
