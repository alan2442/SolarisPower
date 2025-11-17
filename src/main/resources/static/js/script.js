// ===============================
// DADOS INICIAIS DOS PRODUTOS
// ===============================

// Array de objetos representando os produtos disponíveis para venda
let dados = [
  { id: 1, nome: "Placa Solar de 1 metro sem bateria externa", preco: 499.90, img: "/Images/kit01.png" },
  { id: 2, nome: "Conjunto 4x paineis Solares e baterias", preco: 2499.99, img: "/Images/kit3png.jpg" },
  { id: 3, nome: "Conjunto 4x paineis solares com controladora", preco: 1999.90, img: "/Images/kit03.png" },
  { id: 4, nome: "2 painéis solares de 4 metros", preco: 3999.90, img: "/Images/kit5.png" },
  { id: 5, nome: "3 Panéis Solares com controladora", preco: 1499.90, img: "/Images/kit6.png" },
  { id: 6, nome: "Bateria grande para painél solar", preco: 299.90, img: "/Images/bateria.png" }
];

// ===============================
// FUNÇÕES DE INICIALIZAÇÃO
// ===============================

// Armazena os dados dos produtos no sessionStorage como JSON
function insereDados() {
  let bd = JSON.stringify(dados); // Converte o array de objetos em JSON
  sessionStorage.setItem("banco", bd); // Salva no sessionStorage
}

// Inicializa o carrinho vazio no sessionStorage caso não exista
function insereCarrinho() {
  let carrinho = [];
  let cart = JSON.stringify(carrinho);
  if (sessionStorage.getItem("cart") == null) {
    sessionStorage.setItem("cart", cart);
  }
}

// Executa a inicialização de dados e carrinho
insereDados();
insereCarrinho();

// ===============================
// FUNÇÕES DE COMPRA DE CADA PRODUTO
// ===============================

// Cada função compraX() captura a quantidade do input correspondente
// e adiciona o produto selecionado ao carrinho
function compra1() {
  let qtd1 = document.getElementById('qtd1').value;

  if (qtd1 == null || qtd1 == 0) {
    alert("Nenhum produto adicionado no carrinho, verifique a quantidade");
  } else {
    alert("Produto foi adicionado ao carrinho!");
  }

  let total = qtd1 * dados[0].preco;
  adicionaCarrinho(qtd1, 0); // Chama função para adicionar produto ao carrinho
}

function compra2() {
  let qtd2 = document.getElementById('qtd2').value;

  if (qtd2 == null || qtd2 == 0) {
    alert("Nenhum produto adicionado no carrinho, verifique a quantidade");
  } else {
    alert("Produto foi adicionado ao carrinho!");
  }

  let total = qtd2 * dados[1].preco;
  adicionaCarrinho(qtd2, 1);
}

function compra3() {
  let qtd3 = document.getElementById('qtd3').value;

  if (qtd3 == null || qtd3 == 0) {
    alert("Nenhum produto adicionado no carrinho, verifique a quantidade");
  } else {
    alert("Produto foi adicionado ao carrinho!");
  }

  let total = qtd3 * dados[2].preco;
  adicionaCarrinho(qtd3, 2);
}

function compra4() {
  let qtd4 = document.getElementById('qtd4').value;

  if (qtd4 == null || qtd4 == 0) {
    alert("Nenhum produto adicionado no carrinho, verifique a quantidade");
  } else {
    alert("Produto foi adicionado ao carrinho!");
  }

  let total = qtd4 * dados[3].preco;
  adicionaCarrinho(qtd4, 3);
}

function compra5() {
  let qtd5 = document.getElementById('qtd5').value;

  if (qtd5 == null || qtd5 == 0) {
    alert("Nenhum produto adicionado no carrinho, verifique a quantidade");
  } else {
    alert("Produto foi adicionado ao carrinho!");
  }

  let total = qtd5 * dados[4].preco;
  adicionaCarrinho(qtd5, 4);
}

function compra6() {
  let qtd6 = document.getElementById('qtd6').value;

  if (qtd6 == null || qtd6 == 0) {
    alert("Nenhum produto adicionado no carrinho, verifique a quantidade");
  } else {
    alert("Produto foi adicionado ao carrinho!");
  }

  let total = qtd6 * dados[5].preco;
  adicionaCarrinho(qtd6, 5);
}

// ===============================
// FUNÇÃO PARA ADICIONAR PRODUTOS AO CARRINHO
// ===============================

function adicionaCarrinho(qtd, posiçãoDados) {
  let cart = JSON.parse(sessionStorage.getItem("cart")); // Recupera o carrinho
  let cloneProduto = dados[posiçãoDados]; // Seleciona o produto
  cloneProduto.qtd = qtd; // Adiciona a quantidade selecionada

  if (cart.length == 0) {
    cart.push(cloneProduto); // Se carrinho vazio, adiciona produto
  } else if (cloneProduto.qtd == "") {
    // Não faz nada se a quantidade for vazia
  } else {
    // Verifica se produto já existe no carrinho
    resultado = cart.find((valor) => valor.id === cloneProduto.id);
    if (!resultado) {
      cart.push(cloneProduto); // Adiciona produto se não existir
    } else {
      let index = cart.findIndex(item => item == resultado);
      cart[index] = cloneProduto; // Atualiza quantidade se já existir
    }
  }

  sessionStorage.setItem("cart", JSON.stringify(cart)); // Salva carrinho atualizado
}

// ===============================
// FUNÇÕES DE ATUALIZAÇÃO DO CARRINHO
// ===============================

// Atualiza quantidades ou remove produtos do carrinho
function atualizaCarrinho() {
  let cart = JSON.parse(sessionStorage.getItem("cart"));
  for (let i = 0; i < cart.length; i++) {
    let qtd = document.querySelector(`#form${cart[i].id}`);
    if (qtd.value == 0) {
      cart.splice(i, 1); // Remove item do array
      qtd.parentNode.parentNode.parentNode.remove(); // Remove linha da tabela
    } else {
      cart[i].qtd = qtd.value; // Atualiza quantidade
    }
  }
  sessionStorage.setItem("cart", JSON.stringify(cart));
  atualizaValores(); // Atualiza valores finais
}

// ===============================
// FUNÇÃO PARA INSERIR ITENS NA TABELA DO CARRINHO
// ===============================

function adicionaItem() {
  let cart = JSON.parse(sessionStorage.getItem("cart"));
  let corpo = document.querySelector("#corpo");

  corpo.innerHTML = ""; // LIMPA a tabela antes de preencher

  for (let i = 0; i < cart.length; i++) {
    corpo.innerHTML += `
      <tr data-id="${cart[i].id}">
        <th scope="row">
          <div class="d-flex align-items-center">
            <img src="${cart[i].img}" class="img-fluid rounded-3" style="width: 120px;">
            <div class="flex-column ms-4">
              <p class="mb-2 ml-2">${cart[i].nome}</p>
            </div>
          </div>
        </th>

        <td class="align-middle">
          <input id="form${cart[i].id}" min="0" value="${cart[i].qtd}" 
          type="number" class="form-control form-control-sm" 
          style="width: 50px;" oninput="atualizaCarrinho()" />
        </td>

        <td class="align-middle">
          <p class="mb-0" style="font-weight: 500;">R$${cart[i].preco}</p>
        </td>

        <td class="align-middle">
          <button class="btn btn-danger" onclick="excluirItem(${cart[i].id})">
            🗑️ Excluir
          </button>
        </td>
      </tr>
    `;
  }

  atualizaValores();
}


// ===============================
// FUNÇÃO PARA ATUALIZAR VALORES TOTAIS
// ===============================

function atualizaValores() {
  let cart = JSON.parse(sessionStorage.getItem("cart"));
  let tax = 10; // Taxa fixa
  let subTotalE = document.querySelector("#subTotal");
  let totalTaxE = document.querySelector("#totalTax");
  let checkoutE = document.querySelector("#checkout");
  let vFinal = 0;

  for (let i = 0; i < cart.length; i++) {
    vFinal += (cart[i].preco * parseInt(cart[i].qtd)); // Soma preços
  }

  subTotalE.innerHTML = `R$${vFinal.toFixed(2)}`;
  totalTaxE.innerHTML = `R$${(vFinal + tax).toFixed(2)}`;
  checkoutE.innerHTML = `R$${(vFinal + tax).toFixed(2)}`;
}

// ===============================
// INICIALIZAÇÃO DO CARRINHO AO CARREGAR PÁGINA
// ===============================

function iniciarCarrinho() {
  if (sessionStorage.getItem("cart") == null) {
    sessionStorage.setItem("cart", JSON.stringify([])); // Garante carrinho vazio
  }
}
iniciarCarrinho();

// ===============================
// FUNÇÃO PARA ADICIONAR PRODUTOS AO CARRINHO VIA BOTÃO
// ===============================

function adicionarAoCarrinho(botao) {
  const card = botao.closest(".card-body");
  const id = card.querySelector("input[name='idProduto']").value;
  const nome = card.querySelector(".card-title").textContent;
  const preco = parseFloat(card.querySelector(".text-white").textContent.replace("R$", "").trim());
  const qtdInput = card.querySelector("input[name='quantidade']");
  const qtd = parseInt(qtdInput.value);
  const img = card.closest(".produto-card").querySelector("img").getAttribute("src");

  if (!qtd || qtd <= 0) {
    alert("⚠️ Por favor, insira uma quantidade válida!");
    return;
  }

  let cart = JSON.parse(sessionStorage.getItem("cart")) || [];

  let existente = cart.find(item => item.id == id);
  if (existente) {
    existente.qtd = parseInt(existente.qtd) + qtd; // Soma quantidade
  } else {
    cart.push({ id, nome, preco, qtd, img }); // Adiciona novo produto
  }

  sessionStorage.setItem("cart", JSON.stringify(cart));
  atualizarContadorCarrinho(); // Atualiza contador no ícone
  alert("✅ Produto adicionado ao carrinho!");
}

// ===============================
// CONTADOR DE ITENS NO ÍCONE DO CARRINHO
// ===============================

function atualizarContadorCarrinho() {
  let cart = JSON.parse(sessionStorage.getItem("cart")) || [];
  document.querySelectorAll(".cart-count").forEach(el => {
    el.textContent = cart.length; // Atualiza número de itens
  });
}
window.addEventListener("load", atualizarContadorCarrinho);

// ===============================
// FUNÇÃO PARA MENSAGENS DE CARRINHO
// ===============================

function mostrarMensagemCarrinho(texto, tipo = "info") {
  const mensagemDiv = document.getElementById("mensagemCarrinho");
  const icone = mensagemDiv.querySelector("i");
  const textoMsg = document.getElementById("textoMensagem");

  // Define ícone e cor de acordo com o tipo
  if (tipo === "info") {
    mensagemDiv.className = "alert alert-info";
    icone.className = "fas fa-info-circle";
  } else if (tipo === "sucesso") {
    mensagemDiv.className = "alert alert-success";
    icone.className = "fas fa-check-circle";
  } else if (tipo === "erro") {
    mensagemDiv.className = "alert alert-danger";
    icone.className = "fas fa-exclamation-triangle";
  }

  textoMsg.textContent = texto;
  mensagemDiv.style.display = "block";

  // Esconde automaticamente após 3 segundos
  setTimeout(() => {
    mensagemDiv.style.display = "none";
  }, 3000);
}

// ===============================
// FUNÇÃO PARA REMOVER ITEM DO CARRINHO
// ===============================

function excluirItem(idProduto) {
  let cart = JSON.parse(sessionStorage.getItem("cart")) || [];

  cart = cart.filter(item => item.id != idProduto);

  sessionStorage.setItem("cart", JSON.stringify(cart));

  document.querySelector("#corpo").innerHTML = ""; // limpa tabela

  adicionaItem();   // recria tabela corretamente
  atualizarContadorCarrinho();

  mostrarMensagemCarrinho("🗑️ Produto removido do carrinho.", "sucesso");
}

