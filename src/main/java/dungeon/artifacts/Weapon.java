package dungeon.artifacts;

public class Weapon extends Artifact {

    public Weapon(String name, double attackValue) {
        super(ArtifactType.Weapon, name, attackValue);
    }

    @Override
    public String toString() {
        String attack = String.format("%.2f", getValue());
        return "weapon " + getName() + "(strength:" + attack + ")";
    }

}