import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;

/**
 * Class for displaying the result of the algorithm
 */
class GridPanel extends JPanel {
    private int[][] grid;
    private List<SDSAgent> agents;
    private double cellSize;
    private final int windowWidth = 800;
    private final int windowHeight = 800;

    public GridPanel(int[][] grid, List<SDSAgent> agents) {
        this.grid = grid;
        this.agents = agents;
        setPreferredSize(new Dimension(windowWidth, windowHeight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        double side = Math.min(getWidth(), getHeight());
        cellSize = side / grid.length;

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
}