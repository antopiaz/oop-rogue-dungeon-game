package polymorphia.characters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import polymorphia.artifacts.IArtifact;
import polymorphia.strategy.CowardStrategy;
import polymorphia.strategy.DemonStrategy;
import polymorphia.strategy.DoNothingStrategy;
import polymorphia.strategy.FighterStrategy;
import polymorphia.strategy.GluttonStrategy;


public class CharacterFactory {
    private static final Random random = new Random();

    public static final String[] KNIGHT_NAMES = new String[]{"Sir Lancelot", "Lady Brienne", "King Arthur", "Sir Jamey", "Aragorn", "Isildur"};
    public static final String[] COWARD_NAMES = new String[]{"Sir Robin", "Sir Scaredy Cat", "Lady Faints-a-lot", "Lady Runaway", "Sir Chicken", "Lady Hides-a-lot"};
    public static final String[] GLUTTON_NAMES = new String[]{"Sir Eats-a-lot", "Sir Gobbles", "Lady Munches", "Lady Snacks", "Sir Nibbles", "Lady Noshes"};
    public static final String[] CREATURE_NAMES = new String[]{"Dragon", "Ogre", "Orc", "Shelob", "Troll", "Evil Wizard"};
    public static final String[] DEMON_NAMES = new String[]{"Satan", "Beelzebub", "Devil", "Incubus", "Lucifer", "Succubus"};

    public static final Map<String, String[]> NAMES = new HashMap<>();

    static {
        NAMES.put("Knight", KNIGHT_NAMES);
        NAMES.put("Coward", COWARD_NAMES);
        NAMES.put("Glutton", GLUTTON_NAMES);
        NAMES.put("Creature", CREATURE_NAMES);
        NAMES.put("Demon", DEMON_NAMES);
    }

    public static final Double DEFAULT_FIGHTER_INITIAL_HEALTH = 10.0;
    static final Double DEFAULT_GLUTTON_INITIAL_HEALTH = 4.0;
    static final Double DEFAULT_COWARD_INITIAL_HEALTH = 5.0;
    static final Double DEFAULT_CREATURE_INITIAL_HEALTH = 5.0;
    static final Double DEFAULT_DEMON_INITIAL_HEALTH = 15.0;


    public Character createKnight(String name) {
        return createKnight(name, DEFAULT_FIGHTER_INITIAL_HEALTH);
    }

    public Character createKnight(String name, Double initialHealth) {
        return new Adventurer(name, initialHealth, new FighterStrategy());
    }

    public Character createGlutton(String name) {
        return createGlutton(name, DEFAULT_GLUTTON_INITIAL_HEALTH);
    }

    public Character createGlutton(String name, double initialHealth) {
        return new Adventurer(name, initialHealth, new GluttonStrategy());
    }

    public Character createCoward(String name) {
        return new Adventurer(name, DEFAULT_COWARD_INITIAL_HEALTH, new CowardStrategy());
    }

    public Character createCreature(String name) {
        return createCreature(name, DEFAULT_CREATURE_INITIAL_HEALTH);
    }

    public Character createCreature(String name, Double initialHealth) {
        return new Creature(name, initialHealth, new DoNothingStrategy());
    }

    public Character createDemon(String name) {
        return new Creature(name, DEFAULT_DEMON_INITIAL_HEALTH, new DemonStrategy());
    }

    public static Character createArmoredCharacter(Character decoratedCharacter, IArtifact armor) {
        return new ArmoredCharacter(decoratedCharacter, armor);
    }

    public static Character createWeaponizedCharacter(Character decoratedCharacter, IArtifact weapon) {
        return new WeaponizedCharacter(decoratedCharacter, weapon);
    }

    public List<Character> createKnights(int numberOfCharacters) {
        return IntStream.range(0, numberOfCharacters)
                .mapToObj(unused -> createKnight())
                .toList();
    }

    public List<Character> createCowards(int numberOfCharacters) {
        return IntStream.range(0, numberOfCharacters)
                .mapToObj(unused -> createCoward())
                .toList();
    }

    public List<Character> createGluttons(int numberOfCharacters) {
        return IntStream.range(0, numberOfCharacters)
                .mapToObj(unused -> createGlutton())
                .toList();
    }

    public List<Character> createCreatures(int numberOfCharacters) {
        return IntStream.range(0, numberOfCharacters)
                .mapToObj(unused -> createCreature())
                .toList();
    }

    public List<Character> createDemons(int numberOfCharacters) {
        return IntStream.range(0, numberOfCharacters)
                .mapToObj(unused -> createDemon())
                .toList();
    }

    public Character createKnight() {
        return createKnight(getRandomName("Knight"));
    }
    private Character createCoward() {
        return createCoward(getRandomName("Coward"));
    }

    private Character createGlutton() {
        return createGlutton(getRandomName("Glutton"));
    }

    private Character createCreature() {
        return createCreature(getRandomName("Creature"));
    }

    private Character createDemon() {
        return createDemon(getRandomName("Demon"));
    }

    private String getRandomName(String characterType) {
        String[] names = NAMES.get(characterType);
        return names[random.nextInt(names.length)];
    }

}
