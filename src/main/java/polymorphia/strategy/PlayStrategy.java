package polymorphia.strategy;

import polymorphia.characters.Character;
import polymorphia.maze.Room;

abstract public class PlayStrategy {
    abstract public int getFightAction(Character character);
    abstract public void doAction(Character character, Room currentRoom);

    boolean shouldMove(Room room) {
        return true;
    }
}
