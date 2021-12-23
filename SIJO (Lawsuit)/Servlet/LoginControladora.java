/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Classe.Advogado;
import Classe.Juiz;
import Classe.Parte;
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

                if (usuario.getClass() == Juiz.class) {
                    Juiz juiz = (Juiz)usuario;

                    session.setAttribute("id", juiz.getId_juiz());
                    session.setAttribute("email", juiz.getEmail());
                    session.setAttribute("tipo", "juiz");
                    session.setAttribute("objeto", juiz);
                } else if (usuario.getClass() == Advogado.class) {
                    Advogado advogado = (Advogado)usuario;

                    session.setAttribute("id", advogado.getId_adv());
                    session.setAttribute("email", advogado.getEmail());
                    session.setAttribute("tipo", "advogado");
                    session.setAttribute("objeto", advogado);
                } else if (usuario.getClass() == Parte.class) {
                    Parte parte = (Parte)usuario;

                    session.setAttribute("id", parte.getId());
                    session.setAttribute("email", parte.getEmail());
                    session.setAttribute("tipo", "parte");
                    session.setAttribute("objeto", parte);
                }
                
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
