package com.anik.capstone.widgets.options;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anik.capstone.databinding.OptionsViewBinding;

import java.util.List;


public class OptionsView extends FrameLayout {

    private OptionsViewBinding optionsViewBinding;
    private OptionsDialog optionsDialog;
    private OptionsView.OptionsViewListener listener;
    private List<String> options;

    public OptionsView(@NonNull Context context) {
        super(context);
        init();
    }

    public OptionsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OptionsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        optionsViewBinding = OptionsViewBinding.inflate(LayoutInflater.from(getContext()), this, true);
        optionsViewBinding.itemTextView.setOnClickListener(v -> {
            showOptionsDialog(options);
        });

    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    private void showOptionsDialog(List<String> options) {
        optionsDialog = new OptionsDialog(getContext());
        optionsDialog.setListener(listener);
        optionsDialog.setOptions(options);
        optionsDialog.setSelected(getSelected());
        optionsDialog.show();
    }


    public String getSelected() {
        return optionsViewBinding.getSelected();
    }

    public void setSelected(String selected) {
        optionsViewBinding.setSelected(selected);
    }

    public void setListener(OptionsViewListener listener) {
        this.listener = listener;
    }

    public interface OptionsViewListener {
        void onOptionChanged(String selected);
    }
}