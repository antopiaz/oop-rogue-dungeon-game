package dungeon.strategy;

import dungeon.characters.FightActions;
import dungeon.maze.Room;
import dungeon.characters.Character;

public class DefensiveStrategy extends PlayStrategy {
    @Override
    public FightActions getFightAction(Character character) {
        double randomNumber = Math.random();
        if (randomNumber < 0.5) {
            return FightActions.GRAPPLE;
        }
        else{
            return FightActions.LUNGE;
        }
    }

    @Override
    public void doAction(Character character, Room currentRoom) {

    }
}
