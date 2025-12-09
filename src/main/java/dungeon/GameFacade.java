package dungeon;

import java.util.Random;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dungeon.artifacts.ArtifactFactory;
import dungeon.artifacts.IArtifact;
import dungeon.characters.Character;
import dungeon.characters.CharacterFactory;
import dungeon.characters.Player;
import dungeon.maze.DirectionType;
import dungeon.maze.Maze;
import dungeon.maze.Room;
import dungeon.maze.RoomFactory;
import dungeon.strategy.HumanStrategy;

public class GameFacade {
    private final Dungeon dungeon;
    private final Scanner scanner;
    private static final Logger logger = LoggerFactory.getLogger(GameFacade.class);
    private static final Random random = new Random();

    private static final int MAZE_MIN_WIDTH_LENGTH = 3;
    private static final int MAZE_MAX_WIDTH_LENGTH = 6;
    private static final int MIN_NUMBER_OF_ENEMIES = 5;
    private static final int MAX_NUMBER_OF_ENEMIES = 10;
    private static final int MIN_NUMBER_OF_FOOD_ITEMS = 10;
    private static final int MAX_NUMBER_OF_FOOD_ITEMS = 20;
    private static final int DEFAULT_NUMBER_OF_ARMOR_AND_WEAPONS = 10;
    private static final int DEFAULT_NUMBER_OF_TREASURE_ITEMS = 5;

    private GameFacade(Dungeon dungeon, Scanner scanner) {
        this.dungeon = dungeon;
        this.scanner = scanner;
    }

    public static GameFacade initializeGame(Scanner scanner) {

        ArtifactFactory artifactFactory = new ArtifactFactory();
        RoomFactory roomFactory = new RoomFactory();
        CharacterFactory characterFactory = new CharacterFactory();

        Player player = new Player("Hero", scanner, new HumanStrategy(scanner));
        Maze maze = Maze.getNewBuilder(roomFactory)
                .createDungeon(randomDimension(), randomDimension())
                .add(player)
                .addCharacters(characterFactory.createCreatures(randomEnemyCount()))
                .addCharacters(characterFactory.createAttackers(randomEnemyCount()))
                .addCharacters(characterFactory.createDefenders(randomEnemyCount()))
                .addArtifacts(artifactFactory.createFoodItems(randomFoodCount()))
                .addArtifacts(artifactFactory.createWeapons(DEFAULT_NUMBER_OF_ARMOR_AND_WEAPONS))
                .addArtifacts(artifactFactory.createArmoredSuits(DEFAULT_NUMBER_OF_ARMOR_AND_WEAPONS))
                .addArtifacts(artifactFactory.createTreasures(DEFAULT_NUMBER_OF_TREASURE_ITEMS))
                .build();

        logger.info(maze.toString());

        Dungeon dungeon = new Dungeon(maze);
        return new GameFacade(dungeon, scanner);
    }

    private static int randomDimension() {
        return random.nextInt(MAZE_MIN_WIDTH_LENGTH, MAZE_MAX_WIDTH_LENGTH);
    }

    private static int randomEnemyCount() {
        return random.nextInt(MIN_NUMBER_OF_ENEMIES, MAX_NUMBER_OF_ENEMIES);
    }

    private static int randomFoodCount() {
        return random.nextInt(MIN_NUMBER_OF_FOOD_ITEMS, MAX_NUMBER_OF_FOOD_ITEMS);
    }

    public void play() {
        printWelcomeMessage();

        while (!dungeon.isOver()) {
            displayUI();
            dungeon.playTurn();
        }
        
        dungeon.printGameOverMessage();
    }

    public void playTurn() {
        if (dungeon.getTurnCount() == 0) {
            printInstructions();
        }
        dungeon.incrementTurnCount();

        dungeon.playTurn();

        String eventMessage = String.format("Turn %s ended", dungeon.getTurnCount());
        logger.info(eventMessage);
    }

    public Boolean isOver() {
        return dungeon.isOver();
    }

    public void displayUI() {
        StringBuilder UIStringBuilder = new StringBuilder();

        Character player = dungeon.maze.getLivingAdventurers().getFirst();
        Room currentRoom = player.getCurrentLocation();

        UIStringBuilder.append("_____________________________________________________________________________\n");
        if (currentRoom.hasNeighbor(DirectionType.NORTH)) {
            UIStringBuilder.append("|                                     .                                     |\n");
            UIStringBuilder.append("|                                   .:;:.                                   |\n");
            UIStringBuilder.append("|                                 .:;;;;;:.                                 |\n");
            UIStringBuilder.append("|                                   ;;;;;                                   |\n");
            UIStringBuilder.append("|                                   ;;;;;                                   |\n");
            UIStringBuilder.append("|                                  (north)                                  |\n");
        }
        else {
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
        }
        
        if (currentRoom.hasLivingCreatures()) {
            UIStringBuilder.append("|                                                      (enemy)              |\n");
            UIStringBuilder.append("|                                                       o   o               |\n");
            UIStringBuilder.append("|                                                       |\\O/|               |\n");
            UIStringBuilder.append("|                                                        \\Y/                |\n");
            UIStringBuilder.append("|                                                        /_\\                |\n");
            UIStringBuilder.append("|                                                        _W_                |\n");
        }
        else {
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
        }
        UIStringBuilder.append("|                                                                           |\n");

        if (currentRoom.hasNeighbor(DirectionType.WEST)) {
            UIStringBuilder.append("|         .                                                                 |\n");
            UIStringBuilder.append("|       .;;.......                                                          |\n");
            UIStringBuilder.append("|     .;;;;:::::::   (west)                                                 |\n");
            UIStringBuilder.append("|      ':;;:::::::                                                          |\n");
            UIStringBuilder.append("|        ':                                                                 |\n");
        }
        else {
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
        }
        UIStringBuilder.append("|                                                                           |\n");

        if (currentRoom.hasArtifacts()) {
            UIStringBuilder.append("|                               __________                                  |\n");
            UIStringBuilder.append("|                              /\\____;;___\\                                 |\n");
            UIStringBuilder.append("|                             | /         /                                 |\n");
            UIStringBuilder.append("|                             `. ())oo() .                                  |\n");
            UIStringBuilder.append("|                              |\\(%()*^^()^\\                                |\n");
            UIStringBuilder.append("|                              | |-%-------|                                |\n");
            UIStringBuilder.append("|                              \\ | %  ))   |                                |\n");
            UIStringBuilder.append("|                               \\|%________|                                |\n");
        }
        else {
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
        }

        if (currentRoom.hasNeighbor(DirectionType.EAST)) {
            UIStringBuilder.append("|                                                                 .         |\n");
            UIStringBuilder.append("|                                                          .......;;.       |\n");
            UIStringBuilder.append("|                                                 (east)   :::::::;;;;.     |\n");
            UIStringBuilder.append("|                                                          :::::::;;:'      |\n");
            UIStringBuilder.append("|                                                                 :'        |\n");
        }
        else {
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
        }
        UIStringBuilder.append("|                                                                           |\n");

        UIStringBuilder.append("|        (you)                                                              |\n");
        UIStringBuilder.append("|         `o^                                                               |\n");
        UIStringBuilder.append("|       ^\\/0\\_+---                                                          |\n");
        UIStringBuilder.append("|         /O\\                                                               |\n");
        UIStringBuilder.append("|        _| /_                                                              |\n");
        UIStringBuilder.append("|                                                                           |\n");

        if (currentRoom.hasNeighbor(DirectionType.SOUTH)) {
            UIStringBuilder.append("|                                   (south)                                 |\n");
            UIStringBuilder.append("|                                    ;;;;;                                  |\n");
            UIStringBuilder.append("|                                    ;;;;;                                  |\n");
            UIStringBuilder.append("|                                  ..;;;;;..                                |\n");
            UIStringBuilder.append("|                                   ':::::'                                 |\n");
            UIStringBuilder.append("|                                     ':`                                   |\n");
        }
        else {
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
            UIStringBuilder.append("|                                                                           |\n");
        }
        UIStringBuilder.append("|                                                                           |\n");

        UIStringBuilder.append("_____________________________________________________________________________\n");
        
        UIStringBuilder.append(" LOCATION: \n").append(currentRoom.toString()).append("\n");
        UIStringBuilder.append(" Adjacent Rooms: \n").append(currentRoom.getNeighborListString()).append("\n");
        UIStringBuilder.append("========================================\n\n");

        UIStringBuilder.append("--- ARTIFACTS IN ROOM: ---\n");
        if (currentRoom.hasArtifacts()) {
            for (IArtifact artifact : currentRoom.getArtifacts()) {
                UIStringBuilder.append("o ").append(artifact.getName()).append(": ").append(String.format("%.2f", artifact.getValue())).append("\n");
            }
        } else {
            UIStringBuilder.append("(None)\n");
        }
        UIStringBuilder.append("\n");

        UIStringBuilder.append("--- ENEMIES IN ROOM: ---\n");
        if (currentRoom.hasLivingCreatures()) {
            for (Character enemy : currentRoom.getLivingCreatures()) {
                UIStringBuilder.append("x ").append(enemy.getName()).append(" (HP: ").append(String.format("%.2f", enemy.getHealth())).append(")\n");
            }
        } else {
            UIStringBuilder.append("(Safe)\n");
        }
        UIStringBuilder.append("\n");

        UIStringBuilder.append("--- YOUR STATS ---\n");
        UIStringBuilder.append("HP: ").append(String.format("%.2f", player.getHealth())).append("\n");
        UIStringBuilder.append("Turn: ").append(dungeon.getTurnCount()).append("\n");
        UIStringBuilder.append("\n");

        // Print out string representation
        System.out.println(UIStringBuilder.toString());
    }

    private void printWelcomeMessage() {
        logger.info("__________________________________________");
        logger.info("|      WELCOME TO JAVA GUYS DUNGEON!     |");
        logger.info("__________________________________________");
        logger.info("\n");
    }

    public static void printInstructions() {
        logger.info("\n=== HELP ===");
        logger.info("help - To see commands again");
        logger.info("fight <enemy>  - Attack an enemy, (strike, lunge, grapple)");
        logger.info("move <direction> - Move (north/south/east/west)");
        logger.info("eat <food>     - Consume food");
        logger.info("wear <armor>   - Put on armor");
        logger.info("equip <weapon> - Equip a weapon");
        logger.info("obtain <item>  - Pick up treasure");
        logger.info("===============\n");
    }
}
