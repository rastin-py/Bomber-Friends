package back;


import javax.swing.*;
import java.awt.*;

public abstract class Mover extends Mortal {
    private final Image MOVING_UP;
    private final Image MOVING_DOWN;
    private final Image MOVING_LEFT;
    private final Image MOVING_RIGHT;
    private final Image MOVING_IDLE;
    private final Image DEAD = new ImageIcon("rsc\\death.gif").getImage();
    private int speed;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT, IDLE, DEAD
    }

    private Direction currentState = Direction.IDLE;

    public Mover(int xCoordinate, int yCoordinate, int unitSize, int speed, int health, Image moving_up, Image moving_down, Image moving_left, Image moving_right, Image moving_idle) {
        super(xCoordinate, yCoordinate, unitSize, health);
        this.speed = speed;
        MOVING_UP = moving_up;
        MOVING_DOWN = moving_down;
        MOVING_LEFT = moving_left;
        MOVING_RIGHT = moving_right;
        MOVING_IDLE = moving_idle;
    }


    public void move(Direction direction) {
        int xDestination = getXCoordinate();
        int yDestination = getYCoordinate();
        switch (direction) {
            case UP -> yDestination = getYCoordinate() - getSpeed();
            case DOWN -> yDestination = getYCoordinate() + getSpeed();

            case LEFT -> xDestination = getXCoordinate() - getSpeed();

            case RIGHT -> xDestination = getXCoordinate() + getSpeed();

            case IDLE -> {
                this.currentState = direction;
                return;
            }
        }
        if (isMovementBlockedByWall(xDestination, yDestination)) {
            return;
        }

        setYCoordinate(yDestination);
        setXCoordinate(xDestination);
        this.currentState = direction;
    }

    public void draw(Graphics g) {
        int x, y;
        g.setColor(Color.yellow);
        if ((getXCoordinate() + getUnitSize()) % getUnitSize() > getUnitSize() / 2) {
            x = (getXCoordinate() + getUnitSize()) / getUnitSize() * getUnitSize();
        } else
            x = getXCoordinate() - (getXCoordinate() + getUnitSize()) % getUnitSize();
        if ((getYCoordinate() + getUnitSize()) % getUnitSize() > getUnitSize() / 2) {
            y = (getYCoordinate() + getUnitSize()) / getUnitSize() * getUnitSize();
        } else
            y = getYCoordinate() - (getYCoordinate() + getUnitSize()) % getUnitSize();
        g.fillRect(x, y, getUnitSize(), getUnitSize());
        if (0 < getHealth()) {
            short healthPercentage = (short) (getHealth() * 100 / getMAX_HEALTH());
            super.draw(g, healthPercentage);
        }
        switch (getCurrentState()) {
            case UP -> g.drawImage(MOVING_UP, getXCoordinate(), getYCoordinate(), getUnitSize(), getUnitSize(), null);
            case DOWN ->
                    g.drawImage(MOVING_DOWN, getXCoordinate(), getYCoordinate(), getUnitSize(), getUnitSize(), null);

            case LEFT ->
                    g.drawImage(MOVING_LEFT, getXCoordinate(), getYCoordinate(), getUnitSize(), getUnitSize(), null);

            case RIGHT ->
                    g.drawImage(MOVING_RIGHT, getXCoordinate(), getYCoordinate(), getUnitSize(), getUnitSize(), null);

            case IDLE ->
                    g.drawImage(MOVING_IDLE, getXCoordinate(), getYCoordinate(), getUnitSize(), getUnitSize(), null);

            case DEAD -> g.drawImage(DEAD, getXCoordinate(), getYCoordinate(), getUnitSize(), getUnitSize(), null);

        }
    }

    public void getHit(Bomb bomb) {
        setHealth(getHealth() - bomb.getDAMAGE());
        bomb.getHitMortals().add(this);
        if (getHealth() <= 0) {
            destroy();
        }
    }

    public Direction getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Direction currentState) {
        this.currentState = currentState;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
