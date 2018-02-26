package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ben on 2/25/18.
 */

public class TimePickerFragment extends DialogFragment {

  public static final String EXTRA_TIME = "com.bignerdranch.android.criminalintent.time";

  private static final String ARG_TIME = "time";

  private TimePicker mTimePicker;
  private Calendar mCalendar;

  public static TimePickerFragment newInstance(Date date) {
    Bundle bundle = new Bundle();
    bundle.putSerializable(ARG_TIME, date);

    TimePickerFragment fragment = new TimePickerFragment();
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    Date dateTime = (Date) getArguments().getSerializable(ARG_TIME);

    mCalendar = Calendar.getInstance();
    mCalendar.setTime(dateTime);
    int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
    int min = mCalendar.get(Calendar.MINUTE);

    View view = LayoutInflater.from(getActivity())
        .inflate(R.layout.dialog_time, null);

    mTimePicker = (TimePicker) view.findViewById(R.id.dialog_time_picker);
    mTimePicker.setCurrentHour(hour);
    mTimePicker.setCurrentMinute(min);

    return new AlertDialog.Builder(getActivity())
        .setView(view)
        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            int hour, min;
            hour = mTimePicker.getCurrentHour();
            min = mTimePicker.getCurrentMinute();

            mCalendar.set(Calendar.HOUR_OF_DAY, hour);
            mCalendar.set(Calendar.MINUTE, min);

            sendResult(Activity.RESULT_OK, mCalendar.getTime());
          }
        })
        .create();
  }

  private void sendResult(int resultCode, Date date) {
    if (getTargetFragment() == null) {
      return;
    }

    Intent intent = new Intent();
    intent.putExtra(EXTRA_TIME, date);

    getTargetFragment()
        .onActivityResult(getTargetRequestCode(), resultCode, intent);
  }
}
