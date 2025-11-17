// Função responsável por verificar o login do usuário ou empresa
function verificarLogin() {

    // Verifica se existe um usuário logado armazenado na sessionStorage
    if (sessionStorage.getItem("usuario")) {
        // Redireciona o usuário logado para a página de perfil do usuário
        window.location.href = "/perfilUsuario";
    } 
    // Verifica se existe uma empresa logada armazenada na sessionStorage
    else if (sessionStorage.getItem("empresa")) {
        // Redireciona a empresa logada para a página de perfil da empresa
        window.location.href = "/perfilEmpresa";
    } 
    // Caso nenhum usuário ou empresa esteja logado
    else {
        // Redireciona para a página de login de usuário
        window.location.href = "/loginUsuario";
    }   
}

// Função responsável por verificar se o usuário está logado antes de acessar o carrinho
function verificarLogin2() {

    // Verifica se existe um usuário logado armazenado na sessionStorage
    if (sessionStorage.getItem("usuario")) {
        // Redireciona o usuário logado para a página do carrinho de compras
        window.location.href = "../../Produtos/carrinho.html";
    } 

}
