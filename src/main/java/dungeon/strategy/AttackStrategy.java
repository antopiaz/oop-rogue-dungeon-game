package dungeon.strategy;

import dungeon.characters.FightActions;
import dungeon.maze.Room;
import dungeon.characters.Character;

public class AttackStrategy extends PlayStrategy {
    @Override
    public FightActions getFightAction(Character character) {
        return FightActions.STRIKE;
    }

    @Override
    public void doAction(Character character, Room currentRoom) {

    }
}
