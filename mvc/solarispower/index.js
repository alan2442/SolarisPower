const express = require("express");
const usuarioRotas = require("./Routes/rotasUsuario");
const empresaRotas = require("./Routes/rotasEmpresa");
const app = express();
const { connectionDataBase } = require("./db/properties.js");
const { fileURLToPath } = require("url");
const path = require("path");
const bodyParser = require("body-parser");
const cors = require("cors");

const start = async () => {
  app.use(express.json());

  app.get("/", (req, res) => {
    res.redirect("Index/index.html");
  });

  app.use(cors());

  const __dirname = path.dirname(__filename);
  app.use(bodyParser.urlencoded({ extended: true }));

  // Configurar o body-parser para dados JSON
  app.use(bodyParser.json());

  // Usado para poder localizar as Páginas
  app.use(express.static(path.join(__dirname, "../../pages/public")));

  // Usado para poder localizar as Imagens
  app.use("/IMG", express.static("../../pages/IMG"));

  await connectionDataBase();
  await usuarioRotas(app);
  await empresaRotas(app);

  app.listen({
    port: 3000,
  });
  console.log(
    `🚀 Servidor está rodando com sucesso! Acesse: http://localhost:3000`
  );
};

start();
