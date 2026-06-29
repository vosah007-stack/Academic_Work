package core;

import tileengine.Tileset;
import utils.RandomUtils;

public class Player {
    int playerX;
    int playerY;
    World map;
    int score;
    boolean touchedFlower;
    public Player(World w) {// finds random position for player and places them in the world
        map = w;
        int randomFloorX = RandomUtils.uniform(w.rand,1, w.flrTiles.tiles.keySet().size() + 1); // will be used to get a randomX value for the player
        for (int x: w.flrTiles.tiles.keySet()) {
            randomFloorX--;
            if (randomFloorX == 0) {
                playerX = x;
            }
        }
        int randomFloorY = RandomUtils.uniform(w.rand,0, w.flrTiles.tiles.get(playerX).size());
        playerY = w.flrTiles.pickFloorY(playerX, randomFloorY);
        map.world[playerX][playerY] = Tileset.AVATAR;
        touchedFlower = false;
    }

    public void moveUp() { // moves the player up if there is a floor to move to
        if (map.world[playerX][playerY + 1].id() == 2 ) {
            map.world[playerX][playerY] = Tileset.FLOOR;
            map.world[playerX][playerY + 1] = Tileset.AVATAR;
            playerY++;
            touchedFlower = false;
        } else if (map.world[playerX][playerY + 1].id() == 6) {
            map.world[playerX][playerY] = Tileset.FLOOR;
            map.world[playerX][playerY + 1] = Tileset.AVATAR;
            playerY++;
            score++;
            touchedFlower = true;
        }
    }
    public void moveRight() { // moves the player right if there is a floor to move to
        if (map.world[playerX + 1][playerY].id() == 2) {
            map.world[playerX][playerY] = Tileset.FLOOR;
            map.world[playerX + 1][playerY] = Tileset.AVATAR;
            playerX++;
            touchedFlower = false;
        } else if (map.world[playerX + 1][playerY].id() == 6) {
            map.world[playerX][playerY] = Tileset.FLOOR;
            map.world[playerX + 1][playerY] = Tileset.AVATAR;
            playerX++;
            score++;
            touchedFlower = true;
        }
    }
    public void moveDown() { // moves the player down if there is a floor to move to
        if (map.world[playerX][playerY - 1].id() == 2) {
            map.world[playerX][playerY] = Tileset.FLOOR;
            map.world[playerX][playerY - 1] = Tileset.AVATAR;
            playerY--;
            touchedFlower = false;
        } else if (map.world[playerX][playerY - 1].id() == 6) {
            map.world[playerX][playerY] = Tileset.FLOOR;
            map.world[playerX][playerY - 1] = Tileset.AVATAR;
            playerY--;
            score++;
            touchedFlower = true;
        }
    }
    public void moveLeft() { // moves the player left if there is a floor to move to
        if (map.world[playerX - 1][playerY].id() == 2) {
            map.world[playerX][playerY] = Tileset.FLOOR;
            map.world[playerX - 1][playerY] = Tileset.AVATAR;
            playerX--;
            touchedFlower = false;
        } else if (map.world[playerX - 1][playerY].id() == 6) {
            map.world[playerX][playerY] = Tileset.FLOOR;
            map.world[playerX - 1][playerY] = Tileset.AVATAR;
            playerX--;
            score++;
            touchedFlower = true;
        }
    }
}
