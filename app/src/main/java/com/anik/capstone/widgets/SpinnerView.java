package com.anik.capstone.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anik.capstone.databinding.SpinnerViewBinding;

public class SpinnerView extends FrameLayout {

    private SpinnerViewBinding spinnerViewBinding;

    public SpinnerView(@NonNull Context context) {
        super(context);
        init();
    }

    public SpinnerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpinnerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        spinnerViewBinding = SpinnerViewBinding.inflate(LayoutInflater.from(getContext()), this, true);
       // spinner = spinnerViewBinding.spinner;
    }

//    public void setOptions(int optionsId) {
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                getContext(),
//                optionsId, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//    }

    public String getSelected() {
        return spinnerViewBinding.getItem();
    }

    public void setSelected(String item) {
        spinnerViewBinding.setItem(item);
    }

    public boolean getIsEditable() {
        return spinnerViewBinding.getIsEditable();
    }

    public void setIsEditable(boolean isEditable) {
        spinnerViewBinding.setIsEditable(isEditable);
    }
}