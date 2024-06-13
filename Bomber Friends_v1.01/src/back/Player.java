package back;

import javax.swing.*;
import java.util.Vector;

public class Player extends Bomber {
    PlayGround playGround;
    public Player(int xCoordinate, int yCoordinate, int unitSize, int speed, int health, int bombDamage, int bombRadius, int bombCoolDown,PlayGround playGround) {
        super(xCoordinate, yCoordinate, unitSize, speed, health, bombDamage, bombRadius, bombCoolDown,new ImageIcon("rsc\\p1-up.gif").getImage(), new ImageIcon("rsc\\p1-down.gif").getImage(), new ImageIcon("rsc\\p1-left.gif").getImage(), new ImageIcon("rsc\\p1-right.gif").getImage(), new ImageIcon("rsc\\p1-idle.png").getImage());
        this.playGround = playGround;
    }
    public void destroy() {
        super.destroy();
        playGround.endGame(PlayGround.Result.DEFEAT);
    }
}
