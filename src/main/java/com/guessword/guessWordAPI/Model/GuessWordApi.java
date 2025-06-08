package com.guessword.guessWordAPI.Model;

import java.util.List;

public class GuessWordApi {
    private List<String> letters;
    private List<String> statuses;
    private String mustInclude;

    public List<String> getLetters() {
        return letters;
    }

    public void setLetters(List<String> letters) {
        this.letters = letters;
    }

    public List<String> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<String> statuses) {
        this.statuses = statuses;
    }

    public String getMustInclude() {
        return mustInclude;
    }

    public void setMustInclude(String mustInclude) {
        this.mustInclude = mustInclude;
    }

    @Override
    public String toString() {
        return "GuessWordApi{" +
                "letters=" + letters +
                ", statuses=" + statuses +
                ", mustInclude='" + mustInclude + '\'' +
                '}';
    }
}
