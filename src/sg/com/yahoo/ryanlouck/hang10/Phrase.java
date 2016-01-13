package sg.com.yahoo.ryanlouck.hang10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Phrase {
	
	final char[] LETTERS = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
			'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	final char specialSpace = (char) 160;
	private char[] answer;
	private StringBuffer state;
	private boolean[] guessed, inside, outside;
	private int guessAmt;
	
	public Phrase(String content){
		answer = content.toCharArray();
		guessAmt = 0;
		inside = new boolean[26];
		outside = new boolean[26];
		Arrays.fill(inside, false);
		Arrays.fill(outside, true);
		
		state = new StringBuffer();
		for(char c : answer){
			if(Character.isLetter(c)){
				state.append("_");
				for(int i = 0; i < 26; i++){
					if(c == LETTERS[i]){
						inside[i] = true;
						outside[i] = false;
						break;
					}
				}
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
			formattedState.append(specialSpace);
		}
		formattedState.deleteCharAt(formattedState.length() - 1);
		return formattedState.toString();
	}
	
	public String correctAnswer(){
		StringBuffer formattedAnswer = new StringBuffer();
		for(char c : answer){
			formattedAnswer.append(c);
			formattedAnswer.append(specialSpace);
		}
		formattedAnswer.deleteCharAt(formattedAnswer.length() - 1);
		return formattedAnswer.toString();
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
	
	public boolean[] letterElim(){
		boolean[] toElim = new boolean[26];
		Arrays.fill(toElim, false);
		ArrayList<Integer> candidates = new ArrayList<Integer>();
		for(int i = 0; i < 26; i++){
			if(outside[i] & !guessed[i]){
				candidates.add(i);
			}
		}
		
		while(candidates.size() > 5){
			Random r = new Random();
			int next = r.nextInt(candidates.size());
			candidates.remove(next);
		}
		
		for(int i = 0; i < 26; i++){
			if(candidates.contains(i)){
				toElim[i] = true;
				guessed[i] = true;
			}
		}
		return toElim;
	}
	
	public char letterReveal(){
		ArrayList<Integer> candidates = new ArrayList<Integer>();
		for(int i = 0; i < 26; i++){
			if(inside[i] & !guessed[i]){
				candidates.add(i);
			}
		}
		Random r = new Random();
		char c = LETTERS[candidates.get(r.nextInt(candidates.size()))];
		guessLetter(c);
		return c;
	}
	
	public void doFortune(){
		guessed[0] = true;
		guessed[4] = true;
		guessed[8] = true;
		guessed[14] = true;
		guessed[20] = true;
	}
	
	public boolean isFortuneSolved(){
		char[] rawState = state.toString().toCharArray();
		for(int i = 0; i < rawState.length; i++){
			if(rawState[i] == '_' && answer[i] != 'A' && answer[i] != 'E' && answer[i] != 'I' && answer[i] != 'O' && answer[i] != 'U'){
				return false;
			}
		}
		return true;
	}
}
