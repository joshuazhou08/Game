package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.*;

public class World {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 50;
    private static final int MAXROOMSIZE = 15;
    private static final int MINROOMSIZE = 5;

    public TETile[][] world;
    private final TERenderer ter = new TERenderer();

    Random random;
    private HashSet<ArrayList<Integer>> rooms;             //Hashset of {x, y} to repsent bottom left corner of rooms
    private HashMap<Integer, ArrayList<Integer>> halls;    //Maps coordinates of all hall endpoints to an index

    private int numOfRooms;


    public World(int seed) {                               //Initialize world with empty tiles to begin with
        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        random = new Random(seed);
        rooms = new HashSet<>();
        halls = new HashMap<>();
    }

    //call this to redraw the world
    public void render() {
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(world);
    }

    public void createWorld() {
        generateRooms();
        generateHalls();
    }

    public void generateRooms() {
        int num = 0;
        int x; int y; int width; int height;
        int numOfRooms = random.nextInt(35 - 24) + 25;

        while (num <= numOfRooms) {
            boolean skip = false;
            x = random.nextInt(96);
            y = random.nextInt(46);
            width = random.nextInt(MAXROOMSIZE - MINROOMSIZE + 1) + MINROOMSIZE;
            height = random.nextInt(MAXROOMSIZE - MINROOMSIZE + 1) + MINROOMSIZE;

            int k = 0;
            while (checkIntersecting(x, y, width, height)) {
                x = random.nextInt(96);
                y = random.nextInt(46);
                if (k >= 100) {
                    skip = true;
                    break;
                }
                k++;
            }
            if (skip) {
                continue;
            }

            rooms.add(new ArrayList<>(Arrays.asList(x, y)));
            createRoom(x, y, width, height);
            num++;
        }
    }

    /* To create halls, we use kruskals algorithm to create a minimum spanning tree, viewing each room's coordinates
    as a node
     */

    private void generateHalls() {
        PriorityQueue<Edge> edges = kruskals();
        WeightedQuickUnionUF set = new WeightedQuickUnionUF(numOfRooms);

        while (set.count() > 1 && !edges.isEmpty()) {
            Edge curr = edges.poll();
            if (!set.connected(curr.coord1, curr.coord2)) {
                set.union(curr.coord1, curr.coord2);
                createHall(halls.get(curr.coord1), halls.get(curr.coord2));
            }
        }
    }

    private class Edge implements Comparable<Edge> {
        int coord1;
        int coord2;
        int cost;

        public Edge(int index1, int index2) {
            this.coord1 = index1;
            this.coord2 = index2;
            this.cost = cost(halls.get(coord1), halls.get(coord2));
        }
        @Override
        public int compareTo(Edge o) {
            return this.cost - o.cost;
        }


    }
    private int cost(ArrayList<Integer> coord1, ArrayList<Integer> coord2) {
        return Math.abs(coord2.get(0) - coord1.get(0)) + Math.abs(coord2.get(1) - coord1.get(1));
    }

    private PriorityQueue<Edge> kruskals() {
        PriorityQueue<Edge> edges = new PriorityQueue<>();
        for (int i = 0; i < halls.size(); i++) {
            for (int j = i; j < halls.size(); j++) {
                edges.add(new Edge(i, j));
            }
        }
        return edges;
    }

    //FUNCTIONS FOR GRAPHICS
    private void createRoom(int x, int y, int width, int height) {
        if (x + width >= WIDTH) {
            width = WIDTH - x - 1;
        }

        if (y + height >= HEIGHT) {
            height = HEIGHT - y - 1;
        }

        for (int i = x; i <= x + width; i++) {
            for (int j = y; j <=  y + height; j++){
                if (i == x || i == x + width || j == y || j == y + height && world[i][j] != Tileset.FLOOR) {
                    world[i][j] = Tileset.WALL;
                } else {
                    world[i][j] = Tileset.FLOOR;
                }
            }

        }
        //find random coordinate to call home for hall generation
        int randX = x + random.nextInt(width - 2) + 2;
        int randY = y + random.nextInt(height - 2) + 2;

        ArrayList<Integer> coords =  new ArrayList<>(Arrays.asList(randX, randY));
        halls.put(numOfRooms, coords);
        numOfRooms++;
    }

    private void createHall(ArrayList<Integer> coord1, ArrayList<Integer> coord2) {
        int x1 = coord1.get(0);
        int y1 = coord1.get(1);

        int x2 = coord2.get(0);
        int y2 = coord2.get(1);

        int i;
        if (x2 < x1) {
            for (i = x1; i >= x2; i--) {
                world[i][y1] = Tileset.FLOOR;
                if (world[i][y1 + 1] != Tileset.FLOOR) {
                    world[i][y1 + 1] = Tileset.WALL;
                }

                if (world[i][y1 - 1] != Tileset.FLOOR) {
                    world[i][y1 - 1] = Tileset.WALL;
                }
            }
            i = i + 1;
        } else {
            for (i = x1; i <= x2; i++) {
                world[i][y1] = Tileset.FLOOR;
                if (world[i][y1 + 1] != Tileset.FLOOR) {
                    world[i][y1 + 1] = Tileset.WALL;
                }

                if (world[i][y1 - 1] != Tileset.FLOOR) {
                    world[i][y1 - 1] = Tileset.WALL;
                }
            }
            i = i - 1;
        }
        for (int j = Math.min(y2, y1); j <= Math.max(y2, y1); j++) {
            world[i][j] = Tileset.FLOOR;
            if (world[i + 1][j] != Tileset.FLOOR) {
                world[i + 1][j] = Tileset.WALL;
            }

            if (world[i - 1][j] != Tileset.FLOOR) {
                world[i - 1][j] = Tileset.WALL;
            }
        }
    }

    //Helper functions

    private boolean checkIntersecting(int x, int y, int width, int height) {
        if (x + width >= WIDTH) {
            width = WIDTH - x - 1;
        }

        if (y + height >= HEIGHT) {
            height = HEIGHT - y - 1;
        }

        for (int i = x; i <= x + width; i++) {
            for (int j = y; j <= y + height; j++) {
                if (world[i][j] != Tileset.NOTHING) {
                    return true;
                }

            }
        }
        return false;
    }
}