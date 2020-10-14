package web.app.scrable.logic;

import java.util.*;

public class WordLogic {

    public Set<String> wordList;

    public WordLogic() {
        wordList = new HashSet<>();
    }

    public void findAllCollectionArrangement(List<Character> set, int k) {
        int n = set.size();
        addToListAllKLengthRec(set, "", n, k);
    }

    // The main recursive method
    // to print all possible
    // strings of length k
    private void addToListAllKLengthRec(List<Character> set,
                                   String prefix,
                                   int n, int k) {

        // Base case: k is 0,
        // print prefix
        if (k == 0) {
            wordList.add(prefix);
            //System.out.println(prefix);
            return;
        }

        // One by one add all characters
        // from set and recursively
        // call for k equals to k-1
        for (int i = 0; i < n; ++i) {

            // Next character of input added
            String newPrefix = prefix + set.get(i);

            // k is decreased, because
            // we have added a new character
            addToListAllKLengthRec(set, newPrefix,
                    n, k - 1);
        }
    }
}
