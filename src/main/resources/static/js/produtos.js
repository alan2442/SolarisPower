// produtos.js - Agente JavaScript
// Funções específicas para a página de produtos do site SolarisPower

// Executa o código quando o DOM estiver completamente carregado
document.addEventListener('DOMContentLoaded', function() {
    // Carrega a lista de produtos
    loadProdutos();
    
    // Inicializa os filtros de categoria e preço
    initFiltros();
    
    // Atualiza o contador de itens no carrinho
    updateCartCounter();
});

// Função para carregar produtos na página
function loadProdutos() {
    const produtosGrid = document.getElementById('produtos-grid');
    
    if (!produtosGrid) return; // Verifica se o container de produtos existe
    
    // Simula um carregamento assíncrono (em produção, os dados viriam de uma API)
    setTimeout(() => {
        // Lista de produtos simulada
        const produtos = [
            {
                id: 1,
                name: 'Placa Solar de 1 metro sem bateria externa',
                description: '1 painel solar com regulador',
                price: 499.90,
                image: '../assets/images/produto1.jpg',
                categoria: 'paineis'
            },
            {
                id: 2,
                name: 'Conjunto 4x paineis Solares e baterias',
                description: '4 Placa Solares com 4 baterias',
                price: 2499.99,
                image: '../assets/images/produto2.jpg',
                categoria: 'kits'
            },
            {
                id: 3,
                name: 'Conjunto 4x paineis solares com controladora',
                description: '4 painéis solares e 2 baterias',
                price: 1999.90,
                image: '../assets/images/produto3.jpg',
                categoria: 'kits'
            },
            {
                id: 4,
                name: 'Bateria para Sistema Solar 150Ah',
                description: 'Bateria de alta capacidade para sistemas solares',
                price: 899.90,
                image: '../assets/images/produto4.jpg',
                categoria: 'baterias'
            },
            {
                id: 5,
                name: 'Inversor Solar 2000W',
                description: 'Inversor de alta eficiência para sistemas solares',
                price: 1299.90,
                image: '../assets/images/produto5.jpg',
                categoria: 'inversores'
            },
            {
                id: 6,
                name: 'Painel Solar 330W Monocristalino',
                description: 'Painel solar de alta eficiência',
                price: 799.90,
                image: '../assets/images/produto6.jpg',
                categoria: 'paineis'
            }
        ];
        
        // Limpa o grid antes de adicionar novos produtos
        produtosGrid.innerHTML = '';
        
        // Cria e adiciona cada produto ao grid
        produtos.forEach(produto => {
            const produtoCard = createProdutoCard(produto);
            produtosGrid.appendChild(produtoCard);
        });
        
        // Aplica filtros iniciais baseados na URL (se houver)
        applyUrlFilters();
    }, 1000); // Simula atraso de 1 segundo
}

// Função para criar um card visual de produto
function createProdutoCard(produto) {
    const card = document.createElement('div');
    card.className = 'product-card';
    card.dataset.categoria = produto.categoria; // Armazena categoria para filtragem
    card.dataset.preco = produto.price;         // Armazena preço para filtragem
    
    // Define o HTML do card com imagem, informações e ações
    card.innerHTML = `
        <div class="product-image">
            <img src="${produto.image}" alt="${produto.name}" onerror="this.src='../assets/images/produto-placeholder.jpg'">
        </div>
        <div class="product-info">
            <h3>${produto.name}</h3>
            <p>${produto.description}</p>
            <div class="product-price">${formatCurrency(produto.price)}</div>
            <div class="product-actions">
                <input type="number" min="0" value="1" class="quantity-input">
                <button class="btn btn-primary add-to-cart" data-product-id="${produto.id}">Adicionar ao Carrinho</button>
            </div>
        </div>
    `;
    
    // Adiciona evento ao botão "Adicionar ao Carrinho"
    const addToCartButton = card.querySelector('.add-to-cart');
    addToCartButton.addEventListener('click', function() {
        const quantityInput = card.querySelector('.quantity-input');
        const quantity = parseInt(quantityInput.value);
        
        if (quantity > 0) {
            // Adiciona o produto ao carrinho e mostra alerta de sucesso
            addToCart(produto.id, quantity);
            showAlert(`${quantity} unidade(s) de ${produto.name} adicionado(s) ao carrinho!`, 'success');
        } else {
            // Mostra alerta de erro se quantidade inválida
            showAlert('Por favor, selecione uma quantidade válida.', 'error');
        }
    });
    
    return card;
}

// Função para inicializar filtros de categoria e preço
function initFiltros() {
    // Seleciona links de categoria
    const categoriaLinks = document.querySelectorAll('.filtro-list a[data-categoria]');
    categoriaLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            
            // Remove classe "ativa" de todos os links
            categoriaLinks.forEach(l => l.classList.remove('active'));
            
            // Adiciona classe "ativa" ao link clicado
            this.classList.add('active');
            
            // Filtra produtos pela categoria selecionada
            const categoria = this.dataset.categoria;
            filtrarPorCategoria(categoria);
        });
    });
    
    // Configura filtro de preço
    const precoRange = document.getElementById('preco-range');
    const precoMax = document.getElementById('preco-max');
    
    if (precoRange && precoMax) {
        precoRange.addEventListener('input', function() {
            const valor = this.value;
            precoMax.textContent = valor >= 5000 ? 'R$5000+' : `R$${valor}`;
            
            // Filtra produtos pelo preço máximo selecionado
            filtrarPorPreco(valor);
        });
    }
}

// Função para filtrar produtos por categoria
function filtrarPorCategoria(categoria) {
    const produtos = document.querySelectorAll('.product-card');
    
    produtos.forEach(produto => {
        if (categoria === 'todos' || produto.dataset.categoria === categoria) {
            produto.style.display = 'block'; // Mostra produto
        } else {
            produto.style.display = 'none';  // Esconde produto
        }
    });
}

// Função para filtrar produtos por preço
function filtrarPorPreco(precoMaximo) {
    const produtos = document.querySelectorAll('.product-card');
    
    produtos.forEach(produto => {
        const preco = parseFloat(produto.dataset.preco);
        
        if (preco <= precoMaximo || precoMaximo >= 5000) {
            produto.style.display = produto.style.display === 'none' ? 'none' : 'block';
        } else {
            produto.style.display = 'none';
        }
    });
}

// Aplica filtros com base nos parâmetros da URL (ex.: ?categoria=kits)
function applyUrlFilters() {
    const urlParams = new URLSearchParams(window.location.search);
    const categoriaParam = urlParams.get('categoria');
    
    if (categoriaParam) {
        const categoriaLink = document.querySelector(`.filtro-list a[data-categoria="${categoriaParam}"]`);
        if (categoriaLink) {
            // Simula clique no link correspondente à categoria
            categoriaLink.click();
        }
    }
}

// Função para adicionar produto ao carrinho
function addToCart(productId, quantity) {
    let cart = JSON.parse(localStorage.getItem('cart')) || []; // Recupera carrinho do localStorage
    
    const existingProductIndex = cart.findIndex(item => item.productId === productId);
    
    if (existingProductIndex !== -1) {
        // Atualiza quantidade se produto já existe no carrinho
        cart[existingProductIndex].quantity += quantity;
    } else {
        // Adiciona novo produto ao carrinho
        cart.push({
            productId,
            quantity
        });
    }
    
    // Salva carrinho atualizado no localStorage
    localStorage.setItem('cart', JSON.stringify(cart));
    
    // Atualiza contador de itens no header
    updateCartCounter();
}

// Função para atualizar o contador de itens no carrinho do header
function updateCartCounter() {
    const cartCounter = document.querySelector('.cart-counter');
    if (!cartCounter) return;
    
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
    const totalItems = cart.reduce((total, item) => total + item.quantity, 0);
    
    cartCounter.textContent = totalItems;              // Atualiza número visível
    cartCounter.style.display = totalItems > 0 ? 'block' : 'none'; // Mostra ou oculta contador
}


function removerDoCarrinho(productId) {
    let carrinho = JSON.parse(localStorage.getItem("cart")) || [];

    // Remove o produto
    carrinho = carrinho.filter(item => item.productId !== productId);

    // Salva novamente no localStorage
    localStorage.setItem("cart", JSON.stringify(carrinho));

    // Atualiza a tabela automaticamente
    carregarCarrinho();

    // Atualiza número do carrinho
    updateCartCounter();

    // Mostra mensagem
    mostrarMensagemCarrinho("Produto removido do carrinho!", "success");
}


function atualizarQuantidade(productId, novaQuantidade) {
    let carrinho = JSON.parse(localStorage.getItem("cart")) || [];

    const item = carrinho.find(p => p.productId === productId);
    if (!item) return;

    item.quantity = novaQuantidade;

    if (novaQuantidade <= 0) {
        removerDoCarrinho(productId);
        return;
    }

    localStorage.setItem("cart", JSON.stringify(carrinho));

    carregarCarrinho();
    updateCartCounter();
}



function mostrarMensagemCarrinho(mensagem, tipo) {
    const box = document.getElementById("mensagemCarrinho");
    const texto = document.getElementById("textoMensagem");

    texto.innerText = mensagem;

    box.style.display = "block";

    box.className = "alert alert-" + (tipo === "success" ? "success" : "danger");

    setTimeout(() => {
        box.style.display = "none";
    }, 2500);
}



function carregarCarrinho() {
    const corpo = document.getElementById("corpo");
    corpo.innerHTML = "";

    let carrinho = JSON.parse(localStorage.getItem("cart")) || [];

    carrinho.forEach(item => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${item.productId}</td>

            <td>
                <input type="number" min="1" value="${item.quantity}"
                    class="form-control"
                    onchange="atualizarQuantidade(${item.productId}, this.value)">
            </td>

            <td>R$ ${item.price || "0.00"}</td>

            <td>
                <button class="btn btn-danger" onclick="removerDoCarrinho(${item.productId})">
                    <i class="fas fa-trash"></i> Excluir
                </button>
            </td>
        `;

        corpo.appendChild(row);
    });
}

