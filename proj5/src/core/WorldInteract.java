package core;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Queue;

/* Runs the interactivity of the code through its various methods
    All methods will likely be static and there should likely be no instance variables
 */
public class WorldInteract {
    static String savedInputs = "";
    static String loadedInputs = "";
    static String loadedInputsCopies = "";
    static World mainMenu() { //runs the code for the interactivity of the main menu
        //creates the opening text for the main menu
        savedInputs = "";
        loadedInputs = "";
        loadedInputsCopies = "";
        TERenderer ter = new TERenderer();
        ter.initialize(50, 60);
        StdDraw.clear(new Color(0, 0, 0));
        StdDraw.setXscale(0, 50.0);
        StdDraw.setYscale(0, 60.0);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(25, 40, "CS61B: BYOW");
        StdDraw.text(25, 30, "(N) NEW GAME");
        StdDraw.text(25, 25, "(L) LOAD GAME");
        StdDraw.text(25, 20, "(Q) QUIT GAME");
        StdDraw.show();
        //initializes the variables used for this method
        String saveFileName = "save.txt";
        File saveFile = new File(saveFileName);
        boolean done = false;
        boolean canWorldGenerate = false;
        String seed = "";
        World w = null;
        /* while loop should end either if the player inputs q or s after the "Enter seed followed by S" message is displayed
            Even if on the "Enter seed followed by S" screen, q will end the system
         */
        while (!done) {
            //if the user types q which ends the system
            if(StdDraw.isKeyPressed(KeyEvent.getExtendedKeyCodeForChar('q'))) {
                System.exit(0);
                // if the user types l
            } else if (StdDraw.isKeyPressed(KeyEvent.getExtendedKeyCodeForChar('l'))) {
                In in = new In(saveFile);
                loadedInputs = in.readString();
                loadedInputsCopies = loadedInputs;
                return savedMainMenu();
                /* if the user types n
                Transitions the code to the next screen
                 */
            } else if (StdDraw.isKeyPressed(KeyEvent.getExtendedKeyCodeForChar('n'))) {
                savedInputs += "n";
                StdDraw.clear(new Color(0,0,0));
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.text(25, 40, "Enter seed followed by S");
                StdDraw.setPenColor(Color.YELLOW);
                StdDraw.text(25, 30, seed);
                canWorldGenerate = true;
                StdDraw.show();
                /*if the user types s and canWorldGenerate is true
                generates the world and ends the method
                 */
            } else if(StdDraw.isKeyPressed(KeyEvent.getExtendedKeyCodeForChar('s')) && canWorldGenerate) {
                savedInputs += "s";
                w = new World(50, 50, Integer.parseInt(seed), ter);
                w.worldGeneration();
                StdDraw.clear(new Color(0, 0, 0));
                ter.drawTiles(w.world);
                StdDraw.show();
                done = true;
                StdDraw.pause(10);
                /*
                Checks if the player has clicked an input and if canWorldGenerate is true
                updates the "enter seed" screen and the seed variable
                 */
            } else if (StdDraw.hasNextKeyTyped()){
                if(canWorldGenerate) {
                    char key = StdDraw.nextKeyTyped();
                    if(key != 'n') { //deals with a bug where the seed always start with n since you click it to activate this part of the code
                        savedInputs += key;
                        seed = seed + key;
                        StdDraw.clear(new Color(0,0,0));
                        StdDraw.setPenColor(Color.WHITE);
                        StdDraw.text(25, 40, "Enter seed followed by S");
                        StdDraw.setPenColor(Color.YELLOW);
                        StdDraw.text(25, 30, seed);
                        StdDraw.show();
                    }
                }
            }
        }
        return w;
    }

    static World savedMainMenu() { //runs the code for the interactivity of the main menu
        //creates the opening text for the main menu
        StdDraw.clear(new Color(0, 0, 0));
        StdDraw.setXscale(0, 50.0);
        StdDraw.setYscale(0, 60.0);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(25, 40, "CS61B: BYOW");
        StdDraw.text(25, 30, "(N) NEW GAME");
        StdDraw.text(25, 25, "(L) LOAD GAME");
        StdDraw.text(25, 20, "(Q) QUIT GAME");
        StdDraw.show();
        //initializes the variables used for this method
        boolean canWorldGenerate = false;
        boolean done = false;
        String seed = "";
        World w = null;
        /* while loop should end either if the player inputs q or s after the "Enter seed followed by S" message is displayed
            Even if on the "Enter seed followed by S" screen, q will end the system
         */
        while (!done) {
            char key = loadedInputsCopies.charAt(0);
            loadedInputsCopies = loadedInputsCopies.substring(1);
            if (key == 'n') {
                StdDraw.clear(new Color(0,0,0));
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.text(25, 40, "Enter seed followed by S");
                StdDraw.setPenColor(Color.YELLOW);
                StdDraw.text(25, 30, seed);
                canWorldGenerate = true;
                /*if the user types s and canWorldGenerate is true
                generates the world and ends the method
                 */
            } else if(key == 's' && canWorldGenerate) {
                TERenderer t = new TERenderer();
                t.initialize(50, 60);
                w = new World(50, 50, Integer.parseInt(seed), t);
                w.worldGeneration();
                TERenderer ter = w.ter;
                StdDraw.clear(new Color(0, 0, 0));
                ter.drawTiles(w.world);
                StdDraw.show();
                done = true;
                StdDraw.pause(10);
                /*
                Checks if the player has clicked an input and if canWorldGenerate is true
                updates the "enter seed" screen and the seed variable
                 */
            } else {
                if(canWorldGenerate) {
                    if(key != 'n') { //deals with a bug where the seed always start with n since you click it to activate this part of the code
                        seed = seed + key;
                        StdDraw.clear(new Color(0,0,0));
                        StdDraw.setPenColor(Color.WHITE);
                        StdDraw.text(25, 40, "Enter seed followed by S");
                        StdDraw.setPenColor(Color.YELLOW);
                        StdDraw.text(25, 30, seed);
                    }
                }
            }
        }
        return w;
    }
    static void gameLoop(World w, Player p) {
        Deque<Character> movesPerformed = new movementDeque();
        Deque<Boolean> touchedFlower = new movementDeque();
        Character redoAction = null;
        boolean isLastKeyU = false;
        if(!loadedInputs.isEmpty()) {
            savedGameLoop(w, p);
        }
        boolean done = false;
        StdDraw.pause(1000);
        //Initializes the variable used for HUD. mouseTile is the tile the mouse is on
        TETile mouseTile = Tileset.NOTHING;
        int currentMouseX = (int) StdDraw.mouseX();
        int currentMouseY = (int) StdDraw.mouseY();
        boolean isLastKeyColon = false;
        FloorBFS fBFS = null;
        HashMap<Integer, ArrayList<Integer>> hLightedPath = null;
        WorldInteract.reRender(w, p, mouseTile);
        while (!done) {
            TETile tempTile;
            currentMouseX = (int) StdDraw.mouseX();
            currentMouseY = (int) StdDraw.mouseY();
            if (currentMouseY > 49) {
                tempTile = Tileset.NOTHING;
            } else {
                tempTile = w.world[currentMouseX][currentMouseY];
            }
            //If the mouse is on a new tile type, mouseTile is updated and the world is rendered again
            if (tempTile.id() != mouseTile.id()) {
                mouseTile = tempTile;
                WorldInteract.reRender(w, p, mouseTile);
            }
            if (StdDraw.isMousePressed() && (w.world[currentMouseX][currentMouseY].id() == 2 || w.world[currentMouseX][currentMouseY].id() == 6) && fBFS == null) {
                FloorBFS bfs = new FloorBFS(w.flrTiles, p.playerX, p.playerY, currentMouseX, currentMouseY);
                fBFS = bfs;
                hLightedPath = highlightPath(w, bfs);
                WorldInteract.reRender(w, p, mouseTile);
            } else if (StdDraw.isMousePressed() && fBFS != null && (currentMouseX == fBFS.endTile.tileX && currentMouseY == fBFS.endTile.tileY)) {
                walkPath(w, p, mouseTile, movesPerformed, touchedFlower, fBFS);
            }
            //player movement.
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == ':') {
                    isLastKeyColon = true;
                }
            }
            if (StdDraw.isKeyPressed(KeyEvent.getExtendedKeyCodeForChar('w'))) {
                isLastKeyColon = false;
                isLastKeyU = false;
                savedInputs += "w";
                int startY = p.playerY;
                p.moveUp();
                if (startY + 1 == p.playerY) {
                    movesPerformed.addFirst('w');
                    touchedFlower.addFirst(p.touchedFlower);
                }
                if (hLightedPath != null && fBFS != null) { // needs to recalculate best
                    dehighlightPath(w, hLightedPath, fBFS);
                    fBFS = null;
                }
                WorldInteract.reRender(w, p, mouseTile);
            } else if (StdDraw.isKeyPressed(KeyEvent.getExtendedKeyCodeForChar('d'))) {
                isLastKeyColon = false;
                isLastKeyU = false;
                savedInputs += "d";
                int startX = p.playerX;
                p.moveRight();
                if (startX + 1 == p.playerX) {
                    movesPerformed.addFirst('d');
                    touchedFlower.addFirst(p.touchedFlower);
                }
                if (hLightedPath != null && fBFS != null) {
                    dehighlightPath(w, hLightedPath, fBFS);
                    fBFS = null;
                }
                WorldInteract.reRender(w, p, mouseTile);
            } else if (StdDraw.isKeyPressed(KeyEvent.getExtendedKeyCodeForChar('s'))) {
                isLastKeyColon = false;
                isLastKeyU = false;
                savedInputs += "s";
                int startY = p.playerY;
                p.moveDown();
                if (startY - 1 == p.playerY) {
                    movesPerformed.addFirst('s');
                    touchedFlower.addFirst(p.touchedFlower);
                }
                if (hLightedPath != null && fBFS != null) {
                    dehighlightPath(w, hLightedPath, fBFS);
                    fBFS = null;
                }
                WorldInteract.reRender(w, p, mouseTile);
            } else if (StdDraw.isKeyPressed(KeyEvent.getExtendedKeyCodeForChar('a'))) {
                isLastKeyColon = false;
                isLastKeyU = false;
                savedInputs += "a";
                int startX = p.playerX;
                p.moveLeft();
                if (startX - 1 == p.playerX) {
                    movesPerformed.addFirst('a');
                    touchedFlower.addFirst(p.touchedFlower);
                }
                if (hLightedPath != null && fBFS != null) {
                    dehighlightPath(w, hLightedPath, fBFS);
                    fBFS = null;
                }
                WorldInteract.reRender(w, p, mouseTile);
            } else if (StdDraw.isKeyPressed(KeyEvent.getExtendedKeyCodeForChar('q')) && isLastKeyColon) {
                Out out = new Out("save.txt");
                out.println(loadedInputs + savedInputs);
                System.exit(0);
            } else if (StdDraw.isKeyPressed(KeyEvent.getExtendedKeyCodeForChar('u'))) {
                isLastKeyColon = false;
                isLastKeyU = true;
                Character undoAction = movesPerformed.removeFirst();
                Boolean placeFlower = touchedFlower.removeFirst();
                if (undoAction != null) {
                    redoAction = undoAction;
                    if (redoAction.equals('w')) {
                        savedInputs += "s";
                        p.moveDown();
                        if (placeFlower != null && placeFlower) {
                            w.world[p.playerX][p.playerY + 1] = Tileset.FLOWER;
                            p.score--;
                        }
                    } else if (redoAction.equals('d')) {
                        savedInputs += "a";
                        p.moveLeft();
                        if (placeFlower != null && placeFlower) {
                            w.world[p.playerX + 1][p.playerY] = Tileset.FLOWER;
                            p.score--;
                        }
                    } else if (redoAction.equals('s')) {
                        savedInputs += "w";
                        p.moveUp();
                        if (placeFlower != null && placeFlower) {
                            w.world[p.playerX][p.playerY - 1] = Tileset.FLOWER;
                            p.score--;
                        }
                    } else if (redoAction.equals('a')) {
                        savedInputs += "d";
                        p.moveRight();
                        if (placeFlower != null && placeFlower) {
                            w.world[p.playerX - 1][p.playerY] = Tileset.FLOWER;
                            p.score--;
                        }
                    }
                }
                reRender(w, p, mouseTile);
                StdDraw.pause(150);
            } else if (StdDraw.isKeyPressed(KeyEvent.getExtendedKeyCodeForChar('r')) && isLastKeyU) {
                isLastKeyU = false;
                isLastKeyColon = false;
                if (redoAction != null) {
                    savedInputs += redoAction;
                    if (redoAction.equals('w')) {
                        p.moveUp();
                    } else if (redoAction.equals('d')) {
                        p.moveRight();
                    } else if (redoAction.equals('s')) {
                        p.moveDown();
                    } else if (redoAction.equals('a')) {
                        p.moveLeft();
                    }
                }
                reRender(w, p, mouseTile);
            } else if (StdDraw.isKeyPressed(KeyEvent.getExtendedKeyCodeForChar('p'))) {
                isLastKeyColon = false;
                isLastKeyU = false;
                fBFS = null;
                hLightedPath = null;
                movesPerformed = new movementDeque();
                touchedFlower = new movementDeque();
                w = new World(50, 50, w.seed, w.ter);
                w.worldGeneration();
                p = new Player(w);
                StdDraw.clear(new Color(0, 0, 0));
                w.ter.drawTiles(w.world);
                StdDraw.show();
                savedInputs = "n" + w.seed + "s";
                StdDraw.pause(10);
            }
            if (p.score == w.coinCount) {
                done = true;
                w = mainMenu();
                p = new Player(w);
                gameLoop(w, p);
            }
        }
    }

    static void savedGameLoop(World w, Player p) {
        boolean done = false;
        StdDraw.pause(1000);
        loadedInputsCopies = loadedInputs;
        //Initializes the variable used for HUD. mouseTile is the tile the mouse is on
        while (!loadedInputsCopies.isEmpty()) {
            char key = loadedInputsCopies.charAt(0);
            loadedInputsCopies = loadedInputsCopies.substring(1);
            //player movement.
            if (key == 'w') {
                p.moveUp();
            } else if (key == 'd') {
                p.moveRight();
            } else if (key == 's') {
                p.moveDown();
            } else if (key == 'a') {
                p.moveLeft();
            }
        }
    }
    // returns the cords of all floor tiles that were highlighted
    static HashMap<Integer, ArrayList<Integer>> highlightPath(World w, FloorBFS bfs) {
        HashMap<Integer, ArrayList<Integer>> pathCords = new HashMap<>(); // key is x and value is y of highlighted floors
        String path = bfs.pathTo.get(bfs.endTile) + ""; // potential problem from path being connected to pathTo string
        int currentX = bfs.startTile.tileX;
        int currentY = bfs.startTile.tileY;
        ArrayList<Integer> holderY = new ArrayList<>();
        holderY.add(currentY);
        w.world[currentX][currentY] = Tileset.HAVATAR;
        while (!path.isEmpty()) {
            char move = path.charAt(0);
            path = path.substring(1);
            if (move == 'w') {
                currentY++;
            } else if (move == 'd') {
                currentX++;
            } else if (move == 's') {
                currentY--;
            } else if (move == 'a') {
                currentX--;
            }
            if(w.world[currentX][currentY].id() == 6) { //if the floor tile is a flower
                w.world[currentX][currentY] = Tileset.HFLOWER;
            } else {
                w.world[currentX][currentY] = Tileset.HFLOOR;
            }
            if (pathCords.containsKey(currentX)) {
                pathCords.get(currentX).add(currentY);
            } else {
                ArrayList<Integer> tempHolder = new ArrayList<>();
                tempHolder.add(currentY);
                pathCords.put(currentX, tempHolder);
            }
        }
        return pathCords;
    }
    static void dehighlightPath(World w, HashMap<Integer, ArrayList<Integer>> path, FloorBFS bfs) {
        for (int x : path.keySet()) {
            for (int y: path.get(x)) {
                w.world[x][y] = Tileset.FLOOR;
            }
        }
        path = null;
    }
    static void walkPath(World w, Player p, TETile mouseTile, Deque movesPerformed, Deque touchedFlower, FloorBFS bfs) {
        String path = bfs.pathTo.get(bfs.endTile) + "";
        while (!path.isEmpty()) {
            char key = path.charAt(0);
            path = path.substring(1);
            if (key == 'w') {
                savedInputs += "w";
                movesPerformed.addFirst('w');
                p.moveUp();
                touchedFlower.addFirst(p.touchedFlower);
                WorldInteract.reRender(w, p, mouseTile);
            } else if (key == 'd') {
                savedInputs += "d";
                movesPerformed.addFirst('d');
                p.moveRight();
                touchedFlower.addFirst(p.touchedFlower);
                WorldInteract.reRender(w, p, mouseTile);
            } else if (key == 's') {
                savedInputs += "s";
                movesPerformed.addFirst('s');
                p.moveDown();
                touchedFlower.addFirst(p.touchedFlower);
                WorldInteract.reRender(w, p, mouseTile);
            } else if (key == 'a') {
                savedInputs += "a";
                movesPerformed.addFirst('a');
                p.moveLeft();
                touchedFlower.addFirst(p.touchedFlower);
                WorldInteract.reRender(w, p, mouseTile);
            }
        }
    }
    static void reRender(World w, Player p, TETile mouseTile) {
        StdDraw.clear(new Color(0, 0, 0));
        w.ter.drawTiles(w.world);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(10, 55, mouseTile.description());
        StdDraw.text(25, 55, "Click U to undo, R to redo, P to restart");
        StdDraw.text(40, 55, "Score: " + p.score);
        StdDraw.show();
        StdDraw.pause(150);
    }
}
