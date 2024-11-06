import javax.swing.*;

/** Encapsulates the necessary instances needed to run the SDS algorithm, such as the generated CA grid, SDS agents and the algorithm itself. */
public class SDSSimulation {
    public static void startSimulation(int gridSize, int sdsIterations, String diffusionType, String caGeneratorType) {
        int states = 2;
        int caIterations = 100;
        int numAgents = (gridSize * gridSize) / 4;
        int[][] pattern;

        if ("Local Symmetry".equals(caGeneratorType)) {
            LocalSymmetryCA localSymmetryCA = new LocalSymmetryCA(gridSize, gridSize, states, 9);  // 4 subgrids, 2 states
            pattern = localSymmetryCA.generateLocalSymmetryGrid();
        } else {
            SymmetricalCA ca = new SymmetricalCA(gridSize, gridSize, states);
            pattern = ca.generateSymmetricalPattern(caIterations);
        }

        SDSAlgorithm sds = new SDSAlgorithm(pattern, numAgents, sdsIterations, diffusionType);
        sds.run();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("SDS Simulation with Symmetrical CA Pattern");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new GridPanel(pattern, sds.getAgents()));
            frame.pack();
            frame.setVisible(true);
        });
    }
}