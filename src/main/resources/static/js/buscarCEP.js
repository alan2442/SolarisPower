// Aguarda o carregamento completo do DOM antes de executar o script
document.addEventListener("DOMContentLoaded", function () {

    // Obtém os elementos do formulário correspondentes ao CEP, rua e complemento
    const cepInput = document.getElementById("cepPessoa");
    const ruaInput = document.getElementById("ruaPessoa");
    const complementoInput = document.getElementById("complementoPessoa");

    // Adiciona um listener para o evento 'blur' no campo de CEP
    // O evento 'blur' ocorre quando o campo perde o foco
    cepInput.addEventListener("blur", function () {
        // Remove todos os caracteres que não são números do valor do CEP
        const cep = cepInput.value.replace(/\D/g, '');

        // Verifica se o CEP possui exatamente 8 dígitos (formato válido no Brasil)
        if (cep.length === 8) {
            // Realiza requisição à API ViaCEP para obter informações do endereço
            fetch(`https://viacep.com.br/ws/${cep}/json/`)
                .then(response => response.json()) // Converte a resposta da API em JSON
                .then(data => {
                    if (!data.erro) {
                        // Se a API retornar dados válidos, preenche os campos de endereço
                        ruaInput.value = data.logradouro;
                        complementoInput.value = data.complemento || ""; // Caso não haja complemento, define como vazio
                    } else {
                        // Caso a API retorne erro (CEP não encontrado), exibe alerta
                        alert("CEP não encontrado.");
                    }
                })
                .catch(error => {
                    // Captura erros de requisição ou conexão com a API e exibe no console
                    console.error('Erro ao buscar o endereço:', error);
                    alert("Ocorreu um erro ao buscar o endereço.");
                });
        } else {
            // Validação simples para CEPs que não possuem 8 dígitos
            alert("Por favor, insira um CEP válido.");
        }
    });
});
