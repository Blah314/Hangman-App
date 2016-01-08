package sg.com.yahoo.ryanlouck.hang10;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
	
	private Phrase p;
	private Typeface font;
	private int gameMode, roundNo, lives;
	private ArrayList<Category> categories;
	private Category currCat;
	private boolean fgUsed, leUsed, lrUsed, lsUsed;
	private boolean fgActive, lsActive;
	private TextView round, livesLeft, title, display;
	private Button[] letterButtons;
	private ImageButton backButton, nextRoundButton;
	private ImageButton freeGuessButton, letterElimButton, letterRevealButton, lifeSaverButton;
	private View.OnClickListener letterChooser, fgUser, leUser, lrUser, lsUser;
	private String[] enduranceComments;

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
		if(gameMode == 6) p.doFortune(); // fortune mode check
		fgActive = false;
		lsActive = false;
		enduranceComments = getResources().getStringArray(R.array.enduranceComments);
		
		font = Typeface.createFromAsset(getAssets(), "caballar.ttf");
		
		round = (TextView) findViewById(R.id.roundNum);
		livesLeft = (TextView) findViewById(R.id.lives);
		title = (TextView) findViewById(R.id.categoryName);
		display = (TextView) findViewById(R.id.phrase);
		
		round.setTypeface(font);
		livesLeft.setTypeface(font);
		title.setTypeface(font);
		display.setTypeface(font);
		
		round.setText("Round: " + Integer.toString(roundNo));
		title.setText(currCat.getName());
		display.setText(p.currentState());
		livesLeft.setText("Lives Left: " + Integer.toString(lives));
		
		// free Lunch Mode checking
		if(gameMode == 3){
			fgUsed = false;
			leUsed = true;
			lrUsed = true;
			lsUsed = true;
		}
		
		// no frills mode checking
		if(gameMode == 4){
			fgUsed = true;
			leUsed = true;
			lrUsed = true;
			lsUsed = true;
		}
		
		// quadlife mode checking
		if(gameMode == 7){
			fgUsed = true;
			leUsed = true;
			lrUsed = true;
			lsUsed = true;
			lives = 4;
		}
		
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
							if(!correct){
								if(!fgActive){
									if(gameMode != 5){ // escalation mode checking
										lives -= 1;
									}
									else{
										lives -= roundNo;
									}
								}
							}
							else if(lsActive){
								lives += p.getLastGuess();
							}
							
							else if(gameMode == 5){ // escalation mode checking
								lives += 1;
							}
							
							if(fgActive){
								fgActive = false;
								freeGuessButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.redbox));
							}
							if(lsActive){
								lsActive = false;
								lifeSaverButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.redbox));
							}
							updateFields();
							v.setBackgroundDrawable(getResources().getDrawable(R.drawable.redbox));
						}			
					}
				}
			}
		};
		
		for(Button b : letterButtons){
			b.setOnClickListener(letterChooser);
			b.setBackgroundDrawable(getResources().getDrawable(R.drawable.blankbox));
			b.setGravity(17);
			b.setTypeface(font);
		}
		
		freeGuessButton = (ImageButton) findViewById(R.id.freeGuess);
		letterElimButton = (ImageButton) findViewById(R.id.letterElim);
		letterRevealButton = (ImageButton) findViewById(R.id.letterReveal);
		lifeSaverButton = (ImageButton) findViewById(R.id.lifeSaver);
		
		if(fgUsed){
			freeGuessButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.redbox));
		}
		else{
			freeGuessButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.greenbox));
		}
		
		if(leUsed){
			letterElimButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.redbox));
		}
		else{
			letterElimButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.greenbox));
		}
		
		if(lrUsed){
			letterRevealButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.redbox));
		}
		else{
			letterRevealButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.greenbox));
		}
		
		if(lsUsed){
			lifeSaverButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.redbox));
		}
		else{
			lifeSaverButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.greenbox));
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
		
		backButton = (ImageButton) findViewById(R.id.backButton);
		
		backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		nextRoundButton = (ImageButton) findViewById(R.id.nextRoundButton);
		nextRoundButton.setVisibility(View.INVISIBLE);
	}
	
	public void fgActivate(){
		fgActive = true;
		fgUsed = true;
		freeGuessButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.yellowbox));
	}
	
	public void leActivate(){
		leUsed = true;
		boolean[] elim = p.letterElim();
		for(int i = 0; i < 26; i++){
			if(elim[i]){
				letterButtons[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.redbox));
			}
		}
		letterElimButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.redbox));
		updateFields();
	}
	
	public void lrActivate(){
		lrUsed = true;
		char c = p.letterReveal();
		letterRevealButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.redbox));
		for(int i = 0; i < 26; i++){
			if(c == LETTERS[i]){
				letterButtons[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.redbox));
				break;
			}
		}
		updateFields();
	}
	
	public void lsActivate(){
		lsActive = true;
		lsUsed = true;
		lifeSaverButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.yellowbox));
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
			freeGuessButton.setOnClickListener(null);
			letterElimButton.setOnClickListener(null);
			letterRevealButton.setOnClickListener(null);
			lifeSaverButton.setOnClickListener(null);
			
			if(gameMode == 8){ // endurance mode checking
				title.setText("GAME OVER. You made it to Round " + Integer.toString(roundNo) + ".");
				if(roundNo <= 5){
					display.setText(enduranceComments[0]);
				}
				else if(roundNo <= 10){
					display.setText(enduranceComments[1]);
				}
				else if(roundNo <= 15){
					display.setText(enduranceComments[2]);
				}
				else if(roundNo <= 20){
					display.setText(enduranceComments[3]);
				}
				else if(roundNo <= 25){
					display.setText(enduranceComments[4]);
				}
				else if(roundNo <= 30){
					display.setText(enduranceComments[5]);
				}
				else{
					display.setText(enduranceComments[6]);
				}
			}
		}
		
		if(p.isSolved() || gameMode == 6 && p.isFortuneSolved()){ // fortune mode checking
			round.setText("Round " + Integer.toString(roundNo) + " complete!");
			
			for(Button b : letterButtons){
				b.setOnClickListener(null);
			}
			
			freeGuessButton.setOnClickListener(null);
			letterElimButton.setOnClickListener(null);
			letterRevealButton.setOnClickListener(null);
			lifeSaverButton.setOnClickListener(null);
			
			if(roundNo == 10 && gameMode != 8){ // endurance mode checking
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
					beatBefore = gameData.getBoolean("escalation", false);
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
						finish();
					}
				});
			}
		}
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
