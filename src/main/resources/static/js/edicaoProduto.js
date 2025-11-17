// Seleção de elementos do DOM necessários para manipulação
const msgDiv = document.getElementById('msgStatus'); // Div para exibir mensagens ao usuário
const btnCancelar = document.getElementById('btnCancelar'); // Botão para cancelar edição
const formProduto = document.getElementById('form-produto'); // Formulário de cadastro/edição de produtos
const inputBusca = document.getElementById('input-busca'); // Campo de busca de produtos
const btnBuscar = document.getElementById('btn-buscar'); // Botão para acionar busca manual

// ✅ Função para exibir mensagens temporárias ao usuário
function exibirMsg(msg, tipo) {
    if (!msgDiv) return; // Retorna caso a div de mensagem não exista
    msgDiv.innerText = msg; // Define o texto da mensagem
    msgDiv.className = 'alert alert-' + tipo; // Define a classe de alerta (ex: success, danger)
    msgDiv.style.display = 'block'; // Exibe a mensagem
    setTimeout(() => { msgDiv.style.display = 'none'; }, 4000); // Oculta após 4 segundos
}

// ✅ Função para renderizar produtos na tabela
function renderizarProdutos(produtos) {
    const tbody = document.getElementById('tbody-produtos'); // Corpo da tabela
    tbody.innerHTML = ''; // Limpa tabela antes de renderizar

    if (!produtos || produtos.length === 0) { // Caso não haja produtos
        document.getElementById('sem-produtos').style.display = 'block'; // Mostra mensagem de "nenhum produto"
        return;
    } else {
        document.getElementById('sem-produtos').style.display = 'none'; // Oculta mensagem se houver produtos
    }

    // Criação de linhas da tabela dinamicamente
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
        tbody.appendChild(tr); // Adiciona a linha na tabela
    });
}

// ✅ Função para listar todos os produtos via AJAX
function listarProdutos() {
    fetch('/produto/listar-ajax')
        .then(res => res.json())
        .then(renderizarProdutos) // Renderiza os produtos obtidos
        .catch(() => exibirMsg("Erro ao carregar produtos", "danger")); // Tratamento de erro
}

// ✅ Função para buscar produtos conforme o usuário digita
function buscarProdutos() {
    const termo = inputBusca.value.trim(); // Remove espaços desnecessários
    fetch(`/buscar-ajax?nome=${encodeURIComponent(termo)}`)
        .then(res => res.json())
        .then(renderizarProdutos)
        .catch(() => exibirMsg("Erro ao buscar produtos", "danger"));
}

// ✅ Eventos de input e clique para busca de produtos
if (inputBusca) {
    inputBusca.addEventListener('input', buscarProdutos); // Busca em tempo real enquanto digita
    inputBusca.addEventListener('keypress', e => {
        if (e.key === 'Enter') e.preventDefault(); // Evita submissão do formulário ao apertar Enter
    });
}
if (btnBuscar) btnBuscar.addEventListener('click', buscarProdutos); // Busca manual ao clicar no botão

// ✅ Carregar um produto existente no formulário para edição
function carregarProdutoParaEdicao(id) {
    fetch(`/produto/editar-ajax/${id}`)
        .then(res => res.json())
        .then(produto => {
            if (!produto) return exibirMsg("Produto não encontrado", "danger");

            // Preenche o formulário com os dados do produto
            document.getElementById('nome').value = produto.nome;
            document.getElementById('categoria').value = produto.categoria;
            document.getElementById('descricao').value = produto.descricao;
            document.getElementById('preco').value = produto.preco;
            document.getElementById('quantidade').value = produto.quantidade;
            document.getElementById('idProdutoEdicao').value = produto.id;

            // Atualiza título e botão do formulário para edição
            document.getElementById('titulo-form').innerText = 'Editar Produto';
            document.getElementById('btnSalvar').innerText = 'Salvar Alterações';
            btnCancelar.style.display = 'inline-block'; // Exibe botão de cancelar

            // Scroll suave até o topo do formulário
            document.querySelector('form').scrollIntoView({ behavior: 'smooth', block: 'start' });

        })
        .catch(() => exibirMsg("Erro ao carregar produto", "danger"));
}

// ✅ Cancelar edição e resetar formulário
if (btnCancelar) {
    btnCancelar.addEventListener('click', () => {
        formProduto.reset(); // Limpa campos
        document.getElementById('idProdutoEdicao').value = ''; // Limpa ID de edição
        document.getElementById('titulo-form').innerText = 'Cadastrar Produto'; // Restaura título
        document.getElementById('btnSalvar').innerText = 'Salvar Produto'; // Restaura botão
        btnCancelar.style.display = 'none'; // Oculta botão cancelar
    });
}

// ✅ Salvar ou atualizar produto via AJAX
if (formProduto) {
    formProduto.addEventListener('submit', function (e) {
        e.preventDefault(); // Evita reload da página

        const formData = new FormData(this); // Obtém dados do formulário
        const idEdicao = document.getElementById('idProdutoEdicao').value;
        const url = idEdicao
            ? '/produto/atualizar-ajax?idProdutoEdicao=' + idEdicao // URL de atualização
            : '/paginaCadastroProdutos'; // URL de cadastro

        fetch(url, { method: 'POST', body: formData })
            .then(res => res.text())
            .then(msg => {
                exibirMsg(msg || 'Produto salvo com sucesso!', 'success');
                formProduto.reset(); // Limpa formulário
                document.getElementById('idProdutoEdicao').value = ''; // Limpa ID de edição
                document.getElementById('titulo-form').innerText = 'Cadastrar Produto'; // Restaura título
                document.getElementById('btnSalvar').innerText = 'Salvar Produto'; // Restaura botão
                btnCancelar.style.display = 'none'; // Oculta botão cancelar
                listarProdutos(); // Atualiza lista de produtos
            })
            .catch(() => exibirMsg("Erro ao salvar produto", "danger"));
    });
}

// ✅ Excluir produto via AJAX
function excluirProduto(id) {
    if (!confirm("Deseja realmente excluir este produto?")) return; // Confirmação do usuário

    fetch(`/produto/excluir-ajax/${id}`, { method: 'POST' })
        .then(res => res.text())
        .then(msg => {
            exibirMsg(msg, 'success'); // Exibe mensagem de sucesso
            listarProdutos(); // Atualiza lista de produtos
        })
        .catch(() => exibirMsg("Erro ao excluir produto", "danger"));
}

// ✅ Botão cancelar para resetar formulário manualmente
btnCancelar.addEventListener('click', function() {
    // Limpa todos os campos do formulário
    document.getElementById('nome').value = '';
    document.getElementById('categoria').value = '';
    document.getElementById('descricao').value = '';
    document.getElementById('preco').value = '';
    document.getElementById('quantidade').value = '';
    document.getElementById('idProdutoEdicao').value = '';

    // Restaura estado de cadastro
    document.getElementById('titulo-form').innerText = 'Cadastrar Produto';
    document.getElementById('btnSalvar').innerText = 'Salvar';
    
    // Oculta botão cancelar
    btnCancelar.style.display = 'none';
});

// ✅ Inicializa listagem de produtos ao carregar a página
document.addEventListener('DOMContentLoaded', listarProdutos);
