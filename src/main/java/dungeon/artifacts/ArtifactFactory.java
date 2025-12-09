package dungeon.artifacts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class ArtifactFactory {
    private static final Random random = new Random();

    private static final double DEFAULT_VALUE = 0.0;
    private static final double MINIMUM_VALUE = 1.0;
    private static final double MAXIMUM_VALUE = 2.0;

    private static final String[] FOOD_NAMES = new String[]{
            "cake", "apple", "orange", "steak", "banana", "burger", "pizza", "calzone",
            "bacon", "donut", };

    public static final String[] ARMOR_NAMES = new String[]{
            "leather", "chainmail", "plate", "iron", "steel", "mithril", "gold", "adamantium"};

    public static final String[] WEAPON_NAMES = new String[]{
           "dagger", "halberd", "mace", "saber", "staff", "katana", "bow", "greatsword", "sword", "axe", "spear"};

    public static final String[] TREASURE_NAMES = new String[]{
            "gold coins", "treasure chest", "dragon hoard", "gems", "diamonds", "magic items"};
    private static final Map<ArtifactType, String[]> NAMES = new HashMap<>();

    static {
        NAMES.put(ArtifactType.Food, FOOD_NAMES);
        NAMES.put(ArtifactType.Armor, ARMOR_NAMES);
        NAMES.put(ArtifactType.Weapon, WEAPON_NAMES);
        NAMES.put(ArtifactType.Treasure, TREASURE_NAMES);
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

    public List<IArtifact> createTreasures(int numberOfItems) {
        return createArtifacts(ArtifactType.Treasure, numberOfItems);
    }

    private List<IArtifact> createArtifacts(ArtifactType type, int numberOfItems) {
        return IntStream.range(0, numberOfItems)
                .mapToObj(unused -> create(type))
                .toList();
    }

    public IArtifact create(ArtifactType type) {
        return create(type, getRandomName(type), getRandomValue());
    }

    public IArtifact create(ArtifactType type, String name, double value) {
        return switch (type) {
            case Food -> new Food(name, value);
            case Armor -> new Armor(name, value);
            case Weapon -> new Weapon(name, value);
            case Treasure ->  new Treasure(name, value);
        };
    }

    public IArtifact createFood(String name) {
        return create(ArtifactType.Food, name, getRandomValue());
    }

    public IArtifact createArmor(String name, double strength) {
        return create(ArtifactType.Armor, name, strength);
    }

    public IArtifact createWeapon(String name, double strength) {
        return create(ArtifactType.Weapon, name, strength);
    }

    public IArtifact createTreasure(String name) {
        return create(ArtifactType.Treasure, name, DEFAULT_VALUE);
    }
}
