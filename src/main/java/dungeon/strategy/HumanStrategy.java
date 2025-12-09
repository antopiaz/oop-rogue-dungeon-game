package dungeon.strategy;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dungeon.characters.Character;
import dungeon.characters.FightActions;
import dungeon.maze.Room;

public class HumanStrategy extends PlayStrategy {
    private final Scanner scanner;
    Logger logger = LoggerFactory.getLogger(HumanStrategy.class);

    public HumanStrategy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public FightActions getFightAction(Character character) {
        logger.info("Choose fight action (strike, lunge, grapple): ");
        String line = scanner.nextLine().toLowerCase();

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
