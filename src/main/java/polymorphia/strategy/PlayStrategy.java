package polymorphia.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import polymorphia.artifacts.IArtifact;
import polymorphia.characters.Character;
import polymorphia.maze.Room;

import java.util.Optional;

abstract public class PlayStrategy {
    private static final Logger logger = LoggerFactory.getLogger(PlayStrategy.class);

    static void eatFood(Character myself, Room currentRoom) {
        IArtifact food = currentRoom.getFood();
        myself.eat(food);
    }

    static void wearArmor(Character myself, Room currentRoom) {
        Optional<IArtifact> armor = currentRoom.getArmor();
        if (armor.isPresent()) {
            myself.wear(armor.get());
        }
    }

    static void equipWeapon(Character myself, Room currentRoom) {
        Optional<IArtifact> armor = currentRoom.getWeapon();
        if (armor.isPresent()) {
            myself.equip(armor.get());
        }
    }

    abstract public int getFightAction(Character character);
    abstract public void doAction(Character character, Room currentRoom);

    boolean shouldMove(Room room) {
        return true;
    }
}
