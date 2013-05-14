package com.schedulr;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.schedulr.dialogs.*;
import com.schedulr.tasks.NearestStopsSearchTask;
import com.schedulr.tasks.StationBoardSearchTask;
import com.schedulr.transport.Busstop;
import com.schedulr.transport.SMSLocation;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Sheduler extends SharedSettingsActivity implements LocationListener,
        UpdateLocationDialogFragment.UpdateLocationDialogListener
{
    private Timer updateTimer;
    private Activity context;
    private MenuItem starItem;
    private DialogFragment buyTicketDialog;

    public AlertDialog noInternetDialog;
    public AlertDialog noLocationDialog;
    public UpdateLocationDialogFragment progressDialog;
    public static final String ACTION_SMS_SENT = "com.schedulr.FR_SMS_SENT";
    public static final SMSLocation[] locations = {new SMSLocation("Fribourg", "10", "^(Fribourg)|(Villars-sur-Glâne)|(Schönberg)"),
                                                   new SMSLocation("Bulle", "30", "^(Bulle)|(Vuadens)|(Riaz)|(Morlon)")};

    // Whether we have some location to buy tickets
    public SMSLocation currentSMSLocation = null;
    public boolean hasSMS = true;

    // Save user account info
    private Account account;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // set sms feature
        hasSMS = getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
        AccountManager am = AccountManager.get(this);
        Account[] accounts = am.getAccountsByType("com.google");
        if (accounts.length >= 1){
            this.account = accounts[0];
        }

        setContentView(R.layout.main);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setDisplayShowTitleEnabled(false);

        // Register broadcast receivers for SMS sent and delivered intents
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (getResultCode() == Activity.RESULT_OK) {
                    buildAlertDialog(R.string.msg_sent_ok).create().show();
                }
                else {
                    buildAlertDialog(R.string.msg_sent_error).create().show();
                }
            }
        }, new IntentFilter(ACTION_SMS_SENT));

        // create dialog to show when no internet present
        noInternetDialog = buildAlertDialog(R.string.no_internet)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                //TODO: rerun task
                            }
                        }).create();

        noLocationDialog = new AlertDialog.Builder(this)
                        .setMessage(R.string.no_location).setCancelable(false)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                //TODO: rerun task
                            }
                        }).create();

        updateLocation();
        context = this;
    }

    private Handler handler = new Handler();
    MyTimerTask doRefresh = null;

    @Override
    protected void onStart() {
        super.onStart();
        // We need to remove item from favorites if user removed it from settings and pressed back.
        if(starItem != null) {
            Busstop selectedBus = (Busstop)findViewById(R.id.list).getTag();
            if (selectedBus != null) {
                ContentResolver cr = getContentResolver();
                String where = ShedulerContentProvider.KEY_ID + " = " + selectedBus.getId();
                Cursor cursor = cr.query(ShedulerContentProvider.CONTENT_URI_FAV, null, where, null, null);
                if(cursor.getCount() < 1) {
                    starItem.setIcon(R.drawable.ic_star);
                }
                cursor.close();
            }
        }
        if (doRefresh != null) {
            doRefresh.cancel();
        }
        doRefresh = new MyTimerTask();
        if (updateTimer != null) {
            updateTimer.cancel();
        }
        updateTimer = new Timer("stationboardUpdates");
        updateTimer.scheduleAtFixedRate(doRefresh, 1000 * 60, 1000 * 60);
    }

    public void onLocationChanged(Location location)
    {
        if (location != null) {
            new NearestStopsSearchTask(this).execute(location);
        }
        else {
            noLocationDialog.show();
        }
    }

    public void onStatusChanged(String s, int i, Bundle bundle) {  }

    public void onProviderEnabled(String s) { }

    public void onProviderDisabled(String s) { }

    public void onLocationDialogClick(DialogFragment dialog)
    {
        // show set location dialog
        dialog.dismiss();
        new SetLocationDialogFragment().show(getFragmentManager(), "set_location");
    }

    public void setNavigationList(List l)
    {
        final ArrayAdapter<Busstop> dropDownAdapter =
                new ArrayAdapter<Busstop>(this, android.R.layout.simple_dropdown_item_1line, l);

        getActionBar().setListNavigationCallbacks(dropDownAdapter,
                new ActionBar.OnNavigationListener()
                {
                    public boolean onNavigationItemSelected(int itemPosition, long itemId)
                    {
                        // TODO: reference to variable outside of the task
                        Busstop busstop = dropDownAdapter.getItem(itemPosition);

                        for (SMSLocation location : Sheduler.locations)
                        {
                            if (location.getPattern().matcher(busstop.getName()).find())
                            {
                                currentSMSLocation = location;
                                invalidateOptionsMenu();
                                break;
                            }
                        }

                        findViewById(R.id.list).setTag(busstop);

                        List<String> favorites = getFavorites();

                        if (favorites.size() > 0 && favorites.contains(busstop.getName()))
                        {
                            getStarItem().setIcon(R.drawable.ic_star_pressed);
                        } else
                        {
                            getStarItem().setIcon(R.drawable.ic_star);
                        }

                        new StationBoardSearchTask(context).execute(busstop.getId());
                        return true;
                    }
                });
        progressDialog.dismiss();
    }

    private class MyTimerTask extends TimerTask {
        private Runnable runnable = new Runnable() {
            public void run() {
                Busstop selectedBus = (Busstop)findViewById(R.id.list).getTag();
                if (selectedBus == null) {
                    return;
                }
                StationBoardSearchTask stationBoardSearchTask = new StationBoardSearchTask(context);
                stationBoardSearchTask.showDialog = false;
                stationBoardSearchTask.execute(selectedBus.getId());
            }
        };
        public void run() {
            handler.post(runnable);
        }
    }


    private void updateLocation() {
        progressDialog = new UpdateLocationDialogFragment();
        progressDialog.show(getFragmentManager(), "update_location");
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(false);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_MEDIUM);
        criteria.setVerticalAccuracy(Criteria.NO_REQUIREMENT);
        criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setSpeedAccuracy(Criteria.NO_REQUIREMENT);

        Looper looper = null;
        try {
            locationManager.requestSingleUpdate(criteria, this, looper);
        } catch (IllegalArgumentException e)
        {
            // show dialog when no location present
            noLocationDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        if (currentSMSLocation != null && hasSMS) {
            inflater.inflate(R.menu.fribourg_menu, menu);
        }
        else {
            inflater.inflate(R.menu.actions, menu);
        }
        starItem = menu.findItem(R.id.menu_star);
        return true;
    }

    public MenuItem getStarItem() {
        return starItem;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case (R.id.menu_search_location): {
                updateLocation();
                return true;
            }
            case (R.id.menu_refresh): {
                Busstop selectedBus = (Busstop)findViewById(R.id.list).getTag();
                if (selectedBus == null) {
                    updateLocation();
                }
                else {
                    new StationBoardSearchTask(this).execute(selectedBus.getId());
                }
                return true;
            }
            case (R.id.menu_settings): {
                Intent settings = new Intent(this, Settings.class);
                startActivity(settings);
                return true;
            }
            case (R.id.menu_star): {
                ContentResolver cr = getContentResolver();
                ContentValues values = new ContentValues();
                Busstop selectedBus = (Busstop)findViewById(R.id.list).getTag();
                values.put(ShedulerContentProvider.KEY_ID, selectedBus.getId());
                values.put(ShedulerContentProvider.KEY_NAME, selectedBus.getName());

                // Check already exists - TODO: move to separate method?
                Cursor c = cr.query(ShedulerContentProvider.CONTENT_URI_FAV,
                        new String[] { ShedulerContentProvider.KEY_ID },
                        ShedulerContentProvider.KEY_NAME + "=?", new String[] { selectedBus.getName() }, null);
                if (c.getCount() == 0) {
                    cr.insert(ShedulerContentProvider.CONTENT_URI_FAV, values);
                    item.setIcon(R.drawable.ic_star_pressed);
                }
                else {
                    cr.delete(ShedulerContentProvider.CONTENT_URI_FAV, ShedulerContentProvider.KEY_NAME + "=?",
                            new String[]{selectedBus.getName()});
                    item.setIcon(R.drawable.ic_star);
                }
                return true;
            }
            case (R.id.menu_buy): {
                buyTicketDialog = BuyTicketDialogFragment.newInstance(mHalfFare, mDayPass);
                buyTicketDialog.show(getFragmentManager(), "dialog");
                return true;
            }
            default: return false;
        }
    }

    public void saveBuyingSettings(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_half_fare:
                mHalfFare = checked;
                break;
            case R.id.checkbox_day_pass:
                mDayPass = checked;
                break;
        }
        ((TextView)buyTicketDialog.getDialog().findViewById(R.id.sms_code_result)).setText(generateSmsCode());
    }

    public String generateSmsCode() {
        String code = currentSMSLocation.getCode();
        if (mHalfFare) {
            code += "R";
        }
        if (mDayPass) {
            code = "J" + code;
        }
        return code;
    }
}
