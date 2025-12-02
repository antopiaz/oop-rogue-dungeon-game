package polymorphia.strategy;


import java.util.Optional;

import polymorphia.characters.Character;
import polymorphia.maze.Room;

public class FighterStrategy extends PlayStrategy {
    @Override
    public void doAction(Character myself, Room currentRoom) {
        if(shouldEquip(currentRoom)){
            equipWeapon(myself, currentRoom);
            return;
        }
        if(shouldWear(currentRoom)){
            wearArmor(myself, currentRoom);
            return;
        }
        if (shouldEat(currentRoom)) {
            eatFood(myself, currentRoom);
            return;
        }
        if (shouldMove(currentRoom)) {
            myself.move(currentRoom.getRandomNeighbor());
        }
    }

    boolean shouldEat(Room currentRoom) {
        return currentRoom.hasFood();
    }

    boolean shouldWear(Room currentRoom) {
        return currentRoom.hasArmor();
    }

    boolean shouldEquip(Room currentRoom) {
        return currentRoom.hasWeapon();
    }

    boolean shouldFight(Room currentRoom) {
        return currentRoom.hasLivingCreatures();
    }

}
