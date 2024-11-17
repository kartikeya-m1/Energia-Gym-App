package com.project.intellifit_trainer;

public class Exercise {
    private String name;
    private String gifUrl;

    public Exercise(String name, String gifUrl) {
        this.name = name;
        this.gifUrl = gifUrl;
    }

    public String getName() {
        return name;
    }

    public String getGifUrl() {
        return gifUrl;
    }
}

