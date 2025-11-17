// Função para aplicar máscara de CEP em um campo de input
function mascaraCEP(campo) {
  campo.value = campo.value
    .replace(/\D/g, '')          // Remove todos os caracteres que não são números
    .replace(/^(\d{5})(\d)/, '$1-$2'); // Insere um hífen após os 5 primeiros dígitos
}

// Função para aplicar máscara de CNPJ em um campo de input
function mascaraCNPJ(campo) {
  campo.value = campo.value
    .replace(/\D/g, "")                       // Remove todos os caracteres que não são números
    .replace(/^(\d{2})(\d)/, "$1.$2")        // Insere o primeiro ponto após os 2 primeiros dígitos
    .replace(/^(\d{2})\.(\d{3})(\d)/, "$1.$2.$3") // Insere o segundo ponto após o terceiro dígito do bloco seguinte
    .replace(/^(\d{2})\.(\d{3})\.(\d{3})(\d)/, "$1.$2.$3/$4") // Insere a barra antes dos 4 últimos dígitos da empresa
    .replace(/^(\d{2})\.(\d{3})\.(\d{3})\/(\d{4})(\d)/, "$1.$2.$3/$4-$5"); // Insere o traço antes do dígito verificador
}

// Função para aplicar máscara de data de nascimento em um campo de input
function mascaraDataNascimento(campo) {
  campo.value = campo.value
    .replace(/\D/g, "")           // Remove todos os caracteres que não são números
    .replace(/(\d{2})(\d)/, "$1/$2")   // Insere a primeira barra entre dia e mês
    .replace(/(\d{2})(\d{2})$/, "$1/$2"); // Insere a segunda barra entre mês e ano
}
