<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>SIJOGA - Home</title>
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
        
    <main class="page landing-page">
        
        <%-- Mensagens --%>
        <c:choose>
            <c:when test="${param.msg == '1'}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    Você foi deslogado com sucesso.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
        </c:choose>
        
        
        <%-- Erros --%>
        <c:choose>
            <c:when test="${param.erro == '1'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Você foi redirecionado de volta a home por não possuir acesso a página desejada.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
        </c:choose>
        
        <section class="clean-block clean-hero" style="background-image:url(&quot;assets/img/tech/law.png&quot;);color:rgba(9, 162, 255, 0.85);">
            <div class="text">
                <h2>Sistema Jurídico Online para Grandes Administrações</h2>
                <p>Uma maneira informatizada e acessível de trazer a justiça para suas mãos.</p><a class="btn btn-outline-light btn-lg" href="cadastro.jsp" role="button">Cadastre-se</a></div>
        </section>
        <section class="clean-block clean-info dark">
            <div class="container">
                <div class="block-heading">
                    <h2 class="text-info">Informações Básicas</h2>
                    <p>Utilize o menu acima para efetuar as funções básicas do sistema, como logar ou cadastrar.</p>
                </div>
                <div class="row align-items-center">
                    <div class="col-md-6"><img class="img-thumbnail" src="assets/img/tech/law2.png"></div>
                    <div class="col-md-6">
                        <h3>Já possui uma conta?</h3>
                        <div class="getting-started-info">
                            <p>Utilize o botão abaixo para entrar com as suas credenciais no sistema, seja como Juíz, Advogado ou Parte.</p>
                        </div><a class="btn btn-outline-primary btn-lg" href="login.jsp" role="button">Entrar</a></div>
                </div>
            </div>
        </section>
        <section class="clean-block features">
            <div class="container">
                <div class="block-heading">
                    <h2 class="text-info">O Sistema</h2>
                    <p>Com tecnologia de ponta aliada ao melhor da justiça do paraná, nossa equipe de TI com o governo do paraná traz um novo sistema para tornar a justiça acessível para todos.</p>
                </div>
                <div class="row justify-content-center">
                    <div class="col-md-5 feature-box"><i class="icon-star icon"></i>
                        <h4>Informatizado</h4>
                        <p>Sistema novo, inteiramente integrado com a plataforma web.</p>
                    </div>
                    <div class="col-md-5 feature-box"><i class="icon-pencil icon"></i>
                        <h4>Aberto</h4>
                        <p>Juízes, Advogados e Partes envolvidas podem se cadastrar livremente para ter acesso aos processos.</p>
                    </div>
                    <div class="col-md-5 feature-box"><i class="icon-screen-smartphone icon"></i>
                        <h4>Acessível</h4>
                        <p>Com um design simples e direto, procura-se tornar o sistema acessível no computador e aparelhos móveis.</p>
                    </div>
                    <div class="col-md-5 feature-box"><i class="icon-refresh icon"></i>
                        <h4>Integrado</h4>
                        <p>Permite total interação e comunicação entre cliente, advogados e juízes, tornando o processo transparente ao longo de todo o andamento.</p>
                    </div>
                </div>
            </div>
        </section>
    </main>
    
    <%-- Footer --%>
    <%@ include file="JSP/footer.jsp"%>
    
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.js"></script>
    <script src="assets/js/smoothproducts.min.js"></script>
    <script src="assets/js/theme.js"></script>
</body>

</html>