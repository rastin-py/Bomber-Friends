package back;

import java.awt.*;

public abstract class Bomber extends Mover {
    private int bombRadius;
    private int bombDamage;
    private final int BOMB_COOL_DOWN;
    private final int BOMB_DELAY = 3000;
    private int bombsAvailable = 4;

    public Bomber(int xCoordinate, int yCoordinate, int unitSize, int speed, int health, int bombDamage, int bombRadius, int bombCoolDown, Image moving_up, Image moving_down, Image moving_left, Image moving_right, Image moving_idle) {
        super(xCoordinate, yCoordinate, unitSize, speed, health, moving_up, moving_down, moving_left, moving_right, moving_idle);
        this.bombDamage = bombDamage;
        this.bombRadius = bombRadius;
        this.BOMB_COOL_DOWN = bombCoolDown;
    }

    public void plantBomb() {
        if (bombsAvailable > 0) {
            bombsAvailable--;
            int x, y;
            if ((this.getXCoordinate() + getUnitSize()) % getUnitSize() > getUnitSize() / 2) {
                x = (getXCoordinate() + getUnitSize()) / getUnitSize() * getUnitSize();
            } else
                x = getXCoordinate() - (getXCoordinate() + getUnitSize()) % getUnitSize();
            if ((getYCoordinate() + getUnitSize()) % getUnitSize() > getUnitSize() / 2) {
                y = (getYCoordinate() + getUnitSize()) / getUnitSize() * getUnitSize();
            } else
                y = getYCoordinate() - (getYCoordinate() + getUnitSize()) % getUnitSize();
            Bomb bomb = new Bomb(x, y, getUnitSize(), getBombRadius(), getBombDamage(), this);
            bomb.explode(BOMB_DELAY);
            new Thread(() -> {
                try {
                    Thread.sleep(BOMB_COOL_DOWN);
                    bombsAvailable++;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    public int getBombRadius() {
        return bombRadius;
    }

    public int getBombDamage() {
        return bombDamage;
    }

    public int getBombsAvailable() {
        return bombsAvailable;
    }

    public void setBombRadius(int bombRadius) {
        this.bombRadius = bombRadius;
    }

    public void setBombDamage(int bombDamage) {
        this.bombDamage = bombDamage;
    }

    public int getBOMB_DELAY() {
        return BOMB_DELAY;
    }

    public int getBOMB_COOL_DOWN() {
        return BOMB_COOL_DOWN;
    }
}
