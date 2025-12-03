package polymorphia.characters;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.Scanner;


public class PlayerTest {
    Scanner scanner = new Scanner(System.in);

    @Test
    public void testConstructor() {
        Player hero = new Player("Hero", scanner);
        assertEquals("Hero", hero.getName());
        assertEquals(10.0, hero.getHealth());
    }

    @Test
    public void testInput() {
        String input = "command arg1";
        Scanner scanner = new Scanner(input);
        Player hero = new Player("Hero", scanner);
        String[] action = hero.getAction();
        assert(action[0].equals("command"));
        assert(action[1].equals("arg1"));
    }
}
