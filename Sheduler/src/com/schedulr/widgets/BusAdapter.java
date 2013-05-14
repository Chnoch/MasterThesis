package com.schedulr.widgets;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.schedulr.R;
import com.schedulr.tasks.StationBoardSearchTask;
import com.schedulr.transport.Bus;

import java.util.HashMap;

public class BusAdapter extends ArrayAdapter<Bus>{

    Context context;
    Bus[] data = null;
    public static final HashMap<String, Integer> colors;
    private static final int LIST_ROW_HIGHLIGHTED = 0;
    private static final int LIST_ROW = 1;
    private static final int LIST_ROW_COUNT = 2;

    static {
        colors = new HashMap<String, Integer>();
        colors.put("1", R.drawable.green_rect);
        colors.put("2", R.drawable.red_rect);
        colors.put("3", R.drawable.yellow_rect);
        colors.put("4", R.drawable.orange_rect);
        colors.put("5", R.drawable.blue_rect);
        colors.put("6", R.drawable.pink_rect);
        colors.put("7", R.drawable.brown_rect);
    }

    public BusAdapter(Context context, int listRowViewResourceId, Bus[] data) {
        super(context, listRowViewResourceId, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        BusHolder holder;
        int type = getItemViewType(position);

// Use code for better performance in case of big data (android reuses views)
//        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            switch (type) {
                case LIST_ROW:
                    row = inflater.inflate(R.layout.list_row, parent, false);
                    break;
                case LIST_ROW_HIGHLIGHTED:
                    row = inflater.inflate(R.layout.list_row_highlighted, parent, false);
                    break;
            }

            holder = new BusHolder();
            holder.btn = (Button) row.findViewById(R.id.bus_num);
            holder.destination = (TextView) row.findViewById(R.id.destination);
            holder.time = (TextView) row.findViewById(R.id.time);
            holder.min1 = (TextView) row.findViewById(R.id.min1);
            holder.min2 = (TextView) row.findViewById(R.id.min2);
//            row.setTag(holder);
//        } else {
//            holder = (BusHolder) row.getTag();
//        }

        Bus bus = data[position];
        holder.btn.setText(bus.getBusNum());
        if(StationBoardSearchTask.numbers.containsKey(getItem(position).getName())) {
            if(colors.get(getItem(position).getBusNum()) != null)
                holder.btn.setBackgroundResource(colors.get(getItem(position).getBusNum()));
        }
        holder.destination.setText(bus.getDestination());
        holder.time.setText(bus.getTime());
        holder.min1.setText(bus.getMin1());
        holder.min2.setText(bus.getMin2());

        return row;
    }

    static class BusHolder {
        Button btn;
        TextView destination;
        TextView time;
        TextView min1;
        TextView min2;
    }

    @Override
    public int getItemViewType(int position) {
        Bus bus = getItem(position);
        if (bus.getTime().startsWith("0")) {
            return LIST_ROW_HIGHLIGHTED;
        } else {
            return LIST_ROW;
        }
    }

    @Override
    public int getViewTypeCount() {
        return LIST_ROW_COUNT;
    }
}