package sg.com.yahoo.ryanlouck.hang10;

import java.util.ArrayList;

public class Phrase {
	
	private String answer;
	private StringBuffer state;
	private ArrayList<Character> guessed;
	
	public Phrase(String content){
		answer = content;
		
		state = new StringBuffer();
		for(char c : answer.toCharArray()){
			if(Character.isLetter(c)){
				state.append("_");
			}
			else{
				state.append(c);
			}
		}
		guessed = new ArrayList<Character>();
	}
}
