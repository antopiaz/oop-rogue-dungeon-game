package dungeon;

import dungeon.artifacts.ArtifactFactory;
import dungeon.artifacts.IArtifact;
import dungeon.artifacts.Treasure;
import dungeon.characters.Character;
import dungeon.characters.CharacterFactory;
import dungeon.characters.Player;
import dungeon.maze.Maze;
import dungeon.maze.Room;
import dungeon.maze.RoomFactory;
import dungeon.strategy.HumanStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameFacade {

    private final Maze maze;
    private Scanner scanner;
    private final Logger logger = LoggerFactory.getLogger(GameFacade.class);
    private final Random rand = new Random();
    private int turnNum = 0;
    private boolean gameOver = false;

    public GameFacade(Maze maze) {
        this.maze = maze;

        Logger logger = org.slf4j.LoggerFactory.getLogger(GameFacade.class);

        ArtifactFactory artifactFactory = new ArtifactFactory();
        RoomFactory roomFactory = new RoomFactory();
        CharacterFactory characterFactory = new CharacterFactory();

        Scanner scanner = new Scanner(System.in);

        Player player = new Player("Hero", scanner, new HumanStrategy(scanner));
        IArtifact treasure = artifactFactory.createTreasure("treasure");
        maze = Maze.getNewBuilder(roomFactory)
                .createDungeon(10,10)
                .add(player)
                .addCharacters(characterFactory.createCreatures(10))
                .addCharacters(characterFactory.createAttackers(10))
                .addCharacters(characterFactory.createDefenders(10))
                .addArtifacts(artifactFactory.createFoodItems(25))
                .addArtifacts(artifactFactory.createWeapons(10))
                .addArtifacts(artifactFactory.createArmoredSuits(10))
                .addArtifact(treasure)
                .build();
        logger.info(maze.toString());
    }

    public void playTurn() {
        if (turnNum == 0) {
            logger.info("\nStarting Game\nYou can fight\nmove\neat\nwear\nequip\nobtain\nFor example fight ogre or eat apple etc.");
        }
        turnNum +=1;

        List<dungeon.characters.Character> characters = maze.getLivingCharacters();
        while (!characters.isEmpty()) {
            int index = rand.nextInt(characters.size());
            Character character = characters.get(index);
            if (character.isAlive()) {
                character.doAction();
            }
            characters.remove(index);
        }
        String eventMessage = String.format("Turn %s ended", turnNum);
        logger.info(eventMessage);
    }

    public Boolean isOver() {
        boolean treasureTaken = maze.getRooms().stream()
                .flatMap(room -> room.getArtifacts().stream())
                .noneMatch(artifact -> artifact instanceof Treasure);

        return !maze.hasLivingCreatures() || !maze.hasLivingAdventurers() || treasureTaken;
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

        System.out.println(sb.toString());
    }

}
