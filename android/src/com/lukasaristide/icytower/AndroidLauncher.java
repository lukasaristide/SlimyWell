package com.lukasaristide.icytower;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.room.Room;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.lukasaristide.icytower.IcyTower;

public class AndroidLauncher extends AndroidApplication {
	private DatabaseAndroid db;
	private SavedSettings savedSettings;
	private SharedPreferences data;
	private SharedPreferences.Editor editor;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db = new DatabaseAndroid(getApplicationContext());

		data = getPreferences(Context.MODE_PRIVATE);
		editor = data.edit();

		savedSettings = new SavedSettings();

		savedSettings.mod_tilt = data.getFloat("mod_tilt", 1);
		savedSettings.mod_speed = data.getFloat("mod_speed", 1);
		savedSettings.mod_jump = data.getFloat("mod_jump", 1);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = true;
		initialize(new IcyTower(db, savedSettings), config);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.close();

		editor.putFloat("mod_tilt", savedSettings.mod_tilt).putFloat("mod_speed", savedSettings.mod_speed).putFloat("mod_jump", savedSettings.mod_jump);
		editor.apply();
	}
}
