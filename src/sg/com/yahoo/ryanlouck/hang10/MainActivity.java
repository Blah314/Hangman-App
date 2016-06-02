package sg.com.yahoo.ryanlouck.hang10;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private ImageButton playButton, continueButton, helpButton, optionsButton;
	private boolean fileFound;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
		setContentView(R.layout.activity_main);
		
		loadButtons();
	}
	
	private void loadButtons(){
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
		
		continueButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {		
				ArrayList<String[]> saveDetails = findSaveGame();
				
				if(fileFound){
					startSavedGame(saveDetails);
				}			
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
	
	private ArrayList<String[]> findSaveGame(){
		fileFound = true;
		ArrayList<String[]> saveDetails = new ArrayList<String[]>();
		
		// try to find the savegame file
		try{
			FileInputStream fis = openFileInput("savegame");
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			while(br.ready()){
				saveDetails.add(br.readLine().split(","));
			}
			br.close();
		}
		
		// file not found - raise a toast and set fileFound to false
		catch(FileNotFoundException fnfe){
			Context c = getApplicationContext();
			CharSequence text = getResources().getString(R.string.noSaveGame);
			int duration = Toast.LENGTH_SHORT;
				
			Toast t = Toast.makeText(c, text, duration);
			t.show();
			fileFound = false;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return saveDetails;
	}
	
	private void startSavedGame(ArrayList<String[]> saveDetails){
		Intent continueLaunch = new Intent(getApplicationContext(), CategoryActivity.class);
		continueLaunch.putExtra("resumed", true);
		
		String[] globalDetails = saveDetails.get(0);
		continueLaunch.putExtra("mode", Integer.parseInt(globalDetails[0]));
		continueLaunch.putExtra("round", Integer.parseInt(globalDetails[2]));
		continueLaunch.putExtra("livesLeft", Integer.parseInt(globalDetails[1]));
		
		String[] powerupDetails = saveDetails.get(1);
		continueLaunch.putExtra("fg", Boolean.parseBoolean(powerupDetails[0]));
		continueLaunch.putExtra("le", Boolean.parseBoolean(powerupDetails[1]));
		continueLaunch.putExtra("lr", Boolean.parseBoolean(powerupDetails[2]));
		continueLaunch.putExtra("ls", Boolean.parseBoolean(powerupDetails[3]));
		
		String[] categories = saveDetails.get(2);
		String[] categoryStatus = saveDetails.get(3);
		continueLaunch.putExtra("resCats", categories);
		continueLaunch.putExtra("resCatStatus", categoryStatus);
		
		startActivity(continueLaunch);
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
