package com.anik.capstone.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.anik.capstone.databinding.HeaderBinding;

public class HeaderView extends FrameLayout {
    private HeaderBinding headerBinding;

    public HeaderView(Context context) {
        super(context);
        init();
    }

    public HeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        headerBinding = HeaderBinding.inflate(LayoutInflater.from(getContext()), this, true);
    }

    public void setHeaderName(String name){
        headerBinding.setHeaderName(name);
    }

}
