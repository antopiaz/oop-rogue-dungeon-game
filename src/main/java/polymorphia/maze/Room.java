package polymorphia.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import polymorphia.artifacts.ArtifactType;
import polymorphia.artifacts.IArtifact;
import polymorphia.characters.Character;

public class Room {
    static private final Random rand = new Random();

    private final String name;
    private final List<Room> neighbors = new ArrayList<>();
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

    void addNeighbor(Room neighbor) {
        if (this != neighbor) {
            this.neighbors.add(neighbor);
        }
        if (!neighbor.neighbors.contains(this)) {
            neighbor.neighbors.add(this);
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

    public Room getRandomNeighbor() {
        if (neighbors.isEmpty()) {
            return null;
        }
        return neighbors.stream().toList().get(rand.nextInt(neighbors.size()));
    }

    public Room getNeighbor(int cardinality) {
        if (neighbors.isEmpty()) {
            return null;
        }
        return neighbors.stream().toList().get(cardinality);
    }

    public void enter(Character character) {
        add(character);
    }

    public List<Character> getLivingCharacters() {
        return characters.stream()
                .filter(Character::isAlive)
                .toList();
    }

    public boolean hasNeighbor(Room neighbor) {
        return neighbors.contains(neighbor);
    }

    public int numberOfNeighbors() {
        return neighbors.size();
    }

    public boolean hasNeighbors() {
        return !neighbors.isEmpty();
    }

    public boolean contains(Character character) {
        return characters.contains(character);
    }

    public boolean contains(IArtifact artifact) {
        return artifacts.contains(artifact);
    }

    public Character getCreature() {
        List<Character> creatures = getLivingCreatures();
        return (creatures.get(rand.nextInt(creatures.size())));
    }

    public Character getCreature(String name) {
        List<Character> creatures = getLivingCreatures();
        return (creatures.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null));
    }

    public List<IArtifact> getFoodItems() {
        return artifacts.stream()
                .filter(artifact -> artifact.isType(ArtifactType.Food))
                .toList();
    }

    public void add(IArtifact artifact) {
        artifacts.add(artifact);
    }

    public boolean hasArmor() {
        return artifacts.stream()
                .anyMatch(artifact -> artifact.isType(ArtifactType.Armor));
    }

    public Optional<IArtifact> getArmor() {
        Optional<IArtifact> armor = artifacts.stream()
                .filter(artifact -> artifact.isType(ArtifactType.Armor))
                .findAny();
        armor.ifPresent(artifacts::remove);
        return armor;
    }

    public boolean hasWeapon() {
        return artifacts.stream()
                .anyMatch(artifact -> artifact.isType(ArtifactType.Weapon));
    }

    public Optional<IArtifact> getWeapon() {
        Optional<IArtifact> weapon = artifacts.stream()
                .filter(artifact -> artifact.isType(ArtifactType.Weapon))
                .findAny();
        weapon.ifPresent(artifacts::remove);
        return weapon;
    }

    public boolean hasFood() {
        return artifacts.stream()
                .anyMatch(artifact -> artifact.isType(ArtifactType.Food));
    }

    public IArtifact getFood() {
        IArtifact food = artifacts.stream()
                .filter(artifact -> artifact.isType(ArtifactType.Food))
                .findAny().orElse(null);
        artifacts.remove(food);
        return food;
    }

    public IArtifact getFood(String name) {
        IArtifact food = artifacts.stream()
                .filter(f -> f.getName().equalsIgnoreCase(name))
                .findAny().orElse(null);
        artifacts.remove(food);
        return food;
    }

    public void alterCharacter(Character removedCharacter, Character newCharacter) {
        characters.remove(removedCharacter);
        characters.add(newCharacter);
        newCharacter.enterRoom(this);
    }
}
