package back.enemies;

import back.Mover;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public abstract class MoverOnlyEnemy extends Mover { // enemies which can only move
    private Timer directionTimer;

    public MoverOnlyEnemy(int xCoordinate, int yCoordinate, int unitSize, int speed, int health, Image moving_up, Image moving_down, Image moving_left, Image moving_right, Image moving_idle) {
        super(xCoordinate, yCoordinate, unitSize, speed, health, moving_up, moving_down, moving_left, moving_right, moving_idle);
        startMoving();
    }

    public void startMoving() {
        Random random = new Random();
        AtomicInteger randomDirNum = new AtomicInteger(random.nextInt() % 3);
        directionTimer = new Timer(19, e -> {
            switch (randomDirNum.get()) {
                case -2 -> move(Direction.LEFT);
                case -1 -> move(Direction.RIGHT);
                case 0 ->  move(Direction.IDLE);
                case 1 -> move(Direction.DOWN);
                case 2 -> move(Direction.UP);
            }
            if (getXCoordinate() % getUnitSize() == 0 && getYCoordinate() % getUnitSize() == 0) {
                randomDirNum.set((random.nextInt() % 3));
            }
        });
        directionTimer.start();
    }
    private void stopMoving() {
        directionTimer.setRepeats(false);
        directionTimer.stop();
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
