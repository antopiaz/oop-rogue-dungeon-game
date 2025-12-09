package dungeon.characters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreatureTest {
    CharacterFactory characterFactory = new CharacterFactory();
    final double DEFAULT_CREATURE_INITIAL_HEALTH = 5.0;
    @Test
    public void testConstructor() {
        Character orc = characterFactory.createCreature("Orc", DEFAULT_CREATURE_INITIAL_HEALTH);
        assertEquals("Orc", orc.getName());
        assertEquals(DEFAULT_CREATURE_INITIAL_HEALTH, orc.getHealth());
    }
}