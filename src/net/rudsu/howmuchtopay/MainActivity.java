package net.rudsu.howmuchtopay;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText numInvestmentValue;
	private EditText numFeeValue;
	private TextView resultValue;
	
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
				Toast.makeText(this, "Please enter a valid number in Amount", Toast.LENGTH_LONG).show();
				return;
			}
			if(numFeeValue.getText().length() == 0) {
				Toast.makeText(this, "Please enter a valid number in Fee", Toast.LENGTH_LONG).show();
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

}
