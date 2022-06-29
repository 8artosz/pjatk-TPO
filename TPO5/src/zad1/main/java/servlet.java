

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class servlet
 */
@WebServlet("/servlet")
public class servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 PrintWriter out = response.getWriter();
		    Connection conn = null;
		    String type = request.getParameter("type");
		    out.print("<table border ='1'><tr><th>Brand</th><th>Production year</th><th>Fuel consumption</th></tr>");
		    try {
		    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		        String dbURL = "jdbc:sqlserver://db-mssql16;Database=s20296;authenticationScheme=NTLM;IntegratedSecurity=True";
		        conn = DriverManager.getConnection(dbURL,"s20296","rZ]0/4Mj");
		        Statement stmt = conn.createStatement();
		        ResultSet rs =stmt.executeQuery("SELECT * FROM CAR WHERE CarType ='" + type + "'");
		    	while(rs.next()) {
		    		 out.print("<tr><td>");
		    		 out.print(rs.getString(2));
		       		 out.print("</td>");
		       		 out.print("<td>");
		       		 out.print(rs.getString(3));
		       		 out.print("</td>");
		       		 out.print("<td>");
		       		 out.print(rs.getString(4));
		       		 out.print("</td>");
		       		 out.print("</tr>");
		    	}
		    }
		    catch(Exception e) {
		    	out.print(e);
		    }
		    out.print("</table>");
		    out.print("<a href=\"form.html\"><button>NewSearch</button></a>");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
