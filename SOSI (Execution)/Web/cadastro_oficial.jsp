<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>SOSIFOD - Cadastro de Oficial</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet" href="assets/fonts/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
    <link rel="stylesheet" href="assets/css/Navigation-Clean.css">
    <link rel="stylesheet" href="assets/css/Registration-Form-with-Photo.css">
    <link rel="stylesheet" href="assets/css/smoothproducts.css">
    <link rel="stylesheet" href="assets/css/untitled.css">
</head>

<body>
    
    <%-- Redirect : User doesnt have access --%>
    <c:if test="${empty sessionScope.id || sessionScope.tipo != 'admin'}">
       <c:redirect url="home.jsp?erro=1"/>
    </c:if>
    
    <%-- Navbar --%>
    <%@ include file="JSP/navbar.jsp"%>
    
    <div class="register-photo">
        
        <%-- Erro --%>
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
        
        <div class="form-container" style="margin-top: 38px;background-color: rgb(255,255,255);padding-right: 60px;padding-left: 60px;padding-top: 20px;padding-bottom: 20px;">
            <nav class="navbar navbar-light navbar-expand-md navigation-clean">
                <div class="container"><button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
                    <div class="collapse navbar-collapse text-left" id="navcol-1">
                        <ul class="nav navbar-nav text-left ml-auto" style="width: 377px;margin-right: 48%;margin-left: 318px;">
                            <li class="nav-item" role="presentation"><a class="nav-link border rounded-0" href="#" style="background-color: rgba(221,136,131,0.85);color: rgb(45,46,47);">Oficial de Justiça</a></li>
                        </ul>
                    </div>
                </div>
            </nav>
            <h2 class="text-center" style="margin-top: 21px;"><strong>Cadastre </strong>&nbsp;o Oficial de Justiça aqui.</h2>
            <div>
                <form method="POST" action="CadastroControladora?action=cadastroOficial" style="width: 1%;padding: 0px;padding-left: 0px;padding-right: 0px;">
                    <p>Informações Básicas</p>
                    <hr>
                    <input class="form-control" type="text" name="nome" placeholder="Nome Completo" style="margin-bottom: 8px;" minlength="2" maxlength="45" required>
                    <input class="form-control" type="text" name="cpf" placeholder="CPF" style="margin-bottom: 8px;" onkeydown="javascript: fMasc( this, mCPF );" minlength="14" maxlength="14" required>
                    <input class="form-control" type="email" name="email" placeholder="Email" style="margin-bottom: 8px;" minlength="8" maxlength="45" required>
                    <input class="form-control" type="password" name="senha" placeholder="Senha" style="margin-bottom: 8px;" minlength="3" maxlength="18" required>
                    <input class="form-control" type="password" name="senha-repetir" placeholder="Senha (repetir)" style="margin-bottom: 8px;" minlength="3" maxlength="18" required>
                    <button class="btn btn-primary btn-block" type="submit" style="background-color: rgba(9, 162, 255, 0.85);">Cadastrar Oficial</button>
                </form>
            </div>
        </div>
    </div>

    <%-- Footer --%>
    <%@ include file="JSP/footer.jsp"%>
    
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.js"></script>
    <script src="assets/js/smoothproducts.min.js"></script>
    <script src="assets/js/theme.js"></script>
    
    <%-- Mascara do CPF --%>
    <script src="JS/mascaraCPF.js"></script>
</body>

</html>