package core;

import core.World;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

public class Player {

    //PLAYER CONTROL
    int currX;
    int currY;

    private final World world;
    private final TETile[][] worldArray;
    private static final int WIDTH = 100;
    private static final int HEIGHT = 50;


    private final Random random = new Random(100);

    public Player(World world) {
        this.world = world;
        this.worldArray = world.world;
    }

    public void spawn() {
        int x; int y;
        x = random.nextInt(96);
        y = random.nextInt(46);

        while (worldArray[x][y] != Tileset.FLOOR) {
            x = random.nextInt(96);
            y = random.nextInt(46);
        }
        worldArray[x][y] = Tileset.AVATAR;
        currX = x;
        currY = y;
        world.render();
    }

    public void moveUp() {
        if (currY + 1 < HEIGHT && worldArray[currX][currY + 1] == Tileset.FLOOR) {
            worldArray[currX][currY] = Tileset.FLOOR;
            worldArray[currX][currY + 1] = Tileset.AVATAR;
            currY++;
        }
        world.render();
    }

}
