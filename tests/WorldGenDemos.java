import core.World;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class WorldGenDemos {
    public static void main(String[] args) {
        int seed = 20;
        Random random = new Random(100);

        for (int i = 0; i < 10; i++) {
            try {
                // Pause the current thread for 5000 milliseconds (5 seconds)
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                // Catch the InterruptedException that might occur if the thread is interrupted
                System.out.println("Interrupted: " + e.getMessage());
            }
            seed = random.nextInt();
            World test = new World(seed);
            test.render();
        }

        // build your own world!



    }
}
