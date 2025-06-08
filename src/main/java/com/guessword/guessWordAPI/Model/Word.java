package com.guessword.guessWordAPI.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "words")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String text;

    @Column(name = "is_valid_word", nullable = false)
    private boolean isValidWord = true;

    public Word() {}

    public Word(String text) {
        this.text = text.toLowerCase();
        this.isValidWord = true;
    }

    public Word(String text, boolean isValidWord) {
        this.text = text.toLowerCase();
        this.isValidWord = isValidWord;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isValidWord() {
        return isValidWord;
    }

    public void setText(String text) {
        this.text = text.toLowerCase();
    }

    public void setValidWord(boolean validWord) {
        isValidWord = validWord;
    }
}

