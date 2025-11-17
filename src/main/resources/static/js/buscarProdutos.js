// Aguarda o carregamento completo do DOM antes de executar o script
document.addEventListener("DOMContentLoaded", function() {

    // Seleciona o campo de busca de produtos e o container onde os produtos serão exibidos
    const inputBusca = document.getElementById("buscaProdutos"); // Campo de input para busca de produtos
    const container = document.getElementById("produtosContainer"); // Container onde os cards de produtos serão renderizados

    // Variável para controlar o debounce, evitando muitas requisições enquanto o usuário digita
    let timeout = null;

    // Evento acionado sempre que há alteração no input de busca
    inputBusca.addEventListener("input", function() {
        clearTimeout(timeout); // Limpa o timeout anterior
        timeout = setTimeout(() => { // Define um novo timeout de 300ms (debounce)
            const query = inputBusca.value; // Valor digitado pelo usuário

            // Requisição AJAX para buscar produtos pelo nome
            fetch(`/produtos/buscar-ajax?nome=${encodeURIComponent(query)}`)
                .then(response => response.json()) // Converte a resposta da API para JSON
                .then(data => {
                    container.innerHTML = ""; // Limpa os produtos exibidos anteriormente

                    // Se nenhum produto for encontrado, exibe mensagem
                    if (data.length === 0) {
                        container.innerHTML = "<p>Nenhum produto encontrado.</p>";
                        return;
                    }

                    // Inicializa o HTML para exibir os produtos em formato de cards
                    let html = '<div class="row">';
                    data.forEach(produto => { // Para cada produto retornado
                        html += `
                            <div class="col-md-4 mb-4">
                                <div class="card produto-card bg-dark text-white">
                                    <div class="card-img-container">
                                        <img src="/imagem/${produto.id}" alt="Imagem do produto">
                                    </div>
                                    <div class="card-body">
                                        <h5 class="card-title">${produto.nome}</h5>
                                        <p class="card-text">${produto.descricao}</p>
                                        <span class="text-white d-block mb-2">R$ ${produto.preco}</span>
                                        <form action="/carrinho/adicionar" method="post">
                                            <!-- Campo oculto para enviar o ID do produto -->
                                            <input type="hidden" name="idProduto" value="${produto.id}">
                                            <!-- Campo para informar quantidade -->
                                            <input type="number" class="form-control mb-2" name="quantidade" min="1" placeholder="Quantidade">
                                            <!-- Botão para adicionar o produto ao carrinho -->
                                            <button type="submit" class="btn btn-primary w-100">Adicionar ao Carrinho</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        `;
                    });
                    html += '</div>'; // Fecha a div da linha
                    container.innerHTML = html; // Insere o HTML gerado no container
                });
        }, 300); // 300ms de debounce para reduzir número de requisições
    });
});
