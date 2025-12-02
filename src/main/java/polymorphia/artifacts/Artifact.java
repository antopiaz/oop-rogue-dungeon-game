package polymorphia.artifacts;


public abstract class Artifact implements IArtifact {
    public static double DEFAULT_STRENGTH = 0.0;
    public static double DEFAULT_MOVING_COST = 0.0;

    protected final String name;
    protected final double healthValue;
    protected final double strength;
    protected final double movingCost;
    private final ArtifactType type;

    public Artifact(ArtifactType type, String name, double healthValue, double strength, double movingCost) {
        this.healthValue = healthValue;
        this.type = type;
        this.name = name;
        this.strength = strength;
        this.movingCost = movingCost;
    }

    public Artifact(ArtifactType type, String name, double healthValue) {
        this(type, name, healthValue, DEFAULT_STRENGTH, DEFAULT_MOVING_COST);
    }

    @Override
    public boolean isType(ArtifactType type) {
        return this.type == type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getHealthValue() {
        return healthValue;
    }

    @Override
    public double getDefenseValue() {
        return strength;
    }

    @Override
    public double getStrength() {
        return strength;
    }

    @Override
    public double getMovingCost() {
        return movingCost;
    }
}
