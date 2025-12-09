package dungeon.artifacts;

public class Food extends Artifact {

    public Food(String name, double healthValue) {
        super(ArtifactType.Food, name, healthValue);
    }

    @Override
    public String toString() {
        String formattedHealth = String.format("%.2f", getValue());
        return getName() + "(" + formattedHealth + ")";
    }

}
