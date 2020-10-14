package web.app.scrable.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.app.scrable.entity.PolishWords;
import web.app.scrable.logic.WordLogic;
import web.app.scrable.repository.PolishWordsRepository;
import web.app.scrable.service.ScrableService;

import java.util.*;

@RestController
public class ScrabbleController {

    ScrableService scrableService;
    PolishWordsRepository polishWordsRepository;

    public ScrabbleController(PolishWordsRepository polishWordsRepository, ScrableService scrableService) {
        this.polishWordsRepository = polishWordsRepository;
        this.scrableService = scrableService;
    }

    @GetMapping("/scrabble")
    public Map<String, Integer> getMapForLetters(@RequestParam Map<String, String> letters) {
        long startTime = new Date().getTime();

        System.out.println("1");
        List<PolishWords> polishWords = scrableService.getWordsByLength(letters.size());
        System.out.println("2");
        List<String> strList = scrableService.getStringArrayFromPolishWordsList(polishWords);
        System.out.println("3");
        List<String> containsList = scrableService.getWordsWhichContainParamLetters(strList, letters);
        System.out.println("4");

        //WordLogic
        System.out.println("5");
        WordLogic wordLogic = new WordLogic();

        System.out.println("6");
        List<Character> charList = new ArrayList<>();
        System.out.println("7");
        letters.forEach((k, v) -> {
            charList.add(v.charAt(0));
        });

        System.out.println("8");
        for (int i = 1; i <= letters.size(); i++) {
            wordLogic.findAllCollectionArrangement(charList, i);
        }

        System.out.println("9");
        Set<String> finalList = new HashSet<>();

        System.out.println("10");
        wordLogic.wordList.forEach(word -> {
            if (containsList.contains(word))
                finalList.add(word);
        });

        System.out.println("11");
        Map<String, Integer> wordsWithPointsMap = scrableService.createHashMapWithPointsForFinalWorlds(finalList);

        long endTime = new Date().getTime();
        System.out.printf("\nExecution Time: " + (endTime - startTime));

        return wordsWithPointsMap;
    }

}
