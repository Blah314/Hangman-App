package sg.com.yahoo.ryanlouck.hang10;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HelpActivity extends Activity {
	
	private Button backButton, prevButton, nextButton;
	private TextView pageCounter, helpScreen;
	private int pgNum;
	private String[] help;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
		setContentView(R.layout.activity_help);
		
		pgNum = 1;
		help = getResources().getStringArray(R.array.instructions);
		
		backButton = (Button) findViewById(R.id.backButton);
		prevButton = (Button) findViewById(R.id.prevButton);
		nextButton = (Button) findViewById(R.id.nextButton);
		
		pageCounter = (TextView) findViewById(R.id.pageCounter);
		helpScreen = (TextView) findViewById(R.id.helpScreen);
		
		helpScreen.setText(help[0]);
		pageCounter.setText("Page " + Integer.toString(pgNum) + " of " + Integer.toString(help.length));
		
		backButton.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				Intent mainLaunch = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(mainLaunch);
			}
		});
		
		prevButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(pgNum == 1){
					Context c = getApplicationContext();
					CharSequence text = getResources().getString(R.string.firstPage);
					int duration = Toast.LENGTH_SHORT;
					
					Toast t = Toast.makeText(c, text, duration);
					t.show();
				}
				else{
					pgNum--;
					updateFields();
				}
			}
		});
		
		nextButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(pgNum == help.length){
					Context c = getApplicationContext();
					CharSequence text = getResources().getString(R.string.lastPage);
					int duration = Toast.LENGTH_SHORT;
					
					Toast t = Toast.makeText(c, text, duration);
					t.show();
				}
				else{
					pgNum++;
					updateFields();
				}
				
			}
		});
	}
	
	public void updateFields(){
		helpScreen.setText(help[pgNum - 1]);
		pageCounter.setText("Page " + Integer.toString(pgNum) + " of " + Integer.toString(help.length));
	}
	
	public void onStop(){
		super.onStop();
		this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help, menu);
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
