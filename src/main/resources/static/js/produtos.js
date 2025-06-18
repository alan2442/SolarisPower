// produtos.js - Agente JavaScript
// Funções específicas para a página de produtos do site SolarisPower

document.addEventListener('DOMContentLoaded', function() {
    // Carregar produtos
    loadProdutos();
    
    // Inicializar filtros
    initFiltros();
    
    // Atualizar contador do carrinho
    updateCartCounter();
});

// Função para carregar produtos
function loadProdutos() {
    const produtosGrid = document.getElementById('produtos-grid');
    
    if (!produtosGrid) return;
    
    // Simular carregamento
    setTimeout(() => {
        // Simulação de dados de produtos (em um cenário real, estes viriam de uma API)
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
        
        // Limpar o grid de produtos
        produtosGrid.innerHTML = '';
        
        // Adicionar os produtos ao grid
        produtos.forEach(produto => {
            const produtoCard = createProdutoCard(produto);
            produtosGrid.appendChild(produtoCard);
        });
        
        // Aplicar filtros iniciais da URL
        applyUrlFilters();
    }, 1000);
}

// Função para criar um card de produto
function createProdutoCard(produto) {
    const card = document.createElement('div');
    card.className = 'product-card';
    card.dataset.categoria = produto.categoria;
    card.dataset.preco = produto.price;
    
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
    
    // Adicionar evento ao botão de adicionar ao carrinho
    const addToCartButton = card.querySelector('.add-to-cart');
    addToCartButton.addEventListener('click', function() {
        const quantityInput = card.querySelector('.quantity-input');
        const quantity = parseInt(quantityInput.value);
        
        if (quantity > 0) {
            addToCart(produto.id, quantity);
            showAlert(`${quantity} unidade(s) de ${produto.name} adicionado(s) ao carrinho!`, 'success');
        } else {
            showAlert('Por favor, selecione uma quantidade válida.', 'error');
        }
    });
    
    return card;
}

// Função para inicializar filtros
function initFiltros() {
    // Filtro de categorias
    const categoriaLinks = document.querySelectorAll('.filtro-list a[data-categoria]');
    categoriaLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            
            // Remover classe ativa de todos os links
            categoriaLinks.forEach(l => l.classList.remove('active'));
            
            // Adicionar classe ativa ao link clicado
            this.classList.add('active');
            
            // Aplicar filtro
            const categoria = this.dataset.categoria;
            filtrarPorCategoria(categoria);
        });
    });
    
    // Filtro de preço
    const precoRange = document.getElementById('preco-range');
    const precoMax = document.getElementById('preco-max');
    
    if (precoRange && precoMax) {
        precoRange.addEventListener('input', function() {
            const valor = this.value;
            precoMax.textContent = valor >= 5000 ? 'R$5000+' : `R$${valor}`;
            
            filtrarPorPreco(valor);
        });
    }
}

// Função para filtrar produtos por categoria
function filtrarPorCategoria(categoria) {
    const produtos = document.querySelectorAll('.product-card');
    
    produtos.forEach(produto => {
        if (categoria === 'todos' || produto.dataset.categoria === categoria) {
            produto.style.display = 'block';
        } else {
            produto.style.display = 'none';
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

// Função para aplicar filtros da URL
function applyUrlFilters() {
    const urlParams = new URLSearchParams(window.location.search);
    const categoriaParam = urlParams.get('categoria');
    
    if (categoriaParam) {
        const categoriaLink = document.querySelector(`.filtro-list a[data-categoria="${categoriaParam}"]`);
        
        if (categoriaLink) {
            // Simular clique no link da categoria
            categoriaLink.click();
        }
    }
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
    
    // Atualizar o contador do carrinho no header
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
