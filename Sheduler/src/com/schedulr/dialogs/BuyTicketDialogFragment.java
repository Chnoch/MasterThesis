package com.schedulr.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import com.schedulr.R;
import com.schedulr.Sheduler;

public class BuyTicketDialogFragment extends DialogFragment
{
    public static BuyTicketDialogFragment newInstance(boolean mHalfFare, boolean mDayPass)
    {
        BuyTicketDialogFragment frag = new BuyTicketDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean(Sheduler.mHalfFareStr ,mHalfFare);
        args.putBoolean(Sheduler.mDayPassStr, mDayPass);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        boolean mHalfFare = getArguments().getBoolean(Sheduler.mHalfFareStr);
        boolean mDayPass = getArguments().getBoolean(Sheduler.mDayPassStr);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = inflater.inflate(R.layout.buy_dialog, null);
        CheckBox cbHalfFare = ((CheckBox) dialogView.findViewById(R.id.checkbox_half_fare));
        CheckBox cbDayPass = ((CheckBox) dialogView.findViewById(R.id.checkbox_day_pass));
        cbHalfFare.setChecked(mHalfFare);
        cbDayPass.setChecked(mDayPass);
        ((TextView) dialogView.findViewById(R.id.sms_code_result)).setText(((Sheduler) getActivity()).generateSmsCode());

        builder.setView(dialogView).setTitle(R.string.buy_ticket_sms)
                .setPositiveButton(R.string.buy_dialog_ok,
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                CharSequence code = ((TextView) getDialog().findViewById(R.id.sms_code_result)).getText();
                                SmsManager.getDefault().sendTextMessage("873", null, code.toString(),
                                        PendingIntent.getBroadcast(getActivity(), 0, new Intent(Sheduler.ACTION_SMS_SENT), 0), null);
                            }
                        }
                )
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                dialog.cancel();
                            }
                        }
                );
        return builder.create();
    }
}
