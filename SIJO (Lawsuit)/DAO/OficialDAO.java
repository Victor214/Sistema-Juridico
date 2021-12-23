/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Classe.Oficial;
import Classe.Processo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OficialDAO {
    
    public Boolean inserirOficial (Oficial oficial) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        con = ConnectionFactory.getConnection();
        try {
            st = con.prepareStatement("SELECT id_oficial FROM oficial WHERE id_oficial = ?");
            st.setInt(1, oficial.getIdOficial());
            rs = st.executeQuery();
            
            if (rs.next()) {
                return false;
            }
            
            st = con.prepareStatement("INSERT INTO oficial (id_oficial, nome) VALUES (?, ?)");
            st.setInt(1, oficial.getIdOficial());
            st.setString(2, oficial.getNome());
            st.executeUpdate();
            
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Oficial> listarOficiais() {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        con = ConnectionFactory.getConnection();
        try {
            st = con.prepareStatement("SELECT id_oficial, nome FROM oficial");
            rs = st.executeQuery();
            
            List<Oficial> oficiais = new ArrayList<>();
            while (rs.next()) {
                Oficial oficial = new Oficial();
                
                oficial.setIdOficial(rs.getInt("id_oficial"));
                oficial.setNome(rs.getString("nome"));
                
                oficiais.add(oficial);
            }
            
            return oficiais;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void executarIntimacao(Processo processo) {
        Connection con = null;
        PreparedStatement st = null;
        
        con = ConnectionFactory.getConnection();
        try {
            st = con.prepareStatement(
            "UPDATE `status` s " +
            "INNER JOIN fase f  ON (f.id_fase = s.id_fase) " +
            "SET statusIntimacao = 1 " +
            "WHERE f.id_proc = ? " +
            "AND s.id_fase = " +
            "( " +
            "	SELECT f2.id_fase " +
            "    FROM fase f2 " +
            "    INNER JOIN processo proc2 ON (proc2.id_proc = f2.id_proc) " +
            "    WHERE f2.id_proc = f.id_proc " +
            "	ORDER BY f2.etapa DESC " +
            "	LIMIT 1 " +
            ")");
            st.setInt(1, processo.getIdProcesso());
            st.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
    }
    
}
