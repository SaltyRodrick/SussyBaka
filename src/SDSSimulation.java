import javax.swing.*;

/** Encapsulates the necessary instances needed to run the SDS algorithm, such as the generated CA grid, SDS agents, and the algorithm itself. */
public class SDSSimulation {
    public static void startSimulation(int gridSize, int sdsIterations, String diffusionType, int subgridMultiplier) {
        int states = 2;
        int caIterations = 100;
        int numAgents = (gridSize * gridSize * subgridMultiplier * subgridMultiplier) / 4;
        int[][] pattern;

        SymmetricalCA ca = new SymmetricalCA(gridSize, gridSize, states);
        pattern = ca.generateSymmetricalPattern(caIterations);

        int[][] combinedPattern = assembleSubgridPattern(pattern, subgridMultiplier);

        SDSAlgorithm sds = new SDSAlgorithm(combinedPattern, numAgents, sdsIterations, diffusionType);
        sds.run();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("SDS Simulation with Symmetrical CA Pattern");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new GridPanel(combinedPattern, sds.getAgents(), sds));
            frame.pack();
            frame.setVisible(true);
        });
    }

    /**
     * Assembles a larger pattern grid by replicating the provided CA pattern in a grid layout
     * defined by the subgridMultiplier.
     * @param pattern The CA-generated pattern to be replicated.
     * @param subgridMultiplier Determines the number of subgrids (subgridMultiplier^2).
     * @return The combined pattern with separators of zeros.
     */
    private static int[][] assembleSubgridPattern(int[][] pattern, int subgridMultiplier) {
        int patternSize = pattern.length;
        int combinedSize = Math.abs(subgridMultiplier) * patternSize + (Math.abs(subgridMultiplier) - 1);
        int[][] combinedPattern = new int[combinedSize][combinedSize];

        if (subgridMultiplier < 0) {
            subgridMultiplier = Math.abs(subgridMultiplier);

            for (int row = 0; row < subgridMultiplier; row++) {
                for (int col = 0; col < subgridMultiplier; col++) {
                    if (col == 0) {
                        int startX = row * (patternSize + 1);
                        int startY = col * (patternSize + 1);

                        for (int i = 0; i < patternSize; i++) {
                            for (int j = 0; j < patternSize; j++) {
                                combinedPattern[startX + i][startY + j] = pattern[i][j];
                            }
                        }
                    } else {
                        int startX = row * (patternSize + 1);
                        int startY = col * (patternSize + 1);
                        for (int i = 0; i < patternSize; i++) {
                            for (int j = 0; j < patternSize; j++) {
                                combinedPattern[startX + i][startY + j] = 0;
                            }
                        }
                    }
                }
            }
        } else {
            for (int row = 0; row < subgridMultiplier; row++) {
                for (int col = 0; col < subgridMultiplier; col++) {
                    int startX = row * (patternSize + 1);
                    int startY = col * (patternSize + 1);

                    for (int i = 0; i < patternSize; i++) {
                        for (int j = 0; j < patternSize; j++) {
                            combinedPattern[startX + i][startY + j] = pattern[i][j];
                        }
                    }
                }
            }
        }

        return combinedPattern;
    }
}
