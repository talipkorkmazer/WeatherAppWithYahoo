package com.talipcankorkmazer.finalproject.data;

import org.json.JSONObject;

/**
 * Created by talipcankorkmazer on 26/05/2017.
 */

public class Channel implements JSONPapulator {
	private Units mUnits;
	private Item mItem;

	public Units getUnits() {
		return mUnits;
	}

	public Item getItem() {
		return mItem;
	}

	@Override
	public void populate(JSONObject data) {
		mUnits = new Units();
		mUnits.populate(data.optJSONObject("units"));

		mItem = new Item();
		mItem.populate(data.optJSONObject("item"));
	}
}
