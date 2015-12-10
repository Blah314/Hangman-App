package sg.com.yahoo.ryanlouck.hang10;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class Category {
	
	private String name;
	private Hashtable<Integer, ArrayList<String>> phrases;
	private boolean used;
	private Random r;
	
	public Category(String name, Hashtable<Integer, ArrayList<String>> phrases){
		this.name = name;
		this.phrases = (Hashtable<Integer, ArrayList<String>>) phrases.clone();
		used = false;
		r = new Random();
	}
	
	public Phrase newPharse(int difficulty){
		ArrayList<String> diffPhrases = phrases.get(difficulty);
		int next = r.nextInt(diffPhrases.size());
		Phrase p = new Phrase(diffPhrases.get(next));
		used = true;
		return p;
	}
	
	public boolean isUsed(){
		return used;
	}
	
	public String getName(){
		return name;
	}
}
