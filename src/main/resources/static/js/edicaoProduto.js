// Função para excluir produto via AJAX
    function excluirProduto(id) {
        if (!confirm("Tem certeza que deseja excluir este produto?")) return;

        fetch(`/cadastroProdutos/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                alert("Produto excluído com sucesso!");
                location.reload(); // ou re-renderize a tabela manualmente
            } else {
                alert("Erro ao excluir produto");
            }
        });
    }

    // Função para buscar produto e preencher o formulário para edição
    function editarProduto(id) {
        fetch(`/cadastroProdutos/${id}`)
        .then(response => response.json())
        .then(produto => {
            document.querySelector("input[name='nm_produto']").value = produto.nm_produto;
            document.querySelector("textarea[name='descricao_produto']").value = produto.descricao_produto;
            document.querySelector("input[name='preco_produto']").value = produto.preco_produto;
            document.querySelector("input[name='qtd_produto']").value = produto.qtd_produto;
            document.querySelector("select[name='categoria']").value = produto.categoria;

            document.querySelector("input[name='idProdutoEdicao']").value = produto.cd_produto;

            // Scroll para o formulário
            window.scrollTo({ top: 0, behavior: 'smooth' });
        });
    }


    function carregarProdutoParaEdicao(id) {
        fetch(`/cadastroProdutos/${id}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Produto não encontrado");
                }
                return response.json();
            })
            .then(produto => {
                // Preencher o formulário de edição
                document.getElementById('edit-nm_produto').value = produto.nm_produto;
                document.getElementById('edit-categoria').value = produto.categoria;
                document.getElementById('edit-descricao_produto').value = produto.descricao_produto;
                document.getElementById('edit-preco_produto').value = produto.preco_produto;
                document.getElementById('edit-qtd_produto').value = produto.qtd_produto;
                document.getElementById('edit-idProdutoEdicao').value = produto.cd_produto;

                // Mostrar o formulário de edição
                document.getElementById('form-editar-produto').style.display = 'block';

                // Rolar até o formulário de edição
                document.getElementById('form-editar-produto').scrollIntoView({ behavior: 'smooth' });
            })
            .catch(error => {
                alert("Erro ao carregar produto para edição: " + error.message);
            });
    }