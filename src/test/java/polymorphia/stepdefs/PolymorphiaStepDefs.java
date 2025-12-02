//package polymorphia.stepdefs;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import io.cucumber.java.en.And;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import polymorphia.EventType;
//import polymorphia.Polymorphia;
//import polymorphia.observers.EventBus;
//import polymorphia.observers.EventObserver;
//import polymorphia.observers.TestObserver;
//
//public class PolymorphiaStepDefs {
//    World world;
//    List<TestObserver> observers = new ArrayList<TestObserver>();
//
//    public PolymorphiaStepDefs(World world) {
//        this.world = world;
//    }
//
//    @When("the game is played in the created maze")
//    public void theGameIsPlayedInTheCreatedMaze() {
//        Polymorphia game = world.getGame();
//        game.play();
//    }
//
//    @Then("all the adventurers or all of the creatures have died")
//    public void allTheAdventurersOrAllOfTheCreaturesHaveDied() {
//        Polymorphia game = world.getGame();
//        assertTrue(!game.hasLivingAdventurers() || !game.hasLivingCreatures());
//    }
//
//    @Then("the game is over")
//    public void theGameIsOver() {
//        Polymorphia game = world.getGame();
//        assertTrue(game.isOver());
//    }
//
//    @And("a fight has occurred")
//    public void aFightHasOccurred() {
//        assertTrue(world.observer.eventsReceived(EventType.FightOccurred));
//    }
//
//
//    @Then("observers are detached")
//    public void observersAreDetached() {
//        for (TestObserver observer : observers) {
//            world.getGame().detach(observer);
//        }
//        EventBus.INSTANCE.detachAll();
//    }
//
//    @When("the game plays a turn")
//    public void theGamePlaysATurn() {
//        Polymorphia game = world.getGame();
//        game.playTurn();
//    }
//}
