package dungeon.artifacts;

public class Armor extends Artifact {

    public Armor(String name, double armorValue) {
        super(ArtifactType.Armor, name, armorValue);
    }

    @Override
    public String toString() {
        String formattedStrength = String.format("%.2f", getValue());
        return getName() + " armor(defense:" + formattedStrength  + ")";
    }

}