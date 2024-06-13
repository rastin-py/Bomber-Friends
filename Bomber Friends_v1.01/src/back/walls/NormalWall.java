package back.walls;

import back.Bomb;
import back.Mortal;

import javax.swing.*;
import java.awt.*;

public class NormalWall extends Mortal implements Immovable {
    final Image IMAGE;
    final int MAX_HIT_POINT;

    public NormalWall(int xCoordinate, int yCoordinate, int unitSize, int hitPoint) {
        super(xCoordinate, yCoordinate, unitSize, hitPoint);
        IMAGE = new ImageIcon("rsc\\normal_wall.png").getImage();
        MAX_HIT_POINT = hitPoint;
    }
    public void getHit(Bomb bomb) {
        setHealth(getHealth() - 1);
        bomb.getHitMortals().add(this);
        if (getHealth() <= 0) {
            destroy();
        }
    }
    public void draw(Graphics g) {
        g.drawImage(IMAGE, getXCoordinate(), getYCoordinate(), getUnitSize(), getUnitSize(), null);
        if (0 < getHealth()) {
            int healthPercentage = ((getHealth()) * 100) / MAX_HIT_POINT;
            super.draw(g, healthPercentage);
        }
    }
}
