package polymorphia;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import polymorphia.artifacts.ArtifactFactory;
import polymorphia.characters.CharacterFactory;
import polymorphia.maze.Maze;
import polymorphia.maze.RoomFactory;

import java.util.Scanner;

public class PolymorphiaTest {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(PolymorphiaTest.class);
    ArtifactFactory artifactFactory = new ArtifactFactory();
    RoomFactory roomFactory = new RoomFactory();
    CharacterFactory characterFactory = new CharacterFactory();
    Scanner scanner = new Scanner(System.in);
    @Test
    public void testGame() {
        Maze maze = Maze.getNewBuilder(roomFactory)
                .createDungeon(2,2)
                .addCharacters(characterFactory.createKnights(2))
                .add(characterFactory.createCreature("Ogre"))
                .build();
        Polymorphia game = new Polymorphia(maze, scanner);
        game.play();
        assert game.isOver();
        assert !game.hasLivingAdventurers() || !game.hasLivingCreatures();
    }

    @Test
    void testFairPlay() {
        int adventurerWins = 0;
        int creatureWins = 0;
        int numTies = 0;
        int TOTAL_GAMES = 100;
        for (int i = 0; i < TOTAL_GAMES; i++) {
            Maze maze = Maze.getNewBuilder(roomFactory)
                    .createDungeon(3, 17)
                    .distributeRandomly()
                    .addArtifacts(artifactFactory.createFoodItems(25))
                    .addCharacters(characterFactory.createKnights(4))
                    .addCharacters(characterFactory.createCreatures(5))
                    .addCharacters(characterFactory.createDemons(3))
                    .build();
            Polymorphia game = new Polymorphia(maze, scanner);

            game.play();
            if (game.getWinner() == null) {
                numTies++;
            } else if (game.getWinner().isCreature()) {
                creatureWins++;
            } else {
                adventurerWins++;
            }
        }
        logger.info("Adventurers won " + adventurerWins + " and creatures won " + creatureWins);
        logger.info("There were " + numTies + " games with no winners");
    }
}
