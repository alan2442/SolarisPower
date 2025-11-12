
document.addEventListener("DOMContentLoaded", function () {
    // Campos do formulário
    const cepInput = document.getElementById("CepEmpresa");
    const ruaInput = document.getElementById("RuaEmpresa");
    const complementoInput = document.getElementById("ComplementoEmpresa");
    const buscarCepEmpresaBtn = document.getElementById("buscarCepEmpresaBtn");

    // Função de busca de endereço via API ViaCEP
    function buscarEndereco() {
        const cep = cepInput.value.replace(/\D/g, ''); // Remove caracteres não numéricos

        if (cep.length === 8) {
            fetch(`https://viacep.com.br/ws/${cep}/json/`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Erro ao consultar o CEP");
                    }
                    return response.json();
                })
                .then(data => {
                    if (!data.erro) {
                        ruaInput.value = data.logradouro || "";
                        complementoInput.value = data.complemento || "";
                    } else {
                        alert("CEP não encontrado.");
                    }
                })
                .catch(error => {
                    console.error("Erro ao buscar o endereço:", error);
                    alert("Ocorreu um erro ao buscar o endereço.");
                });
        } else {
            alert("Por favor, insira um CEP válido com 8 números.");
        }
    }

    // Evento de clique no botão de busca
    if (buscarCepEmpresaBtn) {
        buscarCepEmpresaBtn.addEventListener("click", buscarEndereco);
    }

    // Permitir buscar com Enter no campo CEP
    cepInput.addEventListener("keypress", function (e) {
        if (e.key === "Enter") {
            e.preventDefault();
            buscarEndereco();
        }
    });
});




