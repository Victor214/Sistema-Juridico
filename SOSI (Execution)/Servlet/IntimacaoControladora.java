/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Classe.Processo;
import DAO.IntimacaoDAO;
import WebService.IntimacaoWS;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "IntimacaoControladora", urlPatterns = {"/IntimacaoControladora"})
public class IntimacaoControladora extends HttpServlet {


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
        if (action.equals("receberIntimacoes")) {
            IntimacaoDAO intimacaoDAO = new IntimacaoDAO();
            
            List<HashMap> intimacoes = null;
            Integer idUsuario = (Integer) session.getAttribute("id");
            String tipoUsuario = (String) session.getAttribute("tipo");
            
            switch (tipoUsuario) {
                case "admin":
                    intimacoes = intimacaoDAO.receberIntimacoesAdmin(idUsuario);
                    break;
                case "oficial":
                    intimacoes = intimacaoDAO.receberIntimacoesOficial(idUsuario);
                    break;
            }
            
            request.setAttribute("intimacoes", intimacoes);
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
        if ( session == null || session.getAttribute("id") == null || !session.getAttribute("tipo").equals("oficial") ) {
            // Envia de volta a home com mensagem de "Não tem acesso".
            response.sendRedirect("home.jsp?erro=1");
            return;
        }
        
        String action = request.getParameter("action");
        if ("executarIntimacao".equals(action)) {
            String idProcString = request.getParameter("id_proc");
            String idIntmString = request.getParameter("id_intm");
            if ( "".equals(idProcString)
              || "".equals(idIntmString)
                    ) {
                response.sendRedirect("home.jsp?erro=2");
                return;
            }
            
            Integer idProc = Integer.valueOf(idProcString);
            Integer idIntm = Integer.valueOf(idIntmString);
            
            // Atualizar DAO StatusIntimação
            IntimacaoDAO intimacaoDAO = new IntimacaoDAO();
            intimacaoDAO.executarIntimacao(idIntm);
            
            // Chamar WebService
            Processo processo = new Processo();
            processo.setIdProcesso(idProc);
            IntimacaoWS.executarIntimacao(processo);
            
            response.sendRedirect("home.jsp?msg=3");
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
