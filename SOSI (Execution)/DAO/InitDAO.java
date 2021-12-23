/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.TimeZone;

public class InitDAO {
    public static void inicializar () {   
        Connection con = null;
        PreparedStatement st = null;
        String table = null;
        
        TimeZone.setDefault(TimeZone.getTimeZone("BRST"));
        System.out.println("Timezone set");
        
        try {
            con = ConnectionFactory.getConnection();          
            
            table = "CREATE TABLE IF NOT EXISTS `oficial` ( " + 
                    "  `id_oficial` BIGINT NOT NULL AUTO_INCREMENT, " +
                    "  `cpf` VARCHAR(45) NULL, " +
                    "  `nome` VARCHAR(45) NULL, " +
                    "  `email` VARCHAR(45) NULL, " +
                    "  `senha` VARCHAR(64) NULL, " +
                    "  PRIMARY KEY (`id_oficial`))"
            ;
            st = con.prepareStatement(table);
            st.executeUpdate(); 
            
            table = "CREATE TABLE IF NOT EXISTS `intimacao` ( " +
                    "  `id_intm` BIGINT NOT NULL AUTO_INCREMENT, " + 
                    "  `id_part` INT NULL, " +
                    "  `nome_part` VARCHAR(45) NULL, " +
                    "  `cpf_part` VARCHAR(45) NULL, " +
                    "  `end_part` VARCHAR(45) NULL, " +
                    "  `numero_part` VARCHAR(45) NULL, " +
                    "  `complemento_part` VARCHAR(45) NULL, " +
                    "  `datahora` DATETIME NULL, " +
                    "  `datahoraex` DATETIME NULL, " +
                    "  `status` INT NULL, " +
                    "  `id_proc` INT NULL, " +
                    "  `id_oficial` BIGINT NULL, " +
                    "  PRIMARY KEY (`id_intm`), " +
                    "  INDEX `fk_intimacao_1_idx` (`id_oficial` ASC), " +
                    "  CONSTRAINT `fk_intimacao_1` " +
                    "    FOREIGN KEY (`id_oficial`) " +
                    "    REFERENCES `sosifod`.`oficial` (`id_oficial`) " +
                    "    ON DELETE NO ACTION " +
                    "    ON UPDATE NO ACTION) "
            ;
            st = con.prepareStatement(table);
            st.executeUpdate(); 
  

            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
