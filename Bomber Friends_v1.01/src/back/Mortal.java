package back;


import java.awt.*;

public abstract class Mortal extends Entity {
    private int health;
    private final int MAX_HEALTH;

    public Mortal(int xCoordinate, int yCoordinate, int unitSize, int health) {
        super(xCoordinate, yCoordinate, unitSize);
        this.health = health;
        this.MAX_HEALTH = health;
    }

    public void draw(Graphics g, int healthPercentage) {
        g.setColor(new Color((int) (255 - (float) (healthPercentage * 2.55)), (int) (healthPercentage * 2.55), 0));
        g.fillRect(getXCoordinate(), getYCoordinate() + getUnitSize(), healthPercentage * getUnitSize() / 100, 3);
    }

    public abstract void getHit(Bomb bomb);

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        if (getHealth() <= 0) {
            destroy();
        }
    }

    public int getMAX_HEALTH() {
        return MAX_HEALTH;
    }

    public void destroy() {
        this.setMarked(true);
    }
}
