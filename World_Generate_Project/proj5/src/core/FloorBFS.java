package core;

import java.util.ArrayList;
import java.util.HashMap;

public class FloorBFS {
    FloorNode startTile;
    FloorNode endTile;
    HashMap<FloorNode, ArrayList<FloorNode>> adjacency;
    HashMap<FloorNode, Boolean> marked;
    HashMap<FloorNode, String> pathTo;
    public FloorBFS(FloorTiles flrTiles, int playX, int playY, int mouseX, int mouseY) {
        ArrayList<FloorNode> frisque = new ArrayList<>();
        startTile = flrTiles.fromCordsFloor(playX, playY);
        endTile = flrTiles.fromCordsFloor(mouseX, mouseY);
        adjacency = flrTiles.adjaceny;
        marked = new HashMap<>();
        pathTo = new HashMap<>();
        for (FloorNode flrs: adjacency.keySet()) {
            marked.put(flrs, false);
            pathTo.put(flrs, "");
        }
        marked.put(startTile, true);
        frisque.add(startTile);
        boolean passed = false;
        while (!frisque.isEmpty() && !passed) {
            passed = bfs(frisque.removeFirst(), frisque);
        }
    }
    private boolean bfs(FloorNode f, ArrayList<FloorNode> frisque) {
        for(FloorNode node: adjacency.get(f)) {
            if (!marked.get(node)) {
                marked.put(node, true);
                pathTo.put(node, pathTo.get(f) + inputTo(f, node));
                frisque.add(node);
                if (node == endTile) {
                    return true;
                }
            }
        }
        return false;
    }
    private String inputTo(FloorNode start, FloorNode neighbor) {
        int sX = start.tileX;
        int sY = start.tileY;
        int neighX = neighbor.tileX;
        int neighY = neighbor.tileY;
        if (sX == neighX && sY + 1 == neighY) {
            return "w";
        } else if (sX == neighX && sY - 1 == neighY) {
            return "s";
        } else if (sY == neighY && sX + 1 == neighX) {
            return "d";
        } else if (sY == neighY && sX - 1 == neighX) {
            return "a";
        } else {
            return "";
        }
    }
}
