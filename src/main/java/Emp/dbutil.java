package Emp;
import java.io.*;
import java.sql.*;

public class dbutil {
 public static Connection con;
 public static Statement st;
 public static PreparedStatement ps;
 public static ResultSet rs;
 public static void connect(String a) {
	 try {
	    String mysqlJDBCDriver = "com.mysql.cj.jdbc.Driver";
	    String url = "jdbc:mysql://localhost:3306/employeemanagement";
	    String user = "root";
	    String pass = "root";
	    Class.forName(mysqlJDBCDriver);
	    con = DriverManager.getConnection(url, user, pass);
	    st=con.createStatement();
	    rs=st.executeQuery(a);
	 }
	 catch(Exception e) {
		 
	 }
 }
}
