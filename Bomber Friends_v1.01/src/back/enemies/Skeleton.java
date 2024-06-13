package back.enemies;

import javax.swing.*;

public class Skeleton extends MoverOnlyEnemy implements AutoMovable{
    public Skeleton(int xCoordinate, int yCoordinate, int unitSize, int speed, int health) {
        super(xCoordinate, yCoordinate, unitSize, speed, health, new ImageIcon("rsc\\p4-up.gif").getImage(), new ImageIcon("rsc\\p4-down.gif").getImage(), new ImageIcon("rsc\\p4-left.gif").getImage(), new ImageIcon("rsc\\p4-right.gif").getImage(), new ImageIcon("rsc\\p4-idle.png").getImage());
    }
}
