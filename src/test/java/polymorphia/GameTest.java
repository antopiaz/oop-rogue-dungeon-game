package polymorphia;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import polymorphia.artifacts.ArtifactFactory;
import polymorphia.characters.CharacterFactory;
import polymorphia.characters.Player;
import polymorphia.maze.Maze;
import polymorphia.maze.RoomFactory;

import java.util.Scanner;

public class GameTest {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(PolymorphiaTest.class);
    ArtifactFactory artifactFactory = new ArtifactFactory();
    RoomFactory roomFactory = new RoomFactory();
    CharacterFactory characterFactory = new CharacterFactory();

    @Test
    public void testGame() {
        Scanner scanner = new Scanner("tello\neat\nmove\nfight\nfight\nfight\nfight\nfight");

        Player player = new Player("Hero", scanner);
        Maze maze = Maze.getNewBuilder(roomFactory)
                .createDungeon(1,2)
                .add(player)
                .add(characterFactory.createCreature("Ogre"))
                .add(characterFactory.createCreature("Ogre2"))
                .addArtifacts(artifactFactory.createFoodItems(2))
                .build();
        System.out.println(maze.toString());
        Polymorphia game = new Polymorphia(maze, scanner);
        game.play();
        assert game.isOver();
        assert !game.hasLivingAdventurers() || !game.hasLivingCreatures();
    }

}
