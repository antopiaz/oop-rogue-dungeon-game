package dungeon.strategy;

import dungeon.characters.Character;
import dungeon.maze.Room;

abstract public class PlayStrategy {
    abstract public int getFightAction(Character character);
    abstract public void doAction(Character character, Room currentRoom);
}
