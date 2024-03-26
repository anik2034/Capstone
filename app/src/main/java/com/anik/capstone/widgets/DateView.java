package com.anik.capstone.widgets;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.anik.capstone.databinding.DateViewBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateView extends FrameLayout {

    private static final String DATE_PATTERN = "dd-MM-yyyy";
    private DateViewBinding dateViewBinding;
    private TextView editableResultTextView;
    private Calendar selectedDate;

    public DateView(Context context) {
        super(context);
        init();
    }

    public DateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dateViewBinding = DateViewBinding.inflate(LayoutInflater.from(getContext()), this, true);
        editableResultTextView = dateViewBinding.editableResultTextView;

        selectedDate = Calendar.getInstance();

        editableResultTextView.setOnClickListener(v -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            selectedDate.set(Calendar.YEAR, year);
            selectedDate.set(Calendar.MONTH, month);
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateText();
        }, selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void updateDateText() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
        dateViewBinding.setDate(sdf.format(selectedDate.getTime()));
    }

    public String getDate() {
        return dateViewBinding.getDate();
    }

    public void setDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
            selectedDate.setTime(sdf.parse(dateString));
            updateDateText();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean getIsEditable() {
        return dateViewBinding.getIsEditable();
    }

    public void setIsEditable(boolean isEditable) {
        dateViewBinding.setIsEditable(isEditable);
    }
}