package back.effects;

import back.Player;

import javax.swing.*;
import java.awt.*;

public class BombDamageEffect extends Potion { // increases the damage by one unit
    private final int BOMB_DAMAGE_UNIT;
    private static final Image RADIUS_EFFECT_ICON = new ImageIcon("rsc\\damage_potion.jpg").getImage();

    public BombDamageEffect(int xCoordinate, int yCoordinate, int unitSize, int bomb_damage_unit) {
        super(xCoordinate, yCoordinate, unitSize, RADIUS_EFFECT_ICON);
        BOMB_DAMAGE_UNIT = bomb_damage_unit;
    }

    public void enableEffect(Player player) {
        super.enableEffect(player);
        player.setBombDamage(player.getBombDamage() + BOMB_DAMAGE_UNIT);
    }
}
