package polymorphia.characters;
import polymorphia.strategy.DoNothingStrategy;
import polymorphia.strategy.PlayStrategy;

import java.util.Scanner;


public class Player extends Character {

    public static final double DEFAULT_HEALTH = 10.0;
    private final Scanner scanner;


    public Player(String name, Scanner scanner) {
        super(name, DEFAULT_HEALTH, new DoNothingStrategy());
        this.scanner = scanner;
    }

    public String getAction(){
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Hello, " + name + "!");
        return name;
    }

    @Override
    public void doAction() {
        String action = getAction();

        if (action.equals("fight")) {
            fight(getCurrentLocation().getCreature());
            System.out.println("You're fight!");
        }
        if (action.equals("move")){
            move(getCurrentLocation().getRandomNeighbor());
            System.out.println("You're move!");
        }
        if (action.equals("eat")) {
            eat(getCurrentLocation().getFood());
            System.out.println("You ate!");
        }
        strategy.doAction(this, getCurrentLocation());
    }


}
