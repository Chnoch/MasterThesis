package com.schedulr;

import android.app.ActionBar;
import android.app.LoaderManager;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import com.schedulr.transport.Busstop;
import com.schedulr.widgets.FavouritesAdapter;

import java.util.ArrayList;

public class Settings extends SharedSettingsActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private ArrayList<Busstop> favourites;
    private FavouritesAdapter favouritesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites);
        setTitle(R.string.settings);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        favourites = new ArrayList<Busstop>();
        favouritesAdapter = new FavouritesAdapter(this, R.layout.fav_row, favourites);
        ListView listView = (ListView)findViewById(R.id.fav);
        listView.setAdapter(favouritesAdapter);

        ((CheckBox)findViewById(R.id.checkbox_buses)).setChecked(mShowBuses);
        ((CheckBox)findViewById(R.id.checkbox_trains)).setChecked(mShowTrains);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, Sheduler.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, ShedulerContentProvider.CONTENT_URI_FAV, null, null, null, null);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int keyTaskIndex = data.getColumnIndexOrThrow(ShedulerContentProvider.KEY_NAME);
        int idIndex = data.getColumnIndexOrThrow(ShedulerContentProvider.KEY_ID);

        favourites.clear();
        while (data.moveToNext()) {
            Busstop newItem = new Busstop(data.getInt(idIndex), data.getString(keyTaskIndex));
            favourites.add(newItem);
        }
        favouritesAdapter.notifyDataSetChanged();
    }

    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /*@javadoc
      Called on change showing settings checkbox click
     */
    public void changeViewState(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_buses:
                mShowBuses = checked;
                break;
            case R.id.checkbox_trains:
                mShowTrains = checked;
                break;
        }
    }

    /*@javadoc
      Called when clicking on rate application button
     */
    public void rateApplication(View view) {
        Intent marketIntent;
        String appName = "com.schedulr";
        try {
            marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appName));
        } catch (android.content.ActivityNotFoundException anfe) {
            marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+appName));
        }
        marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        startActivity(marketIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
    }
}
