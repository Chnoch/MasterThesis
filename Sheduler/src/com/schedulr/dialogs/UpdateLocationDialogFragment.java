package com.schedulr.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import com.schedulr.R;

public class UpdateLocationDialogFragment extends DialogFragment
{
    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface UpdateLocationDialogListener
    {
        public void onLocationDialogClick(DialogFragment dialog);
    }

    UpdateLocationDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getResources().getString(R.string.upd_location));
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.set_location),
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        mListener.onLocationDialogClick(UpdateLocationDialogFragment.this);
                    }
                });
        return dialog;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try
        {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (UpdateLocationDialogListener) activity;
        } catch (ClassCastException e)
        {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement UpdateLocationDialogListener");
        }
    }
}
