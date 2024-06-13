package back.enemies;

import javax.swing.*;

public class BomberSkeleton extends BomberMoverEnemy implements AutoMovable {

    public BomberSkeleton(int xCoordinate, int yCoordinate, int unitSize, int speed, int health, int bombDamage, int bombRadius, int bombCoolDown) {
        super(xCoordinate, yCoordinate, unitSize, speed, health, bombDamage, bombRadius,bombCoolDown ,new ImageIcon("rsc\\p3-up.gif").getImage(), new ImageIcon("rsc\\p3-down.gif").getImage(), new ImageIcon("rsc\\p3-left.gif").getImage(), new ImageIcon("rsc\\p3-right.gif").getImage(), new ImageIcon("rsc\\p3-idle.png").getImage());
    }
}