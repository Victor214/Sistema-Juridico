<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>SOSIFOD - Login</title>
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

    <%-- Redirect : Logged in attempting to access index --%>
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
                    Não foi encontrado um login com as credenciais informadas.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
        </c:choose>
        
        <%-- Mensagens --%>
        <c:choose>
            <c:when test="${param.msg == '1'}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    Sua conta foi criada com sucesso.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
        </c:choose>
        
        <div class="form-container" style="margin-top: 38px;background-color: rgb(255,255,255);padding-right: 60px;padding-left: 60px;padding-top: 20px;padding-bottom: 20px;">
            <h2 class="text-center" style="margin-top: 21px;"><strong>Faça</strong> seu login aqui.</h2>
            <div>
                <form method="POST" action="LoginControladora?action=login" style="width: 1%;padding: 13px;padding-left: 0px;padding-right: 0px;">
                    <input class="form-control" type="email" name="email" placeholder="Email" style="margin-bottom: 8px;" minlength="8" maxlength="45">
                    <input class="form-control" type="password" name="senha" placeholder="Senha" style="margin-bottom: 8px;" minlength="3" maxlength="18">
                    <button class="btn btn-primary btn-block" type="submit" style="background-color: rgba(221,136,131,0.85);">Login</button>
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
</body>

</html>