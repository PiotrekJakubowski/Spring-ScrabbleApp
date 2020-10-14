package web.app.scrable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.scrable.entity.PolishWords;
import web.app.scrable.logic.PointLogic;
import web.app.scrable.repository.PolishWordsRepository;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ScrableServiceImpl implements ScrableService {

    PolishWordsRepository polishWordsRepository;

    @Autowired
    ScrableServiceImpl(PolishWordsRepository polishWordsRepository) {
        this.polishWordsRepository = polishWordsRepository;
    }

    @Override
    public List<PolishWords> getWordsByLength(int length) {
        return polishWordsRepository.findByWordLength(length);
    }

    @Override
    public List<String> getStringArrayFromPolishWordsList(List<PolishWords> polishWordsList) {
        List<String> strPolishWords = new ArrayList<>();
        for (PolishWords polishWords : polishWordsList) {
            strPolishWords.add(polishWords.getWord());
        }
        return strPolishWords;
    }


    @Override
    public List<String> getWordsWhichContainParamLetters(List<String> listOfWords, Map<String, String> lettersMap) {
        List<String> wordsList = listOfWords.stream()
                .filter(word -> {
                    for (Map.Entry<String, String> entry : lettersMap.entrySet()) {
                        if (word.toString().contains(entry.getValue()))
                            return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());

        return wordsList;
    }

    @Override
    public Map<String, Integer> createHashMapWithPointsForFinalWorlds(Collection<String> worldsSet) {
        Map<String, Integer> mapWithWorldsAndPoints = new HashMap<>();
        PointLogic pointLogic = new PointLogic();
        for (String s : worldsSet) {
            mapWithWorldsAndPoints.put(s, pointLogic.calculatePointsForWord(s));
        }
        return mapWithWorldsAndPoints;
    }
}
