package dungeon.maze;

public enum DirectionType {
    NORTH, SOUTH, EAST, WEST;

    public DirectionType opposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
        };
    }
}
