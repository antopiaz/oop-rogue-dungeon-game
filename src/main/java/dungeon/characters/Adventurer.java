package polymorphia.characters;

import polymorphia.strategy.PlayStrategy;

public class Adventurer extends Character {
    public Adventurer(String name, Double initialHealth, PlayStrategy playStrategy) {
        super(name, initialHealth, playStrategy);
    }

    @Override
    public Boolean isCreature() {
        return false;
    }

    @Override
    public Boolean isAdventurer() {
        return true;
    }
}