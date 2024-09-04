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

@WebServlet("/leave")
public class Leave extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Leave() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        // Retrieve form data
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String reason = request.getParameter("reason");
        String email = request.getParameter("email"); // Assuming email is used as a unique identifier

        // JDBC variables
        Connection con = null;

        try {
            // JDBC connection setup
            String mysqlJDBCDriver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/employeemanagement";
            String user = "root";
            String pass = "root";
            Class.forName(mysqlJDBCDriver);

            // Establish the database connection
            con = DriverManager.getConnection(url, user, pass);

            // Insert leave request into the database
            String leaveInsertQuery = "INSERT INTO `leave` (startdate, enddate, reason, email) VALUES (?, ?, ?, ?)";
            PreparedStatement leaveInsertStmt = con.prepareStatement(leaveInsertQuery);
            leaveInsertStmt.setString(1, startDate);
            leaveInsertStmt.setString(2, endDate);
            leaveInsertStmt.setString(3, reason);
            leaveInsertStmt.setString(4, email);

            int rowsAffected = leaveInsertStmt.executeUpdate();

            if (rowsAffected > 0) {
                out.println("Leave request submitted successfully!");
            } else {
                out.println("Failed to submit leave request.");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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
