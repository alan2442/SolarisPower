// Aguarda o carregamento completo do DOM antes de executar o script
document.addEventListener('DOMContentLoaded', function() {

    // Seleção dos elementos do DOM necessários para a simulação
    const cepInput = document.getElementById('cep'); // Campo de CEP
    const gastoInput = document.getElementById('gasto'); // Campo de gasto de energia
    const buscarCepBtn = document.getElementById('buscar-cep'); // Botão para buscar CEP
    const simularBtn = document.getElementById('simular'); // Botão para iniciar a simulação
    const formulario = document.getElementById('formulario'); // Formulário de entrada de dados
    const resultado = document.getElementById('resultado'); // Área de exibição dos resultados
    const novaSimulacaoBtn = document.getElementById('nova-simulacao'); // Botão para reiniciar a simulação

    // Campos onde os resultados da simulação serão exibidos
    const gastoAtualEl = document.getElementById('gasto-atual');
    const economiaMensalEl = document.getElementById('economia-mensal');
    const economiaAnualEl = document.getElementById('economia-anual');
    const economiaTotalEl = document.getElementById('economia-total');

    // Máscara para o campo de CEP (formato 00000-000)
    cepInput.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, ''); // Remove caracteres não numéricos
        if (value.length > 5) {
            value = value.substring(0, 5) + '-' + value.substring(5, 8); // Adiciona hífen após os 5 primeiros dígitos
        }
        e.target.value = value;
    });

    // Máscara monetária para o campo de gasto
    gastoInput.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, ''); // Remove caracteres não numéricos
        value = (value / 100).toFixed(2) + ''; // Converte em valor decimal
        value = value.replace(".", ","); // Troca ponto por vírgula
        value = value.replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.'); // Adiciona separador de milhar
        e.target.value = 'R$ ' + value; // Formata como moeda brasileira
    });

    // Simulação de busca de CEP (apenas validação básica)
    buscarCepBtn.addEventListener('click', function() {
        if (cepInput.value.length === 9) { // Verifica se o CEP possui o formato correto
            alert('CEP encontrado: ' + cepInput.value);
        } else {
            alert('Por favor, insira um CEP válido com 8 dígitos');
        }
    });

    // Evento para simular a economia de energia
    simularBtn.addEventListener('click', function() {
        const cep = cepInput.value;
        const gasto = gastoInput.value;
        const concessionaria = document.getElementById('concessionaria').value;
        const email = document.getElementById('email').value;

        // Validações dos campos obrigatórios
        if (!cep || cep.length < 9) {
            alert('Por favor, insira um CEP válido');
            return;
        }
        if (!gasto || gasto === 'R$ 0,00') {
            alert('Por favor, informe seu gasto com energia');
            return;
        }
        if (!concessionaria) {
            alert('Por favor, selecione sua concessionária');
            return;
        }
        if (!email || !email.includes('@')) {
            alert('Por favor, insira um e-mail válido');
            return;
        }

        // Conversão do valor monetário para número
        const valorNumerico = parseFloat(gasto.replace('R$ ', '').replace('.', '').replace(',', '.'));

        // Cálculo da economia mensal e anual (exemplo de 30% de economia)
        const economiaMensal = valorNumerico * 0.7;
        const economiaAnual = economiaMensal * 12;

        // Atualiza os elementos do DOM com os resultados da simulação
        gastoAtualEl.textContent = gasto;
        economiaMensalEl.textContent = formatarMoeda(economiaMensal);
        economiaAnualEl.textContent = formatarMoeda(economiaAnual);
        economiaTotalEl.textContent = `Você pode economizar até ${formatarMoeda(economiaAnual)} por ano!`;

        // Oculta o formulário e exibe a área de resultados
        formulario.style.display = 'none';
        resultado.style.display = 'block';
    });

    // Evento para iniciar uma nova simulação, resetando os campos
    novaSimulacaoBtn.addEventListener('click', function() {
        formulario.style.display = 'block';
        resultado.style.display = 'none';
        document.getElementById('email').value = ''; // Limpa o campo de e-mail
    });

    // Função auxiliar para formatar números como moeda brasileira
    function formatarMoeda(valor) {
        return 'R$ ' + valor.toFixed(2).replace('.', ',').replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.');
    }
});
