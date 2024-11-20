package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

public class Entity {
    //PLAYER CONTROL
    int currX;
    int currY;

    public final World world;
    public final TETile[][] worldArray;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 50;
    Random random = new Random(100);

    public Entity(World world) {
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
}
