package com.anik.capstone.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.anik.capstone.databinding.EditableTextViewBinding;

import androidx.annotation.Nullable;

public class EditableTextView extends FrameLayout {
    private EditableTextViewBinding editableTextViewBinding;
    private EditableTextViewListener listener;

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
        editableTextViewBinding = EditableTextViewBinding.inflate(LayoutInflater.from(getContext()), this, true);
        editableTextViewBinding.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (getIsEditable()) {
                    listener.onTextChanged(getTextViewText(), s.toString());
                }
            }
        });
    }

    private String getTextViewText() {
        return editableTextViewBinding.textView.getText().toString();
    }

    public void setEditTextViewSize(int textSize) {
        editableTextViewBinding.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }

    public void setEditTextViewBoldStyle() {
        editableTextViewBinding.textView.setTypeface(
                editableTextViewBinding.textView.getTypeface(),
                Typeface.BOLD
        );
    }

    public void setEditTextViewNormalStyle() {
        editableTextViewBinding.textView.setTypeface(null,Typeface.NORMAL);
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