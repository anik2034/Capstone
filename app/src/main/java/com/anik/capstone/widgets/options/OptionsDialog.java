package com.anik.capstone.widgets.options;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import com.anik.capstone.databinding.OptionsDialogBinding;

import java.util.List;

public class OptionsDialog extends Dialog {
    private OptionsDialogBinding optionsDialogBinding;
    private List<String> options;
    private OptionsView.OptionsViewListener listener;
    private String selected;

    public OptionsDialog(@NonNull Context context) {
        super(context);
    }
    public void setSelected(String selected){
        this.selected = selected;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setListener(OptionsView.OptionsViewListener listener) {
        this.listener = listener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        optionsDialogBinding = OptionsDialogBinding.inflate(LayoutInflater.from(getContext()));
        setContentView(optionsDialogBinding.getRoot());

        RadioGroup radioGroup = optionsDialogBinding.optionsRadioGroup;

        for (String option : options) {
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
            ));
            if(option.equals(selected)){
                radioButton.setChecked(true);
            }
            radioButton.setText(option);
            radioButton.setPadding(30, 30, 30, 30);
            radioButton.setOnClickListener(v -> {
                radioButtonClicked((RadioButton) v);
                listener.onOptionChanged(selected);
                dismiss();
            });

            radioGroup.addView(radioButton);
        }
    }

    private void radioButtonClicked(RadioButton radioButton) {
        this.selected = radioButton.getText().toString();
    }
}
