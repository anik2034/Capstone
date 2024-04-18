package com.anik.capstone.home;

import com.anik.capstone.home.DisplayType;

public class NextScreenData {
    private final DisplayType nextScreen;
    private final String data;

    public NextScreenData(DisplayType nextScreen, String data) {
        this.nextScreen = nextScreen;
        this.data = data;
    }

    public DisplayType getNextScreen() {
        return nextScreen;
    }

    public String getData() {
        return data;
    }
}

