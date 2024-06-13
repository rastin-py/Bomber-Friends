package back.walls;

import back.Entity;
import back.Mortal;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class HardWall extends Entity implements Immovable {
    final Image IMAGE;

    public HardWall(int xCoordinate, int yCoordinate, int unitSize) {
        super(xCoordinate, yCoordinate, unitSize);
        IMAGE = new ImageIcon("rsc\\hard_wall.png").getImage();
    }

    public void draw(Graphics g) {
        g.drawImage(IMAGE, getXCoordinate(), getYCoordinate(), getUnitSize(), getUnitSize(), null);
    }
}
