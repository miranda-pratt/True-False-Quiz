/**
 * Question.java
 */

package application;


/**
 * Question class for True/False Quiz
 * This class manages the characteristics of a question in the quiz
 * Each question has a string questionDescription and a boolean answer field
 * @author Miranda Pratt
 * @version 1.0
 * @since 24.09.22
 * @see Game.java, Database.java, Main.java
 *
 */

public class Question {

  // Fields
  private String questionDescription = null;
  private boolean answer = false;

  
  // Constructor
  public Question() {
	super();
  } // End constructor
  
  
  /**
   * Getter for answer
   * @return this.answer
   */

  public boolean getAnswer() {
	return this.answer;
  } // End method
  
  
  /**
   * Getter for question
   * @return this.questionDescription
   */

  public String getQuestion() {
	return this.questionDescription;
  } // End method
  
  
  /**
   * Setter for answer
   * @param answer
   */
	
  public void setAnswer(boolean answer) {
	this.answer = answer;
  } // End method
  
  
  /**
   * Setter for question
   * @param question
   */

  public void setQuestion(String question) {
	this.questionDescription = question;
  } // End method

} // End class
