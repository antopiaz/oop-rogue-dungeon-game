package polymorphia.strategy;

import polymorphia.maze.Room;
import polymorphia.characters.Character;

import java.util.Scanner;

public class HumanStrategy extends PlayStrategy {
    private final Scanner scanner;

    public HumanStrategy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public int getFightAction(Character character) {
        System.out.print("Fight action: ");
        String line = scanner.nextLine();
        return Integer.parseInt(line);
    }

    @Override
    public void doAction(Character character, Room currentRoom) {

    }
}
