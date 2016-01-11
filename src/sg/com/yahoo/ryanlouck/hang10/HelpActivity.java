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
	private TextView header, page0, page1, page2, fgDesc, lsDesc, leDesc, lrDesc;
	private RelativeLayout powerupTable;
	private String[] helpHeaders, help;
	private Button help1, help2, help3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
		setContentView(R.layout.activity_help);
		
		help = getResources().getStringArray(R.array.instructions);
		helpHeaders = getResources().getStringArray(R.array.instructionPages);
		
		backButton = (ImageButton) findViewById(R.id.backButton);
		
		font = Typeface.createFromAsset(getAssets(), "caballar.ttf");
		
		header = (TextView) findViewById(R.id.helpTitle);
		page0 = (TextView) findViewById(R.id.page0);
		page1 = (TextView) findViewById(R.id.page1);
		page2 = (TextView) findViewById(R.id.page2);
		fgDesc = (TextView) findViewById(R.id.fgDesc);
		lsDesc = (TextView) findViewById(R.id.lsDesc);
		lrDesc = (TextView) findViewById(R.id.lrDesc);
		leDesc = (TextView) findViewById(R.id.leDesc);
		
		help1 = (Button) findViewById(R.id.button1);
		help2 = (Button) findViewById(R.id.button2);
		help3 = (Button) findViewById(R.id.button3);
		
		header.setTypeface(font);
		page0.setTypeface(font);
		page1.setTypeface(font);
		page2.setTypeface(font);
		fgDesc.setTypeface(font);
		lsDesc.setTypeface(font);
		lrDesc.setTypeface(font);
		leDesc.setTypeface(font);
		
		page0.setText(help[0]);
		page1.setText(help[1]);
		page2.setText(help[2]);
		
		powerupTable = (RelativeLayout) findViewById(R.id.powerupTable);
		
		help1.setTypeface(font);
		help2.setTypeface(font);
		help3.setTypeface(font);
		
		help1.setText(helpHeaders[0]);
		help2.setText(helpHeaders[1]);
		help3.setText(helpHeaders[2]);
		
		help1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				page0.setVisibility(View.INVISIBLE);
				page2.setVisibility(View.INVISIBLE);
				powerupTable.setVisibility(View.INVISIBLE);
				page1.setVisibility(View.VISIBLE);
			}
		});
		
		help2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				page0.setVisibility(View.INVISIBLE);
				page1.setVisibility(View.INVISIBLE);
				powerupTable.setVisibility(View.INVISIBLE);
				page2.setVisibility(View.VISIBLE);
			}
		});
		
		help3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				page0.setVisibility(View.INVISIBLE);
				page1.setVisibility(View.INVISIBLE);
				page2.setVisibility(View.INVISIBLE);
				powerupTable.setVisibility(View.VISIBLE);
			}
		});
		
		page1.setVisibility(View.INVISIBLE);
		page2.setVisibility(View.INVISIBLE);
		powerupTable.setVisibility(View.INVISIBLE);
		
		backButton.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	public void updateFields(int page){
		
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
