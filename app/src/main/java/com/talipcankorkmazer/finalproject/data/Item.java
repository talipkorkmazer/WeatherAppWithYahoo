package com.talipcankorkmazer.finalproject.data;

import org.json.JSONObject;

/**
 * Created by talipcankorkmazer on 26/05/2017.
 */

public class Item implements JSONPapulator {
	private Condition mCondition;

	public Condition getCondition() {
		return mCondition;
	}

	@Override
	public void populate(JSONObject data) {
		mCondition = new Condition();
		mCondition.populate(data.optJSONObject("condition"));
	}
}
