package dungeon.artifacts;


public abstract class Artifact implements IArtifact {
    public static double DEFAULT_STRENGTH = 0.0;

    protected String name;
    protected double value;
    private final ArtifactType type;

    public Artifact(ArtifactType type, String name, double value) {
        this.value = value;
        this.type = type;
        this.name = name;
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
    public double getValue() {
        return value;
    }
}
