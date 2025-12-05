package polymorphia.characters;
import polymorphia.artifacts.IArtifact;
import polymorphia.strategy.DoNothingStrategy;
import polymorphia.strategy.HumanStrategy;
import polymorphia.strategy.PlayStrategy;

import java.util.Scanner;


public class Player extends Character {

    public static final double DEFAULT_HEALTH = 10.0;
    private final Scanner scanner;


    public Player(String name, Scanner scanner, PlayStrategy strategy) {
        super(name, DEFAULT_HEALTH, strategy);
        this.scanner = scanner;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public String[] getAction(){
        System.out.print("> ");
        String line = scanner.nextLine().trim();
        return line.split("\\s+", 2);
    }


    @Override
    public void doAction() {
        String[] action = getAction();
        String command = action[0];
        String argument = action.length > 1 ? action[1] : null;

        if (command.equals("fight")) { //TODO pick creature by name
            Character creature = getCurrentLocation().getCreature(argument);

            if (creature != null) {
                int fightAction = getFightAction();
                int foeFightAction = creature.getFightAction();

                fight(creature, fightAction, foeFightAction);
                System.out.println("chose: " + fightAction);

            }

            System.out.println("Fought " + argument);
        }
        if (command.equals("move")){ //TODO add direction
            int cardinality = -1;
            switch(argument){
                case "east":
                    cardinality = 0;
                    break;
                case "north":
                    cardinality = 1;
                    break;
                case "south":
                    cardinality = 2;
                    break;
                case "west":
                    cardinality = 3;
                    break;
            }
            if(cardinality!=-1){
                move(getCurrentLocation().getNeighbor(cardinality));
            }
            System.out.println();
            System.out.println("Moved " + argument);
        }
        if (command.equals("eat")) { //TODO pick food by name
            IArtifact food = getCurrentLocation().getFood(argument);
            if (food != null) {
                eat(food);
            }
            System.out.println("Ate "  + argument);
        }

        if (command.equals("wear")) {
            IArtifact armor = getCurrentLocation().getArmor(argument);
            if (armor != null) {
                wear(armor);

            }
            System.out.println("Equipped "  + argument);
        }
    }


}
