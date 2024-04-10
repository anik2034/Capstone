package com.anik.capstone.widgets;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.DatePicker;
import android.widget.FrameLayout;

import com.anik.capstone.databinding.DateViewBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateView extends FrameLayout implements DatePickerDialog.OnDateSetListener {

    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private DateViewBinding dateViewBinding;
    private Calendar selectedDate;
    private SimpleDateFormat sdf;
    private DateViewListener listener;

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
        sdf = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
        dateViewBinding = DateViewBinding.inflate(LayoutInflater.from(getContext()), this, true);

        selectedDate = Calendar.getInstance();

        dateViewBinding.resultTextView.setOnClickListener(v -> {
            showDatePickerDialog();
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                this,
                selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    public String getDate() {
        return dateViewBinding.getDate();
    }

    public void setDate(String dateString) {
        try {
            selectedDate.setTime(sdf.parse(dateString));
            dateViewBinding.setDate(dateString);
            dateViewBinding.executePendingBindings();
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

    public void setListener(DateViewListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        setDate(dayOfMonth + "/" + month + "/" + year);
        listener.onDateChanged(dateViewBinding.getDate());
    }


    public interface DateViewListener {
        void onDateChanged(String date);
    }
}