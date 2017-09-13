import java.io.IOException;
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
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.Statement;

@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   static String             url              = "jdbc:mysql://ec2qadams.ddns.net:3306/myDB";
   static String             user             = "newremoteuser";
   static String             password         = "password";
   static Connection         connection       = null;

   public MyServlet() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      response.getWriter().println("<h1>Welcome to MyContacts Database</h1><br>");
      response.getWriter().println("<h2>Please feel free to add to this database</h2><br>");  
      try {
         Class.forName("com.mysql.jdbc.Driver");
      } catch (ClassNotFoundException e) {
         System.out.println("Where is your MySQL JDBC Driver?");
         e.printStackTrace();
         return;
      }

      connection = null;
      try {
         connection = DriverManager.getConnection(url, user, password);
      } catch (SQLException e) {
         System.out.println("Connection Failed! Check output console");
         e.printStackTrace();
         return;
      }
      if (connection == null) {
    	  System.out.println("Failed to make connection!");
      } 
      try {
         
        String First_Name = "";
        String Last_Name = "";
        String Phone_Number = "";
        String Email = "";
    	String selectSQL = "";
    	int ID = 0;
         
         response.getWriter().println(selectSQL + "<br>");
         
         
         response.getWriter().println("<form name=\"entry\" method=\"post\" action=\"MyServlet\"> "
         		+ "First Name    : <input type=\"text\" name=\"first_name\"/><br/> "
         		+ "Last Name     : <input type=\"text\" name=\"last_name\"/><br/> "
         		+ "Phone Number  : <input type=\"text\" name=\"phone_number\"/><br/> "
         		+ "Email         : <input type=\"text\" name=\"email\"/><br/> "
         		+ "<input type=\"submit\" value=\"Add\"></form>"
    	);
         
         First_Name = request.getParameter("first_name");
         Last_Name = request.getParameter("last_name");
         Phone_Number = request.getParameter("phone_number");
         Email = request.getParameter("email");

         response.getWriter().println("<h3>Last Entry Added</h3>");
         
         response.getWriter().println("First Name is   : " + First_Name + "<br>");
         response.getWriter().println("Last Name is    : " + Last_Name + "<br>");
         response.getWriter().println("Phone Number is : " + Phone_Number + "<br>");
         response.getWriter().println("Email is        : " + Email + "<br>");
                  
         selectSQL = "Select * FROM myTable";
              
         PreparedStatement pst =(PreparedStatement) connection.prepareStatement("INSERT INTO myTable (First_Name,Last_Name,Phone_Number, Email) VALUES (?,?,?,?)");  

         pst.setString(1,First_Name);  
         pst.setString(2,Last_Name);        
         pst.setString(3,Phone_Number);
         pst.setString(4,Email);

         int work = pst.executeUpdate();
         String msg=" ";
         if(work !=0){  
           msg="Record Inserted";
           response.getWriter().println("<font size='5' color=blue>" + msg + "</font><br>");  
         }  
         else{  
           msg="Data Insertion Failed";
           response.getWriter().println("<font size='5' color=red>" + msg + "</font><br>");
          }  
         
         response.getWriter().println("<br>Retreiving the current table from the database<br><br>");
         
         ResultSet rs = null;
         Statement statement = null;
         statement = (Statement) connection.createStatement();
         rs = statement.executeQuery("SELECT * FROM myTable");
         
         while(rs.next()){
        	  response.getWriter().println(rs.getObject(1).toString());
        	  response.getWriter().println("          | ");
        	  response.getWriter().println(rs.getObject(2).toString());
        	  response.getWriter().println("          | ");
        	  response.getWriter().println(rs.getObject(3).toString());
        	  response.getWriter().println("          | ");
        	  response.getWriter().println(rs.getObject(4).toString());
        	  response.getWriter().println("          | ");
        	  response.getWriter().println(rs.getObject(5).toString());
        	  response.getWriter().println("<br>");
        }
       
//        //Trying to delete record --------------------------------------------------------------------------
//         response.getWriter().println("<h2>Feel Free to Delete a Record By ID</h2>");
//         response.getWriter().println("<form name=\"delete\" method=\"post\" action=\"MyServlet\"> "
//      		   + "ID    : <input type=\"text\" name=\"deleteid\"/><br/> "
//      		   + "<input type=\"submit\" value=\"Delete\"></form>");
//         
//         ID = Integer.parseInt(request.getParameter("deleteid"));
//
//
//         response.getWriter().println("<h3>Last Entry Added</h3>");
//         
//         response.getWriter().println("ID is   : " + ID + "<br>");
//
//                  
////         selectSQL = "Select * FROM myTable";
//              
//         pst =(PreparedStatement) connection.prepareStatement("select * from myTable where id='" + ID + "' ");  
//         String setID = ID + "";
//         pst.setString(1,setID);  
//
//
//         work = pst.executeUpdate();
//         msg=" ";
//         if(work !=0){  
//           msg="Record Inserted";
//           response.getWriter().println("<font size='5' color=blue>" + msg + "</font><br>");  
//         }  
//         else{  
//           msg="Data Insertion Failed";
//           response.getWriter().println("<font size='5' color=red>" + msg + "</font><br>");
//          }  
//         
//         response.getWriter().println("<br>This is the table right now<br><br>");
//         
//         rs = null;
//         statement = null;
//         statement = (Statement) connection.createStatement();
//         rs = statement.executeQuery("SELECT * FROM myTable");
//         
//         while(rs.next()){
//        	  response.getWriter().println(rs.getObject(1).toString());
//        	  response.getWriter().println("          | ");
//        	  response.getWriter().println(rs.getObject(2).toString());
//        	  response.getWriter().println("          | ");
//        	  response.getWriter().println(rs.getObject(3).toString());
//        	  response.getWriter().println("          | ");
//        	  response.getWriter().println(rs.getObject(4).toString());
//        	  response.getWriter().println("          | ");
//        	  response.getWriter().println(rs.getObject(5).toString());
//        	  response.getWriter().println("<br>");
//        } 
//         
//        //Search for record by ID
//       response.getWriter().println("Search By ID"); 
//       response.getWriter().println("<form name=\"search\" method=\"post\" action=\"MyServlet\"> "
//    		   + "ID    : <input type=\"text\" name=\"searchid\"/><br/> "
//    		   + "<input type=\"submit\" value=\"Search\"></form>");
//         
//       ID = Integer.parseInt(request.getParameter("searchid"));
//       
//    
//         statement = (Statement) connection.createStatement();
//         String query = "select * from myTable where id='" + ID + "' ";
//         rs = statement.executeQuery(query);
//         
//         while(rs.next()){
//        	  response.getWriter().println(rs.getObject(1).toString());
//        	  response.getWriter().println("          | ");
//        	  response.getWriter().println(rs.getObject(2).toString());
//        	  response.getWriter().println("          | ");
//        	  response.getWriter().println(rs.getObject(3).toString());
//        	  response.getWriter().println("          | ");
//        	  response.getWriter().println(rs.getObject(4).toString());
//        	  response.getWriter().println("          | ");
//        	  response.getWriter().println(rs.getObject(5).toString());
//        	  response.getWriter().println("<br>");
//        }
//         
//         //Delete Record
//           response.getWriter().println("<h2>Feel Free to Delete a Record By ID</h2>");
//           response.getWriter().println("<form name=\"delete\" method=\"post\" action=\"MyServlet\"> "
//        		   + "ID    : <input type=\"text\" name=\"deleteid\"/><br/> "
//        		   + "<input type=\"submit\" value=\"Delete\"></form>");
////         
//           ID = Integer.parseInt(request.getParameter("deleteid"));
//           response.getWriter().println(ID + "<br>");
//           rs = statement.executeQuery("DELETE from myTable where id=" + ID);
//         
//         
//         selectSQL = "SELECT * FROM myTable WHERE Last_Name LIKE ?";
  
               
         
         pst.close();
       }  
       catch (Exception e){  
//         response.getWriter().println(e);  
       }  
      
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
