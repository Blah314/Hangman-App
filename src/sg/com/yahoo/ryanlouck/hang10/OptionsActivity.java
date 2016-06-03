package sg.com.yahoo.ryanlouck.hang10;

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

public class OptionsActivity extends Activity {
	
	private Typeface font;
	private TextView optionsTitle;
	private Button hardResetButton, highscoreResetButton;
	private ImageButton backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
		setContentView(R.layout.activity_options);
		
		loadAssets();
		setButtons();
	}
	
	private void loadAssets(){	
		font = Typeface.createFromAsset(getAssets(), "caballar.ttf");
		
		optionsTitle = (TextView) findViewById(R.id.titleOptions);
		hardResetButton = (Button) findViewById(R.id.hardResetButton);
		highscoreResetButton = (Button) findViewById(R.id.highscoreResetButton);
		backButton = (ImageButton) findViewById(R.id.backButton);
		
		optionsTitle.setTypeface(font);
		hardResetButton.setTypeface(font);
		highscoreResetButton.setTypeface(font);
		
	}
	
	private void setButtons(){
		
		hardResetButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences gameData = getSharedPreferences("modesCompleted", 0);
				SharedPreferences.Editor editor = gameData.edit();
				
				editor.putBoolean("cEasy", false);
				editor.putBoolean("cMedium", false);
				editor.putBoolean("cHard", false);
				editor.putBoolean("freeLunch", false);
				editor.putBoolean("noFrills", false);
				editor.putBoolean("escalation", false);
				editor.putBoolean("fortune", false);
				editor.putBoolean("quadLife", false);
				
				editor.commit();
			}
		});
		
		highscoreResetButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences hs = getSharedPreferences("highscore", 0);
				SharedPreferences.Editor editor = hs.edit();
				
				editor.putInt("endurance", 0);
				editor.commit();
			}
		});
		
		
		backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.options, menu);
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
