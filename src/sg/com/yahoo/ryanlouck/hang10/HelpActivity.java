package sg.com.yahoo.ryanlouck.hang10;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class HelpActivity extends Activity {
	
	private Typeface font;
	private ImageButton backButton, prevButton, nextButton;
	private TextView header, pageCounter, helpScreen;
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
		
		backButton = (ImageButton) findViewById(R.id.backButton);
		prevButton = (ImageButton) findViewById(R.id.prevButton);
		nextButton = (ImageButton) findViewById(R.id.nextButton);
		
		font = Typeface.createFromAsset(getAssets(), "caballar.ttf");
		
		header = (TextView) findViewById(R.id.helpTitle);
		pageCounter = (TextView) findViewById(R.id.pageCounter);
		helpScreen = (TextView) findViewById(R.id.helpScreen);
		
		header.setTypeface(font);
		pageCounter.setTypeface(font);
		helpScreen.setTypeface(font);
		
		helpScreen.setText(help[0]);
		pageCounter.setText("Page " + Integer.toString(pgNum) + " of " + Integer.toString(help.length));
		
		backButton.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				finish();
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
