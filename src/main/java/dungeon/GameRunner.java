package polymorphia;

import java.util.Scanner;


public class GameRunner {
    
    public static void gameRuner(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
//        Maze maze = ; // TODO: Create maze with setup
//
//        Polymorphia game = new Polymorphia(maze, scanner);
//
//        displayWelcome();
//
//        game.play();
        
        scanner.close();
    }
    
    private static void setUpGame() {
        // TODO: Game setup
    }
    
    private static void displayWelcome() {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                    Java Guys Dungeon                      ║");
        System.out.println("║              A Dungeon Crawling Adventure                 ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Commands:");
        System.out.println("  move <direction>  - Move in a direction (east/north/south/west)");
        System.out.println("  fight <enemy>     - Fight an enemy in the room");
        System.out.println("  eat <food>        - Consume food to restore health");
        System.out.println("  wear <armor>      - Equip armor for protection");
        System.out.println();
        System.out.println("Press Enter to begin your adventure...");
        System.out.println();
    }
}