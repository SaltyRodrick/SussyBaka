/**
 * Implementation of the agent in SDS algorithm, consisiting of hypothesis coordinates (x,y) and boolean 'active' value
 */
class SDSAgent {
    private int x, y;
    private boolean active;

    public SDSAgent(int x, int y) {
        this.x = x;
        this.y = y;
        this.active = false;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isActive() { return active; }
    public void setActive(boolean state) { this.active = state; }
    public void setHypothesis(int x, int y) { this.x = x; this.y = y; }
}