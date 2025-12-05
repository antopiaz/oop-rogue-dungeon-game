package polymorphia.maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import org.slf4j.Logger;

import polymorphia.artifacts.IArtifact;
import polymorphia.characters.Character;

public class Maze {
    private List<Room> rooms;

    private Maze() {
    }

    public int size() {
        return rooms.size();
    }

    @Override
    public String toString() {
        return String.join("\n\n", rooms.stream().map(Object::toString).toList());
    }

    public Boolean hasLivingCreatures() {
        return rooms.stream().anyMatch(Room::hasLivingCreatures);
    }

    public Boolean hasLivingAdventurers() {
        return rooms.stream().anyMatch(Room::hasLivingAdventurers);
    }

    public List<Character> getLivingCreatures() {
        List<Character> creatures = new ArrayList<>();
        for (Room room : rooms) {
            creatures.addAll(room.getLivingCreatures());
        }
        return creatures;
    }

    public List<Character> getLivingCharacters() {
        List<Character> characters = new ArrayList<>();
        for (Room room : rooms) {
            characters.addAll(room.getLivingCharacters());
        }
        return characters;
    }

    public boolean hasLivingCharacters() {
        return getLivingCharacters().stream().anyMatch(Character::isAlive);
    }

    public List<Character> getLivingAdventurers() {
        return getLivingCharacters().stream()
                .filter(Character::isAdventurer)
                .toList();
    }

    public List<Room> getRooms() {
        return List.copyOf(rooms);
    }

    public static Builder getNewBuilder(RoomFactory roomFactory) {
        return new Builder(roomFactory);
    }

    public Room getRoom(String roomName) {
        return rooms.stream()
                .filter(room -> room.getName().equals(roomName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No room with name " + roomName));
    }

    public static class Builder {
        static Logger logger = org.slf4j.LoggerFactory.getLogger(Builder.class);
        private final Random rand = new Random();

        final Maze maze = new Maze();
        private int currentRoomIndex = 0;
        private final RoomFactory roomFactory;

        private Builder(RoomFactory roomFactory) {
            this.roomFactory = roomFactory;
        }

        private Room nextRoom() {
            return maze.getRooms().get(currentRoomIndex++ % maze.getRooms().size());
        }

        public Builder createGridOfRooms(int rows, int columns) {
            Room[][] roomGrid = new Room[rows][columns];
            List<Room> rooms = new ArrayList<>();
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    Room newRoom = roomFactory.createRoom(String.valueOf("Room (" + row + "," + column + ")"));
                    roomGrid[row][column] = newRoom;
                    rooms.add(newRoom);
                }
            }
            maze.rooms = rooms;

            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    Room currentRoom = roomGrid[row][column];
                    Room neighbor;
                    if (row > 0) {
                        neighbor = roomGrid[row - 1][column];
                        currentRoom.addNeighbor(neighbor);
                    }
                    if (column > 0) {
                        neighbor = roomGrid[row][column - 1];
                        currentRoom.addNeighbor(neighbor);
                    }
                }
            }
            return this;
        }

        public Builder add(Character character) {
            nextRoom().add(character);
            return this;
        }

        public Builder addCharacters(List<Character> characters) {
            for (Character character : characters) {
                nextRoom().add(character);
            }
            return this;
        }

        public Builder addArtifacts(List<IArtifact> artifacts) {
            for (IArtifact artifact : artifacts) {
                nextRoom().add(artifact);
            }
            return this;
        }
        public Builder addArtifact(IArtifact artifact) {
            nextRoom().add(artifact);
            return this;
        }


        public Maze build() {
            assert maze.size() > 0;
            return maze;
        }

        public Builder createDungeon(int rows, int columns) {
            createGridOfRooms(rows, columns);
            return this;
        }

        public Builder addCharacterToStart(Character character) {
            maze.getRooms().get(0).add(character);

            return this;
        }
    }
}
