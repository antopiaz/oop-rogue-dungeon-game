package dungeon.characters;

import dungeon.artifacts.IArtifact;

public class ArmoredCharacter extends CharacterDecorator {
    private final IArtifact armor;
    
    public ArmoredCharacter(Character character, IArtifact armor) {
        super(character);
        this.armor = armor;
    }

    @Override
    public String getName() {
        return decoratedCharacter.getName() + " with " + armor.getName() + " armor";
    }
    
    @Override
    public void loseFightDamage(double fightDamage) {
        decoratedCharacter.loseFightDamage(Math.max((fightDamage - armor.getValue()), 0.0));
    }

    @Override
    public double dealFightDamage(double baseDamage) {
        return decoratedCharacter.dealFightDamage(baseDamage);
    }

}
