package com.talipcankorkmazer.finalproject.service;

import android.net.Uri;
import android.os.AsyncTask;

import com.talipcankorkmazer.finalproject.data.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by talipcankorkmazer on 26/05/2017.
 */

public class YahooWeatherService {
	private WeatherServiceCallback mCallback;
	private String location;
	private Exception error;


	public YahooWeatherService(WeatherServiceCallback callback) {
		mCallback = callback;
	}

	public String getLocation() {
		return location;
	}

	public void refreshWather(String l){

		this.location = l;

		new AsyncTask<String, Void, String>() {
			@Override
			protected String doInBackground(String... params) {
				String query = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='c'", params[0]);

				String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(query));

				try {
					URL url = new URL(endpoint);
					URLConnection connection = url.openConnection();
					InputStream inputStream = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
					StringBuilder result = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null){
						result.append(line);
					}
					return result.toString();

				} catch (Exception e) {
					error = e;
				}
				return null;
			}

			@Override
			protected void onPostExecute(String s) {
				if(s == null && error != null){
					mCallback.serviceFailure(error);
					return;
				}
				try {
					JSONObject data = new JSONObject(s);
					JSONObject queryResults = data.optJSONObject("query");
					int count = queryResults.optInt("count");
					if(count == 0){
						mCallback.serviceFailure(new LocationWeatherException("No weather info for " + location));
						return;
					}
					Channel channel = new Channel();
					channel.populate(queryResults.optJSONObject("results").optJSONObject("channel"));

					mCallback.serviceSuccess(channel);


				} catch (JSONException e) {
					mCallback.serviceFailure(e);
				}
			}
		}.execute(location);
	}
	public class LocationWeatherException extends Exception{
		public LocationWeatherException(String message) {
			super(message);
		}
	}
}
