import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class containing the algorithm SDS
 * Based on the article of: Javaheri Javid, M.A., Alghamdi, W., Ursyn, A. et al. Swarmic approach for symmetry detection of cellular automata behaviour. Soft Comput 21, 5585–5599 (2017).
 * Available at: https://doi.org/10.1007/s00500-017-2752-y
 */
class SDSAlgorithm {
    private List<SDSAgent> agents;
    private int[][] grid;
    private int gridWidth, gridHeight;
    private int iterations;

    /**
     * Constructor, includes Step 1 of the algorithm, where agents are initialized with random hypotheses
     */
    public SDSAlgorithm(int[][] grid, int numAgents, int iterations) {
        this.grid = grid;
        this.gridWidth = grid.length;
        this.gridHeight = grid[0].length;
        this.iterations = iterations;
        agents = new ArrayList<>();

        for (int i = 0; i < numAgents; i++) {
            int x = new Random().nextInt(gridWidth);
            int y = new Random().nextInt(gridHeight);
            agents.add(new SDSAgent(x, y));
        }
    }

    /**
     * Key method running the whole algorithm
     */
    public void run() {
        for (int i = 0; i < iterations; i++) {
            for (SDSAgent agent : agents) {
                testHypothesis(agent, grid, gridWidth, gridHeight);
            }
            diffusionPhase(agents);
        }
    }

    public List<SDSAgent> getAgents() {
        return agents;
    }

    /**
     *  Step 2 of the algorithm, testing the hypothesis that the currently chosen cell is center of symmetry
     */
    private void testHypothesis(SDSAgent agent, int[][] grid, int width, int height) {
        int x = agent.getX();
        int y = agent.getY();
        int dx = new Random().nextInt(- gridWidth + 1, gridWidth);
        int dy = new Random().nextInt(- gridHeight + 1, gridHeight);

        int xLeft = (x - dx + width) % width;
        int xRight = (x + dx + width) % width;
        int yTop = (y - dy + height) % height;
        int yBottom = (y + dy + height) % height;

        if (grid[yTop][xLeft] == grid[yBottom][xRight]) {
            agent.setActive(true);
        } else {
            agent.setActive(false);
        }
    }

    /**
     *  Step 3 of the algorithm, propagating the hypothesis to other agents
     */
    private void diffusionPhase(List<SDSAgent> agents) {
        for (SDSAgent agent : agents) {
            if (!agent.isActive()) {
                SDSAgent randomAgent = agents.get(new Random().nextInt(agents.size()));
                if (randomAgent.isActive()) {
                    agent.setHypothesis(randomAgent.getX(), randomAgent.getY());
                } else {
                    agent.setHypothesis(new Random().nextInt(gridWidth), new Random().nextInt(gridHeight));
                }
            }
        }
    }
}