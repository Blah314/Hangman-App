package sg.com.yahoo.ryanlouck.hang10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class MainActivity extends Activity {
	
	private ImageButton playButton, continueButton, helpButton, optionsButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
		setContentView(R.layout.activity_main);
		
		playButton = (ImageButton) findViewById(R.id.playButton);
		continueButton = (ImageButton) findViewById(R.id.continueButton);
		helpButton = (ImageButton) findViewById(R.id.helpButton);
		optionsButton = (ImageButton) findViewById(R.id.optionsButton);
		
		playButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent playLaunch = new Intent(getApplicationContext(), GameSelectActivity.class);
				startActivity(playLaunch);
			}
		});
		
		helpButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent helpLaunch = new Intent(getApplicationContext(), HelpActivity.class);
				startActivity(helpLaunch);
			}
		});
		
		optionsButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent optionsLaunch = new Intent(getApplicationContext(), OptionsActivity.class);
				startActivity(optionsLaunch);
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
		getMenuInflater().inflate(R.menu.main, menu);
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
