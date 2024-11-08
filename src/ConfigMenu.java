import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main class serving the purpose of an entry point for the program.
 * Contains very simple UI to input the base variables 'gridSize' and 'sdsIterations' of the algorithm.
 */
public class ConfigMenu extends JFrame {
    private JTextField gridSizeField;
    private JTextField sdsIterationsField;
    private JComboBox<String> diffusionTypeCombo;
    private JTextField gridMultiplier;

    public ConfigMenu() {
        setTitle("SDS Simulation Configuration");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        JLabel gridSizeLabel = new JLabel("Grid Size:");
        gridSizeField = new JTextField();
        add(gridSizeLabel);
        add(gridSizeField);

        JLabel sdsIterationsLabel = new JLabel("SDS Iterations:");
        sdsIterationsField = new JTextField();
        add(sdsIterationsLabel);
        add(sdsIterationsField);

        JLabel diffusionTypeLabel = new JLabel("Diffusion Type:");
        diffusionTypeCombo = new JComboBox<>(new String[]{"Passive", "Context-Sensitive"});
        add(diffusionTypeLabel);
        add(diffusionTypeCombo);

        JLabel gridMultiplierLabel = new JLabel("Grid multiplier:");
        gridMultiplier = new JTextField();
        add(gridMultiplierLabel);
        add(gridMultiplier);

        JButton runButton = new JButton("Start Simulation");
        add(new JLabel()); // Empty cell for alignment
        add(runButton);

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSimulation();
            }
        });

        setVisible(true);
    }

    /**
     * Checks for invalid inputs and, if successful, starts the simulation.
     */
    private void startSimulation() {
        try {
            int gridSize = Integer.parseInt(gridSizeField.getText());
            int sdsIterations = Integer.parseInt(sdsIterationsField.getText());
            int gridMultiplierA = Integer.parseInt(gridMultiplier.getText());

            if (gridSize <= 0 || sdsIterations <= 0) {
                JOptionPane.showMessageDialog(this, "Values must be greater than zero.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (gridSize % 2 == 0) {
                JOptionPane.showMessageDialog(this, "Grid Size must be an odd number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String diffusionType = (String) diffusionTypeCombo.getSelectedItem();

            SwingUtilities.invokeLater(() -> {
                SDSSimulation.startSimulation(gridSize, sdsIterations, diffusionType, gridMultiplierA);
            });
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter numeric values only.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new ConfigMenu();
    }
}
