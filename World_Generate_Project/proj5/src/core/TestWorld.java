package core;

import edu.princeton.cs.algs4.StdDraw;
import org.junit.jupiter.api.Test;
import tileengine.TERenderer;

import java.awt.*;

import static com.google.common.truth.Truth.assertThat;

public class TestWorld {
    @Test
    void testMakeRectangle() {
        TERenderer t = new TERenderer();
        World testWorld = new World(50, 50, 123, t);
        TERenderer ter = new TERenderer();
        Room testRoom = new Room(0, 0, 10, 10);
        Room testRoomTwo = new Room(45, 45, 20, 20);
        testWorld.makeRoomPositive(testRoom, "Up");
        testWorld.makeRoomPositive(testRoomTwo, "Up");
        ter.initialize(50, 60);
        ter.drawTiles(testWorld.world);
        assertThat(testWorld.world[0][0].id()).isEqualTo(1);
        assertThat(testWorld.world[49][46].id()).isEqualTo(1);

    }
    @Test
    void testMakeRootRoom() {
        TERenderer t = new TERenderer();
        World testWorld = new World(50, 50, 123, t);
        testWorld.makeRootRoom();
    }
    @Test
    void testAddNeighbors() {
        TERenderer t = new TERenderer();
        World testWorld = new World(50, 60, 300, t);//error to look at seed 50. Nice seed 187402749 Interesting seed:4567
        Room test1 = new Room(25, 25, 5, 5);
        testWorld.makeRoomPositive(test1, "Up");
        testWorld.addNeighbors(test1, 1);
        TERenderer ter = new TERenderer();
        ter.initialize(50, 60);
        StdDraw.clear(new Color(0, 0, 0));
        ter.drawTiles(testWorld.world);
        StdDraw.show();
        StdDraw.pause(10);
    }

    public static void main(String[] args)
    {
        TERenderer t = new TERenderer();
        World testWorld = new World(50, 60, (int) 5139063412683214690L, t); //error to look at seed 6969. Nice seed 187402749 Interesting seed:4567
        //Room test1 = new Room(25, 25, 5, 5);
        //testWorld.makeRoomPositive(test1, "Up");
        //testWorld.addNeighbors(test1, 1);
        testWorld.worldGeneration();
        TERenderer ter = new TERenderer();
        ter.initialize(50, 60);
        StdDraw.clear(new Color(0, 0, 0));
        ter.drawTiles(testWorld.world);
        StdDraw.show();
        StdDraw.pause(10);
    }


}
