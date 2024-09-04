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

@WebServlet("/Admin")
public class Admin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Admin() {
        super();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("Admin Login");

        String adminUsername = request.getParameter("username");
        String adminPassword = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeemanagement", "root",
                    "root");

            String sql = "SELECT * FROM admin WHERE email=? AND password=?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, adminUsername);
            preparedStatement.setString(2, adminPassword);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                try {
                    out.println("Admin login successful");
                    // Redirect to the admin dashboard or any admin-specific page
                  response.sendRedirect("admin_Dash.html");
                } catch (Exception e) {
                    out.println("Error redirecting to admin dashboard");
                }
            } else {
                out.println("Invalid admin credentials");
            }
        } catch (Exception e) {
            out.println("Error: " + e);
        }
    }
}
