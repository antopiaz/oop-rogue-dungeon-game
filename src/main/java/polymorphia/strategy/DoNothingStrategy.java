package polymorphia.strategy;

import polymorphia.characters.Character;
import polymorphia.characters.Creature;
import polymorphia.maze.Room;
import java.util.Random;

public class DoNothingStrategy extends PlayStrategy {
    Random random = new Random();
    @Override
    public int getFightAction(Character character) {
        return random.nextInt(3);
    }

    @Override
    public void doAction(Character character, Room currentRoom) {
        // Do nothing
    }
}
