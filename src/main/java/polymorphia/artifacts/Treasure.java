package polymorphia.artifacts;

public class Treasure extends Artifact {

    public Treasure(String name, double coinValue) {
        super(ArtifactType.Treasure, name, coinValue);
    }

    @Override
    public String toString() {
        String formattedStrength = String.format("%.2f", getValue());
        return getName() + " Treasure(value:" + formattedStrength + ")";
    }

}
