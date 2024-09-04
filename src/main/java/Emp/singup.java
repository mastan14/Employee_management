package Emp;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import CollegeManagementSystem.connection;sa

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Signup
 */
@WebServlet("/signup")
public class singup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public singup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 PrintWriter out = response.getWriter();
		// Retrieve form data
		out.println("s1");//out.println("hii");
		 String name = request.getParameter("Name");
		 //out.println(name);
		
		 
			
		 String email = request.getParameter("email");
			    String designation = request.getParameter("designation");
			    String phoneNumber = request.getParameter("phoneNumber");
			    String department = request.getParameter("department");
			    String dateOfJoining = request.getParameter("dateOfJoining");
			    int teachingExperience = Integer.parseInt(request.getParameter("teachingExperience"));
			    int industryExperience = Integer.parseInt(request.getParameter("industryExperience"));
			    out.println(name);
			    int p = (int) ((long) teachingExperience + industryExperience);

			    // Rest of the code...

		

	     // JDBC variables
	        Connection con = null;
	        //PreparedStatement preparedStatement = null;

			
	
		 try {
			 out.println("s2");
	            
	            String mysqlJDBCDriver
	                = "com.mysql.cj.jdbc.Driver"; //jdbc driver
	            String url
	                = "jdbc:mysql://localhost:3306/employeemanagement"; //mysql url
	            String user = "root";        //mysql username
	            String pass = "root";  //mysql passcode
	             Class.forName(mysqlJDBCDriver);
	           
	             con = DriverManager.getConnection(url, user,pass);
	          
	             out.println("s3");
	             
		          String  sql1="INSERT INTO login(username, password) VALUES (?, ?);";

		            PreparedStatement preparedStatement1= con.prepareStatement(sql1);
		            preparedStatement1.setString(2, "12345");
		            preparedStatement1.setString(1, email);
		            
	             // query
	             String sql = "select * from registration where email='"
	                   + email+"'";
	             PreparedStatement st= con.prepareStatement(sql);
	             ResultSet rs = st.executeQuery();
	             if(!rs.next()) {
	         // SQL query to insert data into the database
	             sql = "INSERT INTO registration(name, email, designation, phoneNumber, department, dateOfJoining, teachingExperince, IndustryExperince,TotalExperince) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

	            PreparedStatement preparedStatement= con.prepareStatement(sql);
	            preparedStatement.setString(1, name);
	            preparedStatement.setString(2, email);
	            preparedStatement.setString(3, designation);
	            
	            preparedStatement.setString(4, phoneNumber);
	            preparedStatement.setString(5, department);
	            preparedStatement.setString(6, dateOfJoining);
	            preparedStatement.setInt(7, teachingExperience);
	            preparedStatement.setInt(8, industryExperience);
	            preparedStatement.setInt(9, p);
	        
	           
	            out.println("s4");
	            // Execute the query
	            con.setAutoCommit(true);
	           

	            int rowsAffected = preparedStatement.executeUpdate();
	            int rowsAffected1 = preparedStatement1.executeUpdate();
	            out.println(rowsAffected1);
	            con.setAutoCommit(true); // Set auto-commit to true

	            out.println("Connection1234!");

                if (rowsAffected > 0) {
                    out.println("Student details inserted successfully!");
                } else {
                    out.println("Failed to insert student details.");
                }
            
	           // Prepare HTML response
	            response.setContentType("text/html");
	            PrintWriter out1 = response.getWriter();

	            out1.println("<html><head><title>Registration Confirmation</title></head><body>");
	            if (rowsAffected > 0) {
	                out1.println("<h2>Registration Successful</h2>");
	            } else {
	                out1.println("<h2>Registration Failed</h2>");
	            }
	            out1.println("</body></html>");
	             }
else {out.println("u have already registered");}


	        }
	        catch (Exception e) {
	            System.out.println("Connection Failed!"+e);
	        }
		 
		/* finally {
	            // Close resources in the finally block
	            try {
	                if (preparedStatement != null) {
	                    preparedStatement.close();
	                }
	                if (con != null) {
	                    con.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
		 }
		 */
		 
		 
	}
}


