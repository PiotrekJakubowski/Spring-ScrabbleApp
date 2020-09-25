package web.app.scrable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.app.scrable.entity.PolishWords;
import web.app.scrable.logic.WordLogic;
import web.app.scrable.repository.PolishWordsRepository;
import web.app.scrable.service.ScrableService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    PolishWordsRepository polishWordsRepository;
    ScrableService scrableService;

    @Autowired
    TestController(PolishWordsRepository polishWordsRepository, ScrableService scrableService) {
        this.polishWordsRepository = polishWordsRepository;
        this.scrableService = scrableService;
    }

    @GetMapping("/getKajak")
    public String getKajak() {
        return polishWordsRepository.findByWord("kajak").getWord();
    }

    @GetMapping("/getByFirstLetter")
    public int getByFirstLetter(@RequestParam String letter) {
        List<PolishWords> myList = polishWordsRepository.findByFirstLetter(letter);
        return myList.size();
    }

    @GetMapping("/getByLength")
    public int getByLength(@RequestParam int length) {
        List<PolishWords> myList = polishWordsRepository.findByWordLength(length);
        return myList.size();
    }

    @GetMapping("/custom")
    public String customMethod(@RequestParam Map<String, String> customQuery) {
        System.out.printf("param1 = " + customQuery.containsKey("param1") + " equals: " + customQuery.get("param1"));
        System.out.printf("param2 = " + customQuery.containsKey("param2") + " equals: " + customQuery.get("param2"));
        System.out.printf("param3 = " + customQuery.containsKey("param3") + " equals: " + customQuery.get("param3"));
        return customQuery.toString();
    }

    @GetMapping("/scrableServiceTest")
    public String scrableServiceTest(@RequestParam Map<String, String> lettersQuery) {
        long startTime = new Date().getTime();
        lettersQuery.forEach((k, v) -> {
            System.out.println("K=" + k + " | v=" + v);
        });
        List<PolishWords> polishWords = scrableService.getWordsByLength(lettersQuery.size());
        List<String> strList = scrableService.getStringArrayFromPolishWordsList(polishWords);
        List<String> containsList = scrableService.getWordsWhichContainParamLetters(strList, lettersQuery);
        System.out.printf(String.valueOf(containsList.size()));
        long endTime = new Date().getTime();
        System.out.printf("\nExecution Time: " + (endTime - startTime));
        return null;
    }

    @GetMapping("/wordLogic")
    public String wordLogic(@RequestParam Map<String, String> lettersQuery) {
        long startTime = new Date().getTime();
        List<Character> charList = new ArrayList<>();
        lettersQuery.forEach((k, v) -> {
            charList.add(v.charAt(0));
        });

        WordLogic.getWordList().clear();

        for(int i = 1; i <= lettersQuery.size(); i++) {
            WordLogic.printAllKLength(charList,  i);
        }

        System.out.println(WordLogic.getWordList().size());
        WordLogic.getWordList().forEach(value -> System.out.println(value));

        long endTime = new Date().getTime();
        System.out.printf("\nExecution Time: " + (endTime - startTime));
        return null;
    }

}
