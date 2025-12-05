package polymorphia.characters;

import polymorphia.artifacts.IArtifact;

public class ArmoredCharacter extends CharacterDecorator {
    // Methods that need to be overwritten
    // Instead of fight, move, eat, change specific helper methods
    // to avoid repeating code.
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
        decoratedCharacter.loseFightDamage(Math.max((fightDamage - armor.getDefenseValue()), 0.0));
    }
    
    @Override
    public double getDamageInflicted(double myRoll, double foeRoll) {
        return decoratedCharacter.getDamageInflicted(myRoll, foeRoll);
    }


}
