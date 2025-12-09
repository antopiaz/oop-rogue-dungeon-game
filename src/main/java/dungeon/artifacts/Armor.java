package dungeon.artifacts;

public class Armor extends Artifact {
    protected double armorValue;

    public Armor(String name, double armorValue) {
        super(ArtifactType.Armor, name, armorValue);
        this.armorValue = armorValue;
    }

    @Override
    public String toString() {
        String formattedStrength = String.format("%.2f", getValue());
        return getName() + " armor(strength:" + formattedStrength  + ")";
    }

    @Override
    public double getValue(){
        return armorValue;
    }

}