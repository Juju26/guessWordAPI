package com.guessword.guessWordAPI.Service;

import com.guessword.guessWordAPI.Model.GuessWordApi;
import com.guessword.guessWordAPI.Model.Word;
import com.guessword.guessWordAPI.Repository.GuessWordApiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GuessWordApiService {

    @Autowired
    private GuessWordApiRepository guessWordApiRepository;

    private static final Logger log = LoggerFactory.getLogger(GuessWordApiService.class);

    public Map<String, Object> process(GuessWordApi data){
        List<String> matches=new ArrayList<>();

        List<String> letters=data.getLetters();
        List<String> statuses=data.getStatuses();
        Set<Character> mustInclude=data.getMustInclude().toLowerCase().chars()  // [TODO] currenlty ui is not giving any mustInclude
                .mapToObj(c -> (char) c)
                .collect(Collectors.toSet());

        for(char c: data.getMustInclude().toLowerCase().toCharArray()){
            mustInclude.add(c);
        }

        Map<Integer,String> knownPositions =new HashMap<>();
        Set<String> notIn = new HashSet<>();
        Set<String> yellows= new HashSet<>();

        for(int i=0;i<5;i++){
            String letter=letters.get(i).toLowerCase();
            String status=statuses.get(i).toLowerCase();

            switch (status){
                case "green" -> knownPositions.put(i,letter);
                case  "gray" -> notIn.add(letter);
                case "yellow" -> yellows.add(letter);
            }
        }

//        [TODO] not the best way to loop all the words and check if its the given word to be fixed
       List<Word> candidateWords= guessWordApiRepository.findAllValidFiveLetterWords();

        for(Word wordObj:candidateWords){

           final String word=wordObj.getText();

           // 1. Skip if it contains any letter that should not be present (gray)
           if (notIn.stream().anyMatch(word::contains)) continue;

           // 2. Skip if green letters are not at correct positions
           boolean matchesGreen = knownPositions.entrySet().stream()
                   .allMatch(e -> word.charAt(e.getKey()) == e.getValue().charAt(0));
           if (!matchesGreen) continue;

           // 3. Skip if it doesn't contain all mustInclude letters
           if (!mustInclude.stream().allMatch(c -> word.indexOf(c) >= 0)) continue;



           // 4. Skip if yellow letters appear in green positions
           boolean yellowConflict = knownPositions.entrySet().stream()
                   .anyMatch(e -> yellows.contains(e.getValue()) && word.charAt(e.getKey()) == e.getValue().charAt(0));
           if (yellowConflict) continue;


           matches.add(word);


       }
        log.info(matches.toString());
        return Map.of("matches",matches);
    }
}
