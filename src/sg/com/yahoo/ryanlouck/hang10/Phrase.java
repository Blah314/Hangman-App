package sg.com.yahoo.ryanlouck.hang10;

import java.util.Arrays;

public class Phrase {
	
	final char[] LETTERS = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
			'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	
	private char[] answer;
	private StringBuffer state;
	private boolean[] guessed;
	private int guessAmt;
	
	public Phrase(String content){
		answer = content.toCharArray();
		guessAmt = 0;
		
		state = new StringBuffer();
		for(char c : answer){
			if(Character.isLetter(c)){
				state.append("_");
			}
			else{
				state.append(c);
			}
		}
		guessed = new boolean[26];
		Arrays.fill(guessed, false);
	}
	
	public String currentState(){
		char[] rawState = state.toString().toCharArray();
		StringBuffer formattedState = new StringBuffer();
		for(char c : rawState){
			formattedState.append(c);
			formattedState.append(" ");
		}
		formattedState.deleteCharAt(formattedState.length() - 1);
		return formattedState.toString();
	}
	
	public char[] correctAnswer(){
		return answer;
	}
	
	public boolean[] getGuessed(){
		return guessed;
	}
	
	public int getLastGuess(){
		return guessAmt;
	}
	
	public boolean guessLetter(char g){
		for(int i = 0; i < 26; i++){
			if(g == LETTERS[i]){
				guessed[i] = true;
				break;
			}
		}
		char[] currState = state.toString().toCharArray();
		guessAmt = 0;
		for(int i = 0; i < answer.length; i++){
			if(answer[i] == g && currState[i] == '_'){
				guessAmt += 1;
				state.replace(i, i+1 , Character.toString(g));
			}
		}		
		return (guessAmt != 0);
	}
	
	public boolean isSolved(){
		char[] rawState = state.toString().toCharArray();
		for(char c : rawState){
			if(c == '_'){
				return false;
			}
		}
		return true;
	}
}
