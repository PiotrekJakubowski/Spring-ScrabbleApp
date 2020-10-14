package web.app.scrable;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import web.app.scrable.constants.LetterPoints;
import web.app.scrable.logic.PointLogic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class ScrableApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testCharactersValues() {
        assertEquals(LetterPoints.getValueForLetter('a'), 1);
        assertEquals(LetterPoints.getValueForLetter('ą'), 5);
        assertEquals(LetterPoints.getValueForLetter('f'), 5);
        assertEquals(LetterPoints.getValueForLetter('p'), 2);
        assertNotEquals(LetterPoints.getValueForLetter('y'), 3);
        assertNotEquals(LetterPoints.getValueForLetter('r'), 2);
        assertNotEquals(LetterPoints.getValueForLetter('m'), 1);
    }

    @Test
    void testCalculatePointsForWordMethod() {
        PointLogic pointLogic = new PointLogic();
        assertEquals(pointLogic.calculatePointsForWord("kaj"), 6);
        assertEquals(pointLogic.calculatePointsForWord("otwocko"), 10);
    }

}
