<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-light navbar-expand-lg fixed-top bg-white clean-navbar">
    <div class="container">
        
        <c:choose>
            <%-- Deslogado --%>
            <c:when test="${empty sessionScope.id}">
                <a class="navbar-brand logo" href="index.jsp">SOSIFOD</a>
            </c:when>
            <%-- Logado --%>
            <c:otherwise>
                <a class="navbar-brand logo" href="home.jsp">SOSIFOD</a>
            </c:otherwise>
        </c:choose>
        <button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse"
            id="navcol-1">
            <ul class="nav navbar-nav ml-auto">
                <c:choose>
                    
                    <c:when test="${empty sessionScope.id}">
                            <%-- Deslogado --%>
                        <li class="nav-item" role="presentation"><a class="nav-link active" href="index.jsp">Home</a></li>
                        <li class="nav-item" role="presentation"><a class="nav-link" href="login.jsp">Login</a></li>
                    </c:when>
                    <%-- Logado --%>
                    <c:otherwise>
                        <li class="nav-item" role="presentation"><a class="nav-link" href="home.jsp">Home</a></li>
                        <c:if test="${sessionScope.tipo == 'admin'}">
                            <li class="nav-item" role="presentation"><a class="nav-link" href="cadastro_oficial.jsp">Cadastrar Oficial de Justiça</a></li>
                        </c:if>
                            
                        <a class="nav-link disabled" href="#">Bem vindo, <c:out value="${sessionScope.objeto.nome}"/>.</a>
                        <form action="LoginControladora?action=deslogar" method="POST" style="margin-left: 30px;">
                           <button class="btn btn-outline-dark my-2 my-sm-0" type="submit">Deslogar</button>
                        </form>  
                    </c:otherwise>
                </c:choose>
                

            </ul>
        </div>
    </div>
</nav>