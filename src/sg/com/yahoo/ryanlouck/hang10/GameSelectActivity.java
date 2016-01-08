package sg.com.yahoo.ryanlouck.hang10;

import android.app.Activity;
import android.app.FragmentManager;
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

public class GameSelectActivity extends Activity {
	
	private Typeface font;
	private TextView header;
	private Button[] modeButtons;
	private View.OnClickListener gameStart;
	private FragmentManager fm;
	private SharedPreferences gameData;
	private String[] gameModes, modeDescs, unlockReqs;
	private ImageButton backButton;
	private boolean[] gameModesCompleted, modesUnlocked;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
		setContentView(R.layout.activity_game_select);
		
		gameModes = getResources().getStringArray(R.array.modeNames);
		modeDescs = getResources().getStringArray(R.array.modeDesc);
		unlockReqs = getResources().getStringArray(R.array.unlockReqs);
		
		font = Typeface.createFromAsset(getAssets(), "caballar.ttf");
		
		header = (TextView) findViewById(R.id.title);
		header.setTypeface(font);
		
		modeButtons = new Button[]{(Button) findViewById(R.id.cEasy), (Button) findViewById(R.id.cMedium),
				(Button) findViewById(R.id.cHard), (Button) findViewById(R.id.freeLunch), (Button) findViewById(R.id.noFrills),
				(Button) findViewById(R.id.escalation), (Button) findViewById(R.id.fortune), (Button) findViewById(R.id.quadLife),
				(Button) findViewById(R.id.endurance)};
		
		fm = getFragmentManager();
		
		gameData = getSharedPreferences("modesCompleted", 0);
		
		gameModesCompleted = new boolean[]{gameData.getBoolean("cEasy", false), gameData.getBoolean("cMedium", false),
				gameData.getBoolean("cHard", false), gameData.getBoolean("freeLunch", false), gameData.getBoolean("noFrills", false),
				gameData.getBoolean("escalation", false), gameData.getBoolean("fortune", false), gameData.getBoolean("quadLife", false), false};
		
		modesUnlocked = new boolean[]{true, gameModesCompleted[0], gameModesCompleted[1],
				gameModesCompleted[0], gameModesCompleted[3], gameModesCompleted[1] && gameModesCompleted[3],
				gameModesCompleted[4] && gameModesCompleted[5], gameModesCompleted[2] && gameModesCompleted[4],
				gameModesCompleted[6] && gameModesCompleted[7]};
		
		gameStart = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				for(int i = 0; i < 9; i++){
					if(v == modeButtons[i]){
						NewGameDialog ndg = new NewGameDialog(i, modesUnlocked[i], gameModes[i], unlockReqs[i], modeDescs[i]);
						ndg.show(fm, "newGame");
						break;
					}
				}
			}
		};
		
		for(int i = 0; i < 9; i++){
			modeButtons[i].setOnClickListener(gameStart);
			modeButtons[i].setTypeface(font);
			if(gameModesCompleted[i]){
				modeButtons[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.greenbox));
			}
			else if(!modesUnlocked[i]){
				modeButtons[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.redbox));
			}
		}
		
		backButton = (ImageButton) findViewById(R.id.backButton);
		
		backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
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
