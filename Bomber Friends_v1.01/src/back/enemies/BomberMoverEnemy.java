package back.enemies;

import back.Bomber;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BomberMoverEnemy extends Bomber { // enemies which can bomb and move
    private Timer directionTimer;
    private Timer bombPlantingTimer;

    public BomberMoverEnemy(int xCoordinate, int yCoordinate, int unitSize, int speed, int health, int bombDamage, int bombRadius, int bombCoolDown, Image moving_up, Image moving_down, Image moving_left, Image moving_right, Image moving_idle) {
        super(xCoordinate, yCoordinate, unitSize, speed, health, bombDamage, bombRadius, bombCoolDown, moving_up, moving_down, moving_left, moving_right, moving_idle);
        startMoving();
    }

    public void startMoving() { // handles the actions of bots
        Random random = new Random();
        AtomicInteger randomDirNum = new AtomicInteger(random.nextInt() % 3);
        AtomicInteger randomBombNum = new AtomicInteger(random.nextInt() % 2);
        bombPlantingTimer = new Timer(getBOMB_DELAY(), e1 -> { // has a 2/3 chance of planting a bomb
            if (randomBombNum.get() < 1)
                plantBomb();
            randomBombNum.set(random.nextInt() % 2);
        });
        directionTimer = new Timer(19, e -> {
            switch (randomDirNum.get()) {
                case -2 -> move(Direction.LEFT);
                case -1 -> move(Direction.RIGHT);
                case 0 -> move(Direction.IDLE);
                case 1 -> move(Direction.DOWN);
                case 2 -> move(Direction.UP);
            }
            if (getXCoordinate() % getUnitSize() == 0 && getYCoordinate() % getUnitSize() == 0) {
                randomDirNum.set((random.nextInt() % 3));
            }
        });
        directionTimer.start();
        bombPlantingTimer.start();
    }

    public void stopMoving() {
        directionTimer.stop();
        bombPlantingTimer.stop();
    }

    public void destroy() {
        stopMoving();
        new Thread(() -> {
            setCurrentState(Direction.DEAD);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            super.destroy();
        }).start();
    }
}
