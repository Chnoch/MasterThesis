package com.schedulr.widgets;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.schedulr.R;
import com.schedulr.ShedulerContentProvider;
import com.schedulr.transport.Busstop;

import java.util.List;


public class FavouritesAdapter extends ArrayAdapter<Busstop>{

    Context context;
    List<Busstop> items;

    public FavouritesAdapter(Context context, int resource, List<Busstop> items) {
        super(context, resource, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = convertView;
        FavHolder holder;

        if(row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.fav_row, parent, false);
            holder = new FavHolder();
            holder.btn_remove = (ImageButton) row.findViewById(R.id.remove);
            holder.busstop = (TextView) row.findViewById(R.id.busstop);
            row.setTag(holder);
        } else {
            holder = (FavHolder) row.getTag();
        }

        holder.busstop.setText(getItem(position).getName());
        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String where = ShedulerContentProvider.KEY_ID + "=" + getItem(position).getId();
                context.getContentResolver().delete(ShedulerContentProvider.CONTENT_URI_FAV, where, null);
                items.remove(getItem(position));
                notifyDataSetChanged();
            }
        });
        return row;
    }

    static class FavHolder {
        ImageButton btn_remove;
        TextView busstop;
    }
}
