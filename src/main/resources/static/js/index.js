// home.js - Agente JavaScript
// Funções específicas para a página inicial do site SolarisPower

document.addEventListener('DOMContentLoaded', function() {
    // Carregar produtos em destaque
    loadFeaturedProducts();
});

// Função para carregar produtos em destaque
function loadFeaturedProducts() {
    const productGrid = document.querySelector('.product-grid');
    
    if (!productGrid) return;
    
    // Simulação de dados de produtos (em um cenário real, estes viriam de uma API)
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
    
    // Limpar o grid de produtos
    productGrid.innerHTML = '';
    
    // Adicionar os produtos ao grid
    featuredProducts.forEach(product => {
        const productCard = createProductCard(product);
        productGrid.appendChild(productCard);
    });
}

// Função para criar um card de produto
function createProductCard(product) {
    const card = document.createElement('div');
    card.className = 'product-card';
    
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
    
    // Adicionar evento ao botão de adicionar ao carrinho
    const addToCartButton = card.querySelector('.add-to-cart');
    addToCartButton.addEventListener('click', function() {
        const quantityInput = card.querySelector('.quantity-input');
        const quantity = parseInt(quantityInput.value);
        
        if (quantity > 0) {
            addToCart(product.id, quantity);
            showAlert(`${quantity} unidade(s) de ${product.name} adicionado(s) ao carrinho!`, 'success');
        } else {
            showAlert('Por favor, selecione uma quantidade válida.', 'error');
        }
    });
    
    return card;
}

// Função para adicionar produto ao carrinho
function addToCart(productId, quantity) {
    // Obter o carrinho atual do localStorage ou criar um novo
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    
    // Verificar se o produto já está no carrinho
    const existingProductIndex = cart.findIndex(item => item.productId === productId);
    
    if (existingProductIndex !== -1) {
        // Atualizar a quantidade se o produto já estiver no carrinho
        cart[existingProductIndex].quantity += quantity;
    } else {
        // Adicionar novo item ao carrinho
        cart.push({
            productId,
            quantity
        });
    }
    
    // Salvar o carrinho atualizado no localStorage
    localStorage.setItem('cart', JSON.stringify(cart));
    
    // Atualizar o contador do carrinho no header (se existir)
    updateCartCounter();
}

// Função para atualizar o contador do carrinho
function updateCartCounter() {
    const cartCounter = document.querySelector('.cart-counter');
    if (!cartCounter) return;
    
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
    const totalItems = cart.reduce((total, item) => total + item.quantity, 0);
    
    cartCounter.textContent = totalItems;
    cartCounter.style.display = totalItems > 0 ? 'block' : 'none';
}
