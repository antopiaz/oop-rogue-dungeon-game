package dungeon.strategy;

import dungeon.maze.Room;
import dungeon.characters.Character;

public class DefensiveStrategy extends PlayStrategy {
    @Override
    public int getFightAction(Character character) {
        double randomNumber = Math.random();
        if (randomNumber < 0.5) {
            return 1;
        }
        else{
            return 2;
        }
    }

    @Override
    public void doAction(Character character, Room currentRoom) {

    }
}
