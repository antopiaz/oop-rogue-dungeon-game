package dungeon;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;

import dungeon.characters.Character;
import dungeon.maze.Maze;

public class Dungeon {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(Dungeon.class);

    Maze maze;
    Integer turnCount = 0;
    final Random rand = new Random();

    public Dungeon(Maze maze) {
        this.maze = maze;
    }

    @Override
    public String toString() {
        return "Dungeon: turn " + turnCount + "\n" + maze.toString();
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void incrementTurnCount() {
        turnCount++;
    }

    public Boolean isOver() {
        return !hasLivingCreatures() || !hasLivingAdventurers() || maze.getLivingAdventurers().getFirst().hasTreasure;
    }

    public Boolean hasLivingCreatures() {
        return maze.hasLivingCreatures();
    }

    public Boolean hasLivingAdventurers() {
        return maze.hasLivingAdventurers();
    }

    public void playTurn() {
        // Process all the characters in random order
        List<Character> characters = getLivingCharacters();
        while (!characters.isEmpty()) {
            int index = rand.nextInt(characters.size());
            Character character = characters.get(index);
            if (character.isAlive()) {
                character.doAction();
            }
            characters.remove(index);
        }
    }

    private List<Character> getLivingCharacters() {
        return maze.getLivingCharacters();
    }


    public void play() {
        while (!isOver()) {
            incrementTurnCount();
            playTurn();
        }

        printGameOverMessage();
    }

    void printGameOverMessage() {
        System.out.println("GAME OVER\n");
        System.out.println(String.format("Turns: {}\n\n", turnCount));
        String eventDescription;
        if (hasLivingAdventurers()) {
            eventDescription = "YOU WIN!\n";
        } else if (hasLivingCreatures()) {
            eventDescription = "YOU LOSE! You were defeated by monsters!\n";
        } else {
            eventDescription = "TIE GAME! Everyone died!\n";
        }
        System.out.println(eventDescription);
    }
}
