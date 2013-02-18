package net.rudsu.howmuchtopay;

import java.io.InputStream;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
//import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
/*
 * disable due to import from ABSherlock
 * import android.view.Menu;
 * import android.view.MenuItem;
 * 
 */
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
//import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.app.SherlockActivity;

public class MainActivity extends SherlockActivity {
	private EditText numInvestmentValue;
	private EditText numFeeValue;
	private TextView resultValue;
	
	private static final String KEY_AMOUNT = "numAmount";
	private static final String KEY_FEE = "numFee";
	private static final String KEY_RESULT = "numResult";
	
	private static final String CLASS_TAG = "MainActivity";
	
//	private static final String PREFS_NAME = "net.rudsu.howmuchtopay";
//	private static final String PREFS_DARK_THEME = "darkTheme";
	
	// TODO: add advertisement in the bottom part of the screen (not applicable due to adv need dev account which need US$25 to register
	// TODO: maybe add landscape orientation UI (currently not broken in ldpi screen)
	// TODO: add widget (is this appropriate for this app?)
	// TODO: apply HoloEverywhere (if applicable)
	// TODO: add about activity
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Read preference
		//restorePref();
		
		setContentView(R.layout.activity_main);
		
		// States
		numInvestmentValue = (EditText) findViewById(R.id.numInvestmentValue);
		numFeeValue = (EditText) findViewById(R.id.numFeeValue);
		resultValue = (TextView) findViewById(R.id.txtResult);
		
		restoreState(savedInstanceState);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		// disable due to change activity -> sherlock activity
		//getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void CalculateOnClick(View view) {
		switch (view.getId()) {
		case R.id.btnCalculate:
			if(numInvestmentValue.getText().length() == 0) {
				Toast.makeText(this, R.string.amount_warn, Toast.LENGTH_LONG).show();
				return;
			}
			if(numFeeValue.getText().length() == 0) {
				Toast.makeText(this, R.string.fee_warn, Toast.LENGTH_LONG).show();
				return;
			}
			
			float amount = Float.parseFloat(numInvestmentValue.getText().toString());
			float fee = Float.parseFloat(numFeeValue.getText().toString());
			
			resultValue.setText(String.valueOf(calculateFee(amount, fee)));
			
			break;
		}
	}
	
	private float calculateFee(float amount, float fee) {
		return (amount * 100)/(100-fee);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
//		case R.id.submenu_settings:
//			showSettingsActivity();
//			break;
//		
		case R.id.menu_share:
			showShareActivity();
			break;
			
		case R.id.submenu_license:
			showLicenseActivity();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState(outState);
	}

	private void saveState(Bundle state) {
		state.putString(KEY_AMOUNT, numInvestmentValue.getText().toString());
		state.putString(KEY_FEE, numFeeValue.getText().toString());
		state.putString(KEY_RESULT, resultValue.getText().toString());
	}

//	@Override
//	protected void onRestoreInstanceState(Bundle savedInstanceState) {
//		super.onRestoreInstanceState(savedInstanceState);
//		restoreState(savedInstanceState);
//	}

	private void restoreState(Bundle state) {
		if (state != null) {
			numInvestmentValue.setText(state.getString(KEY_AMOUNT));
			numFeeValue.setText(state.getString(KEY_FEE));
			resultValue.setText(state.getString(KEY_RESULT));
			
			Log.i(CLASS_TAG,"restoring state...");
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		restoreState(savedInstanceState);
	}
	
//	private void showSettingsActivity() {
//		Intent intent = new Intent(this, SettingsActivity.class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		startActivity(intent);
//	}
	
	private void showShareActivity() {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		String sendMessage = getResources().getString(R.string.share_text);
//		String sendMessage = "You need to pay " + String.valueOf(resultValue.getText()) + 
//				" for amount of " + numInvestmentValue.getText() +
//				" with fee of " + numFeeValue.getText() + "%";
		sendMessage = sendMessage
				.replace("$$1", String.valueOf(resultValue.getText()))
				.replace("$$2", numInvestmentValue.getText())
				.replace("$$3", numFeeValue.getText());
		sendIntent.putExtra(Intent.EXTRA_TEXT, sendMessage);
		sendIntent.setType("text/plain");
		startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share_to)));
	}
	
	private void showLicenseActivity() {
		String message;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		// start read text message
		try {
	        Resources res = getResources();
	        InputStream in_s = res.openRawResource(R.raw.readme);

	        byte[] b = new byte[in_s.available()];
	        in_s.read(b);
	        message = new String(b);
	        //txtHelp.setText(new String(b));
	    } catch (Exception e) {
	        // e.printStackTrace();
	        //txtHelp.setText("Error: can't show help.");
	    	message = "error showing readme";
	    }
		
		builder.setMessage(message)
			.setTitle("License");
		builder.setPositiveButton(R.string.ok_text, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		AlertDialog dialog = builder.create();
		
		dialog.show();
	}
	
//	private void restorePref() {
//		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
//		boolean darkTheme = false;
//		
//		darkTheme = settings.getBoolean("darkTheme", false);
//		
//		if (!darkTheme) {
//			setTheme(R.style.Sherlock___Theme_Light);
//		}
//		else {
//			setTheme(R.style.Sherlock___Theme_DarkActionBar);
//		}
//	}
}
