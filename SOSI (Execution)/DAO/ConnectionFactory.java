/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String database = "SOSIFOD";
            return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/" + database + "?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&characterEncoding=utf-8",
            "root", "Piressk8pc");
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
