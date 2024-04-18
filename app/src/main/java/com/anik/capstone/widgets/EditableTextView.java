package com.anik.capstone.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

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
        setTextAlignment(TextView.TEXT_ALIGNMENT_TEXT_START);
        imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        editableTextViewBinding = EditableTextViewBinding.inflate(LayoutInflater.from(getContext()), this, true);
        editableTextViewBinding.saveImageButton.setOnClickListener(v -> save());
        editableTextViewBinding.searchDataEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                save();
            }
            return true;
        });
    }

    private void save() {
        if (listener != null) {
            listener.onTextChanged(editableTextViewBinding.searchDataEditText.getText().toString());
            imm.hideSoftInputFromWindow(getWindowToken(), 0);
        }
    }
    public String getText() {
        return editableTextViewBinding.getText();
    }

    public void setText(String text) {
        editableTextViewBinding.setText(text);
    }

    public boolean getIsEditable() {
        return editableTextViewBinding.getIsEditable();
    }

    public void setIsEditable(boolean isEditable) {
        editableTextViewBinding.setIsEditable(isEditable);
    }

    public void setCenter(boolean isCenter){
        editableTextViewBinding.setIsCenter(isCenter);
    }

    public boolean getCenter(){
        return editableTextViewBinding.getIsCenter();
    }

    public void setListener(EditableTextViewListener listener) {
        this.listener = listener;
    }

    public interface EditableTextViewListener {
        void onTextChanged(String text);
    }
}