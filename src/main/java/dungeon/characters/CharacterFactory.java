package dungeon.characters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import dungeon.artifacts.IArtifact;
import dungeon.strategy.AttackStrategy;
import dungeon.strategy.DefensiveStrategy;
import dungeon.strategy.DefaultStrategy;


public class CharacterFactory {
    private static final Random random = new Random();

    public static final String[] CREATURE_NAMES = new String[]{"Ogre", "Orc", "Goblin", "Troll", "Zombie", "Skeleton", "Rat", "Ghoul", "Hobgoblin", "Shadow", "Slime"};
    public static final String[] DEFENDER_NAMES = new String[]{"Dwarf", "Dullahan", "Corrupted Paladin", "Hammerer", "Giant", "Golem", "Nabisco"};


    public static final Map<String, String[]> NAMES = new HashMap<>();

    static {
        NAMES.put("Creature", CREATURE_NAMES);
        NAMES.put("Defender", DEFENDER_NAMES);
    }

    static final Double DEFAULT_HEALTH_LOWER_BOUND = 5.0;
    static final Double DEFAULT_HEALTH_UPPER_BOUND = 30.0;


    public Character createCreature(String name, Double initialHealth) {
        return new Creature(name, initialHealth, new DefaultStrategy());
    }

    public Character createAttacker(String name, Double initialHealth) {
        return new Creature(name, initialHealth, new AttackStrategy());
    }

    public Character createDefender(String name, Double initialHealth) {
        return new Creature(name, initialHealth, new DefensiveStrategy());
    }

    public static Character createArmoredCharacter(Character decoratedCharacter, IArtifact armor) {
        return new ArmoredCharacter(decoratedCharacter, armor);
    }

    public static Character createWeaponizedCharacter(Character decoratedCharacter, IArtifact weapon) {
        return new WeaponizedCharacter(decoratedCharacter, weapon);
    }

    public List<Character> createCreatures(int numberOfCharacters) {
        return IntStream.range(0, numberOfCharacters)
                .mapToObj(unused -> createCreature())
                .toList();
    }

    public List<Character> createAttackers(int numAttackers) {
        return IntStream.range(0, numAttackers)
                .mapToObj(unused -> createAttacker())
                .toList();
    }

    public List<Character> createDefenders(int numAttackers) {
        return IntStream.range(0, numAttackers)
                .mapToObj(unused -> createDefender())
                .toList();
    }


    private Character createCreature() {
        return createCreature(getRandomName("Creature"), random.nextDouble(DEFAULT_HEALTH_LOWER_BOUND, DEFAULT_HEALTH_UPPER_BOUND));
    }
    private Character createAttacker() {
        return createAttacker(getRandomName("Creature"),random.nextDouble(DEFAULT_HEALTH_LOWER_BOUND, DEFAULT_HEALTH_UPPER_BOUND));
    }
    private Character createDefender() {
        return createDefender(getRandomName("Defender"),random.nextDouble(DEFAULT_HEALTH_LOWER_BOUND, DEFAULT_HEALTH_UPPER_BOUND));
    }


    private String getRandomName(String characterType) {
        String[] names = NAMES.get(characterType);
        return names[random.nextInt(names.length)];
    }

}
