package dungeon.characters;

import dungeon.artifacts.IArtifact;

public class WeaponizedCharacter extends CharacterDecorator {
    private final IArtifact weapon;
    
    public WeaponizedCharacter(Character character, IArtifact weapon) {
        super(character);
        this.weapon = weapon;
    }

    @Override
    public String getName() {
        return decoratedCharacter.getName() + " with weapon " + weapon.getName();
    }

    @Override
    public double dealFightDamage() {
        return decoratedCharacter.dealFightDamage() + weapon.getValue();
    }

    @Override
    public void loseFightDamage(double fightDamage) {
        decoratedCharacter.loseFightDamage(fightDamage);
    }


}
