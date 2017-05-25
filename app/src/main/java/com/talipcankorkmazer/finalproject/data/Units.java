package com.talipcankorkmazer.finalproject.data;

import org.json.JSONObject;

/**
 * Created by talipcankorkmazer on 26/05/2017.
 */

public class Units implements JSONPapulator {
	private String temperature;

	public String getTemperature() {
		return temperature;
	}

	@Override
	public void populate(JSONObject data) {
		temperature = data.optString("temperature");
	}
}
