package dungeon;

import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import dungeon.artifacts.ArtifactFactory;
import dungeon.artifacts.IArtifact;
import dungeon.characters.CharacterFactory;
import dungeon.characters.Player;
import dungeon.maze.Maze;
import dungeon.maze.RoomFactory;
import dungeon.strategy.HumanStrategy;

public class DisplayUITest {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(GameTest.class);
    ArtifactFactory artifactFactory = new ArtifactFactory();
    RoomFactory roomFactory = new RoomFactory();
    CharacterFactory characterFactory = new CharacterFactory();

    @Test
    public void testEmptyRoom() {
        Scanner scanner = new Scanner("Hello\n");

        GameFacade game = GameFacade.initializeGame(scanner);
        game.displayUI();
    }

    @Test
    public void testArmor() {
        Scanner scanner = new Scanner("wear platemail\nfight attacker 1 \ngrapple\nfight attacker 1 \ngrapple\nfight attacker 1 \ngrapple\nfight attacker 1 \ngrapple\nfight attacker 1 \ngrapple");

        Player player = new Player("Hero", scanner, new HumanStrategy(scanner));
        IArtifact food = artifactFactory.createFood("apple");
        IArtifact armor = artifactFactory.createArmor("platemail",1.0);
        IArtifact treasure = artifactFactory.createTreasure("treasure");

        Maze maze = Maze.getNewBuilder(roomFactory)
                .createDungeon(1,1)
                .add(player)
                .add(characterFactory.createAttacker("attacker 1", 5.0))
                .addArtifact(food)
                .addArtifact(armor)
                .addArtifact(treasure)
                .build();
        logger.info(maze.toString());
        Dungeon game = new Dungeon(maze);
        game.play();
        assert game.isOver();
        assert !game.hasLivingAdventurers() || !game.hasLivingCreatures();
    }

}
