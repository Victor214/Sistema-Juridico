/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Classe.Fase;
import Classe.Parte;
import Classe.ParteIntimacao;
import Classe.Processo;
import Classe.Status;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProcessoDAO {

    public List<Integer> verificarProcesso(String cpf_cliente, String cpf_promovido) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionFactory.getConnection();
            
            List<String> list = new ArrayList<String>();
            list.add(cpf_cliente);
            list.add(cpf_promovido);
            
            List<Integer> listaId = new ArrayList<Integer>();
            
            
            for (String cpf:list) {
                st = con.prepareStatement("SELECT id_part FROM partes WHERE cpf = ?");
                st.setString(1, cpf);
                rs = st.executeQuery();
                if (!rs.next()) {
                    return null;
                } else {
                    listaId.add(rs.getInt("id_part"));
                }
            }
            
            return listaId;
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean criarProcesso(Processo processo, List<Integer> listaId, Integer idAdvPromovente) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try { 
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT j.id_juiz, count(p.id_juiz) " +
                                      "FROM juiz j LEFT JOIN processo p ON (j.id_juiz = p.id_juiz) " +
                                      "GROUP BY j.id_juiz " +
                                      "ORDER BY count(p.id_juiz) ASC " +
                                      "LIMIT 1;");
            rs = st.executeQuery();
            
            if (!rs.next())
                return false;
            
            Integer idJuiz = rs.getInt("id_juiz");
            st = con.prepareStatement("INSERT INTO processo (id_juiz, datahora) VALUES (?, (SELECT NOW()))");
            st.setInt(1, idJuiz);
            st.executeUpdate();
            
            st = con.prepareStatement("SELECT LAST_INSERT_ID();");
            rs = st.executeQuery();
            rs.next();
            Integer idProcesso = rs.getInt(1);
            
            int index = 0; // Define se é promovente ou promovido, 0 = Promovente, 1 = Promovido no campo tipopart
            for (Integer idPart:listaId) {
                st = con.prepareStatement("INSERT INTO partproc (id_part, id_proc, tipopart, id_adv) VALUES (?, ?, ?, ?)");
                st.setInt(1, idPart);
                st.setInt(2, idProcesso);
                st.setInt(3, index);
                
                
                if (index == 0) { // Promovente
                    //  Para o promovente, o advogado é quem cria o processo.
                    st.setInt(4, idAdvPromovente);
                } else if (index == 1) { // Promovido
                    //  Para o promovido, advogado é quem cadastrou a parte.
                    PreparedStatement st2 = con.prepareStatement("SELECT id_adv FROM partes WHERE id_part = ?");
                    st2.setInt(1, idPart);
                    ResultSet rs2 = st2.executeQuery();
                    rs2.next();
                    st.setInt(4, rs2.getInt("id_adv"));
                }
                
                st.executeUpdate();
                
                index++;
            }
            

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<HashMap> receberProcessosJuiz(Integer idJuiz) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        PreparedStatement st2 = null;
        ResultSet rs2 = null;
        
        try { 
            List<HashMap> processos = new ArrayList<>();
            
            
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement(
            "SELECT proc.id_proc, part.nome " +
            "FROM processo proc " +
            "INNER JOIN partproc pp ON (proc.id_proc = pp.id_proc) " +
            "INNER JOIN partes part ON (pp.id_part = part.id_part) " +
            "WHERE proc.id_proc IN " +
            "	( " +
            "       SELECT proc2.id_proc " +
            "       FROM processo proc2 " +
            "       INNER JOIN partproc pp2 ON (proc2.id_proc = pp2.id_proc) " +
            "       WHERE proc2.id_juiz = ? " +
            "	) " +
            "AND pp.tipopart = 0 "
            );
            st.setInt(1, idJuiz);
            rs = st.executeQuery();
            
            st2 = con.prepareStatement(
            "SELECT part.nome " +
            "FROM processo proc " +
            "INNER JOIN partproc pp ON (proc.id_proc = pp.id_proc) " +
            "INNER JOIN partes part ON (pp.id_part = part.id_part) " +
            "WHERE proc.id_proc IN " +
            "	( " +
            "       SELECT proc2.id_proc " +
            "       FROM processo proc2 " +
            "       INNER JOIN partproc pp2 ON (proc2.id_proc = pp2.id_proc) " +
            "       WHERE proc2.id_juiz = ? " +
            "	) " +
            "AND pp.tipopart = 1 "
            );
            st2.setInt(1, idJuiz);
            rs2 = st2.executeQuery();
            
            // Ambos os queries retornam o mesmo numero de colunas, basta junta-los.
            while (rs.next()) {
                rs2.next(); // "Vira a página" da outra query.
                HashMap<String, Object> procHash = new HashMap<>();
                
                // Verifica se a ultima fase se encontra em estado deliberativo.
                PreparedStatement st3 = null;
                ResultSet rs3 = null;
                
                st3 = con.prepareStatement(
                "SELECT f.tipo " +
                "FROM fase f " +
                "WHERE f.id_proc = ? " +
                "AND etapa IS NOT NULL " +
                "ORDER BY etapa desc " +
                "LIMIT 1"
                );
                st3.setInt(1, rs.getInt("id_proc"));
                rs3 = st3.executeQuery();
                
                Boolean deliberativa = false;
                if (rs3.next()) {
                    if (rs3.getInt("f.tipo") == 1) {
                        deliberativa = true;
                    }
                }
                
                procHash.put("deliberativa", deliberativa);
                procHash.put("id_proc", (Integer) rs.getInt("id_proc"));
                procHash.put("nome1", rs.getString("nome"));
                procHash.put("nome2", rs2.getString("nome"));
                
                
                processos.add(procHash);    
            }
            
            return processos;
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<HashMap> receberProcessosAdvogado(Integer idAdv) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        PreparedStatement st2 = null;
        ResultSet rs2 = null;
        
        try { 
            // Lista de tabelas hash. Essa lista armazena os dados de cada processo. 
            // Cada tabela hash são informações de um único processo, 
            // e a lista de tabelas hash são o conjunto de todas as informaçõse de todos os processos disponiveis.
            List<HashMap> processos = new ArrayList<>();
            
            con = ConnectionFactory.getConnection();
            // SQL para receber informações dos Promoventes
            st = con.prepareStatement(
            "SELECT proc.id_proc, pp.id_adv, part.nome, pp.vencedor " +
            "FROM processo proc " +
            "INNER JOIN partproc pp ON (proc.id_proc = pp.id_proc) " +
            "INNER JOIN partes part ON (pp.id_part = part.id_part) " +
            "WHERE proc.id_proc IN " +
            "	( " +
            "	 SELECT proc2.id_proc " +
            "    FROM processo proc2 " +
            "    INNER JOIN partproc pp2 ON (proc2.id_proc = pp2.id_proc) " +
            "    WHERE pp2.id_adv = ? " +
            "	) " +
            "AND pp.tipopart = 0 "
            );
            st.setInt(1, idAdv);
            rs = st.executeQuery();
            
            // SQL para receber informações dos Promovidos
            st2 = con.prepareStatement(
            "SELECT  part.nome " +
            "FROM processo proc " +
            "INNER JOIN partproc pp ON (proc.id_proc = pp.id_proc) " +
            "INNER JOIN partes part ON (pp.id_part = part.id_part) " +
            "WHERE proc.id_proc IN " +
            "	( " +
            "	 SELECT proc2.id_proc " +
            "    FROM processo proc2 " +
            "    INNER JOIN partproc pp2 ON (proc2.id_proc = pp2.id_proc) " +
            "    WHERE pp2.id_adv = ? " +
            "	) " +
            "AND pp.tipopart = 1 "
            );
            st2.setInt(1, idAdv);
            rs2 = st2.executeQuery();
            
            // Ambos os queries retornam o mesmo numero de colunas, basta junta-los.
            while (rs.next()) {
                rs2.next(); // "Vira a página" da outra query.
                
                // Guarda as informações relativas a um UNICO processo.
                HashMap<String, Object> procHash = new HashMap<>();
                
                
                PreparedStatement st3 = null;
                ResultSet rs3 = null;
                
                // Verifica se a ultima fase se encontra em estado deliberativo, assim como verifica se a última está encerrada, para o mecanismo de busca.
                st3 = con.prepareStatement(
                "SELECT f.tipo, s.`status` as statusAberto " +
                "FROM fase f " +
                "INNER JOIN `status` s ON (f.id_fase = s.id_fase) " +
                "WHERE f.id_proc = ? " +
                "AND etapa IS NOT NULL " +
                "ORDER BY etapa desc " +
                "LIMIT 1"
                );
                st3.setInt(1, rs.getInt("id_proc"));
                rs3 = st3.executeQuery();
                
                
                // Verifica com os dados do banco para ver se é deliberativa / aberta, e salva na variável.
                Boolean deliberativa = false;
                Boolean aberta = true;
                if (rs3.next()) {
                    if (rs3.getInt("f.tipo") == 1) {
                        deliberativa = true;
                    }
                    
                    if ( (rs3.getInt("statusAberto") == 4) ) {
                        aberta = false;
                    }
                }
                
                // Verifica se ja existe um vencedor.
                if ( rs.getString("pp.vencedor") != null ) { // Se não retornar nulo para vencedor. Neste caso vencedor indica se o promovente é vencedor.
                    if ( rs.getInt("id_adv") == idAdv && rs.getString("pp.vencedor").equals("1") ) { 
                        // Se for promovente e o valor de vencedor for 1 (vencedor é retirado do query do promovente), significa que venceu.
                        procHash.put("vencedor", 1);
                    } else if ( rs.getInt("id_adv") != idAdv && rs.getString("pp.vencedor").equals("0") ) {
                        // Se for promovido e o valor de vencedor for 0, significa que venceu.
                        procHash.put("vencedor", 1);
                    } else {
                        // Em qualquer outro caso (outros dois restantes), significa que perdeu.
                        procHash.put("vencedor", 0);
                    }
                } else {
                    procHash.put("vencedor", -1);
                }
                
                // Armazena variaveis retiradas do banco na nossa tabela hash.
                procHash.put("aberta",  aberta); // Diferente de 4 significa que está aberto (não encerramento).
                procHash.put("deliberativa", deliberativa);
                procHash.put("id_proc", (Integer) rs.getInt("id_proc"));
                procHash.put("nome1", rs.getString("nome"));
                procHash.put("nome2", rs2.getString("nome"));
                
                // Significa que o advogado é o promovente, logo, o primeiro nome é o cliente do advogado.
                if (rs.getInt("id_adv") == idAdv) {
                    procHash.put("promovente", true);
                } else {
                    procHash.put("promovente", false);
                }
                
                processos.add(procHash);    
            }
            
            return processos;
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<HashMap> receberProcessosParte(Integer idPart) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        PreparedStatement st2 = null;
        ResultSet rs2 = null;
        
        try { 
            List<HashMap> processos = new ArrayList<>();
            
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement(
            "SELECT proc.id_proc, part.id_part, part.nome " +
            "FROM processo proc " +
            "INNER JOIN partproc pp ON (proc.id_proc = pp.id_proc) " +
            "INNER JOIN partes part ON (pp.id_part = part.id_part) " +
            "WHERE proc.id_proc IN " +
            "	( " +
            "	 SELECT proc2.id_proc " +
            "    FROM processo proc2 " +
            "    INNER JOIN partproc pp2 ON (proc2.id_proc = pp2.id_proc) " +
            "    WHERE pp2.id_part = ? " +
            "	) " +
            "AND pp.tipopart = 0 "
            );
            st.setInt(1, idPart);
            rs = st.executeQuery();
            
            st2 = con.prepareStatement(
            "SELECT proc.id_proc, part.id_part, part.nome " +
            "FROM processo proc " +
            "INNER JOIN partproc pp ON (proc.id_proc = pp.id_proc) " +
            "INNER JOIN partes part ON (pp.id_part = part.id_part) " +
            "WHERE proc.id_proc IN " +
            "	( " +
            "	 SELECT proc2.id_proc " +
            "    FROM processo proc2 " +
            "    INNER JOIN partproc pp2 ON (proc2.id_proc = pp2.id_proc) " +
            "    WHERE pp2.id_part = ? " +
            "	) " +
            "AND pp.tipopart = 1 "
            );
            st2.setInt(1, idPart);
            rs2 = st2.executeQuery();
            
            
            
            // Ambos os queries retornam o mesmo numero de colunas, basta junta-los.
            
            
            while ( (rs.next()) ) {
                rs2.next(); // "Vira a página" da outra query.
                
                HashMap<String, Object> procHash = new HashMap<>();
                
                procHash.put("id_proc", (Integer) rs.getInt("id_proc"));
                procHash.put("nome1", rs.getString("nome"));
                procHash.put("nome2", rs2.getString("nome"));
                
               // Significa que a parte é o promovente.
                if (rs.getInt("id_part") == idPart) {
                    procHash.put("promovente", true);
                } else {
                    procHash.put("promovente", false);
                }
                
                
                processos.add(procHash);    
            }
            
            return processos;
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap visualizarProcesso(String tipo, Integer idProc, Integer idUser) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try { 
            HashMap processo = new HashMap();
            
            con = ConnectionFactory.getConnection();
            
            // Verifica se os usuários respectivos possuem acesso ao processo.
            switch (tipo) {
                case "juiz":
                    st = con.prepareStatement("SELECT * FROM processo proc WHERE proc.id_proc = ? AND proc.id_juiz = ?");
                    st.setInt(1, idProc);
                    st.setInt(2, idUser);
                    rs = st.executeQuery();
                    
                    if (!rs.next())
                        return null;
                    break;
                case "advogado":
                    st = con.prepareStatement("SELECT * FROM processo proc INNER JOIN partproc pp ON (proc.id_proc = pp.id_proc) WHERE proc.id_proc = ? AND pp.id_adv = ?");
                    st.setInt(1, idProc);
                    st.setInt(2, idUser);
                    rs = st.executeQuery();
                    
                    if (!rs.next())
                        return null;
                    break;
                case "parte":                
                    st = con.prepareStatement("SELECT * FROM processo proc INNER JOIN partproc pp ON (proc.id_proc = pp.id_proc) WHERE proc.id_proc = ? AND pp.id_part = ?");
                    st.setInt(1, idProc);
                    st.setInt(2, idUser);
                    rs = st.executeQuery();
                    
                    if (!rs.next())
                        return null;
                    break;
            }
            
            // Receber Informação do Processo (promovente)
            st = con.prepareStatement(
            "SELECT proc.id_proc, part.id_part, j.nome, part.nome, a.nome, pp.partproc " +
            "FROM processo proc " +
            "INNER JOIN partproc pp ON (proc.id_proc = pp.id_proc) " +
            "INNER JOIN partes part ON (pp.id_part = part.id_part) " +
            "INNER JOIN juiz j ON (proc.id_juiz = j.id_juiz) " +
            "INNER JOIN advogado a ON (pp.id_adv = a.id_adv) " +
            "WHERE proc.id_proc IN " +
            "	( " +
            "	 SELECT proc2.id_proc " +
            "    FROM processo proc2 " +
            "    INNER JOIN partproc pp2 ON (proc2.id_proc = pp2.id_proc) " +
            "    WHERE pp2.id_proc = ? " +
            "	) " +
            "AND pp.tipopart = 0;"
            );
            st.setInt(1, idProc);
            rs = st.executeQuery();
            
            rs.next();
            
            processo.put("id_proc", rs.getString("proc.id_proc"));
            processo.put("juiz", rs.getString("j.nome"));
            
            processo.put("id1", rs.getString("part.id_part"));
            processo.put("nome1", rs.getString("part.nome"));
            processo.put("adv1", rs.getString("a.nome"));
            processo.put("pp1", rs.getString("pp.partproc"));
            
            
            // Receber Informação do Processo
            st = con.prepareStatement(
            "SELECT part.id_part, part.nome, a.nome, pp.partproc " +
            "FROM processo proc " +
            "INNER JOIN partproc pp ON (proc.id_proc = pp.id_proc) " +
            "INNER JOIN partes part ON (pp.id_part = part.id_part) " +
            "INNER JOIN advogado a ON (pp.id_adv = a.id_adv) " +
            "WHERE proc.id_proc IN " +
            "	( " +
            "	 SELECT proc2.id_proc " +
            "    FROM processo proc2 " +
            "    INNER JOIN partproc pp2 ON (proc2.id_proc = pp2.id_proc) " +
            "    WHERE pp2.id_proc = ? " +
            "	) " +
            "AND pp.tipopart = 1;"
            );
            st.setInt(1, idProc);
            rs = st.executeQuery();
            
            rs.next();
            
            processo.put("id2", rs.getString("part.id_part")); 
            processo.put("nome2", rs.getString("part.nome")); 
            processo.put("adv2", rs.getString("a.nome")); 
            processo.put("pp2", rs.getString("pp.partproc"));
            
            // Receber informações de fases
            st = con.prepareStatement(
            "SELECT fase.id_fase, fase.tipo, fase.titulo, fase.descricao, fase.datahora, (fase.pdf IS NOT NULL) AS pdf, s.`status` AS stat, s.justificativa AS justif, part.nome AS intimado, ofc.nome AS oficial, s.statusIntimacao AS statusIntimacao  " +
            "FROM fase " +
            "INNER JOIN processo p ON (fase.id_proc = p.id_proc) " +
            "INNER JOIN `status` s ON (fase.id_fase = s.id_fase) " +
            "LEFT JOIN oficial ofc ON (s.id_oficial = ofc.id_oficial) " +
            "LEFT JOIN partes part ON (s.id_intimado = part.id_part) " +
            "WHERE fase.id_proc = ? " +
            "ORDER BY fase.etapa DESC"
            ); 
            st.setInt(1, idProc);
            
            rs = st.executeQuery();
            
            List<HashMap> fases = new ArrayList<>();
            
            
            while (rs.next()) {
                
                HashMap<String, String> fase = new HashMap<>();
                fase.put("id_fase", rs.getString("fase.id_fase"));
                fase.put("titulo", rs.getString("fase.titulo"));
                fase.put("descricao", rs.getString("fase.descricao"));
                fase.put("tipo", rs.getString("fase.tipo"));
                fase.put("datahora", new SimpleDateFormat("dd-MM-yyyy").format(rs.getDate("fase.datahora")) );
                fase.put("pdf", rs.getString("pdf"));
                fase.put("status", rs.getString("stat"));
                fase.put("justificativa", rs.getString("justif"));
                fase.put("intimado", rs.getString("intimado"));
                fase.put("oficial", rs.getString("oficial"));
                fase.put("statusIntimacao", rs.getString("statusIntimacao"));
                
                fases.add(fase);
            }
            
            processo.put("fases", fases);
            
            return processo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public boolean processoAberto(Integer processID) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        // Verificar condições de processo aberto.
        try {
            
            con = ConnectionFactory.getConnection();
            
            
            // Verifica se a ultima não é deliberativa sem resposta.
            st = con.prepareStatement(
            "SELECT f.id_fase " +
            "FROM fase f " +
            "INNER JOIN `status` s ON (f.id_fase = s.id_fase) " +
            "WHERE f.id_proc = ? AND f.tipo = 1 AND s.`status` IS NULL " +
            "ORDER BY f.etapa DESC " +
            "LIMIT 1"
            );

            st.setInt(1, processID);
            rs = st.executeQuery();
            
            if (rs.next()) {
                return false;
            }
            
            // Verifica se a ultima fase não recebeu resposta de um encerramento, ou é uma intimação que ainda não foi executada.
            st = con.prepareStatement(
            "SELECT f.id_fase " +
            "FROM fase f " +
            "INNER JOIN `status` s ON (f.id_fase = s.id_fase) " +
            "WHERE f.id_proc = ? AND (s.`status` = 4 OR (s.`status` = 3 AND s.`statusIntimacao` = 0) )" +
            "ORDER BY f.etapa DESC " +
            "LIMIT 1"
            );

            // Verifica se a ultima fase não é do tipo especial "Execução de Intimação" 
            
            st.setInt(1, processID);
            rs = st.executeQuery();
            
            if (rs.next()) {
                return false;
            }
            
            
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }

    public void criarFase(Fase fase) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        
        // Criar nova fase.
        try {
            con = ConnectionFactory.getConnection();
            
            
            // Verifica se já existe uma fase criada. Se existir, pega qual é a etapa da ultima fase;
            st = con.prepareStatement("SELECT MAX(etapa) FROM fase f WHERE f.id_proc = ?");
            st.setInt(1, fase.getIdProc());
            rs = st.executeQuery();
            
            Integer etapa = 1;
            if (rs.next()) {
                etapa = rs.getInt("MAX(etapa)")+1;
            }    
            
            // Insere a nova fase.
            st = con.prepareStatement(
                "INSERT INTO fase (tipo, id_adv, titulo, descricao, datahora, pdf, etapa, id_proc) " +
                "VALUES (?, ?, ?, ?, (SELECT NOW()), ?, ?, ?)"
            );
            st.setInt(1, fase.getTipo());
            st.setInt(2, fase.getIdAdv());
            st.setString(3, fase.getTitulo());
            st.setString(4, fase.getDescricao());
            st.setBinaryStream(5, fase.getFile());
            st.setInt(6, etapa);
            st.setInt(7, fase.getIdProc());
            st.executeUpdate();
            
            st = con.prepareStatement(
            "INSERT INTO status (id_fase) VALUES ( (SELECT LAST_INSERT_ID()) )"
            );
            st.executeUpdate();
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public InputStream getPdfPorID(Integer idFase) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        
        // Criar nova fase.
        try {
            con = ConnectionFactory.getConnection();
            
            st = con.prepareStatement("SELECT f.pdf FROM fase f WHERE f.id_fase = ?");
            st.setInt(1, idFase);
            rs = st.executeQuery();
            
            if (!rs.next())
                return null;
           
            return rs.getBinaryStream("f.pdf");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
               
    }

    public void responderFase(Status status, Integer vencedor, Integer perdedor, Integer oficial, Integer intimado) {
        Connection con = null;
        PreparedStatement st = null;
        
        // Criar nova fase.
        try {
            con = ConnectionFactory.getConnection();
            
            // Entrada de "Status" já foi colocada junto com a criação da fase. Aqui só vamos atualizar para dizer qual foi a resposta do juiz.
            
            if (status.getAcao() == 3) {
                st = con.prepareStatement("UPDATE `status` SET `status` = ?, justificativa = ?, datahora = (SELECT NOW()), id_oficial = ?, id_intimado = ?, statusIntimacao = 0 WHERE id_fase = ?");
                st.setInt(1, status.getAcao());
                st.setString(2, status.getJustificativa());
                st.setInt(3, oficial);
                st.setInt(4, intimado);
                st.setInt(5, status.getIdFase());
            } else {
                st = con.prepareStatement("UPDATE `status` SET `status` = ?, justificativa = ?, datahora = (SELECT NOW()) WHERE id_fase = ?");
                st.setInt(1, status.getAcao());
                st.setString(2, status.getJustificativa());
                st.setInt(3, status.getIdFase());
            }
            
            st.executeUpdate();
           
            
            // Caso se trate de um encerramento
            if (status.getAcao() == 4) {
                st = con.prepareStatement("UPDATE `partproc` SET `vencedor` = 1 WHERE partproc = ?");
                st.setInt(1, vencedor);
                st.executeUpdate();
                
                st = con.prepareStatement("UPDATE `partproc` SET `vencedor` = 0 WHERE partproc = ?");
                st.setInt(1, perdedor);
                st.executeUpdate();
            }
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verificaDeliberativa(Integer idFase) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        
        // Criar nova fase.
        try {
            con = ConnectionFactory.getConnection();
            
            System.out.println("Fase TEST : " + idFase);
            
            st = con.prepareStatement("SELECT * FROM fase f WHERE f.id_fase = ? AND f.tipo = 1");
            st.setInt(1, idFase);
            rs = st.executeQuery();
            return rs.next();
           
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verificaSeJaRespondida(Integer idFase) {
        // Se já foi respondida, retorna false.
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        
        // Criar nova fase.
        try {
            con = ConnectionFactory.getConnection();
            
            st = con.prepareStatement("SELECT * FROM fase f INNER JOIN `status` s ON (f.id_fase = s.id_fase) WHERE f.id_fase = ? AND s.`status` IS NULL");
            st.setInt(1, idFase);
            rs = st.executeQuery();
            return rs.next();
           
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ParteIntimacao receberParte(ParteIntimacao parteIntimacao) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        // Criar nova fase.
        try {
            con = ConnectionFactory.getConnection();
            
            st = con.prepareStatement("SELECT nome, cpf, endereco, numero, complemento FROM partes WHERE id_part = ?");
            st.setInt(1, parteIntimacao.getId());
            rs = st.executeQuery();
            
            rs.next();
            
            parteIntimacao.setNome(rs.getString("nome"));
            parteIntimacao.setCpf(rs.getString("cpf"));
            parteIntimacao.setEndereco(rs.getString("endereco"));
            parteIntimacao.setNumero(rs.getInt("numero"));
            parteIntimacao.setComplemento(rs.getString("complemento"));
            
            return parteIntimacao;
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
