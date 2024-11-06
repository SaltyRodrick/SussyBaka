import java.util.Random;

public class LocalSymmetryCA extends SymmetricalCA {

    private int subgridCount;

    public LocalSymmetryCA(int width, int height, int states, int subgridCount) {
        super(width, height, states);
        if (subgridCount <= 0 || (int) Math.sqrt(subgridCount) != Math.sqrt(subgridCount)) {
            throw new IllegalArgumentException("Subgrid count must be a perfect square.");
        }
        this.subgridCount = subgridCount;
    }

    public int[][] generateLocalSymmetryGrid() {
        int subgridSideLength = (int) Math.sqrt(subgridCount);
        int subgridWidth = (width - 1) / subgridSideLength;
        int subgridHeight = (height - 1) / subgridSideLength;

        int totalWidth = subgridSideLength * (subgridWidth + 1);
        int totalHeight = subgridSideLength * (subgridHeight + 1);

        // Initialize the grid for the "grid of grids"
        int[][] largeGrid = new int[totalHeight][totalWidth];

        // Generate subgrids and place them in the large grid
        for (int y = 0; y < subgridSideLength; y++) {
            for (int x = 0; x < subgridSideLength; x++) {
                int offsetX = x * (subgridWidth + 1);
                int offsetY = y * (subgridHeight + 1);

                // Create a subgrid with four-fold symmetry
                SymmetricalCA subgridCA = new SymmetricalCA(subgridWidth, subgridHeight, states);
                int[][] subgrid = subgridCA.generateSymmetricalPattern(0);

                // Place the subgrid in the large grid with an extra tile space between them
                for (int subY = 0; subY < subgridHeight; subY++) {
                    for (int subX = 0; subX < subgridWidth; subX++) {
                        largeGrid[offsetY + subY][offsetX + subX] = subgrid[subY][subX];
                    }
                }
            }
        }

        return largeGrid;
    }

    public static void main(String[] args) {
        LocalSymmetryCA localSymmetryCA = new LocalSymmetryCA(15, 15, 2, 4);  // 4 subgrids, 2 states
        int[][] resultGrid = localSymmetryCA.generateLocalSymmetryGrid();

        // Print the resulting grid
        for (int[] row : resultGrid) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}
