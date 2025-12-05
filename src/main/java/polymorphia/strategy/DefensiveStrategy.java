package polymorphia.strategy;

import polymorphia.maze.Room;
import polymorphia.characters.Character;

public class DefensiveStrategy extends PlayStrategy {
    @Override
    public int getFightAction(Character character) {
        return 0;
    }

    @Override
    public void doAction(Character character, Room currentRoom) {

    }
}
