package com.talipcankorkmazer.finalproject;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.talipcankorkmazer.finalproject.data.Channel;
import com.talipcankorkmazer.finalproject.data.Item;
import com.talipcankorkmazer.finalproject.service.WeatherServiceCallback;
import com.talipcankorkmazer.finalproject.service.YahooWeatherService;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements WeatherServiceCallback {

	private ImageView weatherImageView;
	private TextView temperatureTextView;
	private TextView conditionTextView;
	private TextView locationTextView;

	private EditText locationEditText;
	private Button buttonLocation;

	private YahooWeatherService mService;
	private ProgressDialog mDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		weatherImageView = (ImageView)findViewById(R.id.weatherIconImageView);
		temperatureTextView = (TextView)findViewById(R.id.temperatureTextView);
		conditionTextView = (TextView)findViewById(R.id.conditionTextView);
		locationTextView = (TextView)findViewById(R.id.locationTextView);

		buttonLocation = (Button)findViewById(R.id.buttonLocationEditText);

		mService = new YahooWeatherService(this);
		mDialog = new ProgressDialog(this);
		mDialog.setMessage("Loading...");
		mDialog.show();
		mService.refreshWather("Istanbul");

		buttonLocation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				locationEditText = (EditText)findViewById(R.id.locationEditText);
				mDialog.setMessage("Loading...");
				mDialog.show();
				mService.refreshWather(locationEditText.getEditableText().toString());

			}
		});

	}

	@Override
	public void serviceSuccess(Channel channel) {

		mDialog.hide();

		Item item = channel.getItem();
		int resourceId = getResources().getIdentifier("drawable/icon_"+ item.getCondition().getCode(), null, getPackageName());

		@SuppressWarnings("deprecation")
		Drawable weatherIconDrawable = getResources().getDrawable(resourceId);

		weatherImageView.setImageDrawable(weatherIconDrawable);
		temperatureTextView.setText(item.getCondition().getTemperature() + "\u00B0" + channel.getUnits().getTemperature());
		conditionTextView.setText(item.getCondition().getDescription());
		locationTextView.setText(mService.getLocation());



	}

	@Override
	public void serviceFailure(Exception exception) {
		mDialog.hide();
		Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
	}
}
