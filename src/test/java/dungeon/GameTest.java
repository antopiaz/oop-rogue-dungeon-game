package dungeon;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import dungeon.artifacts.ArtifactFactory;
import dungeon.artifacts.IArtifact;
import dungeon.characters.CharacterFactory;
import dungeon.characters.Player;
import dungeon.maze.Maze;
import dungeon.maze.RoomFactory;
import dungeon.strategy.HumanStrategy;

import java.util.Scanner;

public class GameTest {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(GameTest.class);
    ArtifactFactory artifactFactory = new ArtifactFactory();
    RoomFactory roomFactory = new RoomFactory();
    CharacterFactory characterFactory = new CharacterFactory();

    final static double DEFAULT_HEALTH = 5.0;
    @Test
    public void testFight() {
        Scanner scanner = new Scanner("eat apple\nfight ogre 1 \nstrike\nfight ogre 1 \nstrike\nfight ogre 1 \nstrike\nfight ogre 1 \nstrike");

        Player player = new Player("Hero", scanner, new HumanStrategy(scanner));
        IArtifact food = artifactFactory.createFood("apple");
        IArtifact treasure = artifactFactory.createTreasure("treasure");
        Maze maze = Maze.getNewBuilder(roomFactory)
                .createDungeon(1,1)
                .add(player)
                .add(characterFactory.createCreature("Ogre 1", 2.0))
                .addArtifact(food)
                .addArtifact(treasure)
                .build();
        logger.info(maze.toString());
        Dungeon game = new Dungeon(maze);
        game.play();
        assert game.isOver();
        assert !game.hasLivingAdventurers() || !game.hasLivingCreatures();
    }

    @Test
    public void testMove() {
        Scanner scanner = new Scanner("move south\nmove east\nmove south\nmove west\nobtain treasure\nfight ogre\nstrike\nfight ogre\nstrike\nfight ogre\nstrike");
        IArtifact treasure = artifactFactory.createTreasure("treasure");
        Player player = new Player("Hero", scanner, new HumanStrategy(scanner));
        Maze maze = Maze.getNewBuilder(roomFactory)
                .createDungeon(2,2)
                .add(characterFactory.createCreature("Ogre", 1.0))
                .addArtifact(treasure, "Room (0,0)")
                .addCharacterToStart(player)
                .build();
        logger.info(maze.toString());
        Dungeon game = new Dungeon(maze);
        game.play();
        assert true;
    }

    @Test
    public void testAttacker() {
        Scanner scanner = new Scanner("eat apple\nfight attacker 1 \nlunge\nfight attacker 1 \nlunge\nfight attacker 1 \nlunge");

        Player player = new Player("Hero",scanner,  new HumanStrategy(scanner));
        IArtifact food = artifactFactory.createFood("apple");
        IArtifact treasure = artifactFactory.createTreasure("treasure");
        Maze maze = Maze.getNewBuilder(roomFactory)
                .createDungeon(1,1)
                .add(player)
                .add(characterFactory.createAttacker("attacker 1", DEFAULT_HEALTH))
                .addArtifact(food)
                .addArtifact(treasure)
                .build();
        System.out.println(maze.toString());
        Dungeon game = new Dungeon(maze);
        game.play();
        assert game.isOver();
        assert !game.hasLivingAdventurers() || !game.hasLivingCreatures();
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
                .add(characterFactory.createAttacker("attacker 1", DEFAULT_HEALTH))
                .addArtifact(food)
                .addArtifact(armor)
                .addArtifact(treasure)
                .build();
        System.out.println(maze.toString());
        Dungeon game = new Dungeon(maze);
        game.play();
        assert game.isOver();
        assert !game.hasLivingAdventurers() || !game.hasLivingCreatures();
    }

    @Test
    public void testWeapons() {
        Scanner scanner = new Scanner("equip sword\nfight attacker 1 \nlunge\nfight attacker 1 \nlunge\nfight attacker 1 \nlunge\nfight attacker 1 \nlunge");

        Player player = new Player("Hero", scanner, new HumanStrategy(scanner));
        IArtifact food = artifactFactory.createFood("apple");
        IArtifact weapon = artifactFactory.createWeapon("sword", 1.0);
        IArtifact treasure = artifactFactory.createTreasure("treasure");
        Maze maze = Maze.getNewBuilder(roomFactory)
                .createDungeon(1,1)
                .add(player)
                .add(characterFactory.createAttacker("attacker 1",DEFAULT_HEALTH))
                .addArtifact(food)
                .addArtifact(weapon)
                .addArtifact(treasure)
                .build();
        System.out.println(maze.toString());
        Dungeon game = new Dungeon(maze);
        game.play();
        assert game.isOver();
        assert !game.hasLivingAdventurers() || !game.hasLivingCreatures();
    }

}
