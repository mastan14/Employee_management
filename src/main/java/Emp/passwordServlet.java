  package Emp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/passwordServlet")
public class passwordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        // Retrieve form data
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");

        // Assume you have a user session with an attribute "userEmail" that holds the user's email
      //  String userEmail = (String) request.getSession().getAttribute("userEmail");

        // JDBC variables
       /* if(!confirmPassword.equals(confirmPassword)) {
        	out.println("current and confirm password must be same");
        }else {*/
        Connection con = null;

        try {
            String mysqlJDBCDriver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/employeemanagement";
            String user = "root";
            String pass = "root";

            Class.forName(mysqlJDBCDriver);
            con = DriverManager.getConnection(url, user, pass);

            // Check if the current password is correct
            String checkPasswordQuery = "SELECT * FROM login WHERE username=? AND password=?";
            PreparedStatement checkPasswordStatement = con.prepareStatement(checkPasswordQuery);
            checkPasswordStatement.setString(1, email);
            checkPasswordStatement.setString(2, currentPassword);
            ResultSet rs = checkPasswordStatement.executeQuery();

            if (rs.next()) {
                // Current password is correct, proceed to update the password
                String updatePasswordQuery = "UPDATE login SET password=? WHERE username=?";
                PreparedStatement updatePasswordStatement = con.prepareStatement(updatePasswordQuery);
                updatePasswordStatement.setString(1, newPassword);
                updatePasswordStatement.setString(2, email);

                int rowsAffected = updatePasswordStatement.executeUpdate();

                if (rowsAffected > 0) {
                    out.println("Password changed successfully!");
                    response.sendRedirect("employee.html");
                } else {
                    out.println("Failed to change the password.");
                }
            } else {
            	out.println(currentPassword);
                out.println("Current password is incorrect.");
            }
        } catch (Exception e) {
            System.out.println("Connection Failed!" + e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
