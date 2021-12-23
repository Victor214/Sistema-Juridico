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
            
            table = "CREATE TABLE IF NOT EXISTS `advogado` ("  
            + "`id_adv` BIGINT NOT NULL AUTO_INCREMENT,"
            + "`nome` VARCHAR(45) NULL," 
            + "`oab` VARCHAR(45) NULL,"
            + "`email` VARCHAR(45) NULL,"
            + "`senha` VARCHAR(64) NULL,"
            + "PRIMARY KEY (`id_adv`))"
            ;
            st = con.prepareStatement(table);
            st.executeUpdate(); 
  
            table = "CREATE TABLE IF NOT EXISTS `juiz` ("
            + "`id_juiz` BIGINT NOT NULL AUTO_INCREMENT,"
            + "`nome` VARCHAR(45) NULL,"
            + "`cif` VARCHAR(45) NULL,"
            + "`email` VARCHAR(45) NULL,"
            + "`senha` VARCHAR(64) NULL,"
            + "PRIMARY KEY (`id_juiz`))"
            ;
            st = con.prepareStatement(table);
            st.executeUpdate(); 
          
            table = "CREATE TABLE IF NOT EXISTS `partes` ("
            + "`id_part` BIGINT NOT NULL AUTO_INCREMENT,"
            + "`id_adv` BIGINT NOT NULL,"
            + "`nome` VARCHAR(45) NULL,"
            + "`cpf` VARCHAR(45) NULL," 
            + "`endereco` VARCHAR(45) NULL," 
            + "`numero` INT NULL,"
            + "`complemento` VARCHAR(45) NULL,"
            + "`email` VARCHAR(45) NULL,"
            + "`senha` VARCHAR(64) NULL," 
            + "PRIMARY KEY (`id_part`),"
            + "FOREIGN KEY (`id_adv`) REFERENCES advogado(`id_adv`))"
            ;
            st = con.prepareStatement(table);
            st.executeUpdate();
            
            table = "CREATE TABLE IF NOT EXISTS `processo` ("
            + "`id_proc` BIGINT NOT NULL AUTO_INCREMENT,"
            + "`id_juiz` BIGINT NULL,"
            + "`datahora` date," 
            + "PRIMARY KEY (`id_proc`),"
            + "INDEX `fk_processo_1_idx` (`id_juiz` ASC),"
            + "CONSTRAINT `fk_processo_1`"
            + "  FOREIGN KEY (`id_juiz`)"
            + "  REFERENCES `sijoga`.`juiz` (`id_juiz`)"
            + "  ON DELETE NO ACTION"
            + "  ON UPDATE NO ACTION)"
            ;
            st = con.prepareStatement(table);
            st.executeUpdate();

            table = "CREATE TABLE IF NOT EXISTS `partproc` ("
            + "`partproc` BIGINT NOT NULL AUTO_INCREMENT,"
            + "`id_part` BIGINT NULL,"
            + "`tipopart` INT NULL,"
            + "`vencedor` INT NULL,"
            + "`id_proc` BIGINT NULL,"
            + "`id_adv` BIGINT NULL,"
            + "PRIMARY KEY (`partproc`),"
            + "INDEX `fk_partproc_1_idx` (`id_part` ASC),"
            + "INDEX `fk_partproc_2_idx` (`id_proc` ASC),"
            + "INDEX `fk_partproc_3_idx` (`id_adv` ASC),"
            + "CONSTRAINT `fk_partproc_1`"
            + "    FOREIGN KEY (`id_part`)"
            + "    REFERENCES `sijoga`.`partes` (`id_part`)" 
            + "    ON DELETE NO ACTION"
            + "    ON UPDATE NO ACTION,"
            + "CONSTRAINT `fk_partproc_2`"
            + "    FOREIGN KEY (`id_proc`)"
            + "    REFERENCES `sijoga`.`processo` (`id_proc`)"
            + "    ON DELETE NO ACTION"
            + "    ON UPDATE NO ACTION,"
            + "  CONSTRAINT `fk_partproc_3`"
            + "    FOREIGN KEY (`id_adv`)"
            + "    REFERENCES `sijoga`.`advogado` (`id_adv`)"
            + "    ON DELETE NO ACTION"
            + "    ON UPDATE NO ACTION)"
            ;
            st = con.prepareStatement(table);
            st.executeUpdate();

            table = "CREATE TABLE IF NOT EXISTS `fase` ("
            + "  `id_fase` BIGINT NOT NULL AUTO_INCREMENT,"
            + "  `tipo` INT NULL,"
            + "  `id_adv` BIGINT NULL,"
            + "  `titulo` VARCHAR(45) NULL,"
            + "  `descricao` VARCHAR(500) NULL,"
            + "  `datahora` DATETIME NULL," 
            + "  `pdf` MEDIUMBLOB NULL,"
            + "  `etapa` VARCHAR(45) NULL,"
            + "  `id_proc` BIGINT NULL,"
            + "  PRIMARY KEY (`id_fase`),"
            + "  INDEX `fk_fase_1_idx` (`id_adv` ASC),"
            + "  INDEX `fk_fase_2_idx` (`id_proc` ASC),"
            + "  CONSTRAINT `fk_fase_1`"
            + "    FOREIGN KEY (`id_adv`)"
            + "    REFERENCES `sijoga`.`advogado` (`id_adv`)"
            + "    ON DELETE NO ACTION"
            + "    ON UPDATE NO ACTION,"
            + "  CONSTRAINT `fk_fase_2`"
            + "    FOREIGN KEY (`id_proc`)"
            + "    REFERENCES `sijoga`.`processo` (`id_proc`)"
            + "    ON DELETE NO ACTION"
            + "    ON UPDATE NO ACTION)"
            ;
            st = con.prepareStatement(table);
            st.executeUpdate();
            
            table = "CREATE TABLE IF NOT EXISTS `oficial` ( " + 
            "  `id_oficial` BIGINT NOT NULL, " +
            "  `nome` VARCHAR(45) NULL, " +
            "  PRIMARY KEY (`id_oficial`))"
            ;
            st = con.prepareStatement(table);
            st.executeUpdate();
            
            table = "CREATE TABLE IF NOT EXISTS `status` ("
            + "  `status` INT NULL,"
            + "  `statusIntimacao` INT NULL," // null se não for aplicavel, 0 para ainda não executada, 1 para executada.
            + "  `justificativa` VARCHAR(500) NULL,"
            + "  `datahora` DATETIME NULL," 
            + "  `id_fase` BIGINT NULL," 
            + "  `id_oficial` BIGINT NULL, "
            + "  `id_intimado` BIGINT NULL, "
            + "  INDEX `fk_status_1_idx` (`id_fase` ASC),"
            + "  CONSTRAINT `fk_status_1`"
            + "    FOREIGN KEY (`id_fase`)"
            + "    REFERENCES `sijoga`.`fase` (`id_fase`),"
            + "  CONSTRAINT `fk_status_2`"
            + "    FOREIGN KEY (`id_oficial`)"
            + "    REFERENCES `sijoga`.`oficial` (`id_oficial`))"
            ;
            st = con.prepareStatement(table); 
            st.executeUpdate();
            

            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
