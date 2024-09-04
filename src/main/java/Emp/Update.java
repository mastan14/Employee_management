package Emp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/update")
public class Update extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Update() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        // Retrieve form data
        String email = request.getParameter("email");
        String designation = request.getParameter("designation");
        String phoneNumber = request.getParameter("phoneNumber");
        String department = request.getParameter("department");
        int teachingExperience = Integer.parseInt(request.getParameter("teachingExperience"));
        int industryExperience = Integer.parseInt(request.getParameter("industryExperience"));

        int totalExperience = teachingExperience + industryExperience;
        out.println(email+" "+designation+" "+phoneNumber+" "+department+" "+teachingExperience+" "+industryExperience);

        // JDBC variables
        Connection con = null;

        try {
            String mysqlJDBCDriver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/employeemanagement";
            String user = "root";
            String pass = "root";
            Class.forName(mysqlJDBCDriver);

            con = DriverManager.getConnection(url, user, pass);

            // Check if the user exists
            String checkUserQuery = "SELECT * FROM registration WHERE email=?";
            PreparedStatement checkUserStmt = con.prepareStatement(checkUserQuery);
            checkUserStmt.setString(1, email);
            ResultSet userResultSet = checkUserStmt.executeQuery();

            if (userResultSet.next()) {
                // Update user details
                String updateQuery = "UPDATE registration SET designation=?, phoneNumber=?, department=?, "
                        + "teachingExperince=?, IndustryExperince=?, TotalExperince=? WHERE email=?";
                PreparedStatement updateStmt = con.prepareStatement(updateQuery);
                updateStmt.setString(1, designation);
                updateStmt.setString(2, phoneNumber);
                updateStmt.setString(3, department);
                updateStmt.setInt(4, teachingExperience);
                updateStmt.setInt(5, industryExperience);
                updateStmt.setInt(6, totalExperience);
                updateStmt.setString(7, email);
                		
                int rowsAffected = updateStmt.executeUpdate();

                if (rowsAffected > 0) {
                    out.println("User details updated successfully!");
                } else {
                    out.println("Failed to update user details.");
                }
            } else {
                out.println("User not found with email: " + email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
