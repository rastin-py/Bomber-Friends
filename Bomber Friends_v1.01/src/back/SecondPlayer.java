package back;

import back.enemies.BomberSkeleton;
import back.enemies.Ghost;
import back.enemies.Skeleton;

public class SecondPlayer {
    private int enemiesLeft;
    private int ghostCount;
    private int skeletonCount;
    private int bomberSkeletonCount;

    public SecondPlayer(int ghostCount, int skeletonCount, int bomberSkeletonCount) {
        this.ghostCount = ghostCount;
        this.skeletonCount = skeletonCount;
        this.bomberSkeletonCount = bomberSkeletonCount;
        enemiesLeft = ghostCount + skeletonCount + bomberSkeletonCount;
    }

    public int getEnemiesLeft() {
        return enemiesLeft;
    }

    public void plantEnemy(int x, int y, int enemyType, int UNIT_SIZE, int GHOST_SPEED, int GHOST_HEALTH, int SKELETON_SPEED, int SKELETON_HEALTH, int BOMBER_SKELETON_SPEED, int BOMBER_SKELETON_HEALTH, int BOMBER_SKELETON_DAMAGE, int BOMBER_SKELETON_RADIUS, int BOMBER_SKELETON_COOL_DOWN) {
        int x2, y2;
        x2 = x / UNIT_SIZE;
        y2 = y / UNIT_SIZE;
        if (!Entity.isPlacementBlocked(x2 * UNIT_SIZE, y2 * UNIT_SIZE)) {
            switch (enemyType) {
                case 1 -> {
                    if (ghostCount > 0) {
                        new Ghost(x2 * UNIT_SIZE, y2 * UNIT_SIZE, UNIT_SIZE, GHOST_SPEED, GHOST_HEALTH);
                        ghostCount--;
                    }
                }
                case 2 -> {
                    if (skeletonCount > 0) {
                        new Skeleton(x2 * UNIT_SIZE, y2 * UNIT_SIZE, UNIT_SIZE, SKELETON_SPEED, SKELETON_HEALTH);
                        skeletonCount--;
                    }
                }
                case 3 -> {
                    if (bomberSkeletonCount > 0) {
                        new BomberSkeleton(x2 * UNIT_SIZE, y2 * UNIT_SIZE, UNIT_SIZE, BOMBER_SKELETON_SPEED, BOMBER_SKELETON_HEALTH, BOMBER_SKELETON_DAMAGE, BOMBER_SKELETON_RADIUS, BOMBER_SKELETON_COOL_DOWN);
                        bomberSkeletonCount--;
                    }
                }
            }
            enemiesLeft = ghostCount + skeletonCount + bomberSkeletonCount;
        }
    }
}
