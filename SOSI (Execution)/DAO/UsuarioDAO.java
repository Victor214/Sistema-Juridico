/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Classe.Oficial;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    public boolean verificarEmail(String email) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionFactory.getConnection();
            
            Boolean exists = false;
            st = con.prepareStatement("SELECT email FROM oficial WHERE email = ?");
            st.setString(1, email);
            rs = st.executeQuery();

            if (rs.next()) { // Se cair aqui, significa que existe um email igual, logo não precisa mais verificar
                exists = true;
            }
            
            return exists;    
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }  

    public Object verificarCredenciais(String email, String senhaSha256) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionFactory.getConnection();
            
            st = con.prepareStatement("SELECT * FROM oficial WHERE email = ? AND senha = ?");
            st.setString(1, email);
            st.setString(2, senhaSha256);
            rs = st.executeQuery();

            if (rs.next()) { // Se cair aqui, significa que existe uma conta igual. logo não é necessário verificar mais.
                Oficial oficial = new Oficial();
                oficial.setIdOficial(rs.getInt("id_oficial"));
                oficial.setNome(rs.getString("nome"));
                oficial.setCpf(rs.getString("cpf"));
                oficial.setEmail(rs.getString("email"));
                return oficial;
            }
            
            // Se chegou aqui, significa que não conseguiu encontrar um par valido de email e senha.
            return null;    
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public Oficial criarOficial(Oficial oficial) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("INSERT INTO oficial (nome, cpf, email, senha) VALUES (?, ?, ?, ?)");
            st.setString(1, oficial.getNome());
            st.setString(2, oficial.getCpf());
            st.setString(3, oficial.getEmail());
            st.setString(4, oficial.getSenha());
            
            st.executeUpdate();
            
            st = con.prepareStatement("SELECT LAST_INSERT_ID() AS lastId");
            rs = st.executeQuery();
            rs.next();
            
            oficial.setIdOficial(rs.getInt("lastId"));
            
            return oficial;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }    
}
