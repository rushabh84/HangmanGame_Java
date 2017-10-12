//Name: Rushabh Shah   andrew id: rushabhs
package hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public abstract class WordGame {
	String[] dictionaryWords;		//stores words read from the dictionary
	String gameWord;  				//word picked up from the dictionary for the puzzle 
	StringBuilder userInputs = new StringBuilder(); //stores all guesses entered by the user
	String message;					//message to be printed on console after each user interaction
	static final int MAX_TRIALS = 10; //maximum chances given to the player
	int trialCount=1;				//incremented everytime user enters a valid guess 
	boolean won = false;			//set to true when user input matches the gameWord
	String clueWord;				//clue shown to the user on console
	double score;					//updated by calcScore() 

	/** readfile() opens the file and reads it. 
	 * It returns an array of String by splitting the words on new line
	 */
	public String[] readFile() {
		StringBuilder rdfile=new StringBuilder("");  //StringBuilder object rdfile created 
		Scanner sc=null;
		try 
		{
			sc= new Scanner(new File("dictionary.txt"));  //reads the file in a Scanner object sc
			while(sc.hasNextLine())
			{
				rdfile.append(sc.nextLine()+"\n");  //appends the data in rdfile from dictionary.txt file line-by-line
			}											
		} 											
		catch (FileNotFoundException e)  //handles the exception if File name passe wasn't found
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sc.close();
		return rdfile.toString().split("\n"); //returns the content of rdfile after converting to String
	}
	
	/** pickGameWord() picks a word randomly from the dictionaryWords array 
	 * and initializes the gameWord with it. 
	 * The word must be at least 4 characters long
	 */
	public void pickGameWord(){
		Random r= new Random();
		int choice;  //number(position) of word selected from the dicitonary.txt file
		do
		{
			choice = r.nextInt(dictionaryWords.length);  //random number is selected between  and length of dictionaryWords array
			gameWord= dictionaryWords[choice];
			gameWord=gameWord.toLowerCase();  //to avoid case-sensitive problem
		}while(gameWord.length()<=3);  //checks that the word selected is at least 4 characters long

	}
	public abstract int nextTry(String input);
	public abstract double calcScore();
	public abstract void setClueWord();

}
