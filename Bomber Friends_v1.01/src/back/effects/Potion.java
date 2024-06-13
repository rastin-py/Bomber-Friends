package back.effects;

import back.Entity;
import back.Player;

import java.awt.*;

public abstract class Potion extends Entity {
    private final Image IMAGE;

    public Potion(int xCoordinate, int yCoordinate, int unitSize, Image image) {
        super(xCoordinate, yCoordinate, unitSize);
        IMAGE = image;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(IMAGE, getXCoordinate(), getYCoordinate(), getUnitSize(), getUnitSize(), null);
    }

    public void enableEffect(Player player) {
        setMarked(true);
    }

}
