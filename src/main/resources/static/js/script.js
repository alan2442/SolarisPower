let dados = [{ id: 1, nome: "Placa Solar de 1 metro sem bateria externa", preco: 499.90, img: "/Images/kit01.png", },
{ id: 2, nome: "Conjunto 4x paineis Solares e baterias", preco: 2499.99, img: "/Images/kit3png.jpg", },
{ id: 3, nome: "Conjunto 4x paineis solares com controladora", preco: 1999.90, img: "/Images/kit03.png", },
{ id: 4, nome: "2 painéis solares de 4 metros", preco: 3999.90, img: "/Images/kit5.png" },
{ id: 5, nome: "3 Panéis Solares com controladora", preco: 1499.90, img: "/Images/kit6.png" },
{ id: 6, nome: "Bateria grande para painél solar", preco: 299.90, img: "/Images/bateria.png" },]

//usado na função compra

function insereDados() {
  let bd = JSON.stringify(dados) // transformamos em json e passamos para um variavel 
  sessionStorage.setItem("banco", bd)  // enviamos para a session
}
function insereCarrinho() {
  let carrinho = [];
  let cart = JSON.stringify(carrinho)
  if (sessionStorage.getItem("cart") == null) {
    sessionStorage.setItem("cart", cart);
  }
}

insereDados()
insereCarrinho()


function compra1() {
  let qtd1 = document.getElementById('qtd1').value

  if (qtd1 == null || qtd1 == 0) {
    alert("Nenhum produto adicionado no carrinho, verifique a quantidade")
  }
  else {
    alert("Produto foi adicionado ao carrinho!")
  }

  let total = qtd1 * dados[0].preco

  adicionaCarrinho(qtd1, 0)

}

function compra2() {
  let qtd2 = document.getElementById('qtd2').value

  if (qtd2 == null || qtd2 == 0) {
    alert("Nenhum produto adicionado no carrinho, verifique a quantidade")
  }
  else {
    alert("Produto foi adicionado ao carrinho!")
  }

  let total = qtd2 * dados[1].preco
  adicionaCarrinho(qtd2, 1)

}

function compra3() {
  let qtd3 = document.getElementById('qtd3').value

  if (qtd3 == null || qtd3 == 0) {
    alert("Nenhum produto adicionado no carrinho, verifique a quantidade")
  }
  else {
    alert("Produto foi adicionado ao carrinho!")
  }

  let total = qtd3 * dados[2].preco
  adicionaCarrinho(qtd3, 2)

}

function compra4() {
  let qtd4 = document.getElementById('qtd4').value

  if (qtd4 == null || qtd4 == 0) {
    alert("Nenhum produto adicionado no carrinho, verifique a quantidade")
  }
  else {
    alert("Produto foi adicionado ao carrinho!")
  }

  let total = qtd4 * dados[3].preco
  adicionaCarrinho(qtd4, 3)

}

function compra5() {
  let qtd5 = document.getElementById('qtd5').value

  if (qtd5 == null || qtd5 == 0) {
    alert("Nenhum produto adicionado no carrinho, verifique a quantidade")
  }
  else {
    alert("Produto foi adicionado ao carrinho!")
  }

  let total = qtd5 * dados[4].preco
  adicionaCarrinho(qtd5, 4)

}
function compra6() {
  let qtd6 = document.getElementById('qtd6').value

  if (qtd6 == null || qtd6 == 0) {
    alert("Nenhum produto adicionado no carrinho, verifique a quantidade")
  }
  else {
    alert("Produto foi adicionado ao carrinho!")
  }

  let total = qtd6 * dados[5].preco
  adicionaCarrinho(qtd6, 5)

}


function adicionaCarrinho(qtd, posiçãoDados) {
  let cart = JSON.parse(sessionStorage.getItem("cart"));
  let cloneProduto = dados[posiçãoDados];
  cloneProduto.qtd = qtd;
  if (cart.length == 0) {
    cart.push(cloneProduto);
  } else if (cloneProduto.qtd == "") {

  } else {
    resultado = cart.find((valor) => valor.id === cloneProduto.id)
    if (!resultado) {
      cart.push(cloneProduto);
    } else {
      let index = cart.findIndex(item => item == resultado)
      cart[index] = cloneProduto
    }
  }

  sessionStorage.setItem("cart", JSON.stringify(cart))
}

function atualizaCarrinho() {
  let cart = JSON.parse(sessionStorage.getItem("cart"));
  for (let i = 0; i < cart.length; i++) {
    let qtd = document.querySelector(`#form${cart[i].id}`)
    if (qtd.value == 0) {
      cart.splice(i, 1)
      qtd.parentNode.parentNode.parentNode.remove()
    } else {
      cart[i].qtd = qtd.value
    }
  }
  sessionStorage.setItem("cart", JSON.stringify(cart));
  atualizaValores();
}
function adicionaItem() {
  console.log("Chamou");
  let cart = JSON.parse(sessionStorage.getItem("cart"))
  let corpo = document.querySelector("#corpo")
  for (let i = 0; i <= cart.length; i++) {
    corpo.innerHTML += `<tr data-id="${cart[i].id}">
    <th scope="row" >
      <div class="d-flex align-items-center">
        <img src="${cart[i].img}" class="img-fluid rounded-3"
          style="width: 120px;" alt="Book">
        <div class="flex-column ms-4">
          <p class="mb-2 ml-2">${cart[i].nome}</p>
        </div>
      </div>
    </th>
    
    <td class="align-middle">
      <div class="d-flex flex-row">
        <input id="form${cart[i].id}" min="0" name="quantity" value=${cart[i].qtd} type="number"
          class="form-control form-control-sm" style="width: 50px;" />
      </div>
    </td>
    <td class="align-middle">
      <p class="mb-0" style="font-weight: 500;">R$${cart[i].preco}</p>
    </td>
    <td class="align-middle">
      <button class="btn btn-light" onclick="atualizaCarrinho()">Atualizar</button>
    </td>
  </tr>
  `;
    atualizaValores();
  }
}
function atualizaValores() {
  let cart = JSON.parse(sessionStorage.getItem("cart"));
  let tax = 10
  let subTotalE = document.querySelector("#subTotal")
  let totalTaxE = document.querySelector("#totalTax");
  let checkoutE = document.querySelector("#checkout");
  let vFinal = 0
  for (let i = 0; i < cart.length; i++) {
    vFinal += (cart[i].preco * parseInt(cart[i].qtd))
  }
  subTotalE.innerHTML = `R$${vFinal.toFixed(2)}`;
  totalTaxE.innerHTML = `R$${(vFinal + tax).toFixed(2)}`
  checkoutE.innerHTML = `R$${(vFinal + tax).toFixed(2)}`;

}


// ===============================
// INICIALIZAÇÃO DO CARRINHO
// ===============================
function iniciarCarrinho() {
  if (sessionStorage.getItem("cart") == null) {
    sessionStorage.setItem("cart", JSON.stringify([]));
  }
}
iniciarCarrinho();

// ===============================
// ADICIONAR PRODUTO AO CARRINHO
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
    existente.qtd = parseInt(existente.qtd) + qtd;
  } else {
    cart.push({ id, nome, preco, qtd, img });
  }

  sessionStorage.setItem("cart", JSON.stringify(cart));
  atualizarContadorCarrinho();
  alert("✅ Produto adicionado ao carrinho!");
}

// ===============================
// CONTADOR NO ÍCONE DO CARRINHO
// ===============================
function atualizarContadorCarrinho() {
  let cart = JSON.parse(sessionStorage.getItem("cart")) || [];
  document.querySelectorAll(".cart-count").forEach(el => {
    el.textContent = cart.length;
  });
}
window.addEventListener("load", atualizarContadorCarrinho);

// ===============================
// PREENCHER TABELA NA PÁGINA CARRINHO
// ===============================
function adicionaItem() {
  let corpo = document.querySelector("#corpo");
  if (!corpo) return;

  corpo.innerHTML = "";
  let cart = JSON.parse(sessionStorage.getItem("cart")) || [];

  cart.forEach((produto, i) => {
    corpo.innerHTML += `
      <tr data-id="${produto.id}">
        <th scope="row">
          <div class="d-flex align-items-center">
            <img src="${produto.img}" class="img-fluid rounded-3" style="width: 120px;" alt="${produto.nome}">
            <div class="flex-column ms-4">
              <p class="mb-2 ml-2">${produto.nome}</p>
            </div>
          </div>
        </th>
        <td class="align-middle">
          <input id="form${produto.id}" min="0" name="quantity" value="${produto.qtd}" type="number"
            class="form-control form-control-sm" style="width: 60px;" />
        </td>
        <td class="align-middle">
          <p class="mb-0" style="font-weight: 500;">R$${produto.preco.toFixed(2)}</p>
        </td>
        <td class="align-middle">
          <button class="btn btn-light" onclick="atualizaCarrinho()">Atualizar</button>
        </td>
      </tr>
    `;
  });

  atualizaValores();
}

// ===============================
// ATUALIZAR QUANTIDADES OU REMOVER
// ===============================
function atualizaCarrinho() {
  let cart = JSON.parse(sessionStorage.getItem("cart")) || [];

  for (let i = 0; i < cart.length; i++) {
    let input = document.querySelector(`#form${cart[i].id}`);
    if (input) {
      let qtd = parseInt(input.value);
      if (qtd <= 0) {
        cart.splice(i, 1);
        i--;
      } else {
        cart[i].qtd = qtd;
      }
    }
  }

  sessionStorage.setItem("cart", JSON.stringify(cart));
  adicionaItem();
  atualizarContadorCarrinho();
}

// ===============================
// ATUALIZAR VALORES FINAIS
// ===============================
function atualizaValores() {
  let cart = JSON.parse(sessionStorage.getItem("cart")) || [];
  let subTotal = 0;
  cart.forEach(p => {
    subTotal += p.preco * p.qtd;
  });

  const taxa = 10;
  document.querySelector("#subTotal").textContent = `R$${subTotal.toFixed(2)}`;
  document.querySelector("#totalTax").textContent = `R$${(subTotal + taxa).toFixed(2)}`;
  document.querySelector("#checkout").textContent = `R$${(subTotal + taxa).toFixed(2)}`;
}




