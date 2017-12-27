package org.jarvis.code.ui.widget;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;

/**
 * Created by KimChheng on 6/7/2017.
 */

public class JDatePicker implements View.OnTouchListener, DatePickerDialog.OnDateSetListener {

    private ImageButton imageButton;
    private EditText txtDate;
    private Calendar calendar;
    private Context context;
    private String value;

    public JDatePicker(EditText txtDate, Context context) {
        this(null, txtDate, context);
    }

    public JDatePicker(ImageButton imageButton, EditText txtDate, Context context) {
        this.imageButton = imageButton;
        this.txtDate = txtDate;
        this.context = context;
        this.calendar = Calendar.getInstance();
        this.txtDate.setOnTouchListener(this);
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        Calendar calSet = (Calendar) calendar.clone();
        calSet.set(Calendar.DATE, day);
        calSet.set(Calendar.MONTH, month);
        calSet.set(Calendar.YEAR, year);
        long time_val = calSet.getTimeInMillis();
        value = (DateFormat.format("EE/d/MMM/yyyy", time_val)).toString();
        txtDate.setText(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && v.getId() == txtDate.getId()) {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            AlertDialog dialog = new DatePickerDialog(context, this, year, month, day);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
        }
        return false;
    }
}
