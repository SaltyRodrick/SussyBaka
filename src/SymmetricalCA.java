import java.util.Random;

/**
 * Class consisting of simple algorithm for generating four-fold symmetrical grids.
 */
public class SymmetricalCA {
    protected int width, height;
    protected int[][] grid;
    protected int states;

    public SymmetricalCA(int width, int height, int states) {
        if (width % 2 == 0 || height % 2 == 0) {
            throw new IllegalArgumentException("Width and height must be even for 4-fold symmetry.");
        }
        this.width = width;
        this.height = height;
        this.states = states;
        this.grid = new int[height][width];
    }

    protected int[][] generateSymmetricalPattern(int iterations) {
        Random random = new Random();

        for (int y = 0; y < height / 2; y++) {
            for (int x = 0; x < width / 2; x++) {
                grid[y][x] = random.nextInt(states);
                grid[y][width - x - 1] = grid[y][x];
                grid[height - y - 1][x] = grid[y][x];
                grid[height - y - 1][width - x - 1] = grid[y][x];
            }
        }

        for (int i = 0; i < iterations; i++) {
            grid = applyCARules();
        }
        //grid = new int[][]{{1, 1, 0, 1, 1}, {0, 0, 0, 0, 0}, {1, 1, 0, 1, 1}, {0, 0, 0, 0, 0}, {1, 1, 0, 1, 1}};
        return grid;
    }

    protected int[][] applyCARules() {
        int[][] newGrid = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int sum = getMooreNeighborhoodSum(x, y);

                // Cell toggles state if sum of neighbors is even
                newGrid[y][x] = (sum % states == 0) ? (grid[y][x] + 1) % states : grid[y][x];
            }
        }

        for (int y = 0; y < height / 2; y++) {
            for (int x = 0; x < width / 2; x++) {
                newGrid[height - y - 1][x] = newGrid[y][x];
                newGrid[y][width - x - 1] = newGrid[y][x];
                newGrid[height - y - 1][width - x - 1] = newGrid[y][x];
            }
        }

        return newGrid;
    }

    protected int getMooreNeighborhoodSum(int x, int y) {
        int sum = 0;
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue;
                int nx = (x + dx + width) % width;
                int ny = (y + dy + height) % height;
                sum += grid[ny][nx];
            }
        }
        return sum;
    }
}
