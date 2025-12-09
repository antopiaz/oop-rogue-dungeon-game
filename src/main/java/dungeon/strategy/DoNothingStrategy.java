package dungeon.strategy;

import dungeon.characters.Character;
import dungeon.maze.Room;
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
