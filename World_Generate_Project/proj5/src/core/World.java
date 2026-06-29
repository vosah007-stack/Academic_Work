package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.util.*;

/*
Object used to generate a random world
parameters:
world = the 2D TETile array representation of the world
rand = the random object used to control the randomness of the code
rooms = Map storing all the rooms in the World
maxRoom = the limit of the number of rooms expected in this world
width = the width of the world
height = the height of the world
flrTiles = a graph implementation of all the floor tiles
 */
public class World {

    // build your own world!
    TETile[][] world;
    int worldWidth;
    int worldHeight;
    int maxRoom;
    Random rand;
    int seed;
    Map<Room, List<Room>> rooms;
    FloorTiles flrTiles;
    TERenderer ter;
    int coinCount;
    /*
    Constructor
    parameters:
    w = width of world
    h = height of world
    s = chosen seed
    instance variables:
    world = a world with only nothing tiles
    rand = chosen seed
    maxRoom = a random value chosen by rand
    rooms = empty Array
    flrTiles = empty FloorTiles object
     */
    public World(int w, int h, int s, TERenderer t) {
        worldWidth = w;
        worldHeight = h;
        world = new TETile[w][h + 10];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h + 10; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        rand = new Random(s);
        seed = s;
        maxRoom = RandomUtils.uniform(rand, 8, 13);
        rooms = new HashMap<>();
        flrTiles = new FloorTiles();
        ter = t;
    }

    void worldGeneration() {
        List<Room> fringe = new ArrayList<>();
        Set<Room> marked = new HashSet<>();
        Room currentRoom;
        fringe.add(makeRootRoom());
        while(rooms.size() < maxRoom && !fringe.isEmpty()) {
            currentRoom = fringe.removeFirst();
            marked.add(currentRoom);
            addNeighbors(currentRoom, 1);
            for (Room neighbor: currentRoom.neighbors) {
                if (!marked.contains(neighbor)) {
                    fringe.add(neighbor);
                }
            }

        }
    }

    /*
    function:
    creates a root room for the world which has a random startY and size value.
    Will always have a startX of 0.
    Gets added to the room array.
     */
    Room makeRootRoom() {
        int randSize = RandomUtils.uniform(rand, 5, worldWidth / 4);
        Room root = new Room(worldWidth/2, worldHeight/2, randSize, randSize);
        rooms.put(root, new ArrayList<>());
        makeRoomPositive(root, "Up");
        return root;
    }

    /*
    parameters:
    room = chosen room being added to world
    function:
    method adds a room to the world
    IMPORTANT:
    Room will get cut off if there exists a wall, floor, or it reaches the border of the world.
     */
    void makeRoomPositive(Room room, String direction) {
        int endCol = room.startX + room.sizeX;
        int endRow = room.startY + room.sizeY;
        int row = room.startY;
        int col = room.startX;
        int tempSizeY = 0;
        int tempSizeX = 1; // from skipping first column
        row++;
        col++; // need to skip checking the walls above/right to the start position
        while (col < endCol - 1 && col < worldWidth) { // need to skip checking the walls above/right the end position
            tempSizeX++;
            if (world[col][row].id() != 3) {
                endCol = col;
            } else {
                tempSizeY = checkSizeY(room.sizeY, col, row, true);
                if (tempSizeY <= 3) {
                    endCol = col;
                } else {
                    room.sizeY = Math.min(tempSizeY, room.sizeY);
                }
            }
            col++;
        }
        if (col == worldWidth) {
            room.sizeX = worldWidth - 1;
        }
        room.sizeX = tempSizeX;
        col = room.startX;
        row = room.startY;
        endCol = room.startX + room.sizeX;
        endRow = room.startY + room.sizeY;
        while (col < endCol && col < worldWidth) { // a room of size 5 at (0, 0) would have xs have 0, 1, 2, 3, 4
            if (col == room.startX || (col == endCol - 1 || col == worldWidth - 1)) {
                makeLineEdgePositive(col, row, endRow, room);
            } else {
                makeLineOpenPositive(col, row, endRow, room.startY, room);
            }
            // had here row ++
            col++;
        }
        room.isStartPositive = true;
        world[room.startX + (room.sizeX / 2)][room.startY + (room.sizeY / 2)] = Tileset.FLOWER;
        coinCount++;
    }

    void makeRoomNegative(Room room, String direction) {
        int startCol = room.startX - room.sizeX;
        int startRow = room.startY - room.sizeY;
        int row = room.startY;
        int col = room.startX;
        int tempSizeY = 0;
        int tempSizeX = 1; // from skipping first column
        row--;
        col--; // need to skip checking the walls below/left to the end position
        while (col > startCol + 1 && col > 0) { // need to skip checking the walls below/left to the end position
            tempSizeX++;
            if (world[col][row].id() != 3) {
                startCol = col;
            } else {
                tempSizeY = checkSizeY(room.sizeY, col, row, false);
                if (tempSizeY <= 3) {
                    startCol = col;
                } else {
                    room.sizeY = Math.min(tempSizeY, room.sizeY);
                }
            }
            col--;
        }
        if (col == 0) {
            room.sizeX = 1;
        }
        room.sizeX = tempSizeX;
        col = room.startX;
        row = room.startY;
        startCol = room.startX - room.sizeX;
        startRow = room.startY - room.sizeY;
        while (col > startCol && col > 0) {
            if (col == room.startX || (col == startCol + 1 || col == 1)) {
                makeLineEdgeNegative(col, row, startRow, room);
            } else {
                makeLineOpenNegative(col, row, startRow, room.startY, room);
            }
            // had here row ++
            col--;
        }
        world[(room.startX - (room.sizeX / 2))][room.startY - (room.sizeY / 2)] = Tileset.FLOWER;
        room.isStartPositive = false;
        coinCount++;
    }
    private int checkSizeY(int maxOrMinH, int x, int y, boolean isPositive) {
        int height = maxOrMinH;
        int heightAcrewed = 1;
        if (isPositive) {
            for (int i = y; i < y + maxOrMinH; i++) {
                if (world[x][i].id() != 3 || i == worldHeight - 1) {
                    return heightAcrewed;
                }
                heightAcrewed++;
            }
        } else {
            for (int i = y; i > y - maxOrMinH; i--) {
                if (world[x][i].id() != 3 || i == 0) {
                    return heightAcrewed;
                }
                heightAcrewed++;
            }
        }
        return height;
    }

    /*
    parameters:
    col = current column
    row = starting row
    height = height of room
    function:
    helper function for makeRectangle which creates the edge columns for the room
     */
    private void makeLineEdgePositive ( int col, int row, int height, Room room){
        int tileNotPossible = 0;
        while (row < height && row < worldHeight) {
            if (world[col][row].id() == 3) {
                world[col][row] = Tileset.WALL;
            }
            row++;
        }
    }
    private void makeLineEdgeNegative ( int col, int row, int height, Room room){
        while (row > height && row > 0) {
            if (world[col][row].id() == 3) {
                world[col][row] = Tileset.WALL;
            }
            row--;
        }
    }

    /*
parameters:
col = current column
row = starting row
height = height of room
startY = starting row (constant)
function:
helper function for makeRectangle which creates the inner columns for the room
 */
    private void makeLineOpenPositive ( int col, int row, int height, int startY, Room room){
        while (row < height && row < worldHeight) { // need to account for wall tiles
            if (world[col][row].id() == 3) {
                if (row == startY || (row == height - 1 || row == worldHeight - 1)) {
                    world[col][row] = Tileset.WALL;
                } else {
                    world[col][row] = Tileset.FLOOR;
                    flrTiles.addTile(col, row);
                }
            }
            row++;
        }
    }
    private void makeLineOpenNegative ( int col, int row, int height, int startY, Room room){
        while (row > height && row > 0) { // need to account for wall tiles
            if (world[col][row].id() == 3) {
                if (row == startY || (row == height + 1 || row == 1)) {
                    world[col][row] = Tileset.WALL;
                } else {
                    world[col][row] = Tileset.FLOOR;
                    flrTiles.addTile(col, row);
                }
            }
            row--;
        }
    }

    /*
    parameter:
    gettingNeighbors = room that will be receiving neighbors
    function:
    will produce the neighbors for gettingNeighbors
    */
    void addNeighbors(Room gettingNeighbors, int min) {
        int neighborNum = 0;
        int maxNeighborNum = gettingNeighbors.directions.size();
        if (maxRoom < rooms.size() + maxNeighborNum) {
            neighborNum = RandomUtils.uniform(rand, min, maxRoom - rooms.size() + 1);
        } else {
            neighborNum = RandomUtils.uniform(rand, min,maxNeighborNum + 1);
        }
        List<String> directions = new ArrayList<>();
        directions.addAll(gettingNeighbors.directions);
        ArrayList<String> hallTypes = new ArrayList<>();
        hallTypes.add("Straight");
        hallTypes.add("Bend");
        Room tempRoom;
        int addedRooms = 0;
        while (addedRooms < neighborNum && !directions.isEmpty()) {
            int hallLength = RandomUtils.uniform(rand, 3, worldHeight / 10 + 1);
            int randomDirection = RandomUtils.uniform(rand, 0, directions.size());
            int randomRotation = RandomUtils.uniform(rand, 0, 2);
            String direction = directions.remove(randomDirection);
            String rotation;
            if (hallLength <= 3) {
                rotation = "Straight";
            } else {
                rotation = hallTypes.get(randomRotation);
                if (rotation.equals("Bend")) {
                    if (RandomUtils.uniform(rand, 0, 2) == 0) {
                        rotation = "Bend Positive";
                    } else {
                        rotation = "Bend Negative";
                    }
                }
            }
            ArrayList<Integer> hallEnd = makeHall(gettingNeighbors, direction, rotation, hallLength); // need to indicate if straight hall, bend hall, or was bend and now straight hall
            if (hallEnd != null) {
                int size = RandomUtils.uniform(rand, 5, world[0].length / 4);
                if (hallEnd.size() != 3) { // using size() == 3 to signal that tried but didn't bend
                    if (direction.equals("Up") || direction.equals("Down")) {
                        if (rotation.equals("Bend Positive")) {
                            direction = "Right";
                        } else if (rotation.equals("Bend Negative")) {
                            direction = "Left";
                        }
                    } else {
                        if (rotation.equals("Bend Positive")) {
                            direction = "Up";
                        } else if (rotation.equals("Bend Negative")) {
                            direction = "Down";
                        }
                    }
                }
                if (direction.equals("Up"))
                {
                    tempRoom = new Room(hallEnd.get(0), hallEnd.get(1), size, size);
                    makeRoomPositive(tempRoom, direction);
                    gettingNeighbors.roomAddNeighbor(tempRoom, "Down");
                } else if (direction.equals("Right")) {
                    tempRoom = new Room(hallEnd.get(0), hallEnd.get(1), size, size);
                    makeRoomPositive(tempRoom, direction);
                    gettingNeighbors.roomAddNeighbor(tempRoom, "Left");
                } else if (direction.equals("Down")) {
                    tempRoom = new Room(hallEnd.get(0), hallEnd.get(1), size, size);
                    makeRoomNegative(tempRoom, direction);
                    gettingNeighbors.roomAddNeighbor(tempRoom, "Up");
                } else {
                    tempRoom = new Room(hallEnd.get(0), hallEnd.get(1), size, size);
                    makeRoomNegative(tempRoom, direction);
                    gettingNeighbors.roomAddNeighbor(tempRoom, "Right");
                }
                addedRooms++;
                rooms.put(gettingNeighbors, gettingNeighbors.neighbors);
            }
        }
    }

    /*
    parameter:
    originRoom = room the hall starts at
    direction = direction the hall goes
    rotation = determines if the hall bends or not
    hallLength = the length of the hall
    function:
    creates a hall and returns the position it ends at as an array with item 0 = x position and item 1 = y position
    */
    ArrayList<Integer> makeHall(Room originRoom, String direction, String rotation, int hallLength) {
        if (originRoom.sizeX <= 3) {
            return null;
        }
        int hallBurstCord = 0; // the x or y value of the wall of the room it will burst from, will not burst from corner
        if (direction.equals("Up") || direction.equals("Down")) { // important reminder: a room of sizeX 5 at (0, 0) would have x values at 0, 1, 2, 3, 4
            if(originRoom.isStartPositive) { //checks if the start position of the room is positive (ie if the originRoom was made with makeRoomPositve)
                hallBurstCord = RandomUtils.uniform(rand, originRoom.startX +1, originRoom.startX + originRoom.sizeX - 1); // x position of hall
            } else {
                hallBurstCord = RandomUtils.uniform(rand, originRoom.startX - originRoom.sizeX + 2, originRoom.startX); // x position of hall
            }
        } else {
            if(originRoom.isStartPositive) { //checks if the start position of the room is positive (ie if the originRoom was made with makeRoomPositve)
                hallBurstCord = RandomUtils.uniform(rand, originRoom.startY + 1, originRoom.startY + originRoom.sizeY - 1); // y position of hall
            } else {
                hallBurstCord = RandomUtils.uniform(rand, originRoom.startY - originRoom.sizeY + 2, originRoom.startY); // y position of hall
            }
        }
        if (rotation.equals("Straight")) {
            return straightHall(originRoom, direction, hallLength, hallBurstCord); // need to account for if null
        } else {
            return bentHall(originRoom, direction, hallLength, hallBurstCord, rotation); // need to account for if null
        }
    }

    /*
    parameters:
    originRoom = room the hall starts at
    direction = direction the hall goes
    length = the length of the hall
    burstCord = the x or y value of the wall of the room it will burst from
    function:
    creates a straight hall and returns the position it ends at as an array with item 0 = x position and item 1 = y position
    */
    ArrayList<Integer> straightHall(Room originRoom, String direction, int length, int burstCord) {
        int startCord = burstCord;
        int movingCord = 0;
        boolean isVert = true; // true meaning going vertical and false meaning going horizontal
        int appropriateBound = 0; // the world bound to be aware of while creating a hall
        int movement = 0; // will decide what direction the hall moves (up / right or down / left)
        int originalLength = length;
        if (direction.equals("Up")) {
            if(originRoom.isStartPositive) {
                movingCord = originRoom.startY + originRoom.sizeY - 1;
            } else {
                movingCord = originRoom.startY;
            }
            isVert = true;
            appropriateBound = worldHeight;
            movement = 1;
        } else if (direction.equals("Right")) {
            if(originRoom.isStartPositive) {
                movingCord = originRoom.startX + originRoom.sizeX - 1;
            } else {
                movingCord = originRoom.startX;
            }
            isVert = false;
            appropriateBound = worldWidth;
            movement = 1;
        } else if (direction.equals("Down")) {
            if(originRoom.isStartPositive) {
                movingCord = originRoom.startY;
            } else {
                movingCord = originRoom.startY - originRoom.sizeY + 1;
            }
            isVert = true;
            appropriateBound = 0;
            movement = -1;
        } else {
            if(originRoom.isStartPositive) {
                movingCord = originRoom.startX;
            } else {
                movingCord = originRoom.startX - originRoom.sizeX + 1;
            }
            isVert = false;
            appropriateBound = 0;
            movement = -1;
        }
        if (isHallPossible(isVert, movingCord, startCord, movement, length, direction)) {
            ArrayList<Integer> cord = new ArrayList<>();
            while (length > 0) {
                if (isVert) {
                    if (!(length == originalLength)) {
                        movingCord += movement;
                    }
                    straightHallHelper(movingCord, startCord, isVert);
                    length--;
                    if (length == 0) {
                        if (direction.equals("Up")) {
                            cord.add(startCord - 1); // moving cord adding shouldn't have +- 1
                            cord.add(movingCord);
                        } else {
                            cord.add(startCord + 1);
                            cord.add(movingCord);
                        }
                    }
                } else {
                        if (!(length == originalLength)) {
                            movingCord += movement;
                        }
                        straightHallHelper(movingCord, startCord, isVert);
                        length--;
                    if (length == 0) {
                        if(direction.equals("Right")) {
                            cord.add(movingCord);
                            cord.add(startCord - 1);
                        } else {
                            cord.add(movingCord);
                            cord.add(startCord + 1);
                        }
                    }
                }
            }
            return cord;
        }
        return null;
    }
    ArrayList<Integer> straightHall(int moveC, String direction, int length, int burstCord) {
        int startCord = burstCord;
        int movingCord = moveC;
        boolean isVert = true; // true meaning going vertical and false meaning going horizontal
        int appropriateBound = 0; // the world bound to be aware of while creating a hall
        int movement = 0; // will decide what direction the hall moves (up / right or down / left)
        int originalLength = length;
        if (direction.equals("Up")) {
            isVert = true;
            appropriateBound = worldHeight;
            movement = 1;
        } else if (direction.equals("Right")) {
            isVert = false;
            appropriateBound = worldWidth;
            movement = 1;
        } else if (direction.equals("Down")) {
            isVert = true;
            appropriateBound = 0;
            movement = -1;
        } else {
            isVert = false;
            appropriateBound = 0;
            movement = -1;
        }
        if (isHallPossible(isVert, movingCord, startCord, movement, length, direction)) {
            ArrayList<Integer> cord = new ArrayList<>();
            while (length > 0) {
                if (isVert) {
                    if (!(length == originalLength)) {
                        movingCord += movement;
                    }
                    straightHallHelper(movingCord, startCord, isVert);
                    length--;
                    if (length == 0) {
                        if(direction.equals("Up")) {
                            cord.add(startCord - 1);
                            cord.add(movingCord);
                        } else {
                            cord.add(startCord + 1);
                            cord.add(movingCord);
                        }
                    }
                } else {
                    if (!(length == originalLength)) {
                        movingCord += movement;
                    }
                        straightHallHelper(movingCord, startCord, isVert);
                        length--;
                    if (length == 0) {
                        if (direction.equals("Right")) {
                            cord.add(movingCord);
                            cord.add(startCord - 1);
                        } else {
                            cord.add(movingCord);
                            cord.add(startCord + 1);
                        }
                    }
                }
            }
            return cord;
        }
        return null;
    }

    private void straightHallHelper(int movingCord, int startCord, boolean isVert) {
        if (isVert){
            if (world[startCord][movingCord].id() == 1) {
            }
            world[startCord][movingCord] = Tileset.FLOOR;
            flrTiles.addTile(startCord, movingCord);
            if (world[startCord + 1][movingCord].id() == 3) {
                world[startCord + 1][movingCord] = Tileset.WALL;
            }
            if (world[startCord - 1][movingCord].id() == 3) {
                world[startCord - 1][movingCord] = Tileset.WALL;
            }
        } else {
            world[movingCord][startCord] = Tileset.FLOOR;
            flrTiles.addTile(movingCord, startCord);
            if (world[movingCord][startCord + 1].id() == 3) {
                world[movingCord][startCord + 1] = Tileset.WALL;
            }
            if (world[movingCord][startCord - 1].id() == 3) {
                world[movingCord][startCord - 1] = Tileset.WALL;
            }
        }
    }
    /*
    parameter:
    originRoom = room the hall starts at
    direction = direction the hall goes
    length = the length of the hall
    burstCord = the x or y value of the wall of the room it will burst from
    bend = how the hall bends
    function:
    creates a bent hall and returns the position it ends at as an array with item 0 = x position and item 1 = y position
    */
        ArrayList<Integer> bentHall (Room originRoom, String direction,int length, int burstCord, String bend){
            int randomLength = RandomUtils.uniform(rand, 1, length);
            int straightLength = Math.max(randomLength, length - randomLength);
            int bentLength = length - straightLength;
            int movingCord = 0;
            ArrayList<Integer> hallEnd = straightHall(originRoom, direction, straightLength, burstCord);
            if (hallEnd == null) {
                return null;
            }
            if (direction.equals("Up") || direction.equals("Down")) {
                ArrayList<Integer> bentHallEnd;
                if (direction.equals("Up")) {
                    world[hallEnd.get(0) + 1][hallEnd.get(1) + 1] = Tileset.WALL;
                } else {
                    world[hallEnd.get(0) - 1][hallEnd.get(1) - 1] = Tileset.WALL;
                }
                if (bend.equals("Bend Positive")) {
                    if (direction.equals("Up")) {
                        movingCord = hallEnd.get(0) + 2;
                        bentHallEnd = straightHall(movingCord, "Right", bentLength, hallEnd.get(1));
                    } else {
                        movingCord = hallEnd.get(0);
                        bentHallEnd = straightHall(movingCord, "Right", bentLength, hallEnd.get(1));
                    }
                } else {
                    if (direction.equals("Up")) {
                        movingCord = hallEnd.get(0);
                        bentHallEnd = straightHall(movingCord, "Left", bentLength, hallEnd.get(1));
                    } else {
                        movingCord = hallEnd.get(0) - 2;
                        bentHallEnd = straightHall(movingCord, "Left", bentLength, hallEnd.get(1));
                    }
                }
                if(bentHallEnd == null) {
                    if (direction.equals("Up")) {
                        world[hallEnd.get(0) + 1][hallEnd.get(1) + 1] = Tileset.NOTHING;
                    } else {
                        world[hallEnd.get(0) - 1][hallEnd.get(1) - 1] = Tileset.NOTHING;
                    }
                    hallEnd.add(0); // will use size == 3 as tag that this is an unsuccessful bend hall
                    return hallEnd;
                }
                return bentHallEnd;
            } else {
                ArrayList<Integer> bentHallEnd;
                if (direction.equals("Right")) {
                    world[hallEnd.get(0) + 1][hallEnd.get(1) + 1] = Tileset.WALL;
                } else {
                    world[hallEnd.get(0) - 1][hallEnd.get(1) - 1] = Tileset.WALL;
                }
                if (bend.equals("Bend Positive")) {
                    if(direction.equals("Right")) {
                        movingCord = hallEnd.get(1) + 2;
                        bentHallEnd = straightHall(movingCord, "Up", bentLength, hallEnd.get(0));
                    } else {
                        movingCord = hallEnd.get(1);
                        bentHallEnd = straightHall(movingCord, "Up", bentLength, hallEnd.get(0));
                    }
                } else {
                    if (direction.equals("Right")) {
                        movingCord = hallEnd.get(1);
                        bentHallEnd = straightHall(movingCord, "Down", bentLength, hallEnd.get(0));
                    } else {
                        movingCord = hallEnd.get(1) - 2;
                        bentHallEnd = straightHall(movingCord, "Down", bentLength, hallEnd.get(0));
                    }
                }
                if(bentHallEnd == null) {
                    if (direction.equals("Right")) {
                        world[hallEnd.get(0) + 1][hallEnd.get(1) + 1] = Tileset.NOTHING;
                    } else {
                        world[hallEnd.get(0) - 1][hallEnd.get(1) - 1] = Tileset.NOTHING;
                    }
                    hallEnd.add(0);
                    return hallEnd;
                }
                return bentHallEnd;
            }
        }

        private boolean isHallPossible(boolean isVert, int moveCord, int burstCord, int movement, int length, String direction) {
            boolean possible = false;
            length--; // needs to be done to skipping looking at bursting wall
            if (isVert) {
                while (length > 0) {
                    moveCord += movement;
                    //checks if the edge goes out of bounds or hits a nonempty space
                    if(((moveCord >= worldHeight - 1 || moveCord < 0) || (burstCord  >= worldWidth - 1 || burstCord < 1)) || world[burstCord][moveCord].id() != 3) // used to check if wall had space
                    {
                        return false;
                    }
                    length--;
                }
                if (direction.equals("Up")) {
                    possible = isRoomPossible(burstCord - 1, moveCord + 1, direction); // was moveCord + 1
                } else {
                    possible = isRoomPossible(burstCord + 1, moveCord - 1, direction); // was moveCord - 1
                }
            } else {
                while (length > 0) {
                    moveCord += movement;
                    //checks if the edge goes out of bounds or hits a nonempty space
                    if(((moveCord >= worldWidth -1 || moveCord < 0) || (burstCord >= worldHeight - 1 || burstCord < 1)) || world[moveCord][burstCord].id() != 3) // used to check if wall had space
                    {
                        return false;
                    }
                    length--;
                }
                if (direction.equals("Right")) {
                    possible = isRoomPossible(moveCord + 1, burstCord - 1, direction); // was moveCord + 1
                } else {
                    possible = isRoomPossible(moveCord - 1, burstCord + 1, direction); // was moveCord - 1
                }
            }
            return possible;
        }
        /*
        parameters:
        endPointX = the x cordinate for the hallway's end
        endPointY = the y cordinate for the hallway's end
        direction = the direction the line is headed
        function:
        returns true if it has the minimum space to create
        */
        private boolean isRoomPossible(int endPointX, int endPointY, String direction) {
            if (direction.equals("Up")) {
                for (int i = 1; i < 5; i++) { // don't want to check if border wall and back are free for space: don't check lines made of just walls and back walls
                    if (!checkLine(endPointX + i, endPointY, true, 1, 3)) {
                        return false;
                    }
                }
            } else if (direction.equals("Right")) {
                for (int i = 1; i < 5; i++) {
                    if (!checkLine(endPointX, endPointY + i, false, 1, 3)) {
                        return false;
                    }
                }
            } else if (direction.equals("Down")) {
                for (int i = -1; i > -5; i--) {
                    if (!checkLine(endPointX + i, endPointY, true, -1, 3)) {
                        return false;
                    }
                }
            } else {
                for (int i = -1; i > -5; i--) {
                    if (!checkLine(endPointX, endPointY + i, false, -1, 3)) {
                        return false;
                    }
                }
            }
            return true;
        }
        /*
        parameters:
        x = the x cord to start from
        y = the y cord to start from
        isVert = vertical true and horizontal false
        movement = decides if moving positively or negatively
        deepness = how many points to check
        function:
        returns true if it has the minimum space to create
        */
        private boolean checkLine(int x, int y, boolean isVert, int movement, int deepness) {
            if(isVert) {
                if (x >= worldWidth || x < 0) {
                    return false;
                }
                while (deepness > 0) {
                    y += movement;
                    deepness--;
                    if (y >= worldHeight || y < 0 || world[x][y].id() != 3) {
                       return false;
                    }
                }
            } else {
                if (y >= worldHeight || y < 0) {
                    return false;
                }
                while (deepness > 0) {
                    x += movement;
                    deepness--;
                    if (x >= worldWidth || x < 0 || world[x][y].id() != 3) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
