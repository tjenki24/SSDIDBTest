package ssdi.dbtest;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public final class QueryWithContext {

    public static void query(PrintWriter out) throws NamingException {
    	Context context = null;
    	DataSource datasource = null;
    	Connection connect = null;
    	Statement statement = null;
    	
    	try {
    		//Get the context and create a connection
    		context = new InitialContext();
    		datasource = (DataSource) context.lookup("java:/comp/env/jdbc/Presidents");
    		connect = datasource.getConnection();
    		
    		//Create the statement to be used to query DB
    		statement = connect.createStatement();
    		String query = "SELECT * FROM presidents";
    		
    		//Execute query and get result
    		ResultSet resultSet = statement.executeQuery(query);
    		out.println("<table style=width 100%> <tr> <th>Name</th> <th>First Year</th> <th>Last Year</th> </tr>");
    		while (resultSet.next()) {
    			String name = resultSet.getString("Name");
    			String firstYear = resultSet.getString("StartDate");
    			String lastYear = resultSet.getString("EndDate");
    			
    			out.println("<tr> <td>" + name + "</td> <td>" + firstYear + "</td> <td>" + lastYear + "</td> </tr>");
    		} 
    	} catch (SQLException e) { e.printStackTrace(out); }
    		finally {
    			out.println("</table>");
    			try { statement.close(); } catch (SQLException e) { e.printStackTrace(out); }
    			try { connect.close(); } catch (SQLException e) {e.printStackTrace(out); }
    		}
    }
}
