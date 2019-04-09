package com.blogspot.estamoscercadelfututro.clinica;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Calendar;

public class DatePicker extends DialogFragment
{

    private DatePickerDialog.OnDateSetListener listener;

    public static DatePicker newInstance(DatePickerDialog.OnDateSetListener listener) {
        DatePicker fragment = new DatePicker();
        fragment.setListener(listener);
        return fragment;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),listener,year,month,day);
    }

    public void onDateSet(android.widget.DatePicker view, int year, int month, int day) {
        DateFormat df =  DateFormat.getDateInstance();
        Date fecha = new Date(year,month,day);

    }

}
