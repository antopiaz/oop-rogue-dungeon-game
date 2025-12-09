package dungeon.artifacts;

public interface IArtifact {
    String getName();

    double getValue();

    boolean isType(ArtifactType type);
}
