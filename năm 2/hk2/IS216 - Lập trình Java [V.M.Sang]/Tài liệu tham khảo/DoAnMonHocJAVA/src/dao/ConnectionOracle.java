/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.*;

/**
 *
 * @author Nguyen Minh Nhut
 */
public class ConnectionOracle {
    public static Connection getOracleConnection() throws SQLException,
         ClassNotFoundException {
     String hostName = "localhost";
     String sid = "orcl18c";
     String userName = "system";
     String password = "Admin123";
 
     return getOracleConnection(hostName, sid, userName, password);
 }
 
 public static Connection getOracleConnection(String hostName, String sid,
         String userName, String password) throws ClassNotFoundException,
         SQLException {
 
     // Khai báo class Driver cho DB Oracle
     // Việc này cần thiết với Java 5
     // Java6 tự động tìm kiếm Driver thích hợp.
     // Nếu bạn dùng Java6, thì ko cần dòng này cũng được.
     Class.forName("oracle.jdbc.driver.OracleDriver");
 
     // Cấu trúc URL Connection dành cho Oracle
     // Ví dụ: jdbc:oracle:thin:@localhost:1521:db11g
     String connectionURL = "jdbc:oracle:thin:@" + hostName + ":1521:" + sid;
 
     Connection conn = DriverManager.getConnection(connectionURL, userName,
             password);
     return conn;
 }
}
