package com.anik.capstone.bookDetails;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.anik.capstone.databinding.BookDetailsCustomViewBinding;

public class BookDetailsCustomView extends ConstraintLayout implements BookDetailsFragment.BookDetailsReceived {
    private String ISBN;
    private BookDetailsCustomViewBinding bookDetailsCustomViewBinding;

    public BookDetailsCustomView(Context context) {
        super(context);
        init();
    }

    public BookDetailsCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public BookDetailsCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }
    public void init() {
        bookDetailsCustomViewBinding = BookDetailsCustomViewBinding.inflate(LayoutInflater.from(getContext()), this, true);
    }

    @Override
    public void onBookDetailsReceived(String ISBN) {
        this.ISBN = ISBN;
    }
    @Override
    public void onIsNewBookReceived(Boolean isNewBook) {
        if (isNewBook) {
            bookDetailsCustomViewBinding.nonEditableView.setVisibility(View.GONE);
            bookDetailsCustomViewBinding.editableView.setVisibility(View.VISIBLE);
            bookDetailsCustomViewBinding.ISBNEditText.setText(ISBN);
        } else {
            bookDetailsCustomViewBinding.nonEditableView.setVisibility(View.VISIBLE);
            bookDetailsCustomViewBinding.editableView.setVisibility(View.GONE);
            bookDetailsCustomViewBinding.ISBNTextView.setText(ISBN);

        }

    }
}
