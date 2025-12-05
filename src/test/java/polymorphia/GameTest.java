package polymorphia;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import polymorphia.artifacts.ArtifactFactory;
import polymorphia.artifacts.IArtifact;
import polymorphia.characters.CharacterFactory;
import polymorphia.characters.Player;
import polymorphia.maze.Maze;
import polymorphia.maze.RoomFactory;

import java.util.Scanner;

public class GameTest {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(GameTest.class);
    ArtifactFactory artifactFactory = new ArtifactFactory();
    RoomFactory roomFactory = new RoomFactory();
    CharacterFactory characterFactory = new CharacterFactory();

    @Test
    public void testFight() {
        Scanner scanner = new Scanner("tello\neat apple\nfight ogre 1 \n0\nfight ogre 1 \n0\nfight ogre 1 \n0");

        Player player = new Player("Hero", scanner);
        IArtifact food = artifactFactory.createFood("apple");
        Maze maze = Maze.getNewBuilder(roomFactory)
                .createDungeon(1,1)
                .add(player)
                .add(characterFactory.createCreature("Ogre 1", 2.0))
                .addArtifact(food)
                .build();
        System.out.println(maze.toString());
        Polymorphia game = new Polymorphia(maze, scanner);
        game.play();
        assert game.isOver();
        assert !game.hasLivingAdventurers() || !game.hasLivingCreatures();
    }

    @Test
    public void testMove() {
        Scanner scanner = new Scanner("tello\nmove north\nmove east\nmove south\nmove west");

        Player player = new Player("Hero", scanner);
        Maze maze = Maze.getNewBuilder(roomFactory)
                .createDungeon(2,2)
                .add(characterFactory.createCreature("Ogre"))
                .addCharacterToStart(player)
                .build();
        System.out.println(maze.toString());
        Polymorphia game = new Polymorphia(maze, scanner);
        game.play();
        assert game.isOver();
        assert !game.hasLivingAdventurers() || !game.hasLivingCreatures();
    }

    @Test
    public void testAttacker() {
        Scanner scanner = new Scanner("tello\neat apple\nfight attacker 1 \n1\nfight attacker 1 \n1\nfight attacker 1 \n1");

        Player player = new Player("Hero", scanner);
        IArtifact food = artifactFactory.createFood("apple");
        Maze maze = Maze.getNewBuilder(roomFactory)
                .createDungeon(1,1)
                .add(player)
                .add(characterFactory.createAttacker("attacker 1"))
                .addArtifact(food)
                .build();
        System.out.println(maze.toString());
        Polymorphia game = new Polymorphia(maze, scanner);
        game.play();
        assert game.isOver();
        assert !game.hasLivingAdventurers() || !game.hasLivingCreatures();
    }

}
