/**
 * Database.java
 */

package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Database class 
 * Responsible for opening the connection to the database and executing queries
 * @author Miranda Pratt
 * @version 1.0
 * @since 24.09.22
 * @see Game.java, Question.java, Main.java
 *
 */

public class Database {
	
  // Constructor
  public Database() {
	super();
  } // End constructor
	

  /**
   * Method to execute queries from the database
   * This creates a question object with three components: ID, question and answer
   * @param con
   * @param questionID
   * @return question object
   * @throws SQLException
   */
	
  public Question executeQuery(Connection con, int questionID) throws SQLException {
	
	// Create the question object
	Question question = new Question();
	
	// Create the statement and query
	Statement st = con.createStatement();
	
	// Execute query to produce a resultSet
    ResultSet rs = st.executeQuery("select * from questions"); 
    
    // Iterate over the result set and get the questionID, question and answer 
    while (rs.next()) {
    	
      // If the questionID for the current value in the resultSet is equal to the questionID we are looking for
      if (rs.getInt("questionID") == questionID) {
    	  
    	// Form the question object
        String name = rs.getString("question"); 
        boolean answer = rs.getBoolean("answer");
        question.setQuestion(name);
        question.setAnswer(answer);
                
      } // End if    	
    } // End while
    
    // close statement   
    st.close(); 
    
    // Return the statement object
    return question;

  } // End method
  
  
  /**
   * Method to open connection to the database
   * @return mainConnection
   * @throws ClassNotFoundException
   * @throws SQLException
   */
	
  public Connection openConnection () throws ClassNotFoundException, SQLException {
		
    // Database details: url, username and password
	// Censored for security reasons
	String url = "**********"; 
    String username = "************"; 
    String password = "*************";
        
    // Driver name
    Class.forName("com.mysql.cj.jdbc.Driver"); 
        
    // Get connection using url, username and password
    Connection mainConnection = DriverManager.getConnection(url, username, password);
        
    return mainConnection;
    
  } // End method
	
} // End class