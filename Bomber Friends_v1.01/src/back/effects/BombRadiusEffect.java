package back.effects;

import back.Player;

import javax.swing.*;
import java.awt.*;

public class BombRadiusEffect extends Potion { // increases the radius by one unit
    private final int BOMB_RADIUS_UNIT;
    private static final Image RADIUS_EFFECT_ICON = new ImageIcon("rsc\\radius_potion.jpg").getImage();

    public BombRadiusEffect(int xCoordinate, int yCoordinate, int unitSize, int bomb_radius_unit) {
        super(xCoordinate, yCoordinate, unitSize, RADIUS_EFFECT_ICON);
        BOMB_RADIUS_UNIT = bomb_radius_unit;
    }

    public void enableEffect(Player player) {
        super.enableEffect(player);
        player.setBombRadius(player.getBombRadius() + BOMB_RADIUS_UNIT);
    }
}
