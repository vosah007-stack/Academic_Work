package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Main {
    public static void main(String[] args) {
        World map = WorldInteract.mainMenu(); //creates and runs the main menu
        Player player = new Player(map);
        WorldInteract.gameLoop(map, player);
    }
}
