document.addEventListener("DOMContentLoaded", function() {
    const inputBusca = document.getElementById("buscaProdutos");
    const container = document.getElementById("produtosContainer");
    let timeout = null;

    inputBusca.addEventListener("input", function() {
        clearTimeout(timeout);
        timeout = setTimeout(() => {
            const query = inputBusca.value;

            fetch(`/produtos/buscar-ajax?nome=${encodeURIComponent(query)}`)
                .then(response => response.json())
                .then(data => {
                    container.innerHTML = ""; // Limpa produtos
                    if (data.length === 0) {
                        container.innerHTML = "<p>Nenhum produto encontrado.</p>";
                        return;
                    }

                    let html = '<div class="row">';
                    data.forEach(produto => {
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
                                            <input type="hidden" name="idProduto" value="${produto.id}">
                                            <input type="number" class="form-control mb-2" name="quantidade" min="1" placeholder="Quantidade">
                                            <button type="submit" class="btn btn-primary w-100">Adicionar ao Carrinho</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        `;
                    });
                    html += '</div>';
                    container.innerHTML = html;
                });
        }, 300); // 300ms de debounce
    });
});