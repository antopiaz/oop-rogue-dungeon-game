package polymorphia.artifacts;

public class Armor extends Artifact {
    public static double DEFAULT_HEALTH_VALUE = 0.0;
    public static double DEFAULT_MOVING_COST_WITH_ARMOR = 0.5;

    public Armor(String name, double strength, double movingCost) {
        super(ArtifactType.Armor, name, DEFAULT_HEALTH_VALUE, strength, movingCost);
    }

    @Override
    public String toString() {
        String formattedStrength = String.format("%.2f", getDefenseValue());
        return getName() + " armor(strength:" + formattedStrength + ", movingCost:" + getMovingCost() + ")";
    }

}