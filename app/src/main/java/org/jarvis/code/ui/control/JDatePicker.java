package org.jarvis.code.ui.control;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.EditText;

import org.jarvis.code.R;

import java.util.Calendar;

/**
 * Created by KimChheng on 6/7/2017.
 */

public class JDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private EditText txtDate;

    public JDatePicker() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        txtDate = (EditText) getActivity().findViewById(R.id.txtDate);
        txtDate.requestFocus();
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();
        calSet.set(Calendar.DATE, day);
        calSet.set(Calendar.MONTH, month);
        calSet.set(Calendar.YEAR, year);
        long time_val = calSet.getTimeInMillis();
        String formatted_date = (DateFormat.format("EE/d/MMM/yyyy", time_val)).toString();
        txtDate.setText(formatted_date);
    }
}
