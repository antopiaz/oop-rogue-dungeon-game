package dungeon.strategy;

import dungeon.maze.Room;
import dungeon.characters.Character;

public class AttackStrategy extends PlayStrategy {
    @Override
    public int getFightAction(Character character) {
        return 0;
    }

    @Override
    public void doAction(Character character, Room currentRoom) {

    }
}
