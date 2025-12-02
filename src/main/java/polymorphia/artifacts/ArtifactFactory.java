package polymorphia.artifacts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class ArtifactFactory {
    private static final Random random = new Random();

    public static double DEFAULT_VALUE = 0.0;
    public static double DEFAULT_STRENGTH = 0.0;
    public static double DEFAULT_MOVING_COST = 0.2;
    private static final double MINIMUM_VALUE = 1.0;
    private static final double MAXIMUM_VALUE = 2.0;

    private static final String[] FOOD_NAMES = new String[]{
            "cupcake", "apple", "banana", "steak", "salad", "fries", "burger", "pizza", "eggs",
            "bacon", "muffin", "donut", "chicken", "pasta", "rice", "sushi", "taco", "burrito",
            "nachos", "chips"};

    public static final String[] ARMOR_NAMES = new String[]{
            "leather", "chainmail", "plate", "iron", "steel", "mithril", "dragon hide", "titanium", "platinum", "gold"};

    public static final String[] WEAPON_NAMES = new String[]{
            "sword", "axe", "spear", "dagger", "hatchet", "halberd", "mace", "saber", "sling", "bomb", "these hands", "banana gun", "AK-47"};

    private static final Map<ArtifactType, String[]> NAMES = new HashMap<>();

    static {
        NAMES.put(ArtifactType.Food, FOOD_NAMES);
        NAMES.put(ArtifactType.Armor, ARMOR_NAMES);
        NAMES.put(ArtifactType.Weapon, WEAPON_NAMES);
    }

    private static double getRandomValue() {
        return random.nextDouble(MINIMUM_VALUE, MAXIMUM_VALUE);
    }

    private static String getRandomName(ArtifactType type) {
        String[] names = NAMES.get(type);
        return names[random.nextInt(names.length)];
    }

    public List<IArtifact> createFoodItems(int numberOfItems) {
        return createArtifacts(ArtifactType.Food, numberOfItems);
    }

    public List<IArtifact> createArmoredSuits(int numberOfItems) {
        return createArtifacts(ArtifactType.Armor, numberOfItems);
    }

    public List<IArtifact> createWeapons(int numberOfItems) {
        return createArtifacts(ArtifactType.Weapon, numberOfItems);
    }

    private List<IArtifact> createArtifacts(ArtifactType type, int numberOfItems) {
        return IntStream.range(0, numberOfItems)
                .mapToObj(unused -> create(type))
                .toList();
    }

    public IArtifact create(ArtifactType type) {
        return create(type, getRandomName(type), getRandomValue(), getRandomValue(), getRandomValue());
    }

    public IArtifact create(ArtifactType type, String name, double healthValue, double strength) {
        return create(type, name, healthValue, strength, DEFAULT_MOVING_COST);
    }

    public IArtifact create(ArtifactType type, String name, double healthValue, double strength, double movingCost) {
        return switch (type) {
            case Food -> new Food(name, healthValue);
            case Armor -> new Armor(name, strength, movingCost);
            case Weapon -> new Weapon(name, strength, movingCost);
        };
    }

    public IArtifact createFood(String name) {
        return create(ArtifactType.Food, name, getRandomValue(), DEFAULT_STRENGTH);
    }

    public IArtifact createFood(String name, double value) {
        return create(ArtifactType.Food, name, value, DEFAULT_STRENGTH);
    }

    public IArtifact createArmor(String name) {
        return create(ArtifactType.Armor, name, DEFAULT_VALUE, DEFAULT_STRENGTH);
    }

    public IArtifact createArmor(String name, double strength) {
        return create(ArtifactType.Armor, name, DEFAULT_VALUE, strength);
    }

    public IArtifact createWeapon(String name) {
        return create(ArtifactType.Weapon, name, DEFAULT_VALUE, DEFAULT_STRENGTH);
    }

    public IArtifact createWeapon(String name, double strength) {
        return create(ArtifactType.Weapon, name, DEFAULT_VALUE, strength);
    }
}
