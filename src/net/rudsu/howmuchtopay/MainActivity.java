package net.rudsu.howmuchtopay;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText numInvestmentValue;
	private EditText numFeeValue;
	private TextView resultValue;
	
	// TODO: add interactive to Setting when click, maybe jump to other screen
	// TODO: add advertisement in the bottom part of the screen
	// TODO: maybe add landscape orientation UI
	// TODO: add widget (is this appropriate for this app?)
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		numInvestmentValue = (EditText) findViewById(R.id.numInvestmentValue);
		numFeeValue = (EditText) findViewById(R.id.numFeeValue);
		resultValue = (TextView) findViewById(R.id.txtResult);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
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
		case R.id.menu_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

}
