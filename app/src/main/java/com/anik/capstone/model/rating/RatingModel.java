package com.anik.capstone.model.rating;

public class RatingModel {
    private float emotionalImpact;
    private float character;
    private float pacing;
    private float storyline;
    private float writingStyle;
    private float overallRating;

    public RatingModel() {

    }

    public RatingModel(float emotionalImpact, float character, float pacing, float storyline, float writingStyle) {
        this.emotionalImpact = emotionalImpact;
        this.character = character;
        this.pacing = pacing;
        this.storyline = storyline;
        this.writingStyle = writingStyle;
    }

    public float getEmotionalImpact() {
        return emotionalImpact;
    }

    public void setEmotionalImpact(float emotionalImpact) {
        this.emotionalImpact = emotionalImpact;
    }

    public float getCharacter() {
        return character;
    }

    public void setCharacter(float character) {
        this.character = character;
    }

    public float getPacing() {
        return pacing;
    }

    public void setPacing(float pacing) {
        this.pacing = pacing;
    }

    public float getStoryline() {
        return storyline;
    }

    public void setStoryline(float storyline) {
        this.storyline = storyline;
    }

    public float getWritingStyle() {
        return writingStyle;
    }

    public void setWritingStyle(float writingStyle) {
        this.writingStyle = writingStyle;
    }

    public float getOverallRating() {
        overallRating = (emotionalImpact + character + pacing + storyline + writingStyle) / 5;
        return overallRating;
    }
}


