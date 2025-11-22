package polymorphia.strategy;


import java.util.Optional;

import polymorphia.characters.Character;
import polymorphia.maze.Room;

public class FighterStrategy extends PlayStrategy {
    @Override
    public void doAction(Character myself, Room currentRoom) {
        if (shouldFight(currentRoom)) {
            fightCreature(myself, currentRoom);
            return;
        }
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

    private static void fightCreature(Character myself, Room currentRoom) {
        Optional<Character> creature = currentRoom.getCreature();
        if (creature.isPresent()) {
            myself.fight(creature.get());
        }
    }

    boolean shouldFight(Room currentRoom) {
        return currentRoom.hasLivingCreatures();
    }

}
