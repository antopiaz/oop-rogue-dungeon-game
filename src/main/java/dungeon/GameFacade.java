package dungeon;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dungeon.artifacts.ArtifactFactory;
import dungeon.artifacts.IArtifact;
import dungeon.characters.Character;
import dungeon.characters.CharacterFactory;
import dungeon.characters.Player;
import dungeon.maze.Maze;
import dungeon.maze.Room;
import dungeon.maze.RoomFactory;
import dungeon.strategy.HumanStrategy;

public class GameFacade {
    private final Dungeon dungeon;
    private final Scanner scanner;
    private static final Logger logger = LoggerFactory.getLogger(GameFacade.class);

    private GameFacade(Dungeon dungeon, Scanner scanner) {
        this.dungeon = dungeon;
        this.scanner = scanner;
    }

    public static GameFacade initializeGame(Scanner scanner) {

        ArtifactFactory artifactFactory = new ArtifactFactory();
        RoomFactory roomFactory = new RoomFactory();
        CharacterFactory characterFactory = new CharacterFactory();

        Player player = new Player("Hero", scanner, new HumanStrategy(scanner));
        IArtifact treasure = artifactFactory.createTreasure("treasure");
        Maze maze = Maze.getNewBuilder(roomFactory)
                .createDungeon(10,10)
                .add(player)
                .addCharacters(characterFactory.createCreatures(10))
                .addCharacters(characterFactory.createAttackers(10))
                .addCharacters(characterFactory.createDefenders(10))
                .addArtifacts(artifactFactory.createFoodItems(25))
                .addArtifacts(artifactFactory.createWeapons(10))
                .addArtifacts(artifactFactory.createArmoredSuits(10))
                .addArtifacts(artifactFactory.createTreasures(5))
                .addArtifact(treasure)
                .build();

        logger.info(maze.toString());

        Dungeon dungeon = new Dungeon(maze);
        return new GameFacade(dungeon, scanner);
    }

    public void play() {
        printWelcomeMessage();

        while (!dungeon.isOver()) {
            displayUI();
            dungeon.playTurn();
            logger.info(dungeon.toString());
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
        StringBuilder sb = new StringBuilder();

        Character player = dungeon.maze.getLivingAdventurers().getFirst();
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

        sb.append("--- YOUR STATS ---\n");
        sb.append("HP: ").append(player.getHealth()).append("\n");
        sb.append("Turn: ").append(dungeon.getTurnCount()).append("\n");
        sb.append("\n");

        // Print out string representation
        System.out.println(sb.toString());
    }

    private void printWelcomeMessage() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║      WELCOME TO JAVA GUYS DUNGEON!     ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
        logger.info("Game started with maze:\n" + dungeon.maze.toString());
    }

    public static void printInstructions() {
        System.out.println("\n=== HELP ===");
        System.out.println("help - To see commands again");
        System.out.println("fight <enemy>  - Attack an enemy, (strike, lunge, grapple)");
        System.out.println("move <direction> - Move (north/south/east/west)");
        System.out.println("eat <food>     - Consume food");
        System.out.println("wear <armor>   - Put on armor");
        System.out.println("equip <weapon> - Equip a weapon");
        System.out.println("obtain <item>  - Pick up treasure");
        System.out.println("===============\n");
    }
}
