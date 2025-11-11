document.addEventListener('DOMContentLoaded', function() {
    const cepInput = document.getElementById('cep');
    const gastoInput = document.getElementById('gasto');
    const buscarCepBtn = document.getElementById('buscar-cep');
    const simularBtn = document.getElementById('simular');
    const formulario = document.getElementById('formulario');
    const resultado = document.getElementById('resultado');
    const novaSimulacaoBtn = document.getElementById('nova-simulacao');
    
    const gastoAtualEl = document.getElementById('gasto-atual');
    const economiaMensalEl = document.getElementById('economia-mensal');
    const economiaAnualEl = document.getElementById('economia-anual');
    const economiaTotalEl = document.getElementById('economia-total');
    
    // Máscara CEP
    cepInput.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');
        if (value.length > 5) {
            value = value.substring(0, 5) + '-' + value.substring(5, 8);
        }
        e.target.value = value;
    });
    
    // Máscara monetária
    gastoInput.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');
        value = (value / 100).toFixed(2) + '';
        value = value.replace(".", ",");
        value = value.replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.');
        e.target.value = 'R$ ' + value;
    });
    
    // Simulação de busca de CEP
    buscarCepBtn.addEventListener('click', function() {
        if (cepInput.value.length === 9) {
            alert('CEP encontrado: ' + cepInput.value);
        } else {
            alert('Por favor, insira um CEP válido com 8 dígitos');
        }
    });
    
    // Simular economia
    simularBtn.addEventListener('click', function() {
        const cep = cepInput.value;
        const gasto = gastoInput.value;
        const concessionaria = document.getElementById('concessionaria').value;
        const email = document.getElementById('email').value;
        
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
        
        const valorNumerico = parseFloat(gasto.replace('R$ ', '').replace('.', '').replace(',', '.'));
        const economiaMensal = valorNumerico * 0.7;
        const economiaAnual = economiaMensal * 12;
        
        gastoAtualEl.textContent = gasto;
        economiaMensalEl.textContent = formatarMoeda(economiaMensal);
        economiaAnualEl.textContent = formatarMoeda(economiaAnual);
        economiaTotalEl.textContent = `Você pode economizar até ${formatarMoeda(economiaAnual)} por ano!`;
        
        formulario.style.display = 'none';
        resultado.style.display = 'block';
    });
    
    // Nova simulação
    novaSimulacaoBtn.addEventListener('click', function() {
        formulario.style.display = 'block';
        resultado.style.display = 'none';
        document.getElementById('email').value = '';
    });
    
    function formatarMoeda(valor) {
        return 'R$ ' + valor.toFixed(2).replace('.', ',').replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.');
    }
});
