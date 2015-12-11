package sg.com.yahoo.ryanlouck.hang10;

import java.util.ArrayList;

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
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {
	
	final char[] LETTERS = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
			'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	final PorterDuffColorFilter YELLOW = new PorterDuffColorFilter(Color.YELLOW, PorterDuff.Mode.OVERLAY);
	final PorterDuffColorFilter RED = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.OVERLAY);
	
	private Phrase p;
	private int gameMode, roundNo, lives;
	private ArrayList<Category> categories;
	private Category currCat;
	private TextView round, livesLeft, title, display;
	private Button[] letterButtons;
	private Button backButton;
	private View.OnClickListener letterChooser;

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
		int chosenCat = gameDetails.getInt("chosenCat");
		categories = (ArrayList<Category>) gameDetails.getSerializable("categories");
		currCat = categories.get(chosenCat);
		p = new Phrase(gameDetails.getString("next"));
		
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
							if(!correct) lives -= 1;
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
		
		backButton = (Button) findViewById(R.id.backButton);
		
		backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent mainLaunch = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(mainLaunch);
			}
		});
	}
	
	public void updateFields(){
		display.setText(p.currentState());
		livesLeft.setText("Lives Left: " + Integer.toString(lives));
		if(p.isSolved()){
			CharSequence text = getResources().getString(R.string.yay);
			int duration = Toast.LENGTH_SHORT;
			
			Toast t = Toast.makeText(getApplicationContext(), text, duration);
			t.show();
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
