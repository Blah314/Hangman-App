package sg.com.yahoo.ryanlouck.hang10;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HelpActivity extends Activity {
	
	private Typeface font;
	private ImageButton backButton;
	private TextView header;
	private RelativeLayout powerupTable;
	private TextView[] helpPages;
	private TextView[] powerUpDescs;
	private String[] helpHeaders, help;
	private Button[] helpButtons;
	private View.OnClickListener helpButtonListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
		setContentView(R.layout.activity_help);
		
		loadGlobalAssets();
		setUI();
		setListeners();
		configureUI();
		setInitialPage();	
	}
	
	private void loadGlobalAssets(){
		help = getResources().getStringArray(R.array.instructions);
		helpHeaders = getResources().getStringArray(R.array.instructionPages);		
		font = Typeface.createFromAsset(getAssets(), "caballar.ttf");
	}
	
	private void setUI(){
		header = (TextView) findViewById(R.id.helpTitle);
		powerupTable = (RelativeLayout) findViewById(R.id.powerupTable);
		backButton = (ImageButton) findViewById(R.id.backButton);
		
		helpPages = new TextView[]{(TextView) findViewById(R.id.page0), 
				(TextView) findViewById(R.id.page1),
				(TextView) findViewById(R.id.page2)};
		
		powerUpDescs = new TextView[]{(TextView) findViewById(R.id.powerupHeader),
				(TextView) findViewById(R.id.fgDesc),
				(TextView) findViewById(R.id.lsDesc),
				(TextView) findViewById(R.id.lrDesc),
				(TextView) findViewById(R.id.leDesc)};
		
		helpButtons = new Button[]{(Button) findViewById(R.id.button1),
				(Button) findViewById(R.id.button2),
				(Button) findViewById(R.id.button3)};
	}
	
	private void setListeners(){
		
		backButton.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		helpButtonListener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(v == helpButtons[helpButtons.length - 1]){ // last button - powerup screen
					for(TextView page : helpPages){
						page.setVisibility(View.INVISIBLE);
					}
					powerupTable.setVisibility(View.VISIBLE);
					
				} else{
					powerupTable.setVisibility(View.INVISIBLE);
					helpPages[0].setVisibility(View.INVISIBLE);
					
					for(int i = 0; i < helpButtons.length - 1; i++){
						if(v == helpButtons[i]){
							helpPages[i + 1].setVisibility(View.VISIBLE);
						} else{
							helpPages[i + 1].setVisibility(View.INVISIBLE);
						}
					}
				}
			}
		};
	}
	
	private void configureUI(){
		header.setTypeface(font);
		
		for(int i = 0; i < help.length; i++){
			helpPages[i].setVisibility(View.INVISIBLE);
			helpPages[i].setTypeface(font);
			helpPages[i].setText(help[i]);
		}
		
		for(int i = 0; i < helpHeaders.length; i++){
			helpButtons[i].setTypeface(font);
			helpButtons[i].setText(helpHeaders[i]);
		}
		
		for(TextView v : powerUpDescs){
			v.setTypeface(font);
		}
		
		for(Button b : helpButtons){
			b.setTypeface(font);
			b.setOnClickListener(helpButtonListener);
		}
	}
	
	private void setInitialPage(){
		powerupTable.setVisibility(View.INVISIBLE);
		helpPages[0].setVisibility(View.VISIBLE);
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
