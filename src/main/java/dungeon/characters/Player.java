package dungeon.characters;
import java.util.Scanner;

import static dungeon.GameFacade.printInstructions;
import dungeon.artifacts.IArtifact;
import dungeon.maze.DirectionType;
import dungeon.maze.Room;
import dungeon.strategy.PlayStrategy;


public class Player extends Character {

    public static final double DEFAULT_HEALTH = 15.0;
    private final Scanner scanner;


    public Player(String name, Scanner scanner, PlayStrategy strategy) {
        super(name, DEFAULT_HEALTH, strategy);
        this.scanner = scanner;
    }

    public String[] getAction(){
        logger.info("> ");
        String line = scanner.nextLine().trim();
        return line.split("\\s+", 2);
    }


    @Override
    public void doAction() {
        String[] action = getAction();
        String command = action[0];
        String argument = action.length > 1 ? action[1] : null;

        Character selfInRoom = getSelfInRoom(getCurrentLocation());

        switch (command) {
            case "fight":
                handleFight(argument, selfInRoom);
                break;
            case "move":
                handleMove(argument);
                break;
            case "eat":
                handleEat(argument);
                break;
            case "wear":
                handleWear(argument);
                break;
            case "equip":
                handleEquip(argument);
                break;
            case "obtain":
                handleObtain(argument);
                break;
            case "help":
                printInstructions();
                break;
            default:
                logger.info("Invalid command");
        }
    }

    private void handleFight(String argument, Character selfInRoom) {
        Character creature = getCurrentLocation().getCreature(argument);
        if (creature != null) {
            FightActions fightAction = getFightAction();
            FightActions foeFightAction = creature.getFightAction();
            fight(selfInRoom, creature, fightAction, foeFightAction);
        }
        logger.info("Fought " + argument);
    }

    private void handleMove(String argument) {
        DirectionType cardinality = null;
        switch(argument){
            case "east":
                cardinality = DirectionType.EAST;
                break;
            case "north":
                cardinality = DirectionType.NORTH;
                break;
            case "south":
                cardinality = DirectionType.SOUTH;
                break;
            case "west":
                cardinality = DirectionType.WEST;
                break;
        }
        if(cardinality!=null){
            if(getCurrentLocation().hasNeighbor(cardinality)){
                move(cardinality);
                logger.info("Moved " + argument);
            }
            else{
                logger.info("No neighbor found to the " + argument);
            }
        }

    }

    private void handleEat(String argument) {
        IArtifact food = getCurrentLocation().getFood(argument);
        if (food != null) {
            eat(food);
        }
    }

    private void handleWear(String argument) {
        IArtifact armor = getCurrentLocation().getArmor(argument);
        if (armor != null) {
            wear(armor);
        }
        logger.info("Wearing "  + argument);
    }

    private void handleEquip(String argument) {
        IArtifact weapon = getCurrentLocation().getWeapon(argument);
        if(weapon != null){
            equip(weapon);
        }
        logger.info("Equipped "  + argument);
    }

    private void handleObtain(String argument) {
        if(getCurrentLocation().hasLivingCreatures()){
            logger.info("A monster is currently guarding the treasure");
        }
        else{
            IArtifact treasure = getCurrentLocation().getTreasure(argument);
            if(treasure != null){
                obtain(treasure);
                logger.info("Obtained " + argument);
            }
        }
    }

    private Character getSelfInRoom(Room currentLocation){
        Character selfInRoom = currentLocation.getLivingCharacters()
                .stream()
                .filter(c -> c.getName().contains(getName()))
                .findFirst()
                .orElse(this);
        return selfInRoom;
    }



}
