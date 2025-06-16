package com.guessword.guessWordAPI.Controller;

import com.guessword.guessWordAPI.Model.GuessWordApi;
import com.guessword.guessWordAPI.Model.Word;
import com.guessword.guessWordAPI.Service.GuessWordApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GuessWordApiController {
    private static final Logger log = LoggerFactory.getLogger(GuessWordApiController.class);
    @Autowired
    private GuessWordApiService guessWordApiService;

    @GetMapping("/test")
    private String testController(){
        return "Finally Working huh ";
    }

    @GetMapping("/words")
    private List<Word> knownWords(){
        return guessWordApiService.savedWords();
    }
    @PostMapping("/process")
    private Map<String,Object> processGuessWord(@RequestBody GuessWordApi request){
        log.info("Contoller invoked");
        return guessWordApiService.process(request);
    }
}
