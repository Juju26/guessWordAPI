package com.guessword.guessWordAPI.Repository;

import com.guessword.guessWordAPI.Model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface GuessWordApiRepository extends JpaRepository<Word, Long> {

    @Query("SELECT w FROM Word w WHERE LENGTH(w.text) = 5 AND w.isValidWord = true")
    List<Word> findAllValidFiveLetterWords();
}
