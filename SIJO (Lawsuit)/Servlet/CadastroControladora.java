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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CadastroControladora", urlPatterns = {"/CadastroControladora"})
public class CadastroControladora extends HttpServlet {

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
        if (action.equals("cadastroJuiz")) {
            String nome = request.getParameter("nome");
            String cif = request.getParameter("cif");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            String senhaRepetir = request.getParameter("senha-repetir");
            String termos = request.getParameter("termos");
            
            // Verificação de Campos
            if (   "".equals(nome)
                || "".equals(cif)
                || "".equals(email)
                || "".equals(senha)
                || "".equals(senhaRepetir)
                || termos == null )
            {
                response.sendRedirect("cadastro.jsp?erro=1");
                return;
            }
            
            if ( !senha.equals(senhaRepetir) ) {
                response.sendRedirect("cadastro.jsp?erro=2");
                return;
            }
            //
            
            // Próxima Etapa - DAO - Banco de Dados
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Boolean emailExists = usuarioDAO.verificarEmail(email);
            
            if (emailExists) {
                response.sendRedirect("cadastro.jsp?erro=3");
                return;  
            }
            
            
            // Verificações concluidas, agora vamos efetuar a criação de conta.
            
            Juiz juiz = new Juiz();
            juiz.setNome(nome);
            juiz.setCif(cif);
            juiz.setEmail(email);
            
            String senhaSha256 = Hashing.sha256()
            .hashString(senha, StandardCharsets.UTF_8)
            .toString();
            juiz.setSenha(senhaSha256);
            
            
            if ( !usuarioDAO.criarJuiz(juiz) ) {
                response.sendRedirect("cadastro.jsp?erro=4");
                return;   
            }
            
            
            // Se chegar aqui, significa que o cadastro funcionou corretamente.
            response.sendRedirect("login.jsp?msg=1");
            return;
        } else if (action.equals("cadastroAdvogado")) {
            
            
            String nome = request.getParameter("nome");
            String oab = request.getParameter("oab");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            String senhaRepetir = request.getParameter("senha-repetir");
            String termos = request.getParameter("termos");
            
            if (   "".equals(nome)
                || "".equals(oab)
                || "".equals(email)
                || "".equals(senha)
                || "".equals(senhaRepetir)
                || termos == null )
            {
                response.sendRedirect("cadastro.jsp?erro=1");
                return;
            }
            
            
            if ( !senha.equals(senhaRepetir) ) {
                response.sendRedirect("cadastro.jsp?erro=2");
                return;
            }
            
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Boolean emailExists = usuarioDAO.verificarEmail(email);
            
            if (emailExists) {
                response.sendRedirect("cadastro.jsp?erro=3");
                return;  
            }
            
            Advogado advogado = new Advogado();
            advogado.setNome(nome);
            advogado.setOab(oab);
            advogado.setEmail(email);
            
            String senhaSha256 = Hashing.sha256()
            .hashString(senha, StandardCharsets.UTF_8)
            .toString();
            advogado.setSenha(senhaSha256);
            
            if ( !usuarioDAO.criarAdvogado(advogado) ) {
                response.sendRedirect("cadastro.jsp?erro=4");
                return;   
            }
            
            // Se chegar aqui, significa que o cadastro funcionou corretamente.
            response.sendRedirect("login.jsp?msg=1");
            return;
         
        } else if (action.equals("cadastroParte")) {
            // Verificar se advogado está logado
            HttpSession session = request.getSession(false);
            if ( session == null || session.getAttribute("id") == null || !session.getAttribute("tipo").equals("advogado") ) {
                // Envia de volta a home com mensagem de "Não tem acesso".
                response.sendRedirect("home.jsp?erro=1");
                return;
            }
            
            String nome = request.getParameter("nome");
            String cpf = request.getParameter("cpf");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            String senhaRepetir = request.getParameter("senha-repetir");
            String endereco = request.getParameter("endereco");
            String numeroString = request.getParameter("numero");
            String complemento = request.getParameter("complemento");
            
            if (   "".equals(nome)
                || "".equals(cpf)
                || "".equals(email)
                || "".equals(senha)
                || "".equals(senhaRepetir)
                || "".equals(endereco)
                || "".equals(numeroString)
                || "".equals(complemento)
                    )
            {
                response.sendRedirect("cadastro_parte.jsp?erro=1");
                return;
            }
            
            Integer numero = (Integer) Integer.parseInt(numeroString);
            
            if ( !senha.equals(senhaRepetir) ) {
                response.sendRedirect("cadastro_parte.jsp?erro=2");
                return;
            }
            
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Boolean emailExists = usuarioDAO.verificarEmail(email);
            
            if (emailExists) {
                response.sendRedirect("cadastro_parte.jsp?erro=3");
                return;  
            }
            
            Parte parte = new Parte();
            parte.setNome(nome);
            parte.setCpf(cpf);
            parte.setEmail(email);
            parte.setEndereco(endereco);
            parte.setNumero(numero);
            parte.setComplemento(complemento);
            
            String senhaSha256 = Hashing.sha256()
            .hashString(senha, StandardCharsets.UTF_8)
            .toString();
            parte.setSenha(senhaSha256);
            
            if ( !usuarioDAO.criarParte(parte, (Integer) session.getAttribute("id")) ) {
                response.sendRedirect("cadastro_parte.jsp?erro=4");
                return;   
            }
            
            // Se chegar aqui, significa que o cadastro funcionou corretamente.
            response.sendRedirect("home.jsp?msg=3");
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
