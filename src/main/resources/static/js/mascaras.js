function mascaraCEP(campo) {
  campo.value = campo.value
    .replace(/\D/g, '')          // Remove tudo o que não for número
    .replace(/^(\d{5})(\d)/, '$1-$2'); // Adiciona o hífen
}



function mascaraCNPJ(campo) {
  // Remove tudo o que não for número
  campo.value = campo.value.replace(/\D/g, "")
    // Adiciona o primeiro ponto
    .replace(/^(\d{2})(\d)/, "$1.$2")
    // Adiciona o segundo ponto
    .replace(/^(\d{2})\.(\d{3})(\d)/, "$1.$2.$3")
    // Adiciona a barra
    .replace(/^(\d{2})\.(\d{3})\.(\d{3})(\d)/, "$1.$2.$3/$4")
    // Adiciona o traço
    .replace(/^(\d{2})\.(\d{3})\.(\d{3})\/(\d{4})(\d)/, "$1.$2.$3/$4-$5");
}



function mascaraDataNascimento(campo) {
  // Remove tudo o que não for número
  campo.value = campo.value.replace(/\D/g, "")
    // Adiciona a barra entre o dia e o mês
    .replace(/(\d{2})(\d)/, "$1/$2")
    // Adiciona a barra entre o mês e o ano
    .replace(/(\d{2})(\d{2})$/, "$1/$2");
}


