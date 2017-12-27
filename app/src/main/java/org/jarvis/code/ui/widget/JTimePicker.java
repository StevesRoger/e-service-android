package org.jarvis.code.ui.widget;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.MotionEvent;
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

public class JTimePicker implements View.OnTouchListener, TimePickerDialog.OnTimeSetListener {

    private EditText txtTime;
    private Calendar calendar;
    private Context context;
    private String value;

    public JTimePicker(EditText txtTime, Context context) {
        this.txtTime = txtTime;
        this.context = context;
        this.txtTime.setOnTouchListener(this);
        this.calendar = Calendar.getInstance();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = hourOfDay + ":" + minute;
        value = convertTo12Hour(time);
        this.txtTime.setText(value);
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

    public String getValue() {
        return value;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && v.getId() == txtTime.getId()) {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            AlertDialog dialog = new TimePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, this, hour, minute, false);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
        }
        return false;
    }
}
