package dungeon.strategy;

import dungeon.characters.FightActions;
import dungeon.maze.Room;
import dungeon.characters.Character;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class HumanStrategy extends PlayStrategy {
    private final Scanner scanner;
    Logger logger = LoggerFactory.getLogger(HumanStrategy.class);

    public HumanStrategy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public FightActions getFightAction(Character character) {
        logger.info("Choose fight action: ");
        String line = scanner.nextLine();

        return switch(line) {
            case "strike" -> FightActions.STRIKE;
            case "lunge" -> FightActions.LUNGE;
            case "grapple" -> FightActions.GRAPPLE;
            default -> FightActions.STRIKE;
        };
    }

    @Override
    public void doAction(Character character, Room currentRoom) {

    }
}
