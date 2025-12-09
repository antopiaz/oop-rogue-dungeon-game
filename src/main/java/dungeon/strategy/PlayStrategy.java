package dungeon.strategy;

import dungeon.characters.Character;
import dungeon.characters.FightActions;
import dungeon.maze.Room;

abstract public class PlayStrategy {
    abstract public FightActions getFightAction(Character character);
    abstract public void doAction(Character character, Room currentRoom);
}
