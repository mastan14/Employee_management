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

/**
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				PrintWriter out = response.getWriter();
				out.println("hello");
				//String password = request.getParameter("password");
			        
			     //   String email = request.getParameter("email");
			      //  out.println(email+" "+password);
				 String email = request.getParameter("email");
				    String password = request.getParameter("password");
			     
			      try {
			        	 //out.println("hii");
			            Class.forName("com.mysql.cj.jdbc.Driver");
			          //  out.println("hii");
			            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeemanagement", "root", "root");
			            // query
			        
			           String sql = "select * from login where username='"
			                  + email + "' and password=" +"'"+ password+"'";
			        //  out.println("pp");
			           // out.println("pp");
			            
			            PreparedStatement preparedStatement = con.prepareStatement(sql);
			            out.println(email+" "+password);
			            // Execute the query and get the result set
			            ResultSet resultSet = preparedStatement.executeQuery();
			           // out.println(email+" "+password);
			          //  out.println(resultSet);
			            if(resultSet.next()) {
		                try {
		                	 out.println("ppp");
		                    // Iterate through the result set and print values
		                 response.sendRedirect("employee.html");
		                
		                }
		                catch(Exception e) {  out.println("Error1"); }
		      	      
			            }
			  	      else {out.println("Invalid crediential");}
		          }
			       catch(Exception e) {  out.println("Error"+e); }
			      
	}

}
