package web.app.scrable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.app.scrable.constants.LetterPoints;
import web.app.scrable.entity.PolishWords;
import web.app.scrable.logic.WordLogic;
import web.app.scrable.repository.PolishWordsRepository;
import web.app.scrable.service.ScrableService;

import java.util.*;

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
        WordLogic wordLogic = new WordLogic();

        wordLogic.wordList.clear();

        for (int i = 1; i <= lettersQuery.size(); i++) {
            wordLogic.findAllCollectionArrangement(charList, i);
        }

        System.out.println(wordLogic.wordList.size());
        wordLogic.wordList.forEach(value -> System.out.println(value));

        System.out.println(wordLogic.wordList.contains("K"));

        long endTime = new Date().getTime();
        System.out.printf("\nExecution Time: " + (endTime - startTime));
        return null;
    }

    @GetMapping("/scrableAppTest")
    public void scrableAppTest(@RequestParam Map<String, String> lettersQuery) {
        long startTime = new Date().getTime();

        //ScrableService
        long getWordsByLengthTimeStart = new Date().getTime();
        List<PolishWords> polishWords = scrableService.getWordsByLength(lettersQuery.size());
        long getWordsByLengthTimeEnd = new Date().getTime();
        System.out.printf("\nExecution Time for getWordsByLength: " + (getWordsByLengthTimeEnd - getWordsByLengthTimeStart));

        long getStringArrayFromPolishWordsListTimeStart = new Date().getTime();
        List<String> strList = scrableService.getStringArrayFromPolishWordsList(polishWords);
        long getStringArrayFromPolishWordsListTimeEnd = new Date().getTime();
        System.out.printf("\nExecution Time for getStringArrayFromPolishWordsList: " + (getStringArrayFromPolishWordsListTimeEnd - getStringArrayFromPolishWordsListTimeStart));

        long getWordsWhichContainParamLettersTimeStart = new Date().getTime();
        List<String> containsList = scrableService.getWordsWhichContainParamLetters(strList, lettersQuery);
        long getWordsWhichContainParamLettersTimeEnd = new Date().getTime();
        System.out.printf("\nExecution Time for getWordsWhichContainParamLetters: " + (getWordsWhichContainParamLettersTimeEnd - getWordsWhichContainParamLettersTimeStart));

        System.out.println("Words which contain param letters size: " + String.valueOf(containsList.size()));

        //WordLogic
        WordLogic wordLogic = new WordLogic();
        List<Character> charList = new ArrayList<>();
        lettersQuery.forEach((k, v) -> {
            charList.add(v.charAt(0));
        });

        wordLogic.wordList.clear();

        for (int i = 1; i <= lettersQuery.size(); i++) {
            wordLogic.findAllCollectionArrangement(charList, i);
        }

        System.out.println("WordLogic wordList size: " + wordLogic.wordList.size());

        Set<String> finalList = new HashSet<>();

        wordLogic.wordList.forEach(word -> {
            if (containsList.contains(word))
                finalList.add(word);
        });

        System.out.println("Final List size: " + finalList.size());
        for (String s : finalList) {
            System.out.println(s);
        }

        long endTime = new Date().getTime();
        System.out.printf("\nExecution Time: " + (endTime - startTime));
    }

    @GetMapping("/pointLogicTest")
    public void pointLogicTest(@RequestParam Map<String, String> lettersQuery) {
        lettersQuery.forEach((key, value) -> {
            if(value.length() == 1 && Character.isLetter(value.charAt(0))) {
                System.out.println(LetterPoints.getValueForLetter(value.charAt(0)));
            }
        });
    }


    @GetMapping("/scrabbleTest")
    public Map<String, Integer> getMapForLetters(@RequestParam Map<String, String> letters) {
        long startTime = new Date().getTime();

        System.out.println("1");
        long getWordsByLengthTimeStart = new Date().getTime();
        List<PolishWords> polishWords = scrableService.getWordsByLength(letters.size());
        long getWordsByLengthTimeEnd = new Date().getTime();
        System.out.println("\nExecution Time for getWordsByLength: " + (getWordsByLengthTimeEnd - getWordsByLengthTimeStart));
        System.out.println("PolishWords size: " +  polishWords.size());

        System.out.println("2");
        long getStringArrayFromPolishWordsListTimeStart = new Date().getTime();
        List<String> strList = scrableService.getStringArrayFromPolishWordsList(polishWords);
        long getStringArrayFromPolishWordsListTimeEnd = new Date().getTime();
        System.out.println("\nExecution Time for getStringArrayFromPolishWordsList: " + (getStringArrayFromPolishWordsListTimeEnd - getStringArrayFromPolishWordsListTimeStart));
        System.out.println("strList size: " + strList.size());

        System.out.println("3");
        long getWordsWhichContainParamLettersTimeStart = new Date().getTime();
        List<String> containsList = scrableService.getWordsWhichContainParamLetters(strList, letters);
        long getWordsWhichContainParamLettersTimeEnd = new Date().getTime();
        System.out.println("\nExecution Time for getWordsWhichContainParamLetters: " + (getWordsWhichContainParamLettersTimeEnd - getWordsWhichContainParamLettersTimeStart));
        System.out.println("cointainsList: " + containsList.size());

        //WordLogic
        System.out.println("4");
        WordLogic wordLogic = new WordLogic();

        System.out.println("5");
        List<Character> charList = new ArrayList<>();
        System.out.println("6");
        long lettersForEachStart = new Date().getTime();
        letters.forEach((k, v) -> {
            charList.add(v.charAt(0));
        });
        long lettersForEachEnd = new Date().getTime();
        System.out.println("\nExecution Time for letters ForEach: " + (lettersForEachEnd - lettersForEachStart));
        System.out.println("charList size: " + charList.size());

        System.out.println("7");
        long findAllCollectionArrangementForStart = new Date().getTime();
        for (int i = 1; i <= letters.size(); i++) {
            wordLogic.findAllCollectionArrangement(charList, i);
        }
        long findAllCollectionArrangementForEnd = new Date().getTime();
        System.out.println("\nExecution Time for findAllCollectionArrangementFor: " + (findAllCollectionArrangementForEnd - findAllCollectionArrangementForStart));
        System.out.println("wordLogic.wordList size: " + wordLogic.wordList.size());

        System.out.println("8");
        Set<String> finalList = new HashSet<>();

        System.out.println("9");
        long wordLogicWordListForEachStart = new Date().getTime();
        containsList.retainAll(wordLogic.wordList);
//        wordLogic.wordList.forEach(word -> {
//            if (containsList.contains(word))
//                finalList.add(word);
//        });
        long wordLogicWordListForEachEnd = new Date().getTime();
        System.out.println("\nExecution Time for wordLogicWordListForEach: " + (wordLogicWordListForEachEnd - wordLogicWordListForEachStart));
        System.out.println("wordLogic.wordList size: " + containsList.size());

        System.out.println("10");
        long createHashMapWithPointsForFinalWorldsStart = new Date().getTime();
        Map<String, Integer> wordsWithPointsMap = scrableService.createHashMapWithPointsForFinalWorlds(containsList);
        long createHashMapWithPointsForFinalWorldsEnd = new Date().getTime();
        System.out.println("\nExecution Time for createHashMapWithPointsForFinalWorlds: " + (createHashMapWithPointsForFinalWorldsEnd - createHashMapWithPointsForFinalWorldsStart));
        System.out.println("wordsWithPointsMap size: " + wordsWithPointsMap.size());

        long endTime = new Date().getTime();
        System.out.println("\nExecution Time: " + (endTime - startTime));

        return wordsWithPointsMap;
    }

}
