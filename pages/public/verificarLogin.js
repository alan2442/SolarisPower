
function verificarLogin() {

    // Verifica se o usuário está logado (usando sessionStorage)
    if (sessionStorage.getItem("usuario")) {
        window.location.href = "../../Perfil/PerfilUsuario.html";
    } 
    // Verifica se a empresa está logada
    else if (sessionStorage.getItem("empresa")) {
        window.location.href = "../../Perfil/PerfilEmpresa.html";
    } 
    // Se nenhum dos dois estiver logado, redireciona para a página de login
    else {
        window.location.href = "../../Login/login.html";
    }   
}


function verificarLogin2() {

    // Verifica se o usuário está logado (usando sessionStorage)
    if (sessionStorage.getItem("usuario")) {
        window.location.href = "../../Produtos/carrinho.html";
    } 

}