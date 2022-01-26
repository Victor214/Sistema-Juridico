<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-light navbar-expand-lg fixed-top bg-white clean-navbar">
    <div class="container">
        
        <%-- Logo Navbar --%>
        <c:choose>
            <%-- Deslogado --%>  
            <c:when test="${empty sessionScope.id}">
                <a class="navbar-brand logo" href="index.jsp">SIJOGA</a>
            </c:when>

            <%-- Logado --%>    
            <c:otherwise>
                <a class="navbar-brand logo" href="home.jsp">SIJOGA</a>
            </c:otherwise>
        </c:choose>

        <button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="navbar-toggler-icon"></span>
        </button>
            
        <div class="collapse navbar-collapse"
            id="navcol-1">
            <ul class="nav navbar-nav ml-auto">
                
                <%-- Links Navbar --%>
                <c:choose>
                    <c:when test="${empty sessionScope.id}">
                         <%-- Deslogado --%>  
                        <li class="nav-item" role="presentation"><a class="nav-link active" href="index.jsp">Home</a></li>
                        <li class="nav-item" role="presentation"><a class="nav-link" href="login.jsp">Login</a></li>
                        <li class="nav-item" role="presentation"><a class="nav-link" href="cadastro.jsp">Cadastro</a></li>
                    </c:when>
                    <c:otherwise>
                        <%-- Logado --%> 
                        <li class="nav-item" role="presentation"><a class="nav-link active" href="home.jsp">Home</a></li>
                        <c:choose>
                             <c:when test="${sessionScope.tipo == 'juiz'}">
                                 <%-- N/A --%>  
                             </c:when>
                             <c:when test="${sessionScope.tipo == 'advogado'}">
                                 <li class="nav-item" role="presentation"><a class="nav-link" href="criar_processo.jsp">Criar Processo</a></li>
                                 <li class="nav-item" role="presentation"><a class="nav-link" href="cadastro_parte.jsp">Cadastrar Parte</a></li>
                             </c:when>
                             <c:otherwise>
                                 
                             </c:otherwise>
                        </c:choose>
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
