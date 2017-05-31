package uw.tacoma.edu.paidaid.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

import uw.tacoma.edu.paidaid.R;

/**
 * @Author Jake Knowles
 * @Author Dmitriy Onishchenko
 * @version 5/19/17
 *
/** DatePickerFragment is a fragment launched to pick a date from a calendar on when you want your
    request to expire */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    /**
     * Constructor
     */
    public DatePickerFragment() {}

    /**
     * onCreateDialog provided from Dialogs Lab
     * @param savedInstanceState savedInstanceState
     * @return datePicker
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), R.style.DialogTheme, this, year, month, day);

        return datePicker;

    }

    /**
     * onDateSet provided from Dialogs Lab
     * @param view view
     * @param year year
     * @param monthOfYear monthOfYear
     * @param dayOfMonth dayOfMonth
     */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        Toast.makeText(getActivity(), "You picked " + (monthOfYear + 1) + "/" + dayOfMonth + "/" + year,
                Toast.LENGTH_LONG)
                .show();
    }
}



