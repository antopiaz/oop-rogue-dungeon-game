package polymorphia.characters;

import polymorphia.artifacts.IArtifact;

public class WeaponizedCharacter extends CharacterDecorator {
    // Methods that need to be overwritten
    // Instead of fight, move, eat, change specific helper methods
    // to avoid repeating code.
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
    public double getDamageInflicted(double myRoll, double foeRoll) {
        return decoratedCharacter.getDamageInflicted(myRoll, foeRoll) + weapon.getStrength();
    }

    @Override
    public double getDamageReceived(double myRoll, double foeRoll) {
        return decoratedCharacter.getDamageReceived(myRoll, foeRoll) + weapon.getStrength();
    }

}
