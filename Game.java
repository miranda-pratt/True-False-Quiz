/**
 * Game.java
 */

package application;

import java.util.Random;

/**
 * Game class contains methods for the playing the game
 * Mostly about generating 10 random and unique questions
 * @author Miranda Pratt
 * @version 1.0
 * @since 24.09.22
 * @see Database.java, Question.java, Main.java
 *
 */

public class Game {
	
  public Game() {
	super();
  } // End constructor

  
  /**
   * checkArray method checks to ensure each new questionID is unique
   * @param array of current questionID's
   * @param numberToFind in the array
   * @return true if number is in array, false if not in array
   */
	
  private boolean checkArray(int[] array, int numberToFind) {
	// Iterate over the array
	for (int index = 0; index <= array.length; index++) {
		
	  // If the current item in the array is the number to find, return true
	  if (array[index] == numberToFind) {
		return true;
		
	  // Else return false
	  // If the current item is 0 - we have reached the end of the array
	  } else if (array[index] == 0) {
	    return false;
	  } // End else if
	  
	} // End for
	
	// Return true by default
	// Only will happen if an error occurs - this will mean a new number is generated
	return true;
  } // End method
	
  
  /**
   * Method to generate 10 random and unique questionID's from 1-120
   * @return int[] array of question ID's called questionNumbers
   * @see checkArray - private method that checks whether each new questionID already exists in array
   */
  
  public int[] generateQuestions() {
	  
	// Make an array of size 11
	// This is so the last number is 0 so when checking we know when we have reached the end of the array
	int[] questionNumbers = new int[11];
	
	// Create a module from the random module to generate random numbers
	Random randomGeneratorObject = new Random();
	
	// Create a counter to iterate until 10 random questionID's have been generated
	int counter = 0;
	while (counter < 10) {
		
	  // Obtain a number between [0 - 119].
	  int randomNumber = randomGeneratorObject.nextInt(120);
	  
	  // Add 1 to the random number for range [1-120]
	  randomNumber += 1;
	  
	  // Use the checkArray method on the array and random number
	  // If checkArray returns false - the number is new (not in array) and hence can be added
	  // Else don't increment the counter to generate a new number and repeat
	  if (checkArray(questionNumbers, randomNumber) == false) {
	    questionNumbers[counter] = randomNumber;
	    ++counter;
	  } else {
		  questionNumbers[counter] = 0; 
	  } // End else
		
	} // End while
		
	return questionNumbers;
	
  } // End method

	
} // End class
