/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Classe.Oficial;
import Classe.ParteIntimacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IntimacaoDAO {
    
    public Boolean inserirIntimacao (ParteIntimacao parteIntimacao) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        con = ConnectionFactory.getConnection();
        try {
            st = con.prepareStatement("INSERT INTO intimacao (id_part, nome_part, cpf_part, end_part, numero_part, complemento_part, datahora, status, id_proc, id_oficial) VALUES (?, ?, ?, ?, ?, ?, (SELECT NOW()), 0, ?, ?)");
            
            st.setInt(1, parteIntimacao.getId());
            st.setString(2, parteIntimacao.getNome());
            st.setString(3, parteIntimacao.getCpf());
            st.setString(4, parteIntimacao.getEndereco());
            st.setInt(5, parteIntimacao.getNumero());
            st.setString(6, parteIntimacao.getComplemento());
            st.setInt(7, parteIntimacao.getIdProcesso());
            st.setInt(8, parteIntimacao.getIdOficial());
            st.executeUpdate();
            
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<HashMap> receberIntimacoesAdmin(Integer idUsuario) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT id_intm, id_part, nome_part, cpf_part, end_part, numero_part, complemento_part, datahora, datahoraex, status, id_proc, ofc.nome "
            + "FROM intimacao intm "
            + "INNER JOIN oficial ofc ON (intm.id_oficial = ofc.id_oficial)");
            rs = st.executeQuery();
            
            List<HashMap> lista = new ArrayList<>();
            
            while (rs.next()) {
                HashMap intimacao = new HashMap();
                
                intimacao.put("id_intm", rs.getInt("id_intm"));
                intimacao.put("id_part", rs.getInt("id_part"));
                intimacao.put("nome_part", rs.getString("nome_part"));
                intimacao.put("cpf_part", rs.getString("cpf_part"));
                intimacao.put("end_part", rs.getString("end_part"));
                intimacao.put("numero_part", rs.getInt("numero_part"));
                intimacao.put("complemento_part", rs.getString("complemento_part"));
                intimacao.put("datahora", new SimpleDateFormat("dd-MM-yyyy").format(rs.getDate("datahora")));
                
                System.out.println("Retorno datahoraex : " + rs.getString("datahoraex"));
                
                if ( rs.getString("datahoraex") != null &&  !"".equals(rs.getString("datahoraex"))) { 
                    // Data e Hora de Execução pode não existir. Logo, é bom fazer essa verificação antes de transformar em data
                    intimacao.put("datahoraex",  new SimpleDateFormat("dd-MM-yyyy").format(rs.getDate("datahoraex")));
                }
                
                intimacao.put("status", rs.getInt("status"));
                intimacao.put("id_proc", rs.getInt("id_proc"));
                intimacao.put("nome_oficial", rs.getString("ofc.nome"));
                
                lista.add(intimacao);
            }
            
            return lista;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }

    public List<HashMap> receberIntimacoesOficial(Integer idUsuario) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT id_intm, id_part, nome_part, cpf_part, end_part, numero_part, complemento_part, datahora, datahoraex, status, id_proc, id_oficial "
                    + "FROM intimacao intm "
                    + "WHERE id_oficial = ?");
            st.setInt(1, idUsuario);
            rs = st.executeQuery();
            
            List<HashMap> lista = new ArrayList<>();
            
            while (rs.next()) {
                HashMap intimacao = new HashMap();
                
                intimacao.put("id_intm", rs.getInt("id_intm"));
                intimacao.put("id_part", rs.getInt("id_part"));
                intimacao.put("nome_part", rs.getString("nome_part"));
                intimacao.put("cpf_part", rs.getString("cpf_part"));
                intimacao.put("end_part", rs.getString("end_part"));
                intimacao.put("numero_part", rs.getInt("numero_part"));
                intimacao.put("complemento_part", rs.getString("complemento_part"));
                intimacao.put("datahora", new SimpleDateFormat("dd-MM-yyyy").format(rs.getDate("datahora")));
                
                System.out.println("Retorno datahoraex : " + rs.getString("datahoraex"));
                
                if ( rs.getString("datahoraex") != null &&  !"".equals(rs.getString("datahoraex"))) { 
                    // Data e Hora de Execução pode não existir. Logo, é bom fazer essa verificação antes de transformar em data
                    intimacao.put("datahoraex",  new SimpleDateFormat("dd-MM-yyyy").format(rs.getDate("datahoraex")));
                }
                intimacao.put("status", rs.getInt("status"));
                intimacao.put("id_proc", rs.getInt("id_proc"));
                intimacao.put("id_oficial", rs.getInt("id_oficial"));
                
                lista.add(intimacao);
            }
            
            return lista;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void executarIntimacao(Integer idIntm) {
        Connection con = null;
        PreparedStatement st = null;
        
        con = ConnectionFactory.getConnection();
        try {
            st = con.prepareStatement("UPDATE intimacao SET `status` = 1, datahoraex = (SELECT NOW()) WHERE id_intm = ?");
            st.setInt(1, idIntm);
            st.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    
}
