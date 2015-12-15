package sg.com.yahoo.ryanlouck.hang10;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EndGameDialog extends DialogFragment {
	
	private String gameMode, beatComment;
	private boolean hasBeat;
	
	public EndGameDialog(String gameMode, String beatComment, boolean beatBefore){
		this.gameMode = gameMode;
		this.beatComment = beatComment;
		this.hasBeat = beatBefore;
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LinearLayout l = new LinearLayout(this.getActivity());
        TextView body = new TextView(this.getActivity());
        
        builder.setTitle(R.string.gameWon);
        
        if(hasBeat){
        	body.setText("You completed " + gameMode + " mode.\n\n" + R.string.alreadyBeaten);
        }
        else{
        	body.setText("You completed " + gameMode + " mode.\n\n" + beatComment);
        }
        
        l.setOrientation(1);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1,-2);
        l.addView(body, params);
        body.setGravity(17);        
        builder.setView(l);
        
        builder.setNegativeButton(R.string.backButton, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent mainLaunch = new Intent(getActivity().getApplicationContext(), MainActivity.class);
				startActivity(mainLaunch);
				dialog.cancel();
			}
		});
        
        return builder.create();
	}
}
