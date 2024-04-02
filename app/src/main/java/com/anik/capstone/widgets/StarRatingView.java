package com.anik.capstone.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.anik.capstone.databinding.StarRatingViewBinding;

public class StarRatingView extends FrameLayout {

    private StarRatingViewBinding starRatingViewBinding;



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

}