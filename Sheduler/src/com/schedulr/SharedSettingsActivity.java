package com.schedulr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SharedSettingsActivity extends Activity
{
    private static final String PREFS_NAME = "settings";
    protected SharedPreferences mPrefs;
    protected boolean mHalfFare;
    protected boolean mDayPass;
    protected boolean mShowTrains;
    protected boolean mShowBuses;
    public static final String mHalfFareStr = "half_fare";
    public static final String mDayPassStr = "day_pass";
    public static final String mShowTrainsStr = "show_trains";
    public static final String mShowBusesStr = "show_buses";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Load preferences
        mPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        mHalfFare = mPrefs.getBoolean(mHalfFareStr, true);
        mDayPass = mPrefs.getBoolean(mDayPassStr, false);
        mShowTrains = mPrefs.getBoolean(mShowTrainsStr, true);
        mShowBuses = mPrefs.getBoolean(mShowBusesStr, true);
    }

    public boolean getProperty(String propertyName) {
        return mPrefs.getBoolean(propertyName, true);
    }

    public List<String> getFavorites()
    {
        ArrayList<String> favourites = new ArrayList<String>();
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ShedulerContentProvider.CONTENT_URI_FAV, null, null, null, null);
        int keyTaskIndex = cursor.getColumnIndexOrThrow(ShedulerContentProvider.KEY_NAME);

        while (cursor.moveToNext())
        {
            favourites.add(cursor.getString(keyTaskIndex));
        }
        cursor.close();
        return favourites;
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putBoolean(mHalfFareStr, mHalfFare);
        ed.putBoolean(mDayPassStr, mDayPass);
        ed.putBoolean(mShowBusesStr, mShowBuses);
        ed.putBoolean(mShowTrainsStr, mShowTrains);
        ed.commit();
    }

    protected AlertDialog.Builder buildAlertDialog(int messageId) {
        return new AlertDialog.Builder(this).setMessage(messageId).setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id) { }
                });
    }
}
