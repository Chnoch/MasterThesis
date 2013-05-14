package com.schedulr.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import com.schedulr.R;
import com.schedulr.Sheduler;
import com.schedulr.adapters.LocationAutocompleteAdapter;
import com.schedulr.tasks.StationBoardSearchTask;
import com.schedulr.transport.Busstop;

import java.util.ArrayList;


public class SetLocationDialogFragment extends DialogFragment
{
    AutoCompleteTextView textView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.set_location_dialog, null);

        // Get a reference to the AutoCompleteTextView in the layout
        textView = (AutoCompleteTextView) view.findViewById(R.id.location_autocomplete);
        LocationAutocompleteAdapter adapter = new LocationAutocompleteAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line);
        textView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                ((LocationAutocompleteAdapter)adapterView.getAdapter()).setSelectedItem(i);
            }

            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        textView.setAdapter(adapter);

        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
                {
                    //@Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        LocationAutocompleteAdapter adapter = (LocationAutocompleteAdapter) textView.getAdapter();
                        ArrayList<Busstop> busstops = new ArrayList<Busstop>();
                        busstops.add(adapter.getItem(adapter.getSelectedItem()));
                        ((Sheduler)getActivity()).setNavigationList(busstops);
                        SetLocationDialogFragment.this.getDialog().cancel();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        SetLocationDialogFragment.this.getDialog().cancel();
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}