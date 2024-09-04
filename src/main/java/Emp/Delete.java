package Emp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteEmployee")
public class Delete extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Delete() {
        super();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        // Retrieve form data
        String email = request.getParameter("email");
        String adminPassword = request.getParameter("password");

        // JDBC variables
        Connection con = null;

        try {
            String mysqlJDBCDriver = "com.mysql.cj.jdbc.Driver"; // JDBC driver
            String url = "jdbc:mysql://localhost:3306/employeemanagement"; // MySQL URL
            String user = "root"; // MySQL username
            String pass = "root"; // MySQL password

            Class.forName(mysqlJDBCDriver);
            con = DriverManager.getConnection(url, user, pass);

            // Verify admin password (You should have proper authentication for admin)
            String adminCheckQuery = "SELECT * FROM admin WHERE password = ?";
            PreparedStatement adminCheckStatement = con.prepareStatement(adminCheckQuery);
            adminCheckStatement.setString(1, adminPassword);
            if (!adminCheckStatement.executeQuery().next()) {
                out.println("Invalid admin credentials");
                return; // Stop further processing if admin credentials are invalid
            }

            // Perform employee deletion
            String deleteEmployeeQuery = "DELETE FROM registration WHERE email = ?";
            PreparedStatement deleteStatement = con.prepareStatement(deleteEmployeeQuery);
            deleteStatement.setString(1, email);

            int rowsAffected = deleteStatement.executeUpdate();

            if (rowsAffected > 0) {
                out.println("Employee deleted successfully!");
            } else {
                out.println("Failed to delete employee.");
            }
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
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
