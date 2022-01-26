<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>SIJOGA - Processo</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet" href="assets/fonts/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
    <link rel="stylesheet" href="assets/css/Navigation-Clean.css">
    <link rel="stylesheet" href="assets/css/Registration-Form-with-Photo.css">
    <link rel="stylesheet" href="assets/css/smoothproducts.css">
</head>

<body>
    <%-- Redirect : Not logged in user attempting to access page --%>
    <c:if test="${empty sessionScope.id}">
       <c:redirect url="index.jsp?erro=1"/>
    </c:if>
    
    <%-- Dados --%>
    <jsp:include page="ProcessoControladora?action=visualizarProcesso"/>
    
    <%-- VERIFICAR SE INFORMAÇÃO RETORNADA É NULL, SE FOR, DEVOLVER A HOME POR FALTA DE ACESSO --%>
        <c:if test="${empty processo}">
       <c:redirect url="index.jsp?erro=1"/>
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
                    Esse processo não está aberto para novas fases no momento.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.erro == '3'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Você não tem acesso à essa ação.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.erro == '4'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    É necessário uma justificativa para a ação negado.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.erro == '5'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Ainda não há fases a serem respondidas.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.erro == '6'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Não é permitido responder fases informativas, apenas deliberativas.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.erro == '7'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Você já respondeu essa fase anteriormente.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.erro == '8'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    O vencedor não foi propriamente definido.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.erro == '9'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    O Oficial e Intimado não foram propriamente definidos.
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
                    Fase criada com sucesso.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.msg == '2'}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    Fase respondida com sucesso.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
        </c:choose>
            
        <div class="form-container" style="margin-top: 38px;padding-top: 10px;padding-right: 50px;padding-left: 50px;padding-bottom: 35px;background-color: rgb(255,255,255);">
            <h2 class="text-center" style="margin-top: 21px;"><strong>SIJOGA</strong>&nbsp;- Visualizar Processo</h2>
            <hr>
            <p><strong>Juiz Responsável</strong></p>
            <p><c:out value="${processo.juiz}"/></p>
            <p></p>
            <hr>
            <p><strong>Promovente</strong></p>
            <p>Advogado Responsável : <c:out value="${processo.adv1}"/></p>
            <p>Promovente : <c:out value="${processo.nome1}"/></p>
            <hr>
            <p><strong>Promovido</strong></p>
            <p>Advogado Responsável : <c:out value="${processo.adv2}"/></p>
            <p>Promovido : <c:out value="${processo.nome2}"/></p>
        </div>
        <div class="form-container" style="margin-top: 38px;padding-top: 10px;padding-right: 50px;padding-left: 50px;padding-bottom: 35px;background-color: rgb(255,255,255);">
            <h2 class="text-center" style="margin-top: 21px;">Fases</h2>
            <hr>
            <p>
                <strong>Listagem de Fases</strong>
            </p>
            <select id="seletorDeFases" style="width: 100%;">
                <optgroup label="Fases">
                    <c:forEach items="${processo.fases}" var="fase" varStatus="loop">
                        <option value="<c:out value="${loop.count}"/>"><c:out value="${fase.titulo}"/></option>
                    </c:forEach>
                </optgroup>
            </select>
            <p></p>
            <hr>
            
            <c:forEach items="${processo.fases}" var="fase" varStatus="loop">

                <div class="faseSeletor" id="faseBloco<c:out value="${fase.id_fase}"/>" faseBloco="<c:out value="${loop.count}"/>" idFase="<c:out value="${fase.id_fase}"/>" hidden > <%-- hidden --%>
                    <p><strong>Informações da Fase</strong></p>
                    <p><strong>Título : </strong> <c:out value="${fase.titulo}"/></p>
                    <p><strong>Descrição : </strong> <c:out value="${fase.descricao}"/></p>
                    <p>
                        <strong>Tipo : </strong> 
                        <c:choose>
                            <c:when test="${fase.tipo == 0}">
                                <span>Informativa</span>
                            </c:when>
                            <c:otherwise>
                                <span style="color:red;">Deliberativa</span>
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <p><strong>Data : </strong> <c:out value="${fase.datahora}"/> </p>
                    <p>
                        <strong>Anexo : </strong>
                        <c:choose>
                            <c:when test="${fase.pdf == 1}">
                                <a href="PdfControladora?id=<c:out value="${fase.id_fase}"/>">Clique aqui</a>
                            </c:when>
                            <c:otherwise>
                                N/A
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <hr>
                    <p><strong>Resposta do Juiz</strong></p>
                    <p>
                        <strong>Status : </strong>
                        <c:choose>
                            <c:when test="${fase.status == 1}">
                                Aceito
                            </c:when>
                            <c:when test="${fase.status == 2}">
                                Negado
                            </c:when>
                            <c:when test="${fase.status == 3}">
                                <span style="color:#dba123;">Intimação</span>
                            </c:when>
                            <c:when test="${fase.status == 4}">
                                <span style="color:#e3483d;">Encerramento</span>
                            </c:when>
                            <c:otherwise>
                                N/A
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <p>
                        <strong>Justificativa : </strong>
                        <c:choose>
                            <c:when test="${not empty fase.justificativa}">
                                <c:out value="${fase.justificativa}"/>
                            </c:when>
                            <c:otherwise>
                                N/A
                            </c:otherwise>
                        </c:choose>
                    </p>           
                        <c:choose>
                            <c:when test="${fase.status == 3}">
                                <p>
                                    <strong>Status da Intimação : </strong>
                                    <c:choose>
                                        <c:when test="${fase.statusIntimacao == 0}">
                                            Não executada.
                                        </c:when>
                                        <c:when test="${fase.statusIntimacao == 1}">
                                            Executada.
                                        </c:when>
                                    </c:choose>
                                </p>
                                <p>
                                    <strong>Intimado : </strong>
                                    <c:out value="${fase.intimado}" />
                                </p>
                                <p>
                                    <strong>Oficial Responsável : </strong>
                                    <c:out value="${fase.oficial}" />
                                </p>
                            </c:when>
                            <c:otherwise>
                                
                            </c:otherwise>
                        </c:choose>
                    
                </div>      
            </c:forEach>
                    
            <c:if test="${fn:length(processo.fases) <= 0}">
                Sem fases no momento. Uma fase inicial ainda deve ser criada.
            </c:if>
            
            <%-- Verificar também se a fase é deliberativa, para garantir que não pode ser editada (provavelmente javascript) --%>
            <c:if test="${sessionScope.tipo == 'juiz'}">
                <hr>
                <div class="row">
                    <div class="col" style="width: 100%;">
                        <form method="POST" action="ProcessoControladora?action=responderFase" style="width: 1%;padding-right: 0px;padding-left: 0px;">
                            <p style="margin-bottom: 7px;"><strong>Ação</strong></p>
                            <select id="selectAcao" name="acao" style="width: 100%;margin-bottom: 10px;">
                                <optgroup label="Escolha uma ação">
                                    <option value="1" selected>
                                        Aceitar
                                    </option>
                                    <option value="2">
                                        Negar
                                    </option>
                                    <option value="3">
                                        Gerar Intimação
                                    </option>
                                    <option value="4">
                                        Encerrar
                                    </option>
                                </optgroup>
                            </select>

                            <div id="divJustificativa" hidden>
                                <p style="margin-top: 10px;">
                                    <strong>Justificativa</strong>
                                </p>
                                <textarea name="desc" class="form-control" rows="7" style="height: 80px; margin-bottom: 8px;" minlength="3" maxlength="500" ></textarea>
                            </div>
                            
                            <div id="divVencedor" hidden>
                                <p style="margin-bottom: 7px;"><strong>Vencedor</strong></p>
                                <select id='seletorVencedor' name="vencedor" style="width: 100%;margin-bottom: 10px;">
                                    <optgroup label="Escolha a parte vencedora">
                                        <option value="<c:out value="${processo.pp1}"/>" selected>
                                            <c:out value="${processo.nome1}"/>
                                        </option>
                                        <option value="<c:out value="${processo.pp2}"/>">
                                            <c:out value="${processo.nome2}"/>
                                        </option>
                                    </optgroup>
                                </select>
                            </div>
                                   
                            <div id="divIntimacao" hidden>
                                <p style="margin-bottom: 7px;"><strong>Oficial da Intimação</strong></p>
                                <select id='seletorOficial' name="oficial" style="width: 100%;margin-bottom: 10px;">
                                    <optgroup label="Escolha o oficial que realizará a intimação.">
                                        <c:forEach items="${oficiais}" var="oficial">
                                            <option value="<c:out value="${oficial.idOficial}"/>" selected>
                                                <c:out value="${oficial.nome}"/>
                                            </option>
                                        </c:forEach>
                                    </optgroup>
                                </select>
                                
                                <p style="margin-bottom: 7px;"><strong>Intimado</strong></p>
                                <select id='seletorIntimado' name="intimado" style="width: 100%;margin-bottom: 10px;">
                                    <optgroup label="Escolha a parte a ser intimada no processo.">
  
                                        <option value="<c:out value="${processo.id1}"/>" selected>
                                            <c:out value="${processo.nome1}"/>
                                        </option>
                                        
                                        <option value="<c:out value="${processo.id2}"/>" selected>
                                            <c:out value="${processo.nome2}"/>
                                        </option>
 
                                    </optgroup>
                                </select>
                            </div>
                                    
                            <%-- Usado para armazenar o partproc do perdedor, facilita futuramente. --%>
                            <input id='perdedor' type='hidden' name='perdedor'>
                            
                            
                            <input type="hidden" name="fase" value="" id="respostaSeletorFase">
                            <input type="hidden" name="processID" value="<c:out value="${processo.id_proc}"/>">
                            
                            <button class="btn btn-primary" type="submit" style="width: 100%;background-color: rgba(9,162,255,0.85);">Confirmar Resposta</button>
                        </form>
                    </div>
                            

                </div>
            </c:if>
        </div>
            
        <c:if test="${sessionScope.tipo == 'advogado'}">  
            <div class="form-container" style="margin-top: 38px;padding-top: 10px;padding-right: 50px;padding-left: 50px;padding-bottom: 35px;background-color: rgb(255,255,255);">
                <h2 class="text-center" style="margin-top: 21px;">Iniciar Nova Fase</h2>
                <hr>
                <p></p>
                <div class="row">
                    <div class="col" style="padding-right: 0px;padding-left: 0px;">
                        <form method="POST" action="ProcessoControladora?action=novaFase" style="width: 1%;padding-right: 20px;padding-left: 20px;" enctype="multipart/form-data">
                            <p style="margin-bottom: 6px;">
                                <strong>Título</strong></p>
                            <input class="form-control" type="text" name="titulo" style="margin-bottom: 8px;height: 35px;" minlength="5" maxlength="30" required>
                            <p style="margin-bottom: 6px;">
                                <strong>Descrição</strong>
                                <br>
                            </p>
                            <textarea class="form-control" rows="7" name="desc" style="height: 80px" minlength="4" maxlength="500" required></textarea>
                            <p style="margin-bottom: 6px;margin-top: 16px;">
                                <strong>Anexo (Opcional)</strong><br>
                            </p>
                            
                            <input type="file" accept=".pdf" name="pdf" class="upload-file" data-max-size="15000000" >
                            
                            <p style="margin-bottom: 6px; margin-top: 16px">
                                <strong>Tipo</strong>
                            </p>
                            <select name="tipo" style="width: 100%;margin-bottom: 10px;">
                                <option value="0" selected>
                                    Informativa
                                </option>
                                <option value="1">
                                    Deliberativa
                                </option>
                            </select>
                            
                            <input type="hidden" name="processID" value="<c:out value="${processo.id_proc}"/>">
                            
                            <button class="btn btn-primary" type="submit" style="width: 100%;background-color: rgba(9,162,255,0.85);">Iniciar Fase</button>
                        </form>
                    </div>
                </div>
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
    
    <%-- Scripts de Funcionalidade --%>
    <script src="JS/seletorFases.js"></script>
    
    <%-- Scripts de Funcionalidade de Ação --%>
    
    <c:if test="${sessionScope.tipo == 'juiz'}">  
        <script src="JS/seletorAcaoJuiz.js"></script>
    </c:if>
    
</body>

</html>