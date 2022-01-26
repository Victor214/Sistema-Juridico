<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>SIJOGA - Cadastro</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet" href="assets/fonts/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
    <link rel="stylesheet" href="assets/css/Navigation-Clean.css">
    <link rel="stylesheet" href="assets/css/Registration-Form-with-Photo.css">
    <link rel="stylesheet" href="assets/css/smoothproducts.css">
</head>

<body>
    <%-- Redirect : Logged in user attempting to access index --%>
    <c:if test="${not empty sessionScope.id}">
       <c:redirect url="home.jsp"/>
    </c:if>
    
    <%-- Navbar --%>
    <%@ include file="JSP/navbar.jsp"%>
    
    
    <div class="register-photo">
        <%-- Erros --%>
        <c:choose>
            <c:when test="${param.erro == '1'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Um dos campos não foi preenchido.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.erro == '2'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    As senhas fornecidas não são iguais.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.erro == '3'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Esse email já está cadastrado no sistema.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.erro == '4'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Houve um erro e o usuário não pode ser criado.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
        </c:choose>
        
        <div class="form-container" style="margin-top: 38px;background-color: rgb(255,255,255);padding-right: 40px;padding-left: 40px;padding-top: 20px;padding-bottom: 20px;">
            <nav class="navbar navbar-light navbar-expand-md navigation-clean">
                <div class="container"><button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
                    <div class="collapse navbar-collapse text-left" id="navcol-1">
                        <ul class="nav navbar-nav text-left ml-auto" style="width: 377px;margin-right: 48%;margin-left: 318px;">
                            <li class="nav-item" role="presentation"><a class="nav-link border rounded-0" href="#" id="botaoJuiz" style="background-color: rgba(9, 162, 255, 0.85);color: rgb(0,0,0);">Juiz</a></li>
                            <li class="nav-item" role="presentation"><a class="nav-link border rounded-0" href="#" id="botaoAdvogado" style="margin-right: 8px;margin-left: 8px;">Advogado</a></li>
                            <li class="nav-item" role="presentation"><a class="nav-link border rounded-0" href="#" id="botaoParte">Parte</a></li>
                        </ul>
                    </div>
                </div>
            </nav>

            <h2 class="text-center" style="margin-top: 21px;"><strong>Crie</strong> sua conta aqui.</h2>

            <div class="row">
                <div class="col">
                    <div id="divJuiz">
                        <form method="POST" action="CadastroControladora?action=cadastroJuiz" style="width: 1%;padding: 13px;padding-left: 0px;padding-right: 0px;">
                            <input class="form-control" type="text" name="nome" placeholder="Nome Completo" style="margin-bottom: 8px;" minlength="2" maxlength="45" required>
                            <input class="form-control" type="text" name="cif" placeholder="Carteira de Identidade Funcional" style="margin-bottom: 8px;" minlength="7" maxlength="7" required>
                            <input class="form-control" type="email" name="email" placeholder="Email" style="margin-bottom: 8px;" minlength="8" maxlength="45" required>
                            <input class="form-control" type="password" name="senha" placeholder="Senha" style="margin-bottom: 8px;" minlength="3" maxlength="18" required>
                            <input class="form-control" type="password" name="senha-repetir" placeholder="Senha (repetir)" style="margin-bottom: 8px;" minlength="3" maxlength="18" required>
                            <div class="form-check"><label class="form-check-label"><input class="form-check-input" type="checkbox" name="termos">Eu concordo com os termos de uso.</label></div>
                            <button class="btn btn-primary btn-block" type="submit" style="background-color: rgba(9, 162, 255, 0.85);">Cadastrar Juiz</button>
                        </form>
                        <a class="already" href="login.jsp">Já possui uma conta? Entre aqui.</a>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col">
                    <div id="divAdvogado" hidden>
                        <form method="POST" action="CadastroControladora?action=cadastroAdvogado" style="width: 1%;padding: 13px;padding-left: 0px;padding-right: 0px;">
                            <input class="form-control" type="text" name="nome" placeholder="Nome Completo" style="margin-bottom: 8px;" minlength="2" maxlength="45">
                            <input class="form-control" type="text" name="oab" placeholder="Identificação OAB" style="margin-bottom: 8px;" minlength="4" maxlength="8">
                            <input class="form-control" type="email" name="email" placeholder="Email" style="margin-bottom: 8px;" minlength="8" maxlength="45">
                            <input class="form-control" type="password" name="senha" placeholder="Senha" style="margin-bottom: 8px;" minlength="3" maxlength="18">
                            <input class="form-control" type="password" name="senha-repetir" placeholder="Senha (repetir)" style="margin-bottom: 8px;" minlength="3" maxlength="18">
                            <div class="form-check"><label class="form-check-label"><input class="form-check-input" type="checkbox" name="termos">Eu concordo com os termos de uso.</label></div>
                            <button class="btn btn-primary btn-block" type="submit" style="background-color: rgba(9, 162, 255, 0.85);">Cadastrar Advogado</button>
                        </form>
                        <a class="already" href="login.jsp">Já possui uma conta? Entre aqui.</a>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col">
                    <div id="divParte" hidden>
                        <p>O cadastro de partes só é permitido a partir de um advogado representante.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <%-- Footer --%>
    <%@ include file="JSP/footer.jsp"%>
    
    <%-- Scripts da Página / Design --%>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.js"></script>
    <script src="assets/js/smoothproducts.min.js"></script>
    <script src="assets/js/theme.js"></script>
    
    <%-- Scripts de Funcionalidade --%>
    <script src="JS/seletorCadastro.js"></script>
</body>

</html>