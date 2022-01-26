<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>SIJOGA - Criar Processo</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet" href="assets/fonts/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
    <link rel="stylesheet" href="assets/css/Navigation-Clean.css">
    <link rel="stylesheet" href="assets/css/Registration-Form-with-Photo.css">
    <link rel="stylesheet" href="assets/css/smoothproducts.css">
</head>

<body>
    <%-- Redirect : User doesnt have access --%>
    <c:if test="${empty sessionScope.id || sessionScope.tipo != 'advogado'}">
       <c:redirect url="home.jsp?erro=1"/>
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
                    Não foi possível criar o processo.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.erro == '3'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Os CPFs não podem ser iguais.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.erro == '4'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Pelo menos um dos CPFs informados são inválidos.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
        </c:choose>
        
        <div class="form-container" style="margin-top: 38px;background-color: rgb(255,255,255);padding-right: 40px;padding-left: 40px;padding-top: 20px;padding-bottom: 20px;">
            <h2 class="text-center" style="margin-top: 21px;"><strong>SIJOGA</strong>&nbsp;- Criar Processo.</h2>
            <div>
                <form method="POST" action="ProcessoControladora?action=criarProcesso" style="width: 1%;padding: 13px;padding-left: 0px;padding-right: 0px;">
                    <input class="form-control" type="text" name="cpf-cliente" placeholder="CPF do Cliente (Promovente)" style="margin-bottom: 9px;" onkeydown="javascript: fMasc( this, mCPF );" minlength="14" maxlength="14" required>
                    <input class="form-control" type="text" name="cpf-promovido" placeholder="CPF da Promovido" onkeydown="javascript: fMasc( this, mCPF );" minlength="14" maxlength="14" required>
                    <button class="btn btn-primary btn-block" type="submit" style="background-color: rgba(9, 162, 255, 0.85);">Criar Processo</button>
                </form>
            </div>
        </div>
    </div>

    <%-- Footer --%>
    <%@ include file="JSP/footer.jsp"%>
    
    <%-- Mascara do CPF --%>
    <script src="JS/mascaraCPF.js"></script>
    
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.js"></script>
    <script src="assets/js/smoothproducts.min.js"></script>
    <script src="assets/js/theme.js"></script>
</body>

</html>