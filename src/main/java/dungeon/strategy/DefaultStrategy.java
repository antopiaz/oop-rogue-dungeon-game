package dungeon.strategy;

import dungeon.characters.Character;
import dungeon.characters.FightActions;
import dungeon.maze.Room;
import java.util.Random;

public class DefaultStrategy extends PlayStrategy {
    Random random = new Random();
    @Override
    public FightActions getFightAction(Character character) {
        FightActions[] actions = FightActions.values();
        FightActions choice = actions[random.nextInt(actions.length)];
        return choice;
    }

    @Override
    public void doAction(Character character, Room currentRoom) {
        // Do nothing
    }
}
