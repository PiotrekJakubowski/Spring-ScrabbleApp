package web.app.scrable.service;

import org.springframework.stereotype.Service;
import web.app.scrable.entity.PolishWords;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface ScrableService {

    public List<PolishWords> getWordsByLength(int length);

    public List<String> getStringArrayFromPolishWordsList(List<PolishWords> polishWordsList);

    public List<String> getWordsWhichContainParamLetters(List<String> listOfWords, Map<String, String> lettersMap);

    public Map<String, Integer> createHashMapWithPointsForFinalWorlds(Collection<String> worldsSet);

}
