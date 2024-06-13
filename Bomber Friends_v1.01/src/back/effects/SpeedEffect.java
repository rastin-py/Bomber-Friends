package back.effects;

import back.Player;

import javax.swing.*;
import java.awt.*;

public class SpeedEffect extends Potion { // increases the speed for 5 seconds
    private final int SPEED_UNIT;
    private static final Image SPEED_EFFECT_ICON = new ImageIcon("rsc\\speed_potion.jpg").getImage();

    public SpeedEffect(int xCoordinate, int yCoordinate, int unitSize, int speed_unit) {
        super(xCoordinate, yCoordinate, unitSize, SPEED_EFFECT_ICON);
        SPEED_UNIT = speed_unit;
    }

    public void enableEffect(Player player) {
        super.enableEffect(player);
        new Thread(() -> {
            player.setSpeed(SPEED_UNIT * 4);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            player.setSpeed(SPEED_UNIT * 2);
        }).start();
    }
}
