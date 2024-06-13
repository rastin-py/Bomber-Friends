package back.effects;

import back.Player;

import javax.swing.*;
import java.awt.*;

public class HealthEffect extends Potion { // increases the health by one unit
    private final int HEALTH_UNIT;
    private static final Image HEALTH_EFFECT_ICON = new ImageIcon("rsc\\health_potion.jpg").getImage();

    public HealthEffect(int xCoordinate, int yCoordinate, int unitSize, int health_unit) {
        super(xCoordinate, yCoordinate, unitSize, HEALTH_EFFECT_ICON);
        HEALTH_UNIT = health_unit;
    }

    public void enableEffect(Player player) {
        super.enableEffect(player);
        player.setHealth(Math.min(player.getHealth() + HEALTH_UNIT, player.getMAX_HEALTH()));
    }
}
