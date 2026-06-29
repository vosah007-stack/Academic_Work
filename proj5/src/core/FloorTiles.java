package core;

import java.util.ArrayList;
import java.util.HashMap;

public class FloorTiles {
    HashMap<Integer, ArrayList<FloorNode>> tiles; // key will be x value of the floor tiles while the value is an array of the floors at that x
     HashMap<FloorNode, ArrayList<FloorNode>> adjaceny; // tells the neighbors of each floor tile, max four
    public FloorTiles() {
        tiles = new HashMap<>();
        adjaceny = new HashMap<>();
    }
    public void addTile(int x, int y) {
        FloorNode tile = new FloorNode(x, y);
        if (!tiles.containsKey(x)) {
            ArrayList<FloorNode> floorHolder = new ArrayList<>();
            floorHolder.add(tile);
            tiles.put(x, floorHolder);
        } else {
            tiles.get(x).add(tile);
        }
        adjaceny.put(tile, findNeighbors(x, y));
        for (FloorNode flr: adjaceny.get(tile)) {
            adjaceny.get(flr).add(tile); // know flr is in adjaceny from it being in the tiles HashMap
        }
    }
    public int pickFloorY(int x, int index) {
        ArrayList<FloorNode> tilesOfX = tiles.get(x);
        return tilesOfX.get(index).tileY;
    }
    public FloorNode fromCordsFloor (int x, int y) {
        for (FloorNode flr: tiles.get(x)) {
            if (flr.tileY == y) {
                return flr;
            }
        }
        return null;
    }
    private ArrayList<FloorNode> findNeighbors(int x, int y) {
        ArrayList<FloorNode> neighbors = new ArrayList<>();
        boolean found = false;
        int count = 0;
        ArrayList<FloorNode> observing = new ArrayList<>();
        if (tiles.containsKey(x + 1)) {
            observing = tiles.get(x + 1);
            while (count < observing.size() && !found) {
                if (observing.get(count).tileY == y) {
                    neighbors.add(observing.get(count));
                    found = true;
                }
                count++;
            }
        }
        if (tiles.containsKey(x - 1)) {
            found = false;
            count = 0;
            observing = tiles.get(x - 1);
            while (count < observing.size() && !found) {
                if (observing.get(count).tileY == y) {
                    neighbors.add(observing.get(count));
                    found = true;
                }
                count++;
            }
        }
        if (tiles.containsKey(x)) {
            found = false;
            count = 0;
            observing = tiles.get(x);
            while (count < observing.size() && !found) {
                if (observing.get(count).tileY == y + 1) {
                    neighbors.add(observing.get(count));
                    found = true;
                }
                count++;
            }
            found = false;
            count = 0;
            while (count < observing.size() && !found) {
                if (observing.get(count).tileY == y - 1) {
                    neighbors.add(observing.get(count));
                    found = true;
                }
                count++;
            }
        }
        return neighbors;
    }
}
