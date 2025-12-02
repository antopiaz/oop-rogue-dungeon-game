package polymorphia.characters;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterTest {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(CharacterTest.class);
    CharacterFactory characterFactory = new CharacterFactory();

    @Test
    void testToString() {
        Character joe = characterFactory.createKnight("Joe");

        System.out.println(joe);

        assertTrue(joe.toString().contains("Joe"));
    }

    @Test
    void isAlive() {
        Character joe = characterFactory.createKnight("Joe");
        assertTrue(joe.isAlive());
    }

    @Test
    void testLoseHealthAndDeath() {
        Character joe = characterFactory.createKnight("Joe");
        joe.loseHealth(2.0);
        assertEquals(CharacterFactory.DEFAULT_FIGHTER_INITIAL_HEALTH - 2.0, joe.getHealth());

        joe.loseHealth(joe.getHealth());
        logger.info(joe.toString());
        assertFalse(joe.isAlive());
    }

}
