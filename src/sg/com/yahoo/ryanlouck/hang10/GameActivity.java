package sg.com.yahoo.ryanlouck.hang10;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {
	
	final char[] LETTERS = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
			'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	final PorterDuffColorFilter YELLOW = new PorterDuffColorFilter(Color.YELLOW, PorterDuff.Mode.OVERLAY);
	final PorterDuffColorFilter RED = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.OVERLAY);
	final PorterDuffColorFilter GREEN = new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.OVERLAY);
	
	private Phrase p;
	private int gameMode, roundNo, lives;
	private ArrayList<Category> categories;
	private Category currCat;
	private boolean fgUsed, leUsed, lrUsed, lsUsed;
	private boolean fgActive, lsActive;
	private TextView round, livesLeft, title, display;
	private Button[] letterButtons;
	private Button backButton, nextRoundButton;
	private ImageButton freeGuessButton, letterElimButton, letterRevealButton, lifeSaverButton;
	private View.OnClickListener letterChooser, fgUser, leUser, lrUser, lsUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
		setContentView(R.layout.activity_game);
		
		Bundle gameDetails = getIntent().getExtras();
		gameMode = gameDetails.getInt("mode", 0);
		roundNo = gameDetails.getInt("round", 1);
		lives = gameDetails.getInt("livesLeft", -1);
		int chosenCat = gameDetails.getInt("chosenCat", 0);
		fgUsed = gameDetails.getBoolean("fg", false);
		leUsed = gameDetails.getBoolean("le", false);
		lrUsed = gameDetails.getBoolean("lr", false);
		lsUsed = gameDetails.getBoolean("ls", false);
		categories = (ArrayList<Category>) gameDetails.getSerializable("categories");
		currCat = categories.get(chosenCat);
		p = new Phrase(gameDetails.getString("next"));
		fgActive = false;
		lsActive = false;
		
		round = (TextView) findViewById(R.id.roundNum);
		livesLeft = (TextView) findViewById(R.id.lives);
		title = (TextView) findViewById(R.id.categoryName);
		display = (TextView) findViewById(R.id.phrase);
		
		round.setText("Round: " + Integer.toString(roundNo));
		title.setText(currCat.getName());
		display.setText(p.currentState());
		livesLeft.setText("Lives Left: " + Integer.toString(lives));
		
		letterButtons = new Button[]{(Button) findViewById(R.id.button1), (Button) findViewById(R.id.button2),
				(Button) findViewById(R.id.button3), (Button) findViewById(R.id.button4),
				(Button) findViewById(R.id.button5), (Button) findViewById(R.id.button6),
				(Button) findViewById(R.id.button7), (Button) findViewById(R.id.button8),
				(Button) findViewById(R.id.button9), (Button) findViewById(R.id.button10),
				(Button) findViewById(R.id.button11), (Button) findViewById(R.id.button12),
				(Button) findViewById(R.id.button13), (Button) findViewById(R.id.button14),
				(Button) findViewById(R.id.button15), (Button) findViewById(R.id.button16),
				(Button) findViewById(R.id.button17), (Button) findViewById(R.id.button18),
				(Button) findViewById(R.id.button19), (Button) findViewById(R.id.button20),
				(Button) findViewById(R.id.button21), (Button) findViewById(R.id.button22),
				(Button) findViewById(R.id.button23), (Button) findViewById(R.id.button24),
				(Button) findViewById(R.id.button25), (Button) findViewById(R.id.button26)};
		
		letterChooser = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				for(int i = 0; i < 26; i++){
					if(v == letterButtons[i]){
						boolean[] guessed = p.getGuessed();
						if(guessed[i]){
							CharSequence text = getResources().getString(R.string.letterChosen);
							int duration = Toast.LENGTH_SHORT;
							
							Toast t = Toast.makeText(getApplicationContext(), text, duration);
							t.show();
						}
						else{
							boolean correct = p.guessLetter(LETTERS[i]);
							if(!correct & !fgActive){
								if(!fgActive){
									lives -= 1;
								}
							}
							else if(lsActive){
								lives += p.getLastGuess();
							}
							
							if(fgActive){
								fgActive = false;
								freeGuessButton.getBackground().setColorFilter(RED);
							}
							if(lsActive){
								lsActive = false;
								lifeSaverButton.getBackground().setColorFilter(RED);
							}
							updateFields();
							v.getBackground().setColorFilter(RED);
						}			
					}
				}
			}
		};
		
		for(Button b : letterButtons){
			b.setOnClickListener(letterChooser);
			b.getBackground().setColorFilter(YELLOW);
		}
		
		freeGuessButton = (ImageButton) findViewById(R.id.freeGuess);
		letterElimButton = (ImageButton) findViewById(R.id.letterElim);
		letterRevealButton = (ImageButton) findViewById(R.id.letterReveal);
		lifeSaverButton = (ImageButton) findViewById(R.id.lifeSaver);
		
		if(fgUsed){
			freeGuessButton.getBackground().setColorFilter(RED);
		}
		else{
			freeGuessButton.getBackground().setColorFilter(GREEN);
		}
		
		if(leUsed){
			letterElimButton.getBackground().setColorFilter(RED);
		}
		else{
			letterElimButton.getBackground().setColorFilter(GREEN);
		}
		
		if(lrUsed){
			letterRevealButton.getBackground().setColorFilter(RED);
		}
		else{
			letterRevealButton.getBackground().setColorFilter(GREEN);
		}
		
		if(lsUsed){
			lifeSaverButton.getBackground().setColorFilter(RED);
		}
		else{
			lifeSaverButton.getBackground().setColorFilter(GREEN);
		}
		
		fgUser = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(fgActive){
					CharSequence text = getResources().getString(R.string.alreadyActive);
					int duration = Toast.LENGTH_SHORT;
					
					Toast t = Toast.makeText(getApplicationContext(), text, duration);
					t.show();
				}
				else if(fgUsed){
					CharSequence text = getResources().getString(R.string.alreadyUsed);
					int duration = Toast.LENGTH_SHORT;
					
					Toast t = Toast.makeText(getApplicationContext(), text, duration);
					t.show();
				}
				else{
					fgActivate();
				}
			}
		};
		
		leUser = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(leUsed){
					CharSequence text = getResources().getString(R.string.alreadyUsed);
					int duration = Toast.LENGTH_SHORT;
					
					Toast t = Toast.makeText(getApplicationContext(), text, duration);
					t.show();
				}
				else{
					leActivate();
				}
			}
		};
		
		lrUser = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(lrUsed){
					CharSequence text = getResources().getString(R.string.alreadyUsed);
					int duration = Toast.LENGTH_SHORT;
					
					Toast t = Toast.makeText(getApplicationContext(), text, duration);
					t.show();
				}
				else{
					lrActivate();
				}
			}
		};
		
		lsUser = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(lsActive){
					CharSequence text = getResources().getString(R.string.alreadyActive);
					int duration = Toast.LENGTH_SHORT;
					
					Toast t = Toast.makeText(getApplicationContext(), text, duration);
					t.show();
				}
				else if(lsUsed){
					CharSequence text = getResources().getString(R.string.alreadyUsed);
					int duration = Toast.LENGTH_SHORT;
					
					Toast t = Toast.makeText(getApplicationContext(), text, duration);
					t.show();
				}
				else{
					lsActivate();
				}
			}
		};
		
		freeGuessButton.setOnClickListener(fgUser);
		letterElimButton.setOnClickListener(leUser);
		letterRevealButton.setOnClickListener(lrUser);
		lifeSaverButton.setOnClickListener(lsUser);
		
		backButton = (Button) findViewById(R.id.backButton);
		
		backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent mainLaunch = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(mainLaunch);
			}
		});
		
		nextRoundButton = (Button) findViewById(R.id.nextRoundButton);
		nextRoundButton.setVisibility(View.INVISIBLE);
	}
	
	public void fgActivate(){
		fgActive = true;
		fgUsed = true;
		freeGuessButton.getBackground().setColorFilter(YELLOW);
	}
	
	public void leActivate(){
		leUsed = true;
		boolean[] elim = p.letterElim();
		for(int i = 0; i < 26; i++){
			if(elim[i]){
				letterButtons[i].getBackground().setColorFilter(RED);
			}
		}
		letterElimButton.getBackground().setColorFilter(RED);
		updateFields();
	}
	
	public void lrActivate(){
		lrUsed = true;
		p.letterReveal();
		letterRevealButton.getBackground().setColorFilter(RED);
		updateFields();
	}
	
	public void lsActivate(){
		lsActive = true;
		lsUsed = true;
		lifeSaverButton.getBackground().setColorFilter(YELLOW);
	}
	
	public void updateFields(){
		display.setText(p.currentState());
		livesLeft.setText("Lives Left: " + Integer.toString(lives));
		
		if(lives == 0){
			title.setText(R.string.gameOver);
			display.setText(p.correctAnswer());
			livesLeft.setText("Lives Left: X.X");
			for(Button b : letterButtons){
				b.setOnClickListener(null);
			}
		}
		if(p.isSolved()){
			round.setText("Round " + Integer.toString(roundNo) + " complete!");
			
			for(Button b : letterButtons){
				b.setOnClickListener(null);
			}
			
			freeGuessButton.setOnClickListener(null);
			letterElimButton.setOnClickListener(null);
			letterRevealButton.setOnClickListener(null);
			lifeSaverButton.setOnClickListener(null);
			
			if(roundNo == 10){
				SharedPreferences gameData = getSharedPreferences("modesCompleted", 0);
				SharedPreferences.Editor editor = gameData.edit();
				boolean beatBefore;
				
				switch(gameMode){
				case 0:
					beatBefore = gameData.getBoolean("cEasy", false);
					editor.putBoolean("cEasy", true);
					break;
				case 1:
					beatBefore = gameData.getBoolean("cMedium", false);
					editor.putBoolean("cMedium", true);
					break;
				case 2:
					beatBefore = gameData.getBoolean("cHard", false);
					editor.putBoolean("cHard", true);
					break;
				case 3:
					beatBefore = gameData.getBoolean("freeLunch", false);
					editor.putBoolean("freeLunch", true);
					break;
				case 4:
					beatBefore = gameData.getBoolean("noFrills", false);
					editor.putBoolean("noFrills", true);
					break;
				case 5:
					beatBefore = gameData.getBoolean("economy", false);
					editor.putBoolean("economy", true);
					break;
				case 6:
					beatBefore = gameData.getBoolean("fortune", false);
					editor.putBoolean("fortune", true);
					break;
				case 7:
					beatBefore = gameData.getBoolean("quadLife", false);
					editor.putBoolean("quadLife", true);
					break;
				default:
					beatBefore = false;
				}
				String[] beatComments = getResources().getStringArray(R.array.beatComments);
				String[] modes = getResources().getStringArray(R.array.modeNames);
				
				System.out.println(beatComments[gameMode]);
				
				EndGameDialog edg = new EndGameDialog(modes[gameMode], beatComments[gameMode], beatBefore);
				edg.show(getFragmentManager(), "win");
				
				editor.commit();
			}
			
			else{
				display.setText(p.currentState() + "\nPress Next to continue.");
				display.setGravity(17);
				
				nextRoundButton.setVisibility(View.VISIBLE);
				nextRoundButton.setOnClickListener(new View.OnClickListener() {
				
					@Override
					public void onClick(View v) {
						Intent nextRound = new Intent(getApplicationContext(), CategoryActivity.class);
						nextRound.putExtra("mode", gameMode);
						nextRound.putExtra("round", roundNo + 1);
						nextRound.putExtra("livesLeft", lives);
						nextRound.putExtra("categories", categories);
						nextRound.putExtra("fg", fgUsed);
						nextRound.putExtra("le", leUsed);
						nextRound.putExtra("lr", lrUsed);
						nextRound.putExtra("ls", lsUsed);
						startActivity(nextRound);
					}
				});
			}
		}
	}
	
	public void onStop(){
		super.onStop();
		this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
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
