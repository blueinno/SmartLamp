package com.blueinno.android.smartlamp.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.blueinno.android.smartlamp.R;

import java.util.Calendar;

public class TimePickerFragment extends AppCompatDialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView tv = (TextView) getActivity().findViewById(R.id.timerField);
        String hour = String.valueOf(hourOfDay);
        String min = String.valueOf(minute);
        String message = hour + "시 " + min + "분에 예약 설정 되었습니다.";
        tv.setText( hour + " : " + min );
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }
}