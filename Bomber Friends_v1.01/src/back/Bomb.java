package back;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Bomb extends Entity {
    private final int RADIUS;
    private final int DAMAGE;
    private final Bomber BOMBER;
    private final Image PLANTED = new ImageIcon("rsc\\bomb.gif").getImage();
    private final Image CORE = new ImageIcon("rsc\\explosion-core.png").getImage();
    private final Image VERTICAL = new ImageIcon("rsc\\explosion-vertical.png").getImage();
    private final Image HORIZONTAL = new ImageIcon("rsc\\explosion-horizontal.png").getImage();
    private boolean exploded = false;
    private final Vector<Mortal> hitMortals = new Vector<>(); // list of the mortals hit by the bomb

    private int verticalExplosionX, verticalExplosionY, verticalExplosionHeight, verticalExplosionWidth;
    private int horizontalExplosionX, horizontalExplosionY, horizontalExplosionHeight, horizontalExplosionWidth;


    public Bomb(int xCoordinate, int yCoordinate, int unitSize, int radius, int damage, Bomber bomber) {
        super(xCoordinate, yCoordinate, unitSize);
        this.RADIUS = radius;
        this.DAMAGE = damage;
        BOMBER = bomber;
    }

    public void draw(Graphics g) {
        if (exploded) {
            g.drawImage(VERTICAL, verticalExplosionX, verticalExplosionY, verticalExplosionWidth, verticalExplosionHeight, null);
            g.drawImage(HORIZONTAL, horizontalExplosionX, horizontalExplosionY, horizontalExplosionWidth, horizontalExplosionHeight, null);
            g.drawImage(CORE, getXCoordinate(), getYCoordinate(), getUnitSize(), getUnitSize(), null);
        } else
            g.drawImage(PLANTED, getXCoordinate(), getYCoordinate(), getUnitSize(), getUnitSize(), null);
    }

    public void explode(int BOMB_DELAY) {
        new Thread(() -> {
            try {
                setExplosionRange();
                Thread.sleep(BOMB_DELAY);
                exploded = true;
                Thread.sleep(500);
                setMarked(true);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public boolean isExploded() {
        return exploded;
    }

    public int getRADIUS() {
        return RADIUS;
    }

    public int getDAMAGE() {
        return DAMAGE;
    }

    public Vector<Mortal> getHitMortals() {
        return hitMortals;
    }

    public void setExplosionRange() {
        horizontalExplosionX = getXCoordinate();
        while (!isMovementBlockedByWall(horizontalExplosionX - getUnitSize(), getYCoordinate()) && getXCoordinate() - horizontalExplosionX < getRADIUS()) {
            horizontalExplosionX = horizontalExplosionX - getUnitSize();
        }
        horizontalExplosionX = horizontalExplosionX - getUnitSize();
        int horizontalExplosionXEnd = getXCoordinate();
        while (!isMovementBlockedByWall(horizontalExplosionXEnd + getUnitSize(), getYCoordinate()) && horizontalExplosionXEnd - getXCoordinate() < getRADIUS()) {
            horizontalExplosionXEnd = horizontalExplosionXEnd + getUnitSize();
        }
        horizontalExplosionXEnd = horizontalExplosionXEnd + getUnitSize() + getUnitSize();
        horizontalExplosionWidth = horizontalExplosionXEnd - horizontalExplosionX;
        horizontalExplosionY = getYCoordinate();
        horizontalExplosionHeight = getUnitSize();
        verticalExplosionY = getYCoordinate();
        while (!isMovementBlockedByWall(getXCoordinate(), verticalExplosionY - getUnitSize()) && getYCoordinate() - verticalExplosionY < getRADIUS()) {
            verticalExplosionY = verticalExplosionY - getUnitSize();
        }
        verticalExplosionY = verticalExplosionY - getUnitSize();
        int verticalExplosionYEnd = getYCoordinate();
        while (!isMovementBlockedByWall(getXCoordinate(), verticalExplosionYEnd + getUnitSize()) && verticalExplosionYEnd - getYCoordinate() < getRADIUS()) {
            verticalExplosionYEnd = verticalExplosionYEnd + getUnitSize();
        }
        verticalExplosionYEnd = verticalExplosionYEnd + getUnitSize() + getUnitSize();
        verticalExplosionHeight = verticalExplosionYEnd - verticalExplosionY;
        verticalExplosionX = getXCoordinate();
        verticalExplosionWidth = getUnitSize();
    }

    public boolean isCollidingWithEntity(Entity destroyable) {
        if (destroyable.getXCoordinate() + destroyable.getUnitSize() > horizontalExplosionX && destroyable.getYCoordinate() + destroyable.getUnitSize() > horizontalExplosionY
                && horizontalExplosionX + horizontalExplosionWidth > destroyable.getXCoordinate() && horizontalExplosionY + horizontalExplosionHeight > destroyable.getYCoordinate())
            return true;
        return destroyable.getXCoordinate() + destroyable.getUnitSize() > verticalExplosionX && destroyable.getYCoordinate() + destroyable.getUnitSize() > verticalExplosionY
                && verticalExplosionX + verticalExplosionWidth > destroyable.getXCoordinate() && verticalExplosionY + verticalExplosionHeight > destroyable.getYCoordinate();
    }

    public Bomber getBOMBER() {
        return BOMBER;
    }
}
