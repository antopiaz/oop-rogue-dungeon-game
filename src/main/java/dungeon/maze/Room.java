package dungeon.maze;

import java.util.*;

import dungeon.artifacts.IArtifact;
import dungeon.characters.Character;

public class Room {
    static private final Random rand = new Random();

    private final String name;
    private final Map<DirectionType, Room> neighbors = new HashMap<>();
    private final List<Character> characters = new ArrayList<>();
    private final List<IArtifact> artifacts = new ArrayList<>();

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Character> getLivingCreatures() {
        return getLivingCharacters().stream()
                .filter(Character::isCreature)
                .toList();
    }

    public List<String> getContents() {
        List<String> characterStrings = new ArrayList<>(getLivingCharacters().stream()
                .map(Object::toString)
                .toList());

        for (IArtifact artifact : artifacts) {
            characterStrings.add(artifact.toString());
        }

        return characterStrings;
    }

    public void addNeighbor(DirectionType direction, Room neighbor) {
        if(this != neighbor){
            neighbors.put(direction, neighbor);
            neighbor.neighbors.put(direction.opposite(), this);
        }
    }

    @Override
    public String toString() {
        String representation = "\t" + name + ":\n\t\t";
        representation += String.join("\n\t\t", getContents());
        representation += "\n";
        return representation;
    }

    void add(Character character) {
        characters.add(character);
        character.enterRoom(this);
    }

    public Boolean hasLivingCreatures() {
        return getLivingCharacters().stream().anyMatch(Character::isCreature);
    }

    public Boolean hasLivingAdventurers() {
        return getLivingCharacters().stream().anyMatch(Character::isAdventurer);
    }

    public void remove(Character character) {
        characters.remove(character);
    }

    public Room getNeighbor(DirectionType direction) {
        if (neighbors.containsKey(direction)) {
            return neighbors.get(direction);
        }
        return null;
    }

    public void enter(Character character) {
        add(character);
    }

    public List<Character> getLivingCharacters() {
        return characters.stream()
                .filter(Character::isAlive)
                .toList();
    }

    public boolean hasNeighbor(DirectionType direction) {
        return neighbors.containsKey(direction);
    }

    public boolean hasArtifacts() {
        return !artifacts.isEmpty();
    }

    public List<IArtifact> getArtifacts() {
        return artifacts;
    }

    public Character getCreature(String name) {
        List<Character> creatures = getLivingCreatures();
        return (creatures.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null));
    }

    public void add(IArtifact artifact) {
        artifacts.add(artifact);
    }

    public IArtifact getArmor(String name) {
        IArtifact armor = artifacts.stream()
                .filter(f -> f.getName().equalsIgnoreCase(name))
                .findAny().orElse(null);
        artifacts.remove(armor);
        return armor;
    }

    public IArtifact getWeapon(String name) {
        IArtifact weapon = artifacts.stream()
                .filter(w -> w.getName().equalsIgnoreCase(name))
                .findAny().orElse(null);
        artifacts.remove(weapon);
        return weapon;
    }

    public IArtifact getFood(String name) {
        IArtifact food = artifacts.stream()
                .filter(f -> f.getName().equalsIgnoreCase(name))
                .findAny().orElse(null);
        artifacts.remove(food);
        return food;
    }

    public IArtifact getTreasure(String name) {
        IArtifact treasure = artifacts.stream()
                .filter(t -> t.getName().equalsIgnoreCase(name))
                .findAny().orElse(null);
        artifacts.remove(treasure);
        return treasure;
    }

    public void alterCharacter(Character removedCharacter, Character newCharacter) {
        characters.remove(removedCharacter);
        characters.add(newCharacter);
        newCharacter.enterRoom(removedCharacter.getCurrentLocation());
    }

}
