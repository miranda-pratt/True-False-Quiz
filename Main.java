/**
 * Main.java
 */

package application;
	
import java.sql.Connection;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;


/**
 * Main class responsible for creating the front-end GUI, responding to button presses
 * and calling the methods necessary for playing the game (such as opening the database,
 * generating questions etc)
 * 
 * @author Miranda Pratt
 * @version 1.0
 * @since 24.09.22
 * @see Database.java, Game.java, Question.java
 *
 */

public class Main extends Application {
	
  // Fields
  private int[] questionNumbers;
  private Question question;
  private Game game;
  private Database database;
  private boolean rightAnswer;
  private int totalCorrect;
  private int questionCounter = 0;
  private Connection mainConnection;
	
  // Constructor to initialise field objects and arrays
  public Main() {
	super();
	game = new Game();
	database = new Database();
	questionNumbers = new int[11];	
  } // End constructor
	
	
  /**
	* Method to create the GUI screen
	* @return gridPane
	*/
	
  private Parent createStartScreen() {

	// Create a gridPane with size 850 by 600 pixels with a 20 pixel padding on the border
	// And 20 pixel gap between rows
	GridPane gridPane = new GridPane();
	gridPane.setPrefSize(850, 600);
	gridPane.setVgap(20); 
	gridPane.setPadding(new Insets(20, 20, 20, 20));
		
	// Create and style the title text for the start screen
	Text titleText = new Text("True/False Game");
	titleText.setFont(Font.font ("Arial Black", 50));
	titleText.setFill(Color.DARKBLUE);
	    
	// Create and style the description text for the start screen
	Text descriptionText = new Text("A 10 question game to text your general knowledge");
	descriptionText.setFont(Font.font ("Arial", 24));
	descriptionText.setFill(Color.DARKBLUE);
		
	// Create and style a style button for the start screen
	Button startButton = new Button("Press here to start");
	startButton.setFont(Font.font("Arial", 50));
	startButton.setStyle("-fx-background-color: #5F9FF3;");
	    
	// Create and style the question counter text for the main game screen
	Text questionCounterText = new Text("Question " + " of 10");
	questionCounterText.setFont(Font.font ("Arial Black", 32));
	questionCounterText.setFill(Color.DARKBLUE);
	    
	// Create and style the placeholder text for where the question will go for the main game
	Text questionText = new Text("Question here");
	questionText.setFont(Font.font("Arial", 24));
	questionText.setFill(Color.DARKBLUE);
	    
	// Create and style the true button for the main game
    Button trueButton = new Button ("True");
	trueButton.setStyle("-fx-background-color: #50C878;");
	trueButton.setFont(Font.font ("Arial", 24));
    trueButton.setPrefSize(200, 200);
		
	// Create and style the false button for the main game
	Button falseButton = new Button ("False");
	falseButton.setStyle("-fx-background-color: #FF0000;");
	falseButton.setFont(Font.font ("Arial", 24));
	falseButton.setPrefSize(200, 200);
		
	// Create and style the score text for the end of the game
	Text scoreText = new Text(" / 10");
	scoreText.setFont(Font.font("Arial Black", 50));
	scoreText.setFill(Color.DARKBLUE);
		
	// Create and style the placeholder text for the message at the end of the game
	Text scoreMessageText = new Text("Message");
	scoreMessageText.setFont(Font.font("Arial", 50));
	scoreMessageText.setFill(Color.DARKBLUE);
		
	// Create and style the play again button at the end of the game
	Button playAgainButton = new Button("Play again");
	playAgainButton.setStyle("-fx-background-color: #5F9FF3;");
	playAgainButton.setFont(Font.font ("Arial", 50));
		

	// Set the gridPane constraints for the start screen - these components will be displayed
	GridPane.setConstraints(titleText, 0, 0); 
	GridPane.setConstraints(descriptionText, 0, 1);
	GridPane.setConstraints(startButton, 0, 4);
		
	// setVisible(false) the components that won't be displayed yet
    scoreText.setVisible(false);
	scoreMessageText.setVisible(false);
	questionCounterText.setVisible(false);
	questionText.setVisible(false);
	trueButton.setVisible(false);
    falseButton.setVisible(false);
	playAgainButton.setVisible(false);
		
	    
	// Action event for start button
    EventHandler<ActionEvent> eventStartButton = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
            	
        // Set the titleText, descriptionText and startButton to no longer visible
        titleText.setVisible(false);
        descriptionText.setVisible(false);
        startButton.setVisible(false);
                
        // Set the questionCounterText, questionText, trueButton and falseButton to visible
        questionCounterText.setVisible(true);
        questionText.setVisible(true);
        trueButton.setVisible(true);
        falseButton.setVisible(true);
   
        // Set constraints for the visible components
        GridPane.setConstraints(questionCounterText, 0, 0);
        GridPane.setConstraints(questionText, 0, 1, 1, 3);
        GridPane.setConstraints(trueButton, 0, 6);
        GridPane.setConstraints(falseButton, 1, 6);
 
        // Try to start the game by setting up the main connection and displaying the first question
        try {
            	
          startFirstGame();
		  question = nextQuestion(questionCounterText, questionText);
			
		// Catch errors and display stack trace
		} catch (ClassNotFoundException | SQLException exception1) {
		  exception1.printStackTrace();
		} // End catch
        
      } // End method
    }; // End action event
		
    
	// Action event for true button
    EventHandler<ActionEvent> eventTrueButton = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
            	
        // Increment the question counter as the question has been answered
        ++questionCounter;
            	
        // Check to see if the question is right
        checkQuestion(question, true);
            	
        // If questionCounter is 10, display end screen
        if (questionCounter == 10) {
            		
          // Display the message for the user's score
          String message = getMessage();
            			
		  // Set components for the game to false
		  titleText.setVisible(false);
		  descriptionText.setVisible(false);
		  questionCounterText.setVisible(false);
		  questionText.setVisible(false);
	      startButton.setVisible(false);
	      trueButton.setVisible(false);
		  falseButton.setVisible(false);
		
		  // Set components for the end screen to true
	      scoreText.setVisible(true);
	      scoreMessageText.setVisible(true);
		  playAgainButton.setVisible(true);
		
		  // Set constraints for end screen
		  GridPane.setConstraints(scoreText, 0, 0);
          GridPane.setConstraints(scoreMessageText, 0, 1);
  		  GridPane.setConstraints(playAgainButton, 0, 4);
		
		  // Use the totalCorrect field to display score
		  scoreText.setText(totalCorrect + " / 10");
		  scoreMessageText.setText(message);
              			
        // Else (not finished game) go to the next question
        } else {
          try {
            // Load the next question
			nextQuestion(questionCounterText, questionText);
						
		  // If an error occurs print stack trace
		  } catch (ClassNotFoundException | SQLException exception1) {
		    exception1.printStackTrace();
		  } // End catch
        } // End else
				
      } // End method
    }; // End action event
     
    
    // Action event for true button
    EventHandler<ActionEvent> eventFalseButton = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
            	
        // Increment the question counter as the question has been answered
        ++questionCounter;
        	
        // Check to see if the question is right
        checkQuestion(question, false);
        	
        // If questionCounter is 10, display end screen
        if (questionCounter == 10) {
        		
          // Display the message for the user's score
          String message = getMessage();
        			
          // Set components for the game to false
          titleText.setVisible(false);
		  descriptionText.setVisible(false);
		  questionCounterText.setVisible(false);
		  questionText.setVisible(false);
		  startButton.setVisible(false);
		  trueButton.setVisible(false);
		  falseButton.setVisible(false);
			
		  // Set components for the end screen to true
		  scoreText.setVisible(true);
		  scoreMessageText.setVisible(true);
		  playAgainButton.setVisible(true);
			
		  // Set constraints for end screen
		  GridPane.setConstraints(scoreText, 0, 0);
          GridPane.setConstraints(scoreMessageText, 0, 1);
      	  GridPane.setConstraints(playAgainButton, 0, 4);
			
		  // Use the totalCorrect field to display score
		  scoreText.setText(totalCorrect + " / 10");
		  scoreMessageText.setText(message);
			
        // Else (not finished game) go to the next question
    	} else {
    	  try {
    	    // Load the next question
			nextQuestion(questionCounterText, questionText);
					
			// If an error occurs print stack trace
		  } catch (ClassNotFoundException | SQLException exception1) {
		    exception1.printStackTrace();
		  } // End catch
    	} // End else
			
      } // End method
    }; // End action event
        
    
    // Action event for play again button
    EventHandler<ActionEvent> eventPlayAgainButton = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
            	
        // Set start and screen components to false
    	titleText.setVisible(false);
        descriptionText.setVisible(false);
        scoreText.setVisible(false);
        scoreMessageText.setVisible(false);
        startButton.setVisible(false);
        playAgainButton.setVisible(false);
        
        // Display game components
        questionCounterText.setVisible(true);
        questionText.setVisible(true);
        trueButton.setVisible(true);
        falseButton.setVisible(true);
 
        // Set constraints for game components
        GridPane.setConstraints(questionCounterText, 0, 0);
        GridPane.setConstraints(questionText, 0, 1);
  		GridPane.setConstraints(trueButton, 0, 6);
  		GridPane.setConstraints(falseButton, 1, 6);
              
        // Try to start new game and display the first question
        try {
            	
          startNewGame();
		  question = nextQuestion(questionCounterText, questionText);
			
		// If an error occurs, print the stack trace
		} catch (ClassNotFoundException | SQLException exception1) {
		  exception1.printStackTrace();
		} // End catch
      } // End method
    }; // End action event
        
    // Set the buttons with the action events
    startButton.setOnAction(eventStartButton);
    trueButton.setOnAction(eventTrueButton);
    falseButton.setOnAction(eventFalseButton);
    playAgainButton.setOnAction(eventPlayAgainButton);
	    
    // Add children to gridPane
	gridPane.getChildren().addAll(titleText, descriptionText, questionCounterText, questionText, 
	scoreText, scoreMessageText, startButton, trueButton, falseButton, playAgainButton);
	    
	// Return the gridPane
	return gridPane;

  } // End method
  

  /**
	* Start method
	*/
  
  @Override
  public void start(Stage primaryStage) throws SQLException {
    // JavaFX set up
	BorderPane root = new BorderPane();
	Scene scene = new Scene(root,400,400);
	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	primaryStage.setScene(new Scene(createStartScreen()));
	primaryStage.show();
	// On close of JavaFX program, close connection to the database
	primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      public void handle(WindowEvent we) {
        // If mainConnection is not null close the database
        if (mainConnection != null) {
          try {
		    mainConnection.close();
		    // Catch SQL Exception and print stack trace
		  } catch (SQLException e) {
		    e.printStackTrace();
		  } // End catch
        } // End if
      } // End method
    }); // End event handler
  } // End method
  
	
  /**
    * Method to check the question to see if the user answer matches the correct answer
    * @param question object to check answer
    * @param userAnswer
    */
  
  private void checkQuestion(Question question, boolean userAnswer) {
	// Get the right answer
	this.rightAnswer = question.getAnswer();
	// If the user was right, increment totalCorrect
	if (userAnswer == this.rightAnswer) {
      ++this.totalCorrect;
	} // End if
  } // End method
	

  /**
	* Method to get message for the end of the program depending on the user's score
	* @return String message
	*/
  private String getMessage() {
		
	// Use a switch and a fall through method to display the message for the user's score
	// [0-2] "Better luck next time"
	// [3-6] "Getting there"
	// [7-9] "Well done"
	// [10] "Outstanding
	switch (totalCorrect) {
	case 0:
	case 1:
	case 2:
	  return "Better luck next time";
	case 3: 
	case 4:
	case 5:
	case 6:
	  return "Getting there";
	case 7:
	case 8:
	case 9:
	  return "Well done";
	case 10:
	  return "Outstanding";
	default:
	  return "Invalid score";
	} // End switch
  } // End method
	
  
  /**
	* Method to launch main program
	* @param args
	*/
  
  public static void main(String[] args) {
	launch(args);
  } // End method
	
  
  /**
	* Method to display get the next question from the database
	* @param questionCounterText
	* @param questionText
	* @return question from databasee
	* @throws SQLException
	* @throws ClassNotFoundException
	* @see setForQuestion
	*/
  
  private Question nextQuestion(Text questionCounterText, Text questionText) throws SQLException, ClassNotFoundException {
		
	// Get the next question information from the questionNumbers array containing list of questionID's
	this.question = this.database.executeQuery(mainConnection, questionNumbers[questionCounter]);
	
	// Use the setForQuestion method to display the question on screen
	setForQuestion(questionCounterText, questionText);
	
	return this.question;
		
  } // End method
	
  
  /**
	* Method to display the question including question counter and text
	* @param questionCounterText
	* @param questionText
	*/
	
  private void setForQuestion(Text questionCounterText, Text questionText) {
	  
	// Display question counter
	questionCounterText.setText("Question " + (this.questionCounter + 1) + " of 10");
	
	// Set and wrap question
	questionText.setText(question.getQuestion());
	questionText.setWrappingWidth(600);
	
	// Display question counter and question
	questionCounterText.setVisible(true);
	questionText.setVisible(true);
	
  } // End method
	
  
  /**
	* Start first game, including connection to the database
	* Generates questions for the game from the database
	* @return database connection
	* @throws ClassNotFoundException
	* @throws SQLException
	*/
	
  private void startFirstGame() throws ClassNotFoundException, SQLException {
	// Open connection
	this.mainConnection = this.database.openConnection();
	
	// Generate questions
	this.questionNumbers = this.game.generateQuestions();
	
  } // End method
	
  
  /**
	* Start new game generates new question and resets questionCounter and totalCorrect
	* Does not include connection to ghe database (this is for when the user presses 'play again' not 'start game'
	*/
	
  private void startNewGame() {
	this.questionNumbers = this.game.generateQuestions();
	this.totalCorrect = 0;
	this.questionCounter = 0;
  } // End method
		
} // End class
