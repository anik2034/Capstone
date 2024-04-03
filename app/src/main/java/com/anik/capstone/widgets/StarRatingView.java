package com.anik.capstone.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.anik.capstone.databinding.StarRatingViewBinding;

import androidx.annotation.Nullable;

public class StarRatingView extends FrameLayout {

    private StarRatingViewBinding starRatingViewBinding;
    private StartRatingViewListener listener;

    public StarRatingView(Context context) {
        super(context);
        init();
    }

    public StarRatingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StarRatingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        starRatingViewBinding = StarRatingViewBinding.inflate(LayoutInflater.from(getContext()), this, true);
        starRatingViewBinding.saveImageButton.setOnClickListener(v -> {
            if(listener != null){
                listener.onRatingChanged(starRatingViewBinding.ratingBar.getRating());
            }
        });
    }

    public float getRating() {
        return starRatingViewBinding.getRate();
    }

    public void setRating(float rating) {
        starRatingViewBinding.setRate(rating);
        starRatingViewBinding.executePendingBindings();

    }

    public void setRatingType(String ratingType) {
        starRatingViewBinding.setRatingType(ratingType);

    }

    public boolean getIsEditable() {
        return starRatingViewBinding.getIsEditable();
    }

    public void setIsEditable(boolean isEditable) {
        starRatingViewBinding.setIsEditable(isEditable);
    }

    public void setListener(StartRatingViewListener listener) {
        this.listener = listener;
    }

    public interface StartRatingViewListener {
        void onRatingChanged(float rating);
    }
}