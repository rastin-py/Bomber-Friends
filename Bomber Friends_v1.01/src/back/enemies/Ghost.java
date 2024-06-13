package back.enemies;

import javax.swing.*;

public class Ghost extends MoverOnlyEnemy implements AutoMovable {
    public Ghost(int xCoordinate, int yCoordinate, int unitSize, int speed, int health) {
        super(xCoordinate, yCoordinate, unitSize, speed, health, new ImageIcon("rsc\\p2-up.gif").getImage(), new ImageIcon("rsc\\p2-down.gif").getImage(), new ImageIcon("rsc\\p2-left.gif").getImage(), new ImageIcon("rsc\\p2-right.gif").getImage(), new ImageIcon("rsc\\p2-idle.png").getImage());
    }
}


