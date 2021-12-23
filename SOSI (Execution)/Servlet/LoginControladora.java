/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Classe.Admin;
import Classe.Oficial;
import DAO.UsuarioDAO;
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

@WebServlet(name = "LoginControladora", urlPatterns = {"/LoginControladora"})
public class LoginControladora extends HttpServlet {

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
        
        String adminLogin = "admin@admin.com";
        String adminPassword = "admin";
        
        
        String action = request.getParameter("action");
        if (action.equals("login")) {
            
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            
            if (   "".equals(email)
                || "".equals(senha) )
            {
                response.sendRedirect("login.jsp?erro=1");
                return;
            }
            
            if ( adminLogin.equals(email) && adminPassword.equals(senha) ) {
                // Login de administrador
                Admin admin = new Admin();
                admin.setNome("admin");
                
                HttpSession session = request.getSession(true);
                session.setAttribute("id", 1);
                session.setAttribute("email", "admin");
                session.setAttribute("tipo", "admin");
                session.setAttribute("objeto", admin);
                
                response.sendRedirect("home.jsp?msg=1");
                return;
            }
            
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            
            String senhaSha256 = Hashing.sha256()
            .hashString(senha, StandardCharsets.UTF_8)
            .toString();
            
            Object usuario = usuarioDAO.verificarCredenciais(email, senhaSha256);
            
            if (usuario == null) {
                response.sendRedirect("login.jsp?erro=2");
                return;
            } else {
                // Cria-se a sessão, como a autenticação foi correta.
                HttpSession session = request.getSession(true);

                Oficial oficial = (Oficial)usuario;

                session.setAttribute("id", oficial.getIdOficial());
                session.setAttribute("email", oficial.getEmail());
                session.setAttribute("tipo", "oficial");
                session.setAttribute("objeto", oficial);
                
                response.sendRedirect("home.jsp?msg=1");
                return;
            }
        } else if (action.equals("deslogar")) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            
            response.sendRedirect("index.jsp?msg=1");
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
