<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>SOSIFOD - Home</title>
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

    <%-- Redirect : Not logged in attempting to access home --%>
    <c:if test="${empty sessionScope.id}">
       <c:redirect url="index.jsp"/>
    </c:if>
    
    <%-- Dados --%>
    <jsp:include page="IntimacaoControladora?action=receberIntimacoes"/>
    
    
    <%-- Navbar --%>
    <%@ include file="JSP/navbar.jsp"%>
    
    <div class="register-photo">
        
        <%-- Mensagens --%>
        <c:choose>
            <c:when test="${param.msg == '1'}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    Login efetuado com sucesso.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.msg == '2'}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    Conta da Parte criada com sucesso.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.msg == '3'}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    Intimação executada com sucesso.
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
            <c:when test="${param.erro == '2'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Houve um erro e você foi redirecionado de volta.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
        </c:choose>
        
        <c:choose>
            <c:when test="${sessionScope.tipo == 'admin'}">
                <div class="form-container" style="margin-top: 38px;background-color: #ffffff;">
                    <h2 class="text-center" style="margin-top: 21px;"><strong>Intimações</strong></h2>
                    <div class="table-responsive" style="margin-bottom: 13px;">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Intimação</th>
                                    <th>Processo</th>
                                    <th>Oficial</th>
                                    <th>Intimado</th>
                                    <th>Endereço</th>
                                    <th>Data da Intimação</th>
                                    <th>Status da Intimação</th>
                                    <th>Data de Execução</th>
                                    
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${intimacoes}" var="intimacao">
                                    <tr>
                                        <td>Número <c:out value="${intimacao.id_intm}" /></td>
                                        <td>Número <c:out value="${intimacao.id_proc}" /></td>
                                        <td><c:out value="${intimacao.nome_oficial}" /></td>
                                        <td><c:out value="${intimacao.nome_part}" /></td>
                                        <td><c:out value="${intimacao.end_part}" /><br><c:out value="${intimacao.numero_part}" /><br><c:out value="${intimacao.complemento_part}" /></td>
                                        <td><c:out value="${intimacao.datahora}" /></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${intimacao.status == 1}">
                                                    Executada
                                                </c:when>
                                                <c:otherwise>
                                                    Não Executada
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><c:out value="${intimacao.datahoraex}" /></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="form-container" style="margin-top: 38px;background-color: #ffffff;">
                    <h2 class="text-center" style="margin-top: 21px;"><strong>Intimações</strong></h2>
                    <div class="table-responsive" style="margin-bottom: 13px;">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Intimação</th>
                                    <th>Processo</th>
                                    <th>Intimado</th>
                                    <th>Endereço</th>
                                    <th>Data da Intimação</th>
                                    <th>Status da Intimação</th>
                                    <th>Data de Execução</th>
                                    <th>Mais Informações</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <c:forEach items="${intimacoes}" var="intimacao">
                                        <tr>
                                            <td>Número <c:out value="${intimacao.id_intm}" /></td>
                                            <td>Número <c:out value="${intimacao.id_proc}" /></td>
                                            <td><c:out value="${intimacao.nome_part}" /></td>
                                            <td><c:out value="${intimacao.end_part}" /><br><c:out value="${intimacao.numero_part}" /><br><c:out value="${intimacao.complemento_part}" /></td>
                                            <td><c:out value="${intimacao.datahora}" /></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${intimacao.status == 1}">
                                                        Executada
                                                    </c:when>
                                                    <c:otherwise>
                                                        Não Executada
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty intimacao.datahoraex}">
                                                        <c:out value="${intimacao.datahoraex}" />
                                                    </c:when>
                                                    <c:otherwise>
                                                        N/A
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${intimacao.status == 1}">
                                                        N/A
                                                    </c:when>
                                                    <c:otherwise>
                                                        <form method="POST" action="IntimacaoControladora?action=executarIntimacao">
                                                            <input type="hidden" name="id_intm" value="<c:out value="${intimacao.id_intm}" />">
                                                            <input type="hidden" name="id_proc" value="<c:out value="${intimacao.id_proc}" />">
                                                            <button class="btn btn-primary" type="submit" style="background-color: rgba(221,136,131,0.85);">Executar Intimação</button>
                                                        </form>   
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>                       
            </c:otherwise>
        </c:choose>
        

        
        

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