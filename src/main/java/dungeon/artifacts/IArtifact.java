package polymorphia.artifacts;

public interface IArtifact {
    String getName();

    double getHealthValue();

    double getDefenseValue();

    double getStrength();

    double getMovingCost();

    boolean isType(ArtifactType type);
}
