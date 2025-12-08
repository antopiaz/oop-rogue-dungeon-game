package polymorphia.characters;

import polymorphia.artifacts.IArtifact;

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
    public double dealFightDamage(double baseDamage) {
        return decoratedCharacter.dealFightDamage(baseDamage + weapon.getValue());
    }

}
