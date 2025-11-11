const msgDiv = document.getElementById('msgStatus');
const btnCancelar = document.getElementById('btnCancelar');
const formProduto = document.getElementById('form-produto');
const inputBusca = document.getElementById('input-busca');
const btnBuscar = document.getElementById('btn-buscar');


// ✅ Função para exibir mensagens temporárias
function exibirMsg(msg, tipo) {
    if (!msgDiv) return;
    msgDiv.innerText = msg;
    msgDiv.className = 'alert alert-' + tipo;
    msgDiv.style.display = 'block';
    setTimeout(() => { msgDiv.style.display = 'none'; }, 4000);
}

// ✅ Função para preencher tabela de produtos
function renderizarProdutos(produtos) {
    const tbody = document.getElementById('tbody-produtos');
    tbody.innerHTML = '';

    if (!produtos || produtos.length === 0) {
        document.getElementById('sem-produtos').style.display = 'block';
        return;
    } else {
        document.getElementById('sem-produtos').style.display = 'none';
    }

    produtos.forEach(produto => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><img src="/imagem/${produto.id}" width="80" height="80" style="object-fit: cover;"></td>
            <td>${produto.nome}</td>
            <td>${produto.categoria}</td>
            <td>${produto.descricao}</td>
            <td>${parseFloat(produto.preco).toFixed(2)}</td>
            <td>${produto.quantidade}</td>
            <td>
                <button type="button" class="btn-acao atualizar" onclick="carregarProdutoParaEdicao(${produto.id})">Atualizar</button>
                <button type="button" class="btn-acao excluir" onclick="excluirProduto(${produto.id})">Excluir</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

// ✅ Buscar todos produtos
function listarProdutos() {
    fetch('/produto/listar-ajax')
        .then(res => res.json())
        .then(renderizarProdutos)
        .catch(() => exibirMsg("Erro ao carregar produtos", "danger"));
}

// ✅ Buscar produtos conforme digita
function buscarProdutos() {
    const termo = inputBusca.value.trim();
    fetch(`/buscar-ajax?nome=${encodeURIComponent(termo)}`)
        .then(res => res.json())
        .then(renderizarProdutos)
        .catch(() => exibirMsg("Erro ao buscar produtos", "danger"));
}

// Atualiza tabela enquanto digita (em tempo real)
if (inputBusca) {
    inputBusca.addEventListener('input', buscarProdutos);
    inputBusca.addEventListener('keypress', e => {
        if (e.key === 'Enter') e.preventDefault();
    });
}
if (btnBuscar) btnBuscar.addEventListener('click', buscarProdutos);

// ✅ Carregar produto no formulário para edição
function carregarProdutoParaEdicao(id) {
    fetch(`/produto/editar-ajax/${id}`)
        .then(res => res.json())
        .then(produto => {
            if (!produto) return exibirMsg("Produto não encontrado", "danger");

            document.getElementById('nome').value = produto.nome;
            document.getElementById('categoria').value = produto.categoria;
            document.getElementById('descricao').value = produto.descricao;
            document.getElementById('preco').value = produto.preco;
            document.getElementById('quantidade').value = produto.quantidade;
            document.getElementById('idProdutoEdicao').value = produto.id;

            document.getElementById('titulo-form').innerText = 'Editar Produto';
            document.getElementById('btnSalvar').innerText = 'Salvar Alterações';
            btnCancelar.style.display = 'inline-block';


            // Faz rolar suavemente até o topo do formulário
            document.querySelector('form').scrollIntoView({ behavior: 'smooth', block: 'start' });

        })
        .catch(() => exibirMsg("Erro ao carregar produto", "danger"));
}

// ✅ Cancelar edição
if (btnCancelar) {
    btnCancelar.addEventListener('click', () => {
        formProduto.reset();
        document.getElementById('idProdutoEdicao').value = '';
        document.getElementById('titulo-form').innerText = 'Cadastrar Produto';
        document.getElementById('btnSalvar').innerText = 'Salvar Produto';
        btnCancelar.style.display = 'none';
    });
}

// ✅ Salvar ou atualizar produto
if (formProduto) {
    formProduto.addEventListener('submit', function (e) {
        e.preventDefault();

        const formData = new FormData(this);
        const idEdicao = document.getElementById('idProdutoEdicao').value;
        const url = idEdicao
            ? '/produto/atualizar-ajax?idProdutoEdicao=' + idEdicao
            : '/paginaCadastroProdutos';

        fetch(url, { method: 'POST', body: formData })
            .then(res => res.text())
            .then(msg => {
                exibirMsg(msg || 'Produto salvo com sucesso!', 'success');
                formProduto.reset();
                document.getElementById('idProdutoEdicao').value = '';
                document.getElementById('titulo-form').innerText = 'Cadastrar Produto';
                document.getElementById('btnSalvar').innerText = 'Salvar Produto';
                btnCancelar.style.display = 'none';
                listarProdutos();
            })
            .catch(() => exibirMsg("Erro ao salvar produto", "danger"));
    });
}

// ✅ Excluir produto
function excluirProduto(id) {
    if (!confirm("Deseja realmente excluir este produto?")) return;

    fetch(`/produto/excluir-ajax/${id}`, { method: 'POST' })
        .then(res => res.text())
        .then(msg => {
            exibirMsg(msg, 'success');
            listarProdutos();
        })
        .catch(() => exibirMsg("Erro ao excluir produto", "danger"));
}


btnCancelar.addEventListener('click', function() {
    // Limpa o formulário
    document.getElementById('nome').value = '';
    document.getElementById('categoria').value = '';
    document.getElementById('descricao').value = '';
    document.getElementById('preco').value = '';
    document.getElementById('quantidade').value = '';
    document.getElementById('idProdutoEdicao').value = '';

    // Volta o formulário para estado de cadastro
    document.getElementById('titulo-form').innerText = 'Cadastrar Produto';
    document.getElementById('btnSalvar').innerText = 'Salvar';
    
    // Esconde o botão cancelar
    btnCancelar.style.display = 'none';
});

// ✅ Inicialização
document.addEventListener('DOMContentLoaded', listarProdutos);
