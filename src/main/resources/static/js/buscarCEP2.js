document.addEventListener("DOMContentLoaded", function () {
        // Obtém os elementos dos campos de CEP e endereço
        const cepInput = document.getElementById("CepEmpresa");
        const ruaInput = document.getElementById("RuaEmpresa");
        const complementoInput = document.getElementById("ComplementoEmpresa");
        const buscarCepEmpresaBtn = document.getElementById("buscarCepEmpresaBtn");

        // Função para buscar o endereço
        function buscarEndereco() {
            const cep = cepInput.value.replace(/\D/g, ''); // Remove caracteres não numéricos

            if (cep.length === 8) { // Verifica se o CEP tem 8 dígitos
                fetch(`https://viacep.com.br/ws/${cep}/json/`)
                    .then(response => response.json())
                    .then(data => {
                        if (!data.erro) {
                            // Preenche os campos de endereço com os dados recebidos da API
                            ruaInput.value = data.logradouro;
                            complementoInput.value = data.complemento || ""; // Complemento pode ser vazio
                        } else {
                            alert("CEP não encontrado.");
                        }
                    })
                    .catch(error => {
                        console.error('Erro ao buscar o endereço:', error);
                        alert("Ocorreu um erro ao buscar o endereço.");
                    });
            } else {
                alert("Por favor, insira um CEP válido.");
            }
        }

        // Adiciona evento de click ao botão "lupa" para buscar o CEP
        buscarCepEmpresaBtn.addEventListener("click", buscarEndereco);
    });