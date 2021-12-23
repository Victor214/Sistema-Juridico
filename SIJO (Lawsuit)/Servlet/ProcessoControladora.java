/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Classe.Fase;
import Classe.Oficial;
import Classe.Parte;
import Classe.ParteIntimacao;
import Classe.Processo;
import Classe.Status;
import DAO.OficialDAO;
import DAO.ProcessoDAO;
import WebService.IntimacaoWS;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet(name = "ProcessoControladora", urlPatterns = {"/ProcessoControladora"})
@MultipartConfig
public class ProcessoControladora extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verificar se está logado
        HttpSession session = request.getSession(false);
        if ( session == null || session.getAttribute("id") == null ) {
            // Envia de volta a home com mensagem de "Não tem acesso".
            response.sendRedirect("home.jsp?erro=1");
            return;
        }
        
        String action = request.getParameter("action");
        // Requisição que recebe a lista de processos para a home.
        if (action.equals("receberProcessos")) {
            ProcessoDAO processoDAO = new ProcessoDAO();
            
            // Ponteiro para uma tabela hash. Não cria a tabela ainda.
            List<HashMap> processos = null;
            Integer idUsuario = (Integer) session.getAttribute("id");
            String tipoUsuario = (String) session.getAttribute("tipo");

            switch (tipoUsuario) {
                case "juiz":
                    processos = processoDAO.receberProcessosJuiz(idUsuario);
                    break;
                case "advogado":
                    processos = processoDAO.receberProcessosAdvogado(idUsuario);
                    break;
                case "parte":
                    processos = processoDAO.receberProcessosParte(idUsuario);
                    break;
            }
            
            request.setAttribute("processos", processos);
        } else if ( action.equals("visualizarProcesso") ) {
            Integer idProc = (Integer) Integer.parseInt(request.getParameter("id"));
            
            ProcessoDAO processoDAO = new ProcessoDAO();
            
            Integer idUsuario = (Integer) session.getAttribute("id");
            String tipoUsuario = (String) session.getAttribute("tipo");
            
            // Retorna informações detalhadas de um único processo.
            HashMap processo = processoDAO.visualizarProcesso(tipoUsuario, idProc, idUsuario);
            
            // Não possui acesso, retornar a home.
            if (processo == null) {
                response.sendRedirect("home.jsp?erro=1");
                return;
            }
            
            OficialDAO oficialDAO = new OficialDAO();
            List<Oficial> oficiais = oficialDAO.listarOficiais();
            
            request.setAttribute("processo", processo);
            request.setAttribute("oficiais", oficiais);
        }
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession(false);
        if ( session == null || session.getAttribute("id") == null ) {
            // Envia de volta a home com mensagem de "Não tem acesso".
            response.sendRedirect("home.jsp?erro=1");
            return;
        }
        
        String action = request.getParameter("action");
        if (action.equals("criarProcesso")) {
            // Verificar se advogado está logado

            if (!session.getAttribute("tipo").equals("advogado")) {
                response.sendRedirect("home.jsp?erro=1");
                return;
            }
            
            
            String cpf_cliente = request.getParameter("cpf-cliente");
            String cpf_promovido = request.getParameter("cpf-promovido");
            
            if (   "".equals(cpf_cliente)
                || "".equals(cpf_promovido) )
            {
                response.sendRedirect("criar_processo.jsp?erro=1");
                return;
            }
            
            if (cpf_cliente.equals(cpf_promovido)) {
                response.sendRedirect("criar_processo.jsp?erro=3");
                return;
            }
            
            
            Processo processo = new Processo();
            processo.setCpfCliente(cpf_cliente);
            processo.setCpfPromovido(cpf_promovido);
            
            ProcessoDAO processoDAO = new ProcessoDAO();
            
            // Verifica se as partes existem
            List<Integer> listaId = processoDAO.verificarProcesso(cpf_cliente, cpf_promovido);
            if ( listaId  == null ) {
                response.sendRedirect("criar_processo.jsp?erro=4");
                return;
            }
            
            
            // Verifica se é possível criar o processo.
            if ( !processoDAO.criarProcesso(processo, listaId, (Integer) session.getAttribute("id") ) ) {
                response.sendRedirect("criar_processo.jsp?erro=2");
                return;   
            }
            
            // Se chegar aqui, significa que o processo foi criado com sucesso.
            response.sendRedirect("home.jsp?msg=2");
            return;
        } else if (action.equals("novaFase")) {
            // Verificar se advogado está logado
            if (!session.getAttribute("tipo").equals("advogado")) {
                response.sendRedirect("home.jsp?erro=1");
                return;
            }
            
            
            // Recebe informações sobre a fase a ser criada do processo.
            Integer idProc = Integer.valueOf(request.getParameter("processID"));
            String titulo = request.getParameter("titulo");
            String desc = request.getParameter("desc");
            Part pdf = request.getPart("pdf");
            Integer tipo = Integer.valueOf(request.getParameter("tipo"));
            
            String url = request.getRequestURL().toString();
            
            
            if (   "".equals(titulo)
                || "".equals(desc) 
                || "".equals(tipo)    
                    )
            {
                response.sendRedirect("visualizar_processo.jsp?id=" + idProc + "&erro=1");
                return;
            }
            
            
            // O usuário tentando enviar possui permissão a esse processo? Se não, cortar.
            
            
            // É possível enviar uma nova fase? Se a última for deliberativa sem resposta, não.
            ProcessoDAO processoDAO = new ProcessoDAO();
            
            if ( !processoDAO.processoAberto(idProc) ) { // Processo não está aberto, seja por qual motivo for.
                response.sendRedirect("visualizar_processo.jsp?id=" + idProc + "&erro=2");
                return;
            }
            
            
            // Bean de nova fase
            Fase fase = new Fase();
            fase.setIdAdv((Integer) session.getAttribute("id"));
            fase.setIdProc(idProc);
            
            fase.setTitulo(titulo);
            fase.setDescricao(desc);
            fase.setTipo(tipo);
            
            // PDF Opcional
            if (pdf != null && pdf.getSize() > 0) {
                
                // Entrada de dados para arquivos
                InputStream input;
                try {
                    input = pdf.getInputStream();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                
                fase.setFileName(Paths.get(pdf.getSubmittedFileName()).getFileName().toString());
                fase.setFile(input);
            }
            
            processoDAO.criarFase(fase);
            
            response.sendRedirect("visualizar_processo.jsp?id=" + idProc + "&msg=1");  
        } else if (action.equals("responderFase")) {
        
            Integer idProc = Integer.valueOf(request.getParameter("processID"));
            
            if (!session.getAttribute("tipo").equals("juiz")) {
                response.sendRedirect("visualizar_processo.jsp?id=" + idProc + "&erro=3");
                return;
            }
            
            String idFaseString = request.getParameter("fase");
            
            if ( "".equals(idFaseString) ) {
                response.sendRedirect("visualizar_processo.jsp?id=" + idProc + "&erro=5");
                return;
            }
            
            
            Integer idFase = Integer.valueOf(idFaseString);
            

            
            // Verifica se é deliberativa antes de responder.
            ProcessoDAO processoDAO = new ProcessoDAO();
            if (!processoDAO.verificaDeliberativa(idFase)) {
                response.sendRedirect("visualizar_processo.jsp?id=" + idProc + "&erro=6");
                return;
            }
            
            // Verifica se fase já foi respondida (retorna false se já foi)
            if (!processoDAO.verificaSeJaRespondida(idFase)) {
                response.sendRedirect("visualizar_processo.jsp?id=" + idProc + "&erro=7");
                return;
            }
            
            
            Integer acao = Integer.valueOf(request.getParameter("acao"));
            String descricao = request.getParameter("desc");
            
            if (acao == null) {
                response.sendRedirect("visualizar_processo.jsp?id=" + idProc + "&erro=1");
                return;
            }
            
            // Pedido negado exige uma justificativa, e nenhuma justificativa foi dada.
            if (acao == 2 && "".equals(descricao)) {
                response.sendRedirect("visualizar_processo.jsp?id=" + idProc + "&erro=4");
                return;
            }
            
            
            // Encerramento não possui vencedor ou perdedor definido corretamente.
            Integer vencedor = Integer.valueOf(request.getParameter("vencedor"));
            Integer perdedor = Integer.valueOf(request.getParameter("perdedor"));
            if (acao == 4 && (vencedor < 1 || perdedor < 1)) {
                response.sendRedirect("visualizar_processo.jsp?id=" + idProc + "&erro=8");
                return;
            }
            
            // Uma intimação exige que o oficial e o intimado sejam preenchidos.
            String oficialString = request.getParameter("oficial");
            String intimadoString = request.getParameter("intimado");

            if (acao == 3 && (oficialString == null || "null".equals(oficialString) ||  "".equals(oficialString) || intimadoString == null || "null".equals(intimadoString) || "".equals(intimadoString) ) ) {
                response.sendRedirect("visualizar_processo.jsp?id=" + idProc + "&erro=9");
                return;
            }
            
            Integer oficial = null; 
            Integer intimado = null; 
            
            if (acao == 3) {
                oficial = Integer.valueOf(oficialString);
                intimado = Integer.valueOf(intimadoString);
            }
            

            
            
            Status status = new Status();
            status.setIdFase(idFase);
            status.setIdProc(idProc);
            status.setAcao(acao);
            status.setJustificativa(descricao);
            
            
            processoDAO.responderFase(status, vencedor, perdedor, oficial, intimado);
            if (acao == 3) {
                // WebService
                // Avisa o SOSIFOD que está acontecendo uma intimação.
                ParteIntimacao parteIntimacao = new ParteIntimacao();
                parteIntimacao.setId(intimado);
                parteIntimacao.setIdOficial(oficial);
                parteIntimacao.setIdProcesso(idProc);
                parteIntimacao = processoDAO.receberParte(parteIntimacao);
                
                IntimacaoWS.transmitirParteIntimacao(parteIntimacao);
            }
            
            response.sendRedirect("visualizar_processo.jsp?id=" + idProc + "&msg=2");
            return;
            
        }  
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
