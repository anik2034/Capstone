package com.anik.capstone.util;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class SingleLiveData<T> extends MutableLiveData<T> {

    private boolean hasBeenHandled = false;

    @Override
    public void observe(LifecycleOwner owner, Observer<? super T> observer) {
        super.observe(owner, t -> {
            if (hasBeenHandled) {
                return;
            }
            hasBeenHandled = true;
            observer.onChanged(t);
        });
    }

    @Override
    public void setValue(T value) {
        hasBeenHandled = false;
        super.setValue(value);
    }
}