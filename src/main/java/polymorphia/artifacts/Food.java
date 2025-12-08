package polymorphia.artifacts;

public class Food extends Artifact {
    private double healthValue;

    protected Food(String name, double healthValue) {
        super(ArtifactType.Food, name, healthValue);
        this.healthValue = healthValue;
    }

    @Override
    public String toString() {
        String formattedHealth = String.format("%.2f", healthValue);
        return name + "(" + formattedHealth + ")";
    }

}
