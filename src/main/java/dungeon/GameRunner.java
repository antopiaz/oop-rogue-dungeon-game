package dungeon;

import java.util.Scanner;

public class GameRunner {
    
    public static void main(String[] args) {
        runGame();
    }

    private static void runGame() {
        try (Scanner scanner = new Scanner(System.in)) {
            GameFacade game = GameFacade.initializeGame(scanner);
            game.play();
        }
    }
}