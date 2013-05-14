package com.schedulr.tasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import com.schedulr.transport.Busstop;
import com.schedulr.R;
import com.schedulr.Sheduler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class NearestStopsSearchTask extends AsyncTask<Location, Void, List>{

    private Activity context;
    public static final String TAG = "NearestStopsSearchTask";
    private String url = "http://opendata.prokofyev.ch/v1/locations?";
    private AlertDialog noStationsDialog;

    public NearestStopsSearchTask(Activity context) {
        this.context = context;
    }

    @Override
    protected List doInBackground(Location... params) {
        Location location = params[0];
        String feed = url + "x=" + location.getLatitude() + "&y=" + location.getLongitude();
        //String feed = "http://opendata.prokofyev.ch/v1/locations?x=46.802877773762894&y=7.151651249648352";
        LinkedList<Busstop> localData = new LinkedList<Busstop>();
        try {
            URL url = new URL(feed);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection)connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                JSONObject json = new JSONObject(builder.toString());
                JSONArray stations = json.getJSONArray("stations");
                // Warn user that no stations found
                if (stations.length() == 0) {
                    context.runOnUiThread(new Runnable()
                    {
                        public void run()
                        {
                            noStationsDialog.show();
                        }
                    });
                    // TODO: should we return here?
                }
                for(int i = 0; i < stations.length(); i++) {
                    localData.add(new Busstop(stations.getJSONObject(i).getInt("id"),
                                                            stations.getJSONObject(i).getString("name")));
                }
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Malformed URL Exception" + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IO Exception" + e.getMessage());
        } catch (JSONException e) {
            Log.e(TAG, "JSON error" + e.getMessage());
        }


        //moving first favourite busstop to the beginning of the array
        List<String> stopNamesList = new ArrayList<String>();
        for(Busstop busstop : localData){
            stopNamesList.add(busstop.getName());
        }
        for(String favourite: ((Sheduler)context).getFavorites()) {
            if(stopNamesList.contains(favourite)) {
                Busstop busstop = localData.remove(stopNamesList.indexOf(favourite));
                localData.addFirst(busstop);
            }
        }

        return localData;
    }

    @Override
    protected void onPostExecute(List l) {
        ((Sheduler)context).setNavigationList(l);
    }

    @Override
    protected void onPreExecute() {
        noStationsDialog = new AlertDialog.Builder(context)
                        .setMessage("No stations found").setCancelable(false)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                            }
                        }).create();
    }

}
