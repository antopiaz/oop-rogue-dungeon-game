package polymorphia.artifacts;

public interface IArtifact {
    String getName();

    double getValue();

    boolean isType(ArtifactType type);
}
