package dungeon.maze;

public class RoomFactory {
    Room createRoom(String name) {
        return new Room(name);
    }
}
