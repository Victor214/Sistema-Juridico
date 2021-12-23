/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Classe.Oficial;
import Classe.Processo;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@WebServlet(name = "IntimacaoWS", urlPatterns = {"/IntimacaoWS"})
public class IntimacaoWS extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */


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

    
    public static void transmitirOficial(Oficial oficial) {
        
        System.out.println("Oficial 1!");

        // Cliente efetua chamada para notificar o SIJOGA que um oficial foi criado.
        
        Client client = ClientBuilder.newClient();
        Response retorno = client.target("http://localhost:8080/SIJOGA/webresources/intimacao/inserirOficial").request(MediaType.APPLICATION_JSON).post(Entity.json(oficial));
        
        System.out.println("Oficial 3!");
    }
    
    public static void executarIntimacao(Processo processo) {
        
        System.out.println("Intimacao 1!");

        Client client = ClientBuilder.newClient();
        Response retorno = client.target("http://localhost:8080/SIJOGA/webresources/intimacao/executarIntimacao").request(MediaType.APPLICATION_JSON).post(Entity.json(processo));
        
        System.out.println("Intimacao 3!");
    }
}
