// home.js - Agente JavaScript
// Funções específicas para a página inicial do site SolarisPower

// Evento que aguarda o carregamento completo do DOM
document.addEventListener('DOMContentLoaded', function() {
    // Chama a função para carregar produtos em destaque assim que a página é carregada
    loadFeaturedProducts();
});

// ✅ Função para carregar produtos em destaque
function loadFeaturedProducts() {
    const productGrid = document.querySelector('.product-grid'); // Seleciona o container do grid de produtos
    
    if (!productGrid) return; // Se o grid não existir, encerra a função

    // Simulação de dados de produtos (em produção, estes dados viriam de uma API)
    const featuredProducts = [
        {
            id: 1,
            name: 'Placa Solar de 1 metro sem bateria externa',
            description: '1 painel solar com regulador',
            price: 499.90,
            image: '/Images/kit01.png'
        },
        {
            id: 2,
            name: 'Conjunto 4x paineis Solares e baterias',
            description: '4 Placa Solares com 4 baterias',
            price: 2499.99,
            image: '/Images/kit3png.jpg'
        },
        {
            id: 3,
            name: 'Conjunto 4x paineis solares com controladora',
            description: '4 painéis solares e 2 baterias',
            price: 1999.90,
            image: '/Images/kit5.png'
        }
    ];
    
    // Limpa o grid de produtos antes de adicionar novos cards
    productGrid.innerHTML = '';
    
    // Itera sobre os produtos e adiciona cada card ao grid
    featuredProducts.forEach(product => {
        const productCard = createProductCard(product); // Cria um card para cada produto
        productGrid.appendChild(productCard); // Adiciona o card ao DOM
    });
}

// ✅ Função para criar um card de produto individual
function createProductCard(product) {
    const card = document.createElement('div');
    card.className = 'product-card'; // Define a classe do card para estilização

    // Estrutura interna do card com imagem, informações, preço e ações
    card.innerHTML = `
        <div class="product-image">
            <img src="${product.image}" alt="${product.name}" onerror="this.src='../assets/images/produto-placeholder.jpg'">
        </div>
        <div class="product-info">
            <h3>${product.name}</h3>
            <p>${product.description}</p>
            <div class="product-price">${formatCurrency(product.price)}</div>
            <div class="product-actions">
                <input type="number" min="0" value="0" class="quantity-input">
                <button class="btn btn-primary add-to-cart" data-product-id="${product.id}">Adicionar ao Carrinho</button>
            </div>
        </div>
    `;

    // Adiciona evento de clique no botão de adicionar ao carrinho
    const addToCartButton = card.querySelector('.add-to-cart');
    addToCartButton.addEventListener('click', function() {
        const quantityInput = card.querySelector('.quantity-input'); // Obtém a quantidade selecionada
        const quantity = parseInt(quantityInput.value); // Converte valor para número inteiro
        
        if (quantity > 0) {
            addToCart(product.id, quantity); // Chama função para adicionar produto ao carrinho
            showAlert(`${quantity} unidade(s) de ${product.name} adicionado(s) ao carrinho!`, 'success'); // Exibe alerta de sucesso
        } else {
            showAlert('Por favor, selecione uma quantidade válida.', 'error'); // Alerta de erro caso quantidade inválida
        }
    });

    return card; // Retorna o card criado para inserção no DOM
}

// ✅ Função para adicionar produto ao carrinho
function addToCart(productId, quantity) {
    // Recupera o carrinho do localStorage ou inicializa um array vazio
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    
    // Verifica se o produto já está no carrinho
    const existingProductIndex = cart.findIndex(item => item.productId === productId);
    
    if (existingProductIndex !== -1) {
        // Atualiza quantidade do produto existente
        cart[existingProductIndex].quantity += quantity;
    } else {
        // Adiciona novo produto ao carrinho
        cart.push({
            productId,
            quantity
        });
    }
    
    // Salva o carrinho atualizado no localStorage
    localStorage.setItem('cart', JSON.stringify(cart));
    
    // Atualiza o contador de itens do carrinho no header
    updateCartCounter();
}

// ✅ Função para atualizar o contador de itens do carrinho
function updateCartCounter() {
    const cartCounter = document.querySelector('.cart-counter'); // Seleciona o elemento do contador
    if (!cartCounter) return; // Sai se o elemento não existir

    // Recupera carrinho do localStorage
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
    const totalItems = cart.reduce((total, item) => total + item.quantity, 0); // Soma todas as quantidades

    // Atualiza o conteúdo do contador e visibilidade
    cartCounter.textContent = totalItems;
    cartCounter.style.display = totalItems > 0 ? 'block' : 'none';
}
