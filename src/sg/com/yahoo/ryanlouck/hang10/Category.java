package sg.com.yahoo.ryanlouck.hang10;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class Category implements Serializable {
	
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
	
	public String newPharse(int difficulty){
		ArrayList<String> diffPhrases = phrases.get(difficulty);
		int next = r.nextInt(diffPhrases.size());
		used = true;
		return diffPhrases.get(next);
	}
	
	public void setUsed(){
		used = true;
	}
	
	public boolean isUsed(){
		return used;
	}
	
	public String getName(){
		return name;
	}
}
