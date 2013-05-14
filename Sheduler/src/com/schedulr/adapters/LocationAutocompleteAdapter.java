package com.schedulr.adapters;


import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import com.schedulr.transport.Busstop;
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
import java.util.ArrayList;


public class LocationAutocompleteAdapter extends ArrayAdapter implements Filterable
{
    private ArrayList<Busstop> mData;
    private String url = "http://opendata.prokofyev.ch/v1/locations?";
    public static final String TAG = "LocationAutocompleteAdapter";
    private int selectedItem;

    public LocationAutocompleteAdapter(Context context, int textViewResourceId)
    {
        super(context, textViewResourceId);
        mData = new ArrayList<Busstop>();
    }

    @Override
    public int getCount()
    {
        return mData.size();
    }

    @Override
    public Busstop getItem(int index)
    {
        return mData.get(index);
    }

    @Override
    public Filter getFilter()
    {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence constraint)
            {
                ArrayList<Busstop> localData = new ArrayList<Busstop>();
                FilterResults filterResults = new FilterResults();
                if (constraint != null)
                {
                    // A class that queries a web API, parses the data and returns an ArrayList<T>
                    String feed = url + "query=" + constraint;
                    try
                    {
                        URL url = new URL(feed);
                        URLConnection connection = url.openConnection();
                        HttpURLConnection httpConnection = (HttpURLConnection) connection;
                        int responseCode = httpConnection.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK)
                        {
                            String line;
                            StringBuilder builder = new StringBuilder();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            while ((line = reader.readLine()) != null)
                            {
                                builder.append(line);
                            }
                            JSONObject json = new JSONObject(builder.toString());
                            JSONArray stations = json.getJSONArray("stations");
                            for (int i = 0; i < stations.length() && i < 10; i++)
                            {
                                localData.add(new Busstop(stations.getJSONObject(i).getInt("id"),
                                        stations.getJSONObject(i).getString("name")));
                            }
                        }
                    } catch (MalformedURLException e)
                    {
                        Log.e(TAG, "Malformed URL Exception" + e.getMessage());
                    } catch (IOException e)
                    {
                        Log.e(TAG, "IO Exception" + e.getMessage());
                    } catch (JSONException e)
                    {
                        Log.e(TAG, "JSON error" + e.getMessage());
                    }

                    /* StyleFetcher fetcher = new StyleFetcher();
                    mData = fetcher.retrieveResults(constraint.toString()); */
                    // Now assign the values and count to the FilterResults object
                    mData = localData;
                    filterResults.values = mData;
                    filterResults.count = mData.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence contraint, FilterResults results)
            {
                if (results != null && results.count > 0)
                {
                    notifyDataSetChanged();
                } else
                {
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    public int getSelectedItem()
    {
        return selectedItem;
    }

    public void setSelectedItem(int selectedItem)
    {
        this.selectedItem = selectedItem;
    }
}
