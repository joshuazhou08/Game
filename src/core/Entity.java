package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

public class Entity {
    //PLAYER CONTROL
    public int currX;
    public int currY;
    public int hp;

    public final World world;
    public final TETile[][] worldArray;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 50;

    Random random = new Random(100);
    private final TETile avatar;

    public Entity(World world, TETile avatar, int hp) {
        this.world = world;
        this.worldArray = world.world;
        this.avatar = avatar;
        this.hp = hp;
        spawn();
    }

    public void spawn() {
        int x; int y;
        x = random.nextInt(96);
        y = random.nextInt(46);

        while (worldArray[x][y] != Tileset.FLOOR) {
            x = random.nextInt(96);
            y = random.nextInt(46);
        }
        worldArray[x][y] = avatar;
        currX = x;
        currY = y;
        world.render();
    }

    public void moveUp() {
        if (currY + 1 < HEIGHT && worldArray[currX][currY + 1] == Tileset.FLOOR) {
            worldArray[currX][currY] = Tileset.FLOOR;
            worldArray[currX][currY + 1] = avatar;
            currY++;
        }
        world.render();
    }

    public void moveDown() {
        if (currY - 1 > 0 && worldArray[currX][currY - 1] == Tileset.FLOOR) {
            worldArray[currX][currY] = Tileset.FLOOR;
            worldArray[currX][currY - 1] = avatar;
            currY--;
        }
        world.render();
    }

    public void moveRight() {
        if (currX + 1 < WIDTH && worldArray[currX + 1][currY] == Tileset.FLOOR) {
            worldArray[currX][currY] = Tileset.FLOOR;
            worldArray[currX + 1][currY] = avatar;
            currX++;
        }
        world.render();
    }

    public void moveLeft() {
        if (currX - 1 > 0 && worldArray[currX - 1][currY] == Tileset.FLOOR) {
            worldArray[currX][currY] = Tileset.FLOOR;
            worldArray[currX - 1][currY] = avatar;
            currX--;
        }
        world.render();
    }
}
