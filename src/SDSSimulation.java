import javax.swing.*;

/**
 * Encapsulates the necessary instances needed to run the SDS algorithm, such as the generated CA grid, SDS agents and the algorithm itself.
 */
public class SDSSimulation {
    public static void startSimulation(int gridSize, int sdsIterations) {
        int states = 2; // Number of states for the CA (binary states for simplicity)
        int caIterations = 100; // Number of CA iterations to generate patterns
        int numAgents = (gridSize * gridSize) / 4;

        // Generate a symmetrical CA pattern
        SymmetricalCA ca = new SymmetricalCA(gridSize, gridSize, states);
        int[][] symmetricalPattern = ca.generateSymmetricalPattern(caIterations);

        SDSAlgorithm sds = new SDSAlgorithm(symmetricalPattern, numAgents, sdsIterations);
        sds.run();

        // Window displaying the results of the algorithm
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("SDS Simulation with Symmetrical CA Pattern");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new GridPanel(symmetricalPattern, sds.getAgents()));
            frame.pack();
            frame.setVisible(true);
        });
    }
}