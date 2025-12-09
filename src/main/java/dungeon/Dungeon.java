package dungeon;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.slf4j.Logger;

import dungeon.artifacts.IArtifact;
import dungeon.artifacts.Treasure;
import dungeon.characters.Character;
import dungeon.maze.Maze;
import dungeon.maze.Room;

public class Dungeon {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(Dungeon.class);

    Maze maze;
    Integer turnCount = 0;
    final Random rand = new Random();

    public Dungeon(Maze maze, Scanner scanner) {
        this.maze = maze;
    }

    @Override
    public String toString() {
        return "Polymorphia MAZE: turn " + turnCount + "\n" + maze.toString();
    }

    public Boolean isOver() {
        boolean treasureTaken = maze.getRooms().stream()
                .flatMap(room -> room.getArtifacts().stream())
                .noneMatch(artifact -> artifact instanceof Treasure);

        return !hasLivingCreatures() || !hasLivingAdventurers() || treasureTaken;
    }

    public Boolean hasLivingCreatures() {
        return maze.hasLivingCreatures();
    }

    public Boolean hasLivingAdventurers() {
        return maze.hasLivingAdventurers();
    }

    public void playTurn() {
        if (turnCount == 0) {
            logger.info("\nStarting play...\n");
        }
        turnCount += 1;

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
        String eventMessage = String.format("Turn %s ended and there are %s living adventurers", turnCount, maze.getLivingAdventurers().size());
        logger.info(eventMessage);
    }

    private List<Character> getLivingCharacters() {
        return maze.getLivingCharacters();
    }


    public void play() {
        while (!isOver()) {
            displayUI();
            playTurn();
            logger.info(this.toString());
        }
        String eventMessage = String.format("The game ended after %s turns", turnCount);

        logger.info("The game ended after {} turns.\n", turnCount);
        String eventDescription;
        if (hasLivingAdventurers()) {
            eventDescription = "The adventurers won! Left standing are: " + getAdventurerNames() + "\n";
        } else if (hasLivingCreatures()) {
            eventDescription = "The creatures won. Boo! Left standing are: " + getCreatureNames() + "\n";
        } else {
            eventDescription = "No team won! Everyone died!\n";
        }
        logger.info(eventDescription);
    }

    public void displayUI() {
        StringBuilder sb = new StringBuilder();

        Character player = maze.getLivingAdventurers().getFirst(); // TODO: confirm that player is only adventurer
        Room currentRoom = player.getCurrentLocation();

        sb.append("_____________________________________________________________________________\n");
        
        if (currentRoom.hasLivingCreatures()) {
            sb.append("|                                                      (enemy)              |\n");
            sb.append("|                                                       o   o               |\n");
            sb.append("|                                                       |\\O/|               |\n");
            sb.append("|                                                        \\Y/                |\n");
            sb.append("|                                                        /_\\                |\n");
            sb.append("|                                                        _W_                |\n");
        }
        else {
            sb.append("|                                                                           |\n");
            sb.append("|                                                                           |\n");
            sb.append("|                                                                           |\n");
            sb.append("|                                                                           |\n");
            sb.append("|                                                                           |\n");
            sb.append("|                                                                           |\n");
        }
        sb.append("|                                                                           |\n");

        if (currentRoom.hasArtifacts()) {
            sb.append("|                               __________                                  |\n");
            sb.append("|                              /\\____;;___\\                                 |\n");
            sb.append("|                             | /         /                                 |\n");
            sb.append("|                             `. ())oo() .                                  |\n");
            sb.append("|                              |\\(%()*^^()^\\                                |\n");
            sb.append("|                              | |-%-------|                                |\n");
            sb.append("|                              \\ | %  ))   |                                |\n");
            sb.append("|                               \\|%________|                                |\n");
        }
        else {
            sb.append("|                                                                           |\n");
            sb.append("|                                                                           |\n");
            sb.append("|                                                                           |\n");
            sb.append("|                                                                           |\n");
            sb.append("|                                                                           |\n");
            sb.append("|                                                                           |\n");
            sb.append("|                                                                           |\n");
            sb.append("|                                                                           |\n");
        }
        sb.append("|        (you)                                                              |\n");
        sb.append("|         `o^                                                               |\n");
        sb.append("|       ^\\/0\\_+---                                                          |\n");
        sb.append("|         /O\\                                                               |\n");
        sb.append("|        _| /_                                                              |\n");
        sb.append("|                                                                           |\n");
        sb.append("_____________________________________________________________________________\n");
        
        sb.append(" LOCATION: \n").append(currentRoom.toString()).append("\n\n");
        sb.append("========================================\n\n");

        sb.append("--- ARTIFACTS IN ROOM: ---\n");
        if (currentRoom.hasArtifacts()) {
            for (IArtifact artifact : currentRoom.getArtifacts()) {
                sb.append("o ").append(artifact.getName()).append(": ").append(artifact.getValue()).append("\n");
            }
        } else {
            sb.append("(None)\n");
        }
        sb.append("\n");

        sb.append("--- ENEMIES IN ROOM: ---\n");
        if (currentRoom.hasLivingCreatures()) {
            for (Character monster : currentRoom.getLivingCreatures()) {
                sb.append("x ").append(monster.getName()).append(" (HP: ").append(monster.getHealth()).append(")\n");
            }
        } else {
            sb.append("(Safe)\n");
        }
        sb.append("\n");

        // Print out string representation
        System.out.println(sb.toString());
    }

    private String getAdventurerNames() {
        return String.join(", ", getLivingCharacters().stream().map(Object::toString).toList());
    }

    private String getCreatureNames() {
        return String.join(", ", getLivingCreatures().stream().map(Object::toString).toList());
    }

    private List<Character> getLivingCreatures() {
        return maze.getLivingCreatures();
    }

    public Character getWinner() {
        if (!isOver() || !hasLivingCharacters()) {
            // No one has won yet, or no one won -- all died
            return null;
        }
        return getLivingCharacters().getFirst();
    }

    private boolean hasLivingCharacters() {
        return maze.hasLivingCharacters();
    }
}
