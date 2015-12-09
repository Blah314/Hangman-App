package sg.com.yahoo.ryanlouck.hang10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class GameSelectActivity extends Activity {
	
	private Button backButton, cEasy, cMedium, cHard, freeLunch, noFrills, economy, fortune, quadLife, endurance;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
		setContentView(R.layout.activity_game_select);
		
		cEasy = (Button) findViewById(R.id.cEasy);
		cMedium = (Button) findViewById(R.id.cMedium);
		cHard = (Button) findViewById(R.id.cHard);
		freeLunch = (Button) findViewById(R.id.freeLunch);
		noFrills = (Button) findViewById(R.id.noFrills);
		economy = (Button) findViewById(R.id.economy);
		fortune = (Button) findViewById(R.id.fortune);
		quadLife = (Button) findViewById(R.id.quadLife);
		endurance = (Button) findViewById(R.id.endurance);
		
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
