package com.moonssoft.diubus;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

import static com.moonssoft.diubus.SetBusScheduleFragment.mEditTextTime;
import static com.moonssoft.diubus.SetBusScheduleFragment.mTime;

/**
 * Created by MoonS on 04-03-17.
 */

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker timePicker, int i, int i1) {

        if(i > 9){
            mTime = String.valueOf(i);
        } else {
            mTime = "0" + String.valueOf(i);
        }

        if(i1 > 9){
            mTime = mTime + "." + String.valueOf(i1);
        } else {
            mTime = mTime + "." + "0" + String.valueOf(i);
        }


        mEditTextTime.setText(mTime);

    }



}
