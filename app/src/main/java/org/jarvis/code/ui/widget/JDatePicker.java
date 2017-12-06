package org.jarvis.code.ui.widget;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;

/**
 * Created by KimChheng on 6/7/2017.
 */

public class JDatePicker implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener {

    private ImageButton imageButton;
    private EditText txtDate;
    private Calendar calendar;
    private Context context;

    public JDatePicker(EditText txtDate, Context context) {
        this(null, txtDate, context);
    }

    public JDatePicker(ImageButton imageButton, EditText txtDate, Context context) {
        this.imageButton = imageButton;
        this.txtDate = txtDate;
        this.context = context;
        this.calendar = Calendar.getInstance();
        this.txtDate.setOnFocusChangeListener(this);
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        Calendar calSet = (Calendar) calendar.clone();
        calSet.set(Calendar.DATE, day);
        calSet.set(Calendar.MONTH, month);
        calSet.set(Calendar.YEAR, year);
        long time_val = calSet.getTimeInMillis();
        String formatted_date = (DateFormat.format("EE/d/MMM/yyyy", time_val)).toString();
        txtDate.setText(formatted_date);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(context, this, year, month, day).show();
        }
    }
}
