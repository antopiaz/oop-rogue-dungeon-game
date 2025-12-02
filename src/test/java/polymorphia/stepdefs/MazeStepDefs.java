//package polymorphia.stepdefs;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import io.cucumber.java.en.And;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import polymorphia.artifacts.ArtifactFactory;
//import polymorphia.artifacts.ArtifactType;
//import polymorphia.artifacts.IArtifact;
//import polymorphia.characters.Character;
//import polymorphia.characters.CharacterFactory;
//import polymorphia.maze.Room;
//
//
//public class MazeStepDefs {
//    World world;
//    private static final int DEFAULT_NUMBER_OF_ROOMS = 5;
//    private static final int DEFAULT_MIN_CONNECTIONS = 2;
//    private static final String BIDIRECTIONAL = "2-way";
//    private static final CharacterFactory characterFactory = new CharacterFactory();
//    private static final ArtifactFactory artifactFactory = new ArtifactFactory();
//    CharacterStepDefs characterStepDefs;
//
//    public MazeStepDefs(World world, CharacterStepDefs characterStepDefs) {
//        this.world = world;
//        this.characterStepDefs = characterStepDefs;
//    }
//
//    @Given("a maze with the following attributes:")
//    public void aMazeWithTheFollowingAttributes(Map<String, Integer> mazeArgs) {
//        int numRooms = DEFAULT_NUMBER_OF_ROOMS;
//        int numConnections = DEFAULT_MIN_CONNECTIONS;
//        for (Map.Entry<String, Integer> entry : mazeArgs.entrySet()) {
//            String key = entry.getKey();
//            int value = entry.getValue();
//            switch (key) {
//                case "number of rooms":
//                    numRooms = value;
//                    break;
//                case "number of connections":
//                    numConnections = value;
//                    break;
//            }
//        }
//
//        world.mazeBuilder.createDungeon(2,2);
//        addToRooms(mazeArgs);
//    }
//
//    private void addToRooms(Map<String, Integer> mazeArgs) {
//        for (Map.Entry<String, Integer> entry : mazeArgs.entrySet()) {
//            String key = entry.getKey();
//            int value = entry.getValue();
//            switch (key) {
//                case "number of food items":
//                    world.mazeBuilder.addArtifacts(artifactFactory.createFoodItems(value));
//                    break;
//                case "number of knights":
//                    world.mazeBuilder.addCharacters(characterFactory.createKnights(value));
//                    break;
//                case "number of gluttons":
//                    world.mazeBuilder.addCharacters(characterFactory.createKnights(value));
//                    break;
//                case "number of cowards":
//                    world.mazeBuilder.addCharacters(characterFactory.createKnights(value));
//                    break;
//                case "number of creatures":
//                    world.mazeBuilder.addCharacters(characterFactory.createCreatures(value));
//                    break;
//                case "number of demons":
//                    world.mazeBuilder.addCharacters(characterFactory.createDemons(value));
//                    break;
//                case "number of armored suits":
//                    world.mazeBuilder.addArtifacts(artifactFactory.createArmoredSuits(value));
//                    break;
//                case "number of weapons":
//                    world.mazeBuilder.addArtifacts(artifactFactory.createWeapons(value));
//                    break;
//            }
//        }
//    }
//
//    @And("^a (knight|glutton|coward|creature|demon) named (.+) in the (\\w+) room$")
//    public Character createCharacterAndPutInRoom(String characterType, String characterName, String roomName) {
//        Character character = characterStepDefs.createCharacter(characterType, characterName);
//        world.mazeBuilder.addToRoom(roomName, character);
//        return character;
//    }
//
//    @When("{word} plays a turn")
//    public void characterPlaysATurn(String characterName) {
//        // Make sure we have started the game first...
//        world.createGame();
//        Character character = world.getCharacter(characterName);
//        character.doAction();
//    }
//
//    @Then("{word} room contains {word}")
//    public void roomContainsCharacter(String roomName, String characterName) {
//        Room room = world.getMaze().getRoom(roomName);
//        Character character = world.getCharacter(characterName);
//        assertTrue(room.contains(character));
//    }
//
//    @Given("a fully-connected maze with the following rooms:")
//    public void aFullyConnectedMazeWithTheFollowingRooms(List<String> roomNames) {
//        world.mazeBuilder.createDungeon(2,2);
//    }
//
//    @And("the {word} room contains the following food items:")
//    public void roomContainsTheFollowingFoodItems(String roomName, List<Map<String, String>> foodDescriptions) {
//        Room room = world.getMaze().getRoom(roomName);
//        for (Map<String, String> foodDescription : foodDescriptions) {
//            IArtifact food = artifactFactory.createFood(
//                    foodDescription.get("Name"),
//                    Double.parseDouble(foodDescription.get("Value")));
//            room.add(food);
//            world.putArtifact(food.getName(), food);
//        }
//    }
//
//    @And("the {word} room contains the following artifacts:")
//    public void roomContainsTheFollowingArtifacts(String roomName, List<Map<String, String>> artifactDescriptions) {
//        Room room = world.getMaze().getRoom(roomName);
//        for (Map<String, String> artifactDescription : artifactDescriptions) {
//            IArtifact artifact = artifactFactory.create(
//                    ArtifactType.valueOf(artifactDescription.get("Type")),
//                    artifactDescription.get("Name"),
//                    Double.parseDouble(artifactDescription.get("Health Value")),
//                    Double.parseDouble(artifactDescription.get("Value")),
//                    Double.parseDouble(artifactDescription.get("Moving Cost")));
//            room.add(artifact);
//            world.artifacts.put(artifact.getName(), artifact);
//        }
//    }
//
//
//    @Then("^the (.+) room no longer contains (.+)$")
//    public void roomNoLongerContainsFoodItem(String roomName, String foodName) {
//        Room room = world.getMaze().getRoom(roomName);
//        assertFalse(room.contains(world.getArtifact(foodName)));
//    }
//
//    @And("^(.+) is now(| not) in the (.+) room$")
//    public void characterIsInRoom(String characterName, String not, String roomName) {
//        Character character = world.getCharacter(characterName);
//        if (not.equals(" not")) {
//            assertNotEquals(roomName, character.getCurrentLocation().getName());
//        } else {
//            assertEquals(roomName, character.getCurrentLocation().getName());
//        }
//    }
//
//    @And("{word} has gained {double} in health")
//    public void characterHasGainedInHealth(String characterName, Double lostHealth) {
//        Character character = world.getCharacter(characterName);
//        assertEquals(world.getInitialHealth(characterName) + lostHealth, character.getHealth());
//    }
//
//    @And("the {word} room has {int} food items")
//    public void startingRoomHasNoFoodItems(String roomName, int expectedFoodItems) {
//        Room room = world.getMaze().getRoom(roomName);
//        assertEquals(expectedFoodItems, room.getFoodItems().size());
//    }
//
//    @Given("^a (unidirectional|bidirectional), (randomly|sequentially)-distributed maze with the following attributes:$")
//    public void connectedDistributedMazeWithTheFollowingAttributes(String roomConnectionType, String distributionType, Map<String, Integer> mazeArgs) {
//        if (Objects.equals(distributionType, "randomly")) {
//            world.mazeBuilder.distributeRandomly();
//        } else {
//            world.mazeBuilder.distributeSequentially();
//        }
//        aMazeWithTheFollowingAttributes(mazeArgs);
//    }
//
//    @Given("a maze with the following rooms:")
//    public void aMazeWithTheFollowingRooms(List<String> roomNames) {
//        world.mazeBuilder.createRooms(roomNames);
//    }
//
//    @And("each room is connected to {int} other rooms via {word} connections")
//    public void eachRoomIsConnectedToOtherRoomsViaConnections(int numberOfConnections, String connectionType) {
//        world.mazeBuilder.connectRooms(numberOfConnections);
//    }
//
//    @And("{word} is in the {word} room")
//    public void characterIsInTheRoom(String characterName, String roomName) {
//        world.mazeBuilder.addToRoom(roomName, world.getCharacter(characterName));
//    }
//
//    @And("there is no {word} in {word} room")
//    public void thereIsNoArmorInStartingRoom(String artifactTypeString, String roomName) {
//        String artifactTypeStringCorrectCase = artifactTypeString.substring(0,1).toUpperCase() + artifactTypeString.substring(1);
//        ArtifactType artifactType = ArtifactType.valueOf(artifactTypeStringCorrectCase);
//        Room room = world.mazeBuilder.getRoom(roomName);
//        Optional<IArtifact> artifact = room.getArtifact(artifactType);
//        assertFalse(artifact.isPresent());
//    }
//
//}
