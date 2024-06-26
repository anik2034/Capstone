package com.anik.capstone.model.rating;

public class RatingModel {
    private float emotionalImpact;
    private float character;
    private float pacing;
    private float storyline;
    private float writingStyle;
    private float overallRating;

    public RatingModel() {}

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
        return overallRating;
    }
    public void setOverallRating(float overallRating) {
        this.overallRating = overallRating;
    }
}


