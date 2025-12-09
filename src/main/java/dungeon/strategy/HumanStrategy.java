package dungeon.strategy;

import dungeon.characters.FightActions;
import dungeon.maze.Room;
import dungeon.characters.Character;

import java.util.Scanner;

public class HumanStrategy extends PlayStrategy {
    private final Scanner scanner;

    public HumanStrategy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public FightActions getFightAction(Character character) {
        System.out.print("Fight action: ");
        String line = scanner.nextLine();

        return switch(line) {
            case "strike" -> FightActions.STRIKE;
            case "lunge" -> FightActions.LUNGE;
            case "block" -> FightActions.GRAPPLE;
            default -> FightActions.STRIKE;
        };
    }

    @Override
    public void doAction(Character character, Room currentRoom) {

    }
}
