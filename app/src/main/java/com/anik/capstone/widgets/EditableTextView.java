package com.anik.capstone.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.anik.capstone.databinding.EditableTextViewBinding;

public class EditableTextView extends FrameLayout {
    private EditableTextViewBinding editableTextViewBinding;
    private EditableTextViewListener listener;
    private InputMethodManager imm;

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
        imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        editableTextViewBinding = EditableTextViewBinding.inflate(LayoutInflater.from(getContext()), this, true);
        editableTextViewBinding.saveImageButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTextChanged(editableTextViewBinding.searchDataEditText.getText().toString());
                imm.hideSoftInputFromWindow(getWindowToken(), 0);


            }
        });
    }

    public String getText() {
        return editableTextViewBinding.getText();
    }

    public void setText(String text) {
        editableTextViewBinding.setText(text);
        editableTextViewBinding.executePendingBindings();
    }

    public boolean getIsEditable() {
        return editableTextViewBinding.getIsEditable();
    }

    public void setIsEditable(boolean isEditable) {
        editableTextViewBinding.setIsEditable(isEditable);
    }


    public void setListener(EditableTextViewListener listener) {
        this.listener = listener;
    }

    public interface EditableTextViewListener {
        void onTextChanged(String text);
    }
}