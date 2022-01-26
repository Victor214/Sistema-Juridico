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
    <%-- Redirect : Not logged in user attempting to access home --%>
    <c:if test="${empty sessionScope.id}">
       <c:redirect url="index.jsp?erro=1"/>
    </c:if>
    
    
    <%-- Dados --%>
    <jsp:include page="ProcessoControladora?action=receberProcessos"/>
    
    
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
                    Processo criado com sucesso. Utilize o menu abaixo para acessar e criar a fase inicial do processo.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.msg == '3'}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    Conta da Parte criada com sucesso.
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
                    Você deve informar as datas corretamente para a criação do relatório.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.erro == '3'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Houve um erro e não foi possível retornar o relatório.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
        </c:choose>
        

        
        <div class="form-container" style="margin-top: 38px;background-color: #ffffff;">
            <h2 class="text-center" style="margin-top: 21px;"><strong>Home</strong></h2>
            <hr>
            <c:if test="${sessionScope.tipo == 'advogado'}">
                <div>
                    <select id='seletorAberto' style="margin-left: 19px;">
                        <optgroup label="Escolha uma opção">
                            <option value="todos" selected>Todos</option>
                            <option value="true">Em Aberto</option>
                            <option value="false">Encerrado</option>
                        </optgroup>
                    </select>

                    <select id='seletorPromovido' style="  margin-left: 19px;">
                        <optgroup label="Escolha uma opção">
                            <option value="todos" selected>Todos</option>
                            <option value="true">Promovente</option>
                            <option value="false">Promovido</option>
                        </optgroup>
                    </select>

                    <select id='seletorGanhou' style="  margin-left: 19px;">
                        <optgroup label="Escolha uma opção">
                            <option value="todos" selected>Todos</option>
                            <option value="1">Vencedor</option>
                            <option value="0">Perdedor</option>
                        </optgroup>
                    </select>

                    <button id='botaoFiltrarProcessos' class="btn btn-primary" type="button" style="margin-left: 27px;">Filtrar</button>
                </div> 
            </c:if>

            
            
            <c:choose>
                 <%-- Juiz --%>
                 <c:when test="${sessionScope.tipo == 'juiz'}">
                    <div class="table-responsive" style="margin-bottom: 13px;">
                       <table class="table">
                           <thead>
                               <tr>
                                   <th>Processo</th>
                                   <th>Partes</th>
                                   <th style="width: 194px;">Mais Informações</th>
                               </tr>
                           </thead>
                           <tbody>
                               
                            <c:forEach items="${processos}" var="processo">
                                <tr <c:if test="${processo.deliberativa == true}">style='background-color: #efb5b5;'</c:if> > 
                                   <td>Número <c:out value="${processo.id_proc}"/></td>
                                   <td>
                                       <c:out value="${processo.nome1}"/>
                                       <br>
                                       <c:out value="${processo.nome2}"/>
                                   </td>
                                   <td class="text-center"><a class="btn btn-primary" href="visualizar_processo.jsp?id=<c:out value="${processo.id_proc}"/>" role="button">Abrir Processo</a></td>
                               </tr>
                            </c:forEach>

                           </tbody>
                       </table>
                   </div>
                 </c:when>
                 
                 <%-- Advogado --%>
                 <c:when test="${sessionScope.tipo == 'advogado'}">
                    <div class="table-responsive" style="margin-bottom: 13px;">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Processo</th>
                                    <th>Partes</th>
                                    <th>Cliente</th>
                                    <th>Teste / Apresentação</th>
                                    <th style="width: 194px;">Mais Informações</th>
                                </tr>
                            </thead>
                            
                            <%-- Usa a variável da lista de processos para montar nossa tabela que lista os processos. --%>
                            <tbody id='tabelaFiltro'>
                            <c:forEach items="${processos}" var="processo">
                                <tr aberta='<c:out value="${processo.aberta}"/>' promovente='<c:out value="${processo.promovente}"/>' vencedor='<c:out value="${processo.vencedor}"/>' <c:if test="${processo.deliberativa == true}">style='background-color: #efb5b5;'</c:if> statAberto='' statPromovido='' statGanhou='' >
                                    <td>Número <c:out value="${processo.id_proc}"/></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${processo.promovente == true}">
                                                <strong>
                                                    <c:out value="${processo.nome1}"/> (Cliente)
                                                </strong>
                                                <br>
                                                    <c:out value="${processo.nome2}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${processo.nome1}"/>
                                                <br>
                                                <strong>
                                                    <c:out value="${processo.nome2}"/> (Cliente)
                                                 </strong>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${processo.promovente == true}">
                                                Promovente
                                            </c:when>
                                            <c:otherwise>
                                                Promovido
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <i>
                                            aberta : <c:out value="${processo.aberta}"/><br>
                                            promovente : <c:out value="${processo.promovente}"/><br>
                                            vencedor : <c:out value="${processo.vencedor}"/><br>
                                        </i>
                                    </td>
                                    <td class="text-center"><a class="btn btn-primary" href="visualizar_processo.jsp?id=<c:out value="${processo.id_proc}"/>" role="button">Abrir Processo</a></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                 </c:when>
                 
                 <%-- Parte --%>
                 <c:otherwise>
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Processo</th>
                                    <th>Partes</th>
                                    <th>Status</th>
                                    <th style="width: 194px;">Mais Informações</th> 
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${processos}" var="processo">
                                <tr>
                                    <td>Número <c:out value="${processo.id_proc}"/></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${processo.promovente == true}">
                                                <strong>
                                                    <c:out value="${processo.nome1}"/>
                                                </strong>
                                                <br>
                                                    <c:out value="${processo.nome2}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${processo.nome1}"/>
                                                <br>
                                                <strong>
                                                    <c:out value="${processo.nome2}"/>
                                                 </strong>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${processo.promovente == true}">
                                                Promovente
                                            </c:when>
                                            <c:otherwise>
                                                Promovido
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-center"><a class="btn btn-primary" href="visualizar_processo.jsp?id=<c:out value="${processo.id_proc}"/>" role="button">Abrir Processo</a></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                 </c:otherwise>
            </c:choose>
            
            



        </div>
        
        
        <c:if test="${sessionScope.tipo != 'juiz'}">
            <br><br><br><br>
            <div class="form-container" style="margin-top: 38px;background-color: #ffffff;">
                <h2 class="text-center" style="margin-top: 21px;"><strong>Relatórios</strong></h2>
                <c:choose>
                    <c:when test="${sessionScope.tipo == 'advogado'}">
                        <div class="row">
                            <div class="col">
                                <form method="POST" action="GerarRelatorio?action=processoData" style="width: 1%;">
                                    <p><strong>Relatório de Processos Abertos por Data</strong></p>
                                    <hr />
                                    <p>Data Inicial</p>
                                    <input class="form-control" type="date" name="data1" />
                                    <p style="margin-top: 21px;">Data Final</p>
                                    <input class="form-control" type="date" name="data2" />
                                    <input type="hidden"  name="idAdv" value="<c:out value="${sessionScope.id}"/>" /> 
                                    <button class="btn btn-primary" type="submit" style="width: 100%;">Baixar Relatório</button>
                                    <hr />
                                </form>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col">
                                <form method="POST" action="GerarRelatorio?action=processoEncerrado" style="width: 1%;">
                                    <p><strong>Relatório de Processos Encerrados</strong></p>
                                    <hr />
                                    <input type="hidden"  name="idAdv" value="<c:out value="${sessionScope.id}"/>" /> 
                                    <button class="btn btn-primary" type="submit" style="width: 100%;">Baixar Relatório</button>
                                    <hr />
                                </form>
                            </div>
                        </div>
                    </c:when>
                    <c:when test="${sessionScope.tipo == 'parte'}">
                        <form method="POST" action="GerarRelatorio?action=processoTodos" style="width: 1%;">
                            <p><strong>Relatório de Todos os Processos</strong></p>
                            <hr />
                            <button class="btn btn-primary" type="submit" style="width: 100%;">Baixar Relatório</button>
                            <hr />
                        </form>
                    </c:when>
                </c:choose>
            </div>
        </c:if>
    </div>
   
    <%-- Footer --%>
    <%@ include file="JSP/footer.jsp"%>
    
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.js"></script>
    <script src="assets/js/smoothproducts.min.js"></script>
    <script src="assets/js/theme.js"></script>
    
    <%-- Filtro de Processos --%>
    <script src="JS/filtrarProcessos.js"></script>
</body>

</html>