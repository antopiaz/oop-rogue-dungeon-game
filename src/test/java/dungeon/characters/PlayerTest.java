package dungeon.characters;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import dungeon.strategy.HumanStrategy;

import java.util.Scanner;


public class PlayerTest {
    Scanner scanner = new Scanner(System.in);
    final static double DEFAULT_HEALTH=15.0;

    @Test
    public void testConstructor() {
        Player hero = new Player("Hero", scanner, new HumanStrategy(scanner));
        assertEquals("Hero", hero.getName());
        assertEquals(DEFAULT_HEALTH, hero.getHealth());
    }

    @Test
    public void testInput() {
        String input = "command arg1";
        Scanner scanner = new Scanner(input);
        Player hero = new Player("Hero", scanner, new HumanStrategy(scanner));
        String[] action = hero.getAction();
        assert(action[0].equals("command"));
        assert(action[1].equals("arg1"));
    }
}
