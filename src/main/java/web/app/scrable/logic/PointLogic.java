package web.app.scrable.logic;

import web.app.scrable.constants.LetterPoints;

public class PointLogic {

    public int calculatePointsForWord(String word) {
        int sum = 0;
        for(int i = 0; i < word.length(); i++) {
            if(Character.isLetter(word.charAt(i))) {
                sum += LetterPoints.getValueForLetter(word.charAt(i));
            }
        }
        return sum;
    }

}
