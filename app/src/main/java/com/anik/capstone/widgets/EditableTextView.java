package com.anik.capstone.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.anik.capstone.databinding.EditableTextViewBinding;

import androidx.annotation.Nullable;

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
        editableTextViewBinding.editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                save();
            }
            return true;
        });
    }

    private void save() {
        if (listener != null) {
            listener.onTextChanged(getTextViewText(), getEditTextText());
            imm.hideSoftInputFromWindow(getWindowToken(), 0);
        }
    }

    private String getEditTextText() {
        return editableTextViewBinding.editText.getText().toString();
    }

    private String getTextViewText() {
        return editableTextViewBinding.textView.getText().toString();
    }

    public void setStyle(int textSize) {
        editableTextViewBinding.textView.setTypeface(
                editableTextViewBinding.textView.getTypeface(),
                Typeface.BOLD
        );
        editableTextViewBinding.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
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

    public void setHint(String hint) {
        editableTextViewBinding.setHint(hint);
    }

    public void setListener(EditableTextViewListener listener) {
        this.listener = listener;
    }

    public interface EditableTextViewListener {
        void onTextChanged(String oldText, String newText);
    }
}