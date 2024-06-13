package back.walls;

import back.Bomb;
import back.Mortal;
import back.effects.BombDamageEffect;
import back.effects.BombRadiusEffect;
import back.effects.HealthEffect;
import back.effects.SpeedEffect;

import javax.swing.*;
import java.awt.*;

public class AmazingWall extends Mortal implements Immovable {
    final Image IMAGE;
    final int MAX_HIT_POINT;
    final int HEALTH_UNIT;
    final int BOMB_DAMAGE_UNIT;
    final int BOMB_RADIUS_UNIT;
    final int SPEED_EFFECT;

    public AmazingWall(int xCoordinate, int yCoordinate, int unitSize, int hitPoint, int health_unit, int bomb_damage_unit, int bomb_radius_unit, int speed_effect) {
        super(xCoordinate, yCoordinate, unitSize, hitPoint);
        BOMB_DAMAGE_UNIT = bomb_damage_unit;
        BOMB_RADIUS_UNIT = bomb_radius_unit;
        SPEED_EFFECT = speed_effect;
        IMAGE = new ImageIcon("rsc\\amazing_wall.png").getImage();
        MAX_HIT_POINT = hitPoint;
        HEALTH_UNIT = health_unit;
    }

    public void getHit(Bomb bomb) {
        setHealth(getHealth() - 1);
        bomb.getHitMortals().add(this);
        if (getHealth() <= 0) {
            destroy();
        }
    }

    public void destroy() {
        super.destroy();
        int randomEffect = (int) Math.floor(Math.random() * (4) + 1);
        switch (randomEffect) {
            case 1 -> new HealthEffect(getXCoordinate(), getYCoordinate(), getUnitSize(), HEALTH_UNIT);
            case 2 -> new BombDamageEffect(getXCoordinate(), getYCoordinate(), getUnitSize(), BOMB_DAMAGE_UNIT);
            case 3 -> new BombRadiusEffect(getXCoordinate(), getYCoordinate(), getUnitSize(), BOMB_RADIUS_UNIT);
            case 4 -> new SpeedEffect(getXCoordinate(), getYCoordinate(), getUnitSize(), SPEED_EFFECT);
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
