/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Classe.Oficial;
import DAO.UsuarioDAO;
import WebService.IntimacaoWS;
import com.google.common.hash.Hashing;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CadastroControladora", urlPatterns = {"/CadastroControladora"})
public class CadastroControladora extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CadastroControladora</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CadastroControladora at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        processRequest(request, response);
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
        
        String action = request.getParameter("action");
        if (action.equals("cadastroOficial")) {
            // Verificar se advogado está logado
            HttpSession session = request.getSession(false);
            if ( session == null || session.getAttribute("id") == null || !session.getAttribute("tipo").equals("admin") ) {
                // Envia de volta a home com mensagem de "Não tem acesso".
                response.sendRedirect("home.jsp?erro=1");
                return;
            }
            
            String nome = request.getParameter("nome");
            String cpf = request.getParameter("cpf");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            String senhaRepetir = request.getParameter("senha-repetir");
            
            if (   "".equals(nome)
                || "".equals(cpf)
                || "".equals(email)
                || "".equals(senha)
                || "".equals(senhaRepetir)
                    )
            {
                response.sendRedirect("cadastro_oficial.jsp?erro=1");
                return;
            }
            
            if ( !senha.equals(senhaRepetir) ) {
                response.sendRedirect("cadastro_oficial.jsp?erro=2");
                return;
            }
            
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Boolean emailExists = usuarioDAO.verificarEmail(email);
            
            if (emailExists) {
                response.sendRedirect("cadastro_oficial.jsp?erro=3");
                return;  
            }
            
            Oficial oficial = new Oficial();
            oficial.setNome(nome);
            oficial.setCpf(cpf);
            oficial.setEmail(email);
            
            String senhaSha256 = Hashing.sha256()
            .hashString(senha, StandardCharsets.UTF_8)
            .toString();
            oficial.setSenha(senhaSha256);
            
            if ( usuarioDAO.criarOficial(oficial) == null ) {
                response.sendRedirect("cadastro_oficial.jsp?erro=4");
                return;   
            }
            
            // Cadastro Feito - WEBSERVICE
            // Oficial foi criado, logo notificar por webservice que o oficial foi criado ao SIJOGA
            IntimacaoWS.transmitirOficial(oficial);
            
            // Se chegar aqui, significa que o cadastro funcionou corretamente.
            response.sendRedirect("home.jsp?msg=2");
            return;
            
        } else {
            
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
