
function verificarLogin() {

    // Verifica se o usuário está logado (usando sessionStorage)
    if (sessionStorage.getItem("usuario")) {
        window.location.href = "/perfilUsuario";
    } 
    // Verifica se a empresa está logada
    else if (sessionStorage.getItem("empresa")) {
        window.location.href = "/perfilEmpresa";
    } 
    // Se nenhum dos dois estiver logado, redireciona para a página de login
    else {
        window.location.href = "/loginUsuario";
    }   
}


function verificarLogin2() {

    // Verifica se o usuário está logado (usando sessionStorage)
    if (sessionStorage.getItem("usuario")) {
        window.location.href = "../../Produtos/carrinho.html";
    } 

}