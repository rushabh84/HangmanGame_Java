//Name: Rushabh Shah   andrew id: rushabhs
package hw1;

import java.util.Random;

public class Hangman extends WordGame{

	int hit; //number of correct guesses made by player
	int miss; //number of wrong guesses made by player

	/** Hangman() invokes methods to 
	 * initialize dictionaryWords, gameWord, and clueWord
	 */
	Hangman() {
		dictionaryWords=super.readFile();  
		super.pickGameWord(); 
		setClueWord(); 
		message="Let's play Hangman!";
	}

	/**calcScore() calculates the game score and initialize the member variable score.
	 * The formula is: 
	 * if miss equals 0, then score = hit, 
	 * else score = hit/miss
	 */
	@Override
	public double calcScore() {
		double score;  //variable to store the score 
		if(miss==0)  //checks if the player finished the game with no miss
			score=hit;
		else
		{
			score=(double)hit/miss;  //type-casting the value of hit/miss to a double value
		}
		return score;
	}

	/** setClueWord() creates the clueWord by randomly replacing (i.e. hiding) alphabets in the gameWord 
	 * with dashes until the dashCount is equal to or more than the half the length of gameWord
	 * If an alphabet occurs more than once in the word and is hidden by a dash, then all its 
	 * occurrences must be replaced by dashes. For example, if the gameWord is 'apple', 
	 * and you hide p with a dash, then both 'p's must be replaced. 
	 */
	@Override
	public void setClueWord(){
		Random rn= new Random();
		clueWord=new String(gameWord);
		int l= gameWord.length();
		int pos;
		char atpos;  //stores the character to be replaced by '-'
		int dash=rn.nextInt(l-(l/2))+(l/2);  //randomly selects the number of dashes, ensures the number is >=0.5(gameWord.length())
		for(int i=0; i<dash;i++){  //loop to randomly replace dash number of characters with '-' in clueWord
			atpos='-';
			while(atpos=='-'){
				pos=rn.nextInt(l);
				atpos=clueWord.charAt(pos);
			}
			clueWord=clueWord.replaceAll(atpos+"","-");  //replaces all the atpos's value in clueWord with '-'
			i=countDashes(clueWord)-1;  //1 is subtracted as variable i will be auto-incremented for for loop's next iteration
		}
	}

	/** dashCount() returns the number of dashes in the word used as a helper method for setClueWord()
	 */
	public int countDashes(String word) {
		int no_dashes= 0;  //variable to store the number of dashes in a word
		for(int i=0;i<word.length();i++){
			if(word.charAt(i)=='-'){  //checks if the character at i position is '-' or not, if yes then increments no_dashes
				no_dashes++; 
			}
		}
		return no_dashes;
	}

	/** nextTry takes the user input as 'guess' and returns a response based on whether the input is valid or invalid. 
	 * Invalid input: If 'guess' already exists in the clueWord, it sets the message "Part of the clue!" and returns -1.
	 * Invalid input: If 'guess' was already entered by the user previously, it sets the message "You already entered that" and returns -1 
	 * Valid input: If 'guess' exists in gameWord but not in clueWord, then it replaces all 'dashes' in clueWord at correct places with 'guess' and returns 1. 
	 * Valid input: If 'guess' does not exist in gameWord and was never entered previously, it returns 0 and appends 'guess' into userInputs  
	 * The method also increments trialCount, hit, and miss for the valid inputs 
	 */
	@Override
	public int nextTry(String guess) {
		char entry=guess.toLowerCase().charAt(0);  //converts guess entered to lowerCase and stores it as a character in entry variable
		if (clueWord.indexOf(entry)!=-1){  //checks if entry's value is already present in the clueWord
			message="Part of the clue!";
			return -1;
		}
		else{
			if(userInputs.toString().indexOf(entry)!=-1){  //checks if the user has already made this guess
				message="You already entered that!";  //if user has already made this guess before prints this message
				return -1;
			}
			if(gameWord.indexOf(entry)==-1){  //checks if the guess made by user is present in the gameWord
				message="Sorry! Got it wrong!"; 
				userInputs.append(entry);  
				miss++;  //since user made a wrong guess, increments miss
				trialCount++; 
				return 0;
			}
			else{
				int []positions=new int[20];
				int i=0, count=0;
				int index = gameWord.indexOf(entry);  

				/** while loop finds indexes of all the occurrences in the guessWord 
				 * and stores these indexes in the positions array
				 */
				while (index>= 0) {
					positions[i]=index;  
					index= gameWord.indexOf(guess, index + 1);  //searches the next occurrence of guess in gameWord
					i++;
					count++;
				}
				for(int p=0;p<count;p++){  //loop replaces the dashes in clueWord with the correct guess at the index stored in positions array
					clueWord=clueWord.substring(0, positions[p])+entry+clueWord.substring(positions[p]+1);
				}
				hit++;  //since user made a right guess, increments hit
				trialCount++;
				userInputs.append(entry);
				message="You got that right!";

				/** checks if the game is won by finding if there are any more dashes
				 * if yes then prints the message in the required format
				 */
				if(clueWord.indexOf('-')==-1){ 
					won=true;
					System.out.println(message);
					System.out.println(gameWord);
					message="";
				}
				return 1;
			}
		}
	}
}

