package core;

import core.World;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

public class Player extends Entity{
    public Player(World world) {
        super(world);
    }

    public void moveUp() {
        if (currY + 1 < HEIGHT && worldArray[currX][currY + 1] == Tileset.FLOOR) {
            worldArray[currX][currY] = Tileset.FLOOR;
            worldArray[currX][currY + 1] = Tileset.AVATAR;
            currY++;
        }
        world.render();
    }

    public void moveDown() {
        if (currY - 1 > 0 && worldArray[currX][currY - 1] == Tileset.FLOOR) {
            worldArray[currX][currY] = Tileset.FLOOR;
            worldArray[currX][currY - 1] = Tileset.AVATAR;
            currY--;
        }
        world.render();
    }

    public void moveRight() {
        if (currX + 1 < WIDTH && worldArray[currX + 1][currY] == Tileset.FLOOR) {
            worldArray[currX][currY] = Tileset.FLOOR;
            worldArray[currX + 1][currY] = Tileset.AVATAR;
            currX++;
        }
        world.render();
    }

    public void moveLeft() {
        if (currX - 1 > 0 && worldArray[currX - 1][currY] == Tileset.FLOOR) {
            worldArray[currX][currY] = Tileset.FLOOR;
            worldArray[currX - 1][currY] = Tileset.AVATAR;
            currX--;
        }
        world.render();
    }

}
