package dungeon.artifacts;


public abstract class Artifact implements IArtifact {
    private final String name;
    private final double value;
    private final ArtifactType type;

    public Artifact(ArtifactType type, String name, double value) {
        this.value = value;
        this.type = type;
        this.name = name;
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
