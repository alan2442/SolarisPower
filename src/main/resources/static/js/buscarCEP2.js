// Aguarda o carregamento completo do DOM antes de executar o script
document.addEventListener("DOMContentLoaded", function () {

    // Seleciona os campos do formulário relacionados ao endereço da empresa
    const cepInput = document.getElementById("CepEmpresa"); // Campo de CEP
    const ruaInput = document.getElementById("RuaEmpresa"); // Campo de rua
    const complementoInput = document.getElementById("ComplementoEmpresa"); // Campo de complemento
    const buscarCepEmpresaBtn = document.getElementById("buscarCepEmpresaBtn"); // Botão de busca de CEP

    // Função responsável por buscar o endereço utilizando a API ViaCEP
    function buscarEndereco() {
        // Remove qualquer caractere que não seja número do valor do CEP
        const cep = cepInput.value.replace(/\D/g, '');

        // Valida se o CEP possui 8 dígitos (formato válido no Brasil)
        if (cep.length === 8) {
            // Requisição para a API ViaCEP
            fetch(`https://viacep.com.br/ws/${cep}/json/`)
                .then(response => {
                    // Verifica se a resposta da API foi bem-sucedida
                    if (!response.ok) {
                        throw new Error("Erro ao consultar o CEP");
                    }
                    return response.json(); // Converte a resposta para JSON
                })
                .then(data => {
                    // Se não houver erro na resposta, preenche os campos do formulário
                    if (!data.erro) {
                        ruaInput.value = data.logradouro || ""; // Preenche a rua ou define vazio
                        complementoInput.value = data.complemento || ""; // Preenche o complemento ou define vazio
                    } else {
                        // Caso o CEP não seja encontrado na API, exibe alerta
                        alert("CEP não encontrado.");
                    }
                })
                .catch(error => {
                    // Captura erros de requisição e exibe no console
                    console.error("Erro ao buscar o endereço:", error);
                    alert("Ocorreu um erro ao buscar o endereço.");
                });
        } else {
            // Caso o CEP não tenha 8 números, exibe alerta de validação
            alert("Por favor, insira um CEP válido com 8 números.");
        }
    }

    // Adiciona evento de clique no botão de busca, se existir
    if (buscarCepEmpresaBtn) {
        buscarCepEmpresaBtn.addEventListener("click", buscarEndereco);
    }

    // Permite que a busca de endereço seja realizada pressionando a tecla Enter no campo CEP
    cepInput.addEventListener("keypress", function (e) {
        if (e.key === "Enter") {
            e.preventDefault(); // Previne envio de formulário padrão
            buscarEndereco(); // Chama a função de busca de endereço
        }
    });
});
