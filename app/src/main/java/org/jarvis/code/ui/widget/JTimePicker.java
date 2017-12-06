package org.jarvis.code.ui.widget;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ki.kao on 12/2/2017.
 */

public class JTimePicker implements View.OnFocusChangeListener, TimePickerDialog.OnTimeSetListener {

    private EditText txtTime;
    private Calendar calendar;
    private Context context;

    public JTimePicker(EditText txtTime, Context context) {
        this.txtTime = txtTime;
        this.context = context;
        this.txtTime.setOnFocusChangeListener(this);
        this.calendar = Calendar.getInstance();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = hourOfDay + ":" + minute;
        this.txtTime.setText(convertTo12Hour(time));
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            new TimePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, this, hour, minute, false).show();
        }
    }

    public String convertTo12Hour(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            Date date = sdf.parse(time);
            return new SimpleDateFormat("K:mm a").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
