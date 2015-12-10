package sg.com.yahoo.ryanlouck.hang10;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class GameSelectActivity extends Activity {
	
	private Button backButton, cEasy, cMedium, cHard, freeLunch, noFrills, economy, fortune, quadLife, endurance;
	private View.OnClickListener gameStart;
	private FragmentManager fm;
	private SharedPreferences gameData;
	private String[] gameModes, modeDescs, unlockReqs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
		setContentView(R.layout.activity_game_select);
		
		gameModes = getResources().getStringArray(R.array.modeNames);
		modeDescs = getResources().getStringArray(R.array.modeDesc);
		unlockReqs = getResources().getStringArray(R.array.unlockReqs);
		
		cEasy = (Button) findViewById(R.id.cEasy);
		cMedium = (Button) findViewById(R.id.cMedium);
		cHard = (Button) findViewById(R.id.cHard);
		freeLunch = (Button) findViewById(R.id.freeLunch);
		noFrills = (Button) findViewById(R.id.noFrills);
		economy = (Button) findViewById(R.id.economy);
		fortune = (Button) findViewById(R.id.fortune);
		quadLife = (Button) findViewById(R.id.quadLife);
		endurance = (Button) findViewById(R.id.endurance);
		
		fm = getFragmentManager();
		
		gameData = getSharedPreferences("modesCompleted", 0);
		
		gameStart = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch(v.getId()){
				case R.id.cEasy:
					NewGameDialog ngd0 = new NewGameDialog(0, true, true, gameModes[0], unlockReqs[0], modeDescs[0]);
					ngd0.show(fm, "start");
					break;
				case R.id.cMedium:
					NewGameDialog ngd1 = new NewGameDialog(1, gameData.getBoolean("cEasy", false), true, gameModes[1], unlockReqs[1], modeDescs[1]);
					ngd1.show(fm, "start");
					break;
				case R.id.cHard:
					NewGameDialog ngd2 = new NewGameDialog(2, gameData.getBoolean("cMedium", false), true, gameModes[2], unlockReqs[2], modeDescs[2]);
					ngd2.show(fm, "start");
					break;
				case R.id.freeLunch:
					NewGameDialog ngd3 = new NewGameDialog(3, gameData.getBoolean("cEasy", false), true, gameModes[3], unlockReqs[3], modeDescs[3]);
					ngd3.show(fm, "start");
					break;
				case R.id.noFrills:
					NewGameDialog ngd4 = new NewGameDialog(4, gameData.getBoolean("freeLunch", false), true, gameModes[4], unlockReqs[4], modeDescs[4]);
					ngd4.show(fm, "start");
					break;
				case R.id.economy:
					NewGameDialog ngd5 = new NewGameDialog(5, gameData.getBoolean("freeLunch", false), gameData.getBoolean("cMedium", false), gameModes[5], unlockReqs[5], modeDescs[5]);
					ngd5.show(fm, "start");
					break;
				case R.id.fortune:
					NewGameDialog ngd6 = new NewGameDialog(6, gameData.getBoolean("noFrills", false), gameData.getBoolean("economy", false), gameModes[6], unlockReqs[6], modeDescs[6]);
					ngd6.show(fm, "start");
					break;
				case R.id.quadLife:
					NewGameDialog ngd7 = new NewGameDialog(7, gameData.getBoolean("cHard", false), gameData.getBoolean("economy", false), gameModes[7], unlockReqs[7], modeDescs[7]);
					ngd7.show(fm, "start");
					break;
				case R.id.endurance:
					NewGameDialog ngd8 = new NewGameDialog(8, gameData.getBoolean("fortune", false), gameData.getBoolean("quadLife", false), gameModes[8], unlockReqs[8], modeDescs[8]);
					ngd8.show(fm, "start");
					break;
				}			
			}
		};
		
		cEasy.setOnClickListener(gameStart);
		cMedium.setOnClickListener(gameStart);
		cHard.setOnClickListener(gameStart);
		freeLunch.setOnClickListener(gameStart);
		noFrills.setOnClickListener(gameStart);
		economy.setOnClickListener(gameStart);
		fortune.setOnClickListener(gameStart);
		quadLife.setOnClickListener(gameStart);
		endurance.setOnClickListener(gameStart);
		
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
		getMenuInflater().inflate(R.menu.game_select, menu);
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
