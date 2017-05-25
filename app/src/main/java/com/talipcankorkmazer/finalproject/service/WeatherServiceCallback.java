package com.talipcankorkmazer.finalproject.service;

import com.talipcankorkmazer.finalproject.data.Channel;

/**
 * Created by talipcankorkmazer on 26/05/2017.
 */

public interface WeatherServiceCallback {
	void serviceSuccess(Channel channel);
	void serviceFailure(Exception exception);
}
