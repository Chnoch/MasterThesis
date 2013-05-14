package com.schedulr.tasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import com.schedulr.*;
import com.schedulr.transport.Bus;
import com.schedulr.widgets.BusAdapter;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StationBoardSearchTask extends AsyncTask<Integer, Void, Bus[]> {

    private Activity context;
    private String url = "http://opendata.prokofyev.ch/v1/stationboard?id=";
    private HashMap<String, LinkedHashMap<String, Bus>> foldedBuses = new LinkedHashMap<String, LinkedHashMap<String, Bus>>();
    private static final String TAG = "StationBoardSearchTask";
    public static final HashMap<String, String> numbers;
    private ProgressDialog progressDialog;
    boolean mShowTrains;
    boolean mShowBuses;
    public boolean showDialog = true;

    static {
            numbers = new HashMap<String, String>();
            numbers.put("NFO12", "2");
            numbers.put("NFB16", "6");
            numbers.put("NFB17", "7");
            numbers.put("NFO11", "1");
            numbers.put("NFB11", "1");
            numbers.put("NFO13", "3");
            numbers.put("NFB14", "4");
            numbers.put("NFB15", "5");
    }

    public StationBoardSearchTask(Activity context) {
        this.context = context;
    }

    private List<Bus> filterBuses(List<Bus> buses) {
        List<Bus> newbuses = new ArrayList<Bus>();
        for(Bus bus : buses) {
            boolean add = true;
            if (!mShowBuses && bus.getCategory().toUpperCase().matches("NFO|NFB|BUS"))
                add = false;
            if (!mShowTrains && bus.getCategory().toUpperCase().matches("R|IR|IC|ICE|RE|ECN|S\\d{1,2}"))
                add = false;
            if (add)
                newbuses.add(bus);
        }
        return newbuses;
    }

    @Override
    protected Bus[] doInBackground(Integer... params) {
        String limit = "20";
        // increase limit in case we showing only trains
        if (!mShowBuses)
            limit = "30";
        String feed = url + params[0] + "&limit=" + limit;
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
                JSONArray stops = json.getJSONArray("stationboard");
                for(int i = 0; i < stops.length(); i++) {
                    String dest = stops.getJSONObject(i).getString("to");
                    String name = stops.getJSONObject(i).getString("name");
                    if (name.length() >=5 )
                        name = name.substring(0, 5);
                    String number = stops.getJSONObject(i).getString("number");
                    String category = stops.getJSONObject(i).getString("category");
                    String depart_time = stops.getJSONObject(i).getJSONObject("stop").getString("departure");
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                    Date date = formatter.parse(depart_time);
                    String time = resolveTime(date);
                    String busNum = resolveNumber(name, category, number);
                    //folding buses by destination
                    if(foldedBuses.containsKey(busNum) && foldedBuses.get(busNum).containsKey(dest)) {
                        Bus bus = foldedBuses.get(busNum).get(dest);
                        if(!bus.getTime().contains(":") && !time.contains(":")){
                            if("".equals(bus.getMin1())){
                                bus.setMin1(time);
                            } else if ("".equals(bus.getMin2())) {
                                bus.setMin2(time);
                            }
                        }
                    } else {
                        LinkedHashMap<String, Bus> busMap;
                        if (foldedBuses.containsKey(busNum)) {
                            busMap = foldedBuses.get(busNum);
                        }
                        else {
                            busMap = new LinkedHashMap<String, Bus>();
                        }
                        busMap.put(dest, new Bus(busNum, dest, category, time, "", "", date.getTime(), name));
                        foldedBuses.put(busNum, busMap);
                    }
                }

            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Malformed URL Exception");
        } catch (IOException e)
        {
            final AlertDialog noInternetDialog = ((Sheduler)context).noInternetDialog;
            context.runOnUiThread(new Runnable()
            {
                public void run()
                {
                    if (!context.isFinishing())
                    {
                        if (!noInternetDialog.isShowing())
                        {
                            noInternetDialog.show();
                        }
                    }
                }
            });
            Log.e(TAG, "IO Exception");
        } catch (JSONException e) {
            Log.e(TAG, "JSON error");
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing date");
        }
        List<Bus> buses = new ArrayList<Bus>();
        for (LinkedHashMap<String, Bus> entry : foldedBuses.values()) {
            for (Bus bus : entry.values()) {
                buses.add(bus);
            }
        }

        /*Collections.sort(buses, new Comparator<Bus>()
        {
            public int compare(Bus bus, Bus bus1)
            {
                if (!bus.isNumberNum()){
                    return 1;
                }
                if (!bus1.isNumberNum()){
                    return -1;
                }
                return 0;
            }
        });*/

        // Filter based on preferences
        buses = filterBuses(buses);
        return buses.toArray(new Bus [buses.size()]);
    }

    @Override
    protected void onPostExecute(Bus[] list) {
        final BusAdapter busAdapter = new BusAdapter(context, R.layout.list_row, list);
        ListView listView = (ListView)context.findViewById(R.id.list);
        listView.setAdapter(busAdapter);
        busAdapter.notifyDataSetChanged();
        if(showDialog)
            progressDialog.dismiss();
    }

    private String resolveNumber(String name, String category, String number) {
        String busNum = "";
        if (numbers.containsKey(name)) {
            busNum = numbers.get(name);
        } else if ("NFO".equals(category) || "NFB".equals(category)) {
            if (number.length() > 3) {
                busNum = number.substring(0, number.length() - 3);
            } else if (number.length() == 3) {
                busNum = number.substring(0, number.length() - 2);
            }
        } else if (number.length() <= 2 && !category.equals("BUS")) {
            busNum = number;
        } else {
            busNum = category;
        }
        return busNum;
    }

    private String resolveTime(Date date) {
        String time = "";
        Date currentDate = new Date();
        int hours = (int) (date.getTime() - currentDate.getTime()) / (1000 * 60 * 60);
        int minutes = (int) ((date.getTime() - currentDate.getTime()) / (1000 * 60)) % 60;
        if (hours > 0) {
            time = time + hours + ":";
        }
        if (minutes >= 0) {
            if (hours > 0 && minutes < 10 && minutes >= 0) {
                time = time + "0" + minutes + "'";
            } else {
                time = time + minutes + "'";
            }
        }

        return time;
    }

    @Override
    protected void onPreExecute() {
        if(showDialog)
            progressDialog = ProgressDialog.show(context, "", context.getResources().getString(R.string.upd_stationboard));
        mShowTrains = ((SharedSettingsActivity)this.context).getProperty(SharedSettingsActivity.mShowTrainsStr);
        mShowBuses = ((SharedSettingsActivity)this.context).getProperty(SharedSettingsActivity.mShowBusesStr);
    }
}
