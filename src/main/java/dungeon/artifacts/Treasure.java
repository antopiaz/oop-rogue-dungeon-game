package dungeon.artifacts;

public class Treasure extends Artifact {

    public Treasure(String name, double coinValue) {
        super(ArtifactType.Treasure, name, coinValue);
    }

    @Override
    public String toString() {
        return getName() + " (treasure)";
    }

}
