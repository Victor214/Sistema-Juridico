/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Classe.Oficial;
import Classe.Processo;
import DAO.OficialDAO;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Victor
 */

// Recebe pedido de intimacao - web service
@Path("intimacao")
public class OficialResource {
    
    @POST
    @Path("inserirOficial")
    @Consumes(MediaType.APPLICATION_JSON)
    public Oficial inserirOficial(Oficial oficial) {
        OficialDAO oficialDAO = new OficialDAO();
        // Insere oficial recebido do cliente
        oficialDAO.inserirOficial(oficial);
        return oficial;
    }
    
    @POST
    @Path("executarIntimacao")
    @Consumes(MediaType.APPLICATION_JSON)
    public Processo executarIntimacao(Processo processo) {
        System.out.println("Intimação 2!");
        
        OficialDAO oficialDAO = new OficialDAO();
        oficialDAO.executarIntimacao(processo);
        return processo;
    }
}
