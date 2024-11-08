import javax.swing.*;
import java.awt.*;
import java.util.List;

class DotGraphPanel extends JPanel {
    private int[] data;
    private int numberOfAgents;

    public DotGraphPanel(int[] data, int numberOfAgents) {
        this.data = data;
        this.numberOfAgents = numberOfAgents;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(Color.WHITE);
        g.setColor(Color.BLACK);

        int graphHeight = getHeight();
        int graphWidth = getWidth();
        int margin = 30;
        g.drawLine(margin, graphHeight - margin, graphWidth - margin, graphHeight - margin);
        g.drawLine(margin, margin, margin, graphHeight - margin);

        g.setColor(Color.RED);
        for (int i = 0; i < data.length; i++) {
            int x = margin + i * (graphWidth - 2 * margin) / (data.length - 1);
            int y = graphHeight - margin - data[i] * (graphHeight - 2 * margin) / numberOfAgents;

            g.fillOval(x - 3, y - 3, 6, 6);
        }

        drawAverageLine(g, graphHeight, graphWidth, margin);
        drawAxesLabels(g, graphHeight, graphWidth, margin);
    }

    private void drawAverageLine(Graphics g, int graphHeight, int graphWidth, int margin) {
        int interval = data.length / 10; // Calculate intervals for 1/10 of the data
        int step = Math.max(1, interval); // Ensure at least 1 step

        // Calculate and draw the average line
        int prevX = -1, prevY = -1;
        for (int i = 0; i < data.length; i += step) {
            int sum = 0;
            int count = 0;

            // Calculate the average for the current interval
            for (int j = i; j < Math.min(i + step, data.length); j++) {
                sum += data[j];
                count++;
            }

            int average = sum / count;

            // Calculate X and Y positions for the average
            int x = margin + i * (graphWidth - 2 * margin) / (data.length - 1);
            int y = graphHeight - margin - average * (graphHeight - 2 * margin) / numberOfAgents;

            // Draw the average line
            if (prevX != -1 && prevY != -1) {
                g.setColor(Color.BLUE); // Color for the average line
                g.drawLine(prevX, prevY, x, y);
            }

            prevX = x;
            prevY = y;
        }
    }

    private void drawAxesLabels(Graphics g, int graphHeight, int graphWidth, int margin) {
        g.setColor(Color.BLACK);
        FontMetrics fm = g.getFontMetrics();
        int axisYStep = 10;

        // Adjusting Y-axis step based on the number of agents
        int yStep = numberOfAgents / 10; // We divide by 10 to show 10 labels on the Y-axis
        yStep = Math.max(1, yStep); // Ensure at least 1 label per interval

        // Draw Y-axis labels from 0 to numberOfAgents
        for (int i = 0; i <= numberOfAgents; i += yStep) {
            int yPos = graphHeight - margin - i * (graphHeight - 2 * margin) / numberOfAgents;
            g.drawString(String.valueOf(i), margin - fm.stringWidth(String.valueOf(i)) - 5, yPos + fm.getAscent() / 2);
        }

        // Ensure the last x value is drawn, even if it doesn't align with the step interval
        int xStep = Math.max(1, data.length / 10);
        int totalLabels = data.length / xStep;

        for (int i = 0; i <= totalLabels; i++) {
            int index = i * xStep;
            if (index >= data.length) {
                index = data.length - 1; // Ensure the last label is always displayed
            }

            int xPos = margin + index * (graphWidth - 2 * margin) / (data.length - 1);
            g.drawString(String.valueOf(index + 1), xPos - fm.stringWidth(String.valueOf(index + 1)) / 2, graphHeight - margin + fm.getAscent() + 5);
        }
    }
}
