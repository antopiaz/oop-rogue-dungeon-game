package dungeon;

import java.util.Scanner;

public class GameRunner {
    
    public static void gameRuner(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            GameFacade game = GameFacade.initializeGame(scanner);
            game.play();
        }
    }
}