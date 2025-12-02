//package polymorphia.stepdefs;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import io.cucumber.java.en.And;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import polymorphia.EventType;
//import polymorphia.characters.Character;
//import polymorphia.characters.CharacterFactory;
//import polymorphia.maze.Room;
//
//public class CharacterStepDefs {
//
//    World world;
//    CharacterFactory characterFactory = new CharacterFactory();
//
//    public CharacterStepDefs(World world) {
//        this.world = world;
//    }
//
//    @Given("an adventurer {string} with health {double}")
//    public void anAdventurerWithHealth(String name, double initialHealth) {
//        world.characters.put(name, characterFactory.createKnight(name, initialHealth));
//    }
//
//    @And("a creature {string} with health {double}")
//    public void aCreatureWithHealth(String name, double initialHealth) {
//        world.characters.put(name, characterFactory.createCreature(name, initialHealth));
//    }
//
//    @When("this fight occurs:")
//    public void thisFightOccurs(List<Map<String, String>> fightData) {
////        List<Character> fighters = new ArrayList<>();
////        for (int i = 0; i <= 1; i++) {
////            Character fighter = world.characters.get(fightData.get(i).get("foe"));
////            int rollValue = Integer.parseInt(fightData.get(i).get("roll"));
////            fighter.setDie(new FixedDie(rollValue));
////            fighters.add(fighter);
////        }
////        fighters.getFirst().fight(fighters.getLast());
//
//    }
//
//    @Then("{string} has health {double}")
//    public void hasHealth(String name, double expectedHealth) {
//        Character character = world.characters.get(name);
//        System.out.println(character.getName() + " has health " + character.getHealth());
//        System.out.println("Expected health: " + expectedHealth);
//        assertEquals(expectedHealth, character.getHealth());
//    }
//
//    @Then("{word} has died")
//    public void characterHasDied(String characterName) {
//        Character character = world.characters.get(characterName);
//        assertFalse(character.isAlive());
//
//    }
//
//    public Character createCharacter(String characterType, String characterName) {
//        Character character = null;
//        switch (characterType) {
//            case "knight":
//                character = characterFactory.createKnight(characterName);
//                break;
//            case "glutton":
//                character = characterFactory.createGlutton(characterName);
//                break;
//            case "coward":
//                character = characterFactory.createCoward(characterName);
//                break;
//            case "creature":
//                character = characterFactory.createCreature(characterName);
//                break;
//            case "demon":
//                character = characterFactory.createDemon(characterName);
//                break;
//            default:
//                System.out.println("Error -- shouldn't get here");
//        }
//        world.characters.put(characterName, character);
//        assert character != null;
//        world.initialHealth.put(characterName, character.getHealth());
//        return character;
//    }
//
//
//    @And("^a (knight|glutton|coward|creature|demon) named (\\w+)$")
//    public void aCharacter(String characterType, String characterName) {
//        createCharacter(characterType, characterName);
//    }
//
//    @And("{int} deaths have occurred")
//    public void deathsHaveOccurred(int numberOfDeathEvents) {
//        assert numberOfDeathEvents <= world.observer.numberOfEventsReceived(EventType.Death);
//    }
//
//    @Then("{word} picked up the {word}")
//    public void characterPickedUpTheArmor(String characterName, String artifactType) {
//        String artifactTypeString = artifactType.toLowerCase();
//        Optional<Character> optChar = world.getMaze().getLivingCharacters().stream()
//            .filter(c -> c.getName().contains(artifactTypeString))
//            .findFirst();
//        Character character = optChar.get();
//        Room currentRoom = character.getCurrentLocation();
//        Character characterWithArtifact = currentRoom.getLivingCharacters().stream()
//                .filter(c -> c.getName().contains(characterName))
//                .findFirst()
//                .get();
//        world.characters.replace(characterName, characterWithArtifact); // Update the world's character map with the new CharacterDecorator instance with the artifact
//        assertTrue(characterWithArtifact.getName().contains(artifactTypeString));
//    }
//
//    @And("{word} did not lose some health")
//    public void characterDidNotLoseSomeHealth(String characterName) {
//        Character character = world.getCharacter(characterName);
//        assertEquals(world.getInitialHealth(characterName), character.getHealth());
//    }
//
//    @And("{word} lost some health")
//    public void characterLostSomeHealth(String characterName) {
//        Character character = world.getCharacter(characterName);
//        assertTrue(character.getHealth() < world.getInitialHealth(characterName));
//    }
//
//}
