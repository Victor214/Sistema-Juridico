/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import DAO.ConnectionFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperRunManager;

@WebServlet(name = "GerarRelatorio", urlPatterns = {"/GerarRelatorio"})
public class GerarRelatorio extends HttpServlet {

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
         try {
             
            String host = "http://" + request.getServerName() + ":" + request.getServerPort();
            String jasper = "";
            
            String action = request.getParameter("action");
            HashMap params = new HashMap(); 
            
            if ("processoData".equals(action)) {
            
                String dataIni = request.getParameter("data1");
                String dataFim = request.getParameter("data2");
                String idAdv = request.getParameter("idAdv");
                
                if ("".equals(dataIni) 
                 || "".equals(dataFim)
                 || "".equals(idAdv)       
                        ) {
                    response.sendRedirect("home.jsp?erro=2");
                    return;
                }
                
                jasper = request.getContextPath() + "/Jasper/processoData.jasper";
                
                params.put("dataIni", new java.sql.Date( new SimpleDateFormat("yyyy-MM-dd").parse(dataIni).getTime() ) );
                params.put("dataFim", new java.sql.Date( new SimpleDateFormat("yyyy-MM-dd").parse(dataFim).getTime() ) );
                params.put("idAdv", idAdv);
                
            } else if ("processoEncerrado".equals(action)) {
                
                String idAdv = request.getParameter("idAdv");
                
                if ( "".equals(idAdv)       
                        ) {
                    response.sendRedirect("home.jsp?erro=3");
                    return;
                }
                
                
                jasper = request.getContextPath() + "/Jasper/processoEncerrado.jasper";
                
                params.put("idAdv", idAdv);
                
            } else if ("processoTodos".equals(action)) {
            
            }
            
            Connection con = ConnectionFactory.getConnection();

            URL jasperURL = new URL(host + jasper);
            byte[] bytes = JasperRunManager.runReportToPdf(jasperURL.openStream(), params, con);

            if (bytes != null) {
                // A página será mostrada em PDF
                response.setContentType("application/pdf");
                // Envia o PDF para o Cliente
                OutputStream ops = response.getOutputStream();
                ops.write(bytes);
            }
            
        } catch (Exception e) {
            throw new RuntimeException(e);
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
