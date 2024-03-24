package com.anik.capstone.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.anik.capstone.databinding.EditableTextViewBinding;

import androidx.annotation.Nullable;

public class EditableTextView extends FrameLayout {
    private EditableTextViewBinding editableTextViewBinding;

    public EditableTextView(Context context) {
        super(context);
        init();
    }

    public EditableTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public EditableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }
    public void init() {
        editableTextViewBinding = EditableTextViewBinding.inflate(LayoutInflater.from(getContext()), this, true);
    }

    public void setText(String text) {
        editableTextViewBinding.setText(text);
    }

    public void setIsEditable(boolean isEditable) {
        editableTextViewBinding.setIsEditable(isEditable);
    }
}