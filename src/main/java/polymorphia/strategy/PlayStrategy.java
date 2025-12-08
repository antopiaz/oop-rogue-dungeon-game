package polymorphia.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import polymorphia.artifacts.IArtifact;
import polymorphia.characters.Character;
import polymorphia.maze.Room;

abstract public class PlayStrategy {
    private static final Logger logger = LoggerFactory.getLogger(PlayStrategy.class);

    abstract public int getFightAction(Character character);
    abstract public void doAction(Character character, Room currentRoom);

    boolean shouldMove(Room room) {
        return true;
    }
}
