package sg.com.yahoo.ryanlouck.hang10;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewGameDialog extends DialogFragment {
	
	private int gameMode;
	private boolean unlocked;
	private String modeTitle, modeUnlock, modeDesc;
	
	public NewGameDialog(int gM, boolean unlocked, String title, String unlock, String desc){
		this.gameMode = gM;
		this.unlocked = unlocked;
		this.modeTitle = title;
		this.modeUnlock = unlock;
		this.modeDesc = desc;
		
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {	
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LinearLayout l = new LinearLayout(this.getActivity());
        TextView body = new TextView(this.getActivity());
        
        builder.setTitle(modeTitle);
        
        if(unlocked){
        	body.setText("\n" + modeDesc);
        	builder.setPositiveButton(R.string.playButton, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent gameLaunch = new Intent(getActivity().getApplicationContext(), CategoryActivity.class);
					gameLaunch.putExtra("mode", gameMode);
					startActivity(gameLaunch);
				}
			});
        }
        
        else{
        	body.setText(modeUnlock);
        	builder.setPositiveButton(R.string.playButton, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					CharSequence text = getResources().getString(R.string.modeLocked);
					int duration = Toast.LENGTH_SHORT;
					
					Toast t = Toast.makeText(getActivity().getApplicationContext(), text, duration);
					t.show();
					
				}
			});
        }
        
        l.setOrientation(1);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1,-2);
        l.addView(body, params);
        body.setGravity(17);        
        builder.setView(l);
        
        builder.setNegativeButton(R.string.backButton, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
        
        return builder.create();
	}
}
