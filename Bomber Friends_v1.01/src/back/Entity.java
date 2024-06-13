package back;

import back.enemies.AutoMovable;
import back.walls.Immovable;

import java.util.Vector;

abstract public class Entity implements Drawable {
    private int xCoordinate;
    private int yCoordinate;
    private final int unitSize;
    private static Vector<Entity> entities = new Vector<>();
    private static int enemiesCount;
    private static final Vector<Entity> entitiesToBeAdd = new Vector<>();
    private boolean marked = false; // entities that need to be destroyed are marked

    public Entity(int xCoordinate, int yCoordinate, int unitSize) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.unitSize = unitSize;
        entitiesToBeAdd.add(this);
    }

    // returns true if an entity is blocked by wall
    public static boolean isMovementBlockedByWall(int xDestination, int yDestination) {
        for (Entity entity : getEntities()) {
            if (entity instanceof Immovable) {
                if (xDestination + entity.getUnitSize() > entity.getXCoordinate() && yDestination + entity.getUnitSize() > entity.getYCoordinate()
                        && entity.getXCoordinate() + entity.getUnitSize() > xDestination && entity.getYCoordinate() + entity.getUnitSize() > yDestination)
                    return true;
            }
        }
        return false;
    }

    // returns true if an entity is blocked by anything
    public static boolean isPlacementBlocked(int x, int y) {
        for (Entity entity : entities) {
            if (entity.xCoordinate == x && entity.yCoordinate == y) {
                return true;
            }
        }
        return false;
    }

    public static boolean isColliding(Entity entity1, Entity entity2) {
        return entity1.xCoordinate + entity2.getUnitSize() > entity2.getXCoordinate() && entity1.yCoordinate + entity2.getUnitSize() > entity2.getYCoordinate()
                && entity2.getXCoordinate() + entity2.getUnitSize() > entity1.xCoordinate && entity2.getYCoordinate() + entity2.getUnitSize() > entity1.yCoordinate;
    }

    public static void refreshEntitiesList() { // refreshes the entities list, by removing the marked ones and adding the new ones
        entities.addAll(entitiesToBeAdd);
        entitiesToBeAdd.removeAllElements();
        Entity.getEntities().removeIf(Entity::isMarked);
        enemiesCount = 0;
        for (Entity entity : entities) {
            if (entity instanceof AutoMovable) {
                enemiesCount++;
            }
        }
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public static int getEnemiesCount() {
        return enemiesCount;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public int getUnitSize() {
        return unitSize;
    }

    public static Vector<Entity> getEntities() {
        return entities;
    }

    public static void setEntities(Vector<Entity> entities) {
        Entity.entities = entities;
    }
}
