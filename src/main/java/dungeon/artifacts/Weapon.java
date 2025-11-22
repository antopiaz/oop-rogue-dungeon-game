package polymorphia.artifacts;

public class Weapon extends Artifact {
    public static double DEFAULT_HEALTH_VALUE = 0.0;

    public Weapon(String name, double strength, double movingCost) {
        super(ArtifactType.Weapon, name, DEFAULT_HEALTH_VALUE, strength, movingCost);
    }

    @Override
    public String toString() {
        String formattedStrength = String.format("%.2f", getStrength());
        return "weapon " + getName() + "(strength:" + formattedStrength + ", movingCost:" + getMovingCost() + ")";
    }

}