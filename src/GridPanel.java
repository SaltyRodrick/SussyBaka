import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.util.List;

/**
 * Class for displaying the result of the algorithm
 */
class GridPanel extends JPanel {
    private final SDSAlgorithm sds;
    private int[][] grid;
    private List<SDSAgent> agents;
    private double cellSize;
    private final int windowWidth = 800;
    private final int windowHeight = 800;

    public GridPanel(int[][] grid, List<SDSAgent> agents, SDSAlgorithm sds) {
        this.grid = grid;
        this.agents = agents;
        this.sds = sds;
        setPreferredSize(new Dimension(windowWidth, windowHeight));

        // Create a button to trigger the dot graph display
        JButton showGraphButton = new JButton("Show Dot Graph");
        showGraphButton.setBounds(10, 10, 150, 30);
        showGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the dot graph when button is clicked
                showDotGraph();
            }
        });

        // Add the button to the panel
        this.add(showGraphButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        double side = Math.min(getWidth(), getHeight());
        cellSize = side / grid.length;

        // Draw grid
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                g2d.setColor(grid[y][x] == 1 ? Color.BLACK : Color.WHITE);
                Rectangle2D rect = new Rectangle2D.Double(x * cellSize, y * cellSize, cellSize, cellSize);
                g2d.fill(rect);
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.draw(rect);
            }
        }

        // Draw agents
        for (SDSAgent agent : agents) {
            Color color = agent.isActive() ? new Color(0, 255, 0, 100) : new Color(0, 0, 255, 100);
            g2d.setColor(color);
            Ellipse2D oval = new Ellipse2D.Double(agent.getX() * cellSize, agent.getY() * cellSize, cellSize, cellSize);
            g2d.fill(oval);
        }
    }

    private void showDotGraph() {
        JFrame graphFrame = new JFrame("Dot Graph");
        graphFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        graphFrame.setSize(600, 400);
        graphFrame.add(new DotGraphPanel(sds.getActiveAgentCounts(), sds.getAgents().size()));
        graphFrame.setVisible(true);
    }
}