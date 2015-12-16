package sg.com.yahoo.ryanlouck.hang10;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class CategoryActivity extends Activity {
	
	final int[] MODELIVES = new int[]{50, 40, 30, 45, 50, 40, 40, 4, 35};
	final PorterDuffColorFilter YELLOW = new PorterDuffColorFilter(Color.YELLOW, PorterDuff.Mode.OVERLAY);
	final PorterDuffColorFilter RED = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.OVERLAY);
	
	private Button backButton;
	private Button[] categoryButtons;
	private int gameMode, roundNo, lives;
	private boolean fgUsed, leUsed, lrUsed, lsUsed;
	private ArrayList<Category> categories;
	private View.OnClickListener gameStart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
		setContentView(R.layout.activity_category);
		
		Bundle gameDetails = getIntent().getExtras();
		gameMode = gameDetails.getInt("mode", 0);
		roundNo = gameDetails.getInt("round", 1);
		lives = gameDetails.getInt("livesLeft", -1);
		fgUsed = gameDetails.getBoolean("fg", false);
		leUsed = gameDetails.getBoolean("le", false);
		lrUsed = gameDetails.getBoolean("lr", false);
		lsUsed = gameDetails.getBoolean("ls", false);
		categories = (ArrayList<Category>) gameDetails.getSerializable("categories");
		
		categoryButtons = new Button[]{(Button) findViewById(R.id.button1), (Button) findViewById(R.id.button2),
				(Button) findViewById(R.id.button3), (Button) findViewById(R.id.button4),
				(Button) findViewById(R.id.button5), (Button) findViewById(R.id.button6),
				(Button) findViewById(R.id.button7), (Button) findViewById(R.id.button8),
				(Button) findViewById(R.id.button9), (Button) findViewById(R.id.button10)};
		
		if(lives == -1){
			lives = MODELIVES[gameMode];
			
			try{
				InputStream fis = getApplicationContext().getAssets().open("hangman_phrases.txt");
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr);
				
				categories = new ArrayList<Category>();
				
				String currCat;
				int currDiff = 0;
				ArrayList<String> currPhrases = new ArrayList<String>();
				Hashtable<Integer, ArrayList<String>> currCategory = new Hashtable<Integer, ArrayList<String>>();
				
				String line = br.readLine();
				currCat = line.substring(5);
				
				line = br.readLine();
				line = br.readLine();			
				
				while(line != null){
					if(line.startsWith("CAT: ")){
						currCategory.put(currDiff, currPhrases);
						currPhrases = new ArrayList<String>();
						Category newCat = new Category(currCat, currCategory);
						categories.add(newCat);
						currCat = line.substring(5);
						currDiff = 0;
						currCategory = new Hashtable<Integer, ArrayList<String>>();
						line = br.readLine();
					}
					else if(line.startsWith("DIFF: ")){
						currCategory.put(currDiff, currPhrases);
						currDiff += 1;
						currPhrases = new ArrayList<String>();
					}
					else{
						currPhrases.add(line);
					}
					line = br.readLine();
				}
				
				currCategory.put(currDiff, currPhrases);
				Category newCat = new Category(currCat, currCategory);
				categories.add(newCat);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			Random r = new Random();
			while(categories.size() > 10){
				int next = r.nextInt(categories.size());
				categories.remove(next);
			}
			
			for(int i = 0; i < 10; i++){
				categoryButtons[i].setText(categories.get(i).getName());
				categoryButtons[i].getBackground().setColorFilter(YELLOW);
			}
			
		}
		
		else{
			for(int i = 0; i < 10; i++){
				categoryButtons[i].setText(categories.get(i).getName());
				if(categories.get(i).isUsed()){
					categoryButtons[i].getBackground().setColorFilter(RED);
				}
				else{
					categoryButtons[i].getBackground().setColorFilter(YELLOW);
				}
			}	
		}
		
		gameStart = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int diff = 0;
				switch(roundNo){
				case 1:
				case 2:
				case 3:
					diff = 0;
					break;
				case 4:
				case 5:
				case 6:
					diff = 1;
					break;
				default:
					diff = 2;
					break;				
				}
				
				for(int i = 0; i < 10; i++){
					if (categoryButtons[i] == v){
						if(categories.get(i).isUsed()){
							CharSequence text = getResources().getString(R.string.categoryChosen);
							int duration = Toast.LENGTH_SHORT;
							
							Toast t = Toast.makeText(getApplicationContext(), text, duration);
							t.show();
						}
						else{
							Intent gameLaunch = new Intent(getApplicationContext(), GameActivity.class);
							String chosen = categories.get(i).newPharse(diff);
							gameLaunch.putExtra("mode", gameMode);
							gameLaunch.putExtra("round", roundNo);
							gameLaunch.putExtra("livesLeft", lives);
							gameLaunch.putExtra("categories", categories);
							gameLaunch.putExtra("chosenCat", i);
							gameLaunch.putExtra("next", chosen);
							gameLaunch.putExtra("fg", fgUsed);
							gameLaunch.putExtra("le", leUsed);
							gameLaunch.putExtra("lr", lrUsed);
							gameLaunch.putExtra("ls", lsUsed);
							startActivity(gameLaunch);
						}
					}
				}			
			}
		};
		
		for(Button b : categoryButtons){
			b.setOnClickListener(gameStart);
		}
		
		backButton = (Button) findViewById(R.id.backButton);
		
		backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent mainLaunch = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(mainLaunch);
			}
		});
	}
	
	public void onStop(){
		super.onStop();
		this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.category, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
