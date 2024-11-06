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

    public ConfigMenu() {
        setTitle("SDS Simulation Configuration");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        JLabel gridSizeLabel = new JLabel("Grid Size:");
        gridSizeField = new JTextField();
        add(gridSizeLabel);
        add(gridSizeField);

        JLabel sdsIterationsLabel = new JLabel("SDS Iterations:");
        sdsIterationsField = new JTextField();
        add(sdsIterationsLabel);
        add(sdsIterationsField);

        JButton runButton = new JButton("Start Simulation");
        add(new JLabel());
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
     * Checks for invalid inputs (even 'gridSize', non-numeric values). If successful, starts the simulation.
     */
    private void startSimulation() {
        try {
            int gridSize = Integer.parseInt(gridSizeField.getText());
            int sdsIterations = Integer.parseInt(sdsIterationsField.getText());

            if (gridSize <= 0 || sdsIterations <= 0) {
                JOptionPane.showMessageDialog(this, "Values must be greater than zero.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (gridSize % 2 == 0) {
                JOptionPane.showMessageDialog(this, "Grid Size must be an even number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SwingUtilities.invokeLater(() -> {
                SDSSimulation.startSimulation(gridSize, sdsIterations);
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
