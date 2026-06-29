package core;

import java.util.ArrayList;
import java.util.List;
/* Object representing the rooms in the world
    Parameters:
    startX = the start x value used to make the room
    startY = the start Y value used to make the room
    size = the size used to make the room
    id = the unique id of the room
    edgePoints = an array of array lists that each have size two, item 0 = x position and item 1 = y position
    neighbors = the list of the neighbors of the room
     */
public class Room {
    static int roomNum;
    int startX;
    int startY;
    int id;
    int sizeX;
    int sizeY;
    boolean isStartPositive;
    List<String> directions;
    List<Room> neighbors;
    /*
    Constructor:
    Parameters:
    x = startX
    y = startY
    s = size
     */
    public Room(int x, int y, int sX, int sY) {
        startX = x;
        startY = y;
        sizeX = sX;
        sizeY = sY;
        neighbors = new ArrayList<>();
        id = roomNum;
        roomNum++;
        directions = new ArrayList<>();
        directions.add("Up");
        directions.add("Right");
        directions.add("Down");
        directions.add("Left");
    }
    /*
    parameters:
    n = the neighbor being added
    function:
    adds a room to the neighbor list of the room.
    Also adds this room to the neighbor list of the other room.
     */
    void roomAddNeighbor(Room room, String direction) {
        this.neighbors.add(room);
        room.neighbors.add(this);
        room.directions.remove(direction);
    }
}
