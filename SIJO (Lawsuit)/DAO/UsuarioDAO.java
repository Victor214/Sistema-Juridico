/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Classe.Advogado;
import Classe.Juiz;
import Classe.Parte;
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
            
            List<String> tables = new ArrayList<String>();
            tables.add("advogado");
            tables.add("juiz");
            tables.add("partes");
            
            Boolean exists = false;
            for (String table:tables) {
                st = con.prepareStatement("SELECT email FROM " + table + " WHERE email = ?");
                st.setString(1, email);
                rs = st.executeQuery();
                
                if (rs.next()) { // Se cair aqui, significa que existe um email igual, logo não precisa mais verificar
                    exists = true;
                    break;
                }
            }
            
            return exists;    
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }    

    public Boolean criarJuiz(Juiz juiz) {
        Connection con = null;
        PreparedStatement st = null;
        
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("INSERT INTO juiz (nome, cif, email, senha) VALUES (?, ?, ?, ?)");
            st.setString(1, juiz.getNome());
            st.setString(2, juiz.getCif());
            st.setString(3, juiz.getEmail());
            st.setString(4, juiz.getSenha());
            
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }

    public boolean criarAdvogado(Advogado advogado) {
        Connection con = null;
        PreparedStatement st = null;
        
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("INSERT INTO advogado (nome, oab, email, senha) VALUES (?, ?, ?, ?)");
            st.setString(1, advogado.getNome());
            st.setString(2, advogado.getOab());
            st.setString(3, advogado.getEmail());
            st.setString(4, advogado.getSenha());
            
            st.executeUpdate();
            return true;
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
            
            List<String> tables = new ArrayList<String>();
            tables.add("advogado");
            tables.add("juiz");
            tables.add("partes");
            
            for (String table:tables) {
                st = con.prepareStatement("SELECT * FROM " + table + " WHERE email = ? AND senha = ?");
                st.setString(1, email);
                st.setString(2, senhaSha256);
                rs = st.executeQuery();
                
                if (rs.next()) { // Se cair aqui, significa que existe uma conta igual. logo não é necessário verificar mais.
                    
                    switch(table) {
                        case "advogado":
                            Advogado advogado = new Advogado();
                            advogado.setId_adv(rs.getInt("id_adv"));
                            advogado.setNome(rs.getString("nome"));
                            advogado.setOab(rs.getString("oab"));
                            advogado.setEmail(rs.getString("email"));
                            return advogado;
                        case "juiz":
                            Juiz juiz = new Juiz();
                            juiz.setId_juiz(rs.getInt("id_juiz"));
                            juiz.setNome(rs.getString("nome"));
                            juiz.setCif(rs.getString("cif"));
                            juiz.setEmail(rs.getString("email"));
                            return juiz;
                        case "partes":
                            Parte parte = new Parte();
                            parte.setId(rs.getInt("id_part"));
                            parte.setNome(rs.getString("nome"));
                            parte.setCpf(rs.getString("cpf"));
                            parte.setEndereco(rs.getString("endereco"));
                            parte.setNumero(rs.getInt("numero"));
                            parte.setComplemento(rs.getString("complemento"));
                            parte.setEmail(rs.getString("email"));
                            return parte;
                            //break;
                    }
                    
                    
                    break;
                }
            }
            
            // Se chegou aqui, significa que não conseguiu encontrar um par valido de email e senha.
            return null;    
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean criarParte(Parte parte, Integer idAdv) {
        Connection con = null;
        PreparedStatement st = null;
        
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("INSERT INTO partes (nome, cpf, endereco, numero, complemento, email, senha, id_adv) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            st.setString(1, parte.getNome());
            st.setString(2, parte.getCpf());
            st.setString(3, parte.getEndereco());
            st.setInt(4, parte.getNumero());
            st.setString(5, parte.getComplemento());
            st.setString(6, parte.getEmail());
            st.setString(7, parte.getSenha());
            st.setInt(8, idAdv); // Advogado responsável pelo cadastro da parte.
            
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
}
