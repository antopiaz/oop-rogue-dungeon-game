package polymorphia.strategy;

import polymorphia.maze.Room;
import polymorphia.characters.Character;
import java.util.Random;

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
