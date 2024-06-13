package back;

import back.effects.Potion;
import back.enemies.AutoMovable;
import back.enemies.BomberSkeleton;
import back.enemies.Ghost;
import back.enemies.Skeleton;
import back.walls.AmazingWall;
import back.walls.HardWall;
import back.walls.NormalWall;
import front.SettingPanel;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.abs;

public class PlayGround {

    public enum Result {
        DEFEAT, VICTORY,
    }

    public enum Mode {
        SINGLE_PLAYER, MULTIPLAYER
    }

    Image gameOverImage = new ImageIcon("rsc\\game-over.gif").getImage();
    final Image VICTORY_Image = new ImageIcon("rsc\\victory.png").getImage();
    final Image DEFEAT_Image = new ImageIcon("rsc\\defeat.png").getImage();
    final Image PLAYER_ONE_WIN_IMAGE = new ImageIcon("rsc\\p1_win.png").getImage();
    final Image PLAYER_TWO_WIN_IMAGE = new ImageIcon("rsc\\p2_win.png").getImage();
    final Image GRASS = new ImageIcon("rsc\\grass3.png").getImage();
    final Clip IN_GAME_CLIP;
    final Clip DEFEAT_CLIP;
    final Clip VICTORY_CLIP;
    Clip gameOverClip;

    {
        try {
            IN_GAME_CLIP = AudioSystem.getClip();
            IN_GAME_CLIP.open(AudioSystem.getAudioInputStream(new File("rsc\\in-game.wav")));
            DEFEAT_CLIP = AudioSystem.getClip();
            DEFEAT_CLIP.open(AudioSystem.getAudioInputStream(new File("rsc\\defeat.wav")));
            VICTORY_CLIP = AudioSystem.getClip();
            VICTORY_CLIP.open(AudioSystem.getAudioInputStream(new File("rsc\\defeat.wav")));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    private final int PLAYGROUND_WIDTH;
    private final int PLAYGROUND_HEIGHT;
    static private final int SCREEN_WIDTH = 1600;
    static private final int SCREEN_HEIGHT = 780;
    // units:
    static private final int UNIT_SIZE = 60;
    static private final int HEALTH_UNIT = 100;
    static private final int SPEED_UNIT = 5;
    static private final int BOMB_DAMAGE_UNIT = 25;
    // player states:
    static private final int PLAYER_SPEED = 2 * SPEED_UNIT;
    private final int PLAYER_HEALTH;
    private final int PLAYER_BOMB_DAMAGE;
    private final int PLAYER_BOMB_RADIUS;
    private final int PLAYER_BOMB_COOL_DOWN = 2000;
    // ghost states:
    static private final int GHOST_SPEED = 3 * SPEED_UNIT;
    static private final int GHOST_HEALTH = HEALTH_UNIT;
    // skeleton states:
    static private final int SKELETON_SPEED = SPEED_UNIT;
    static private final int SKELETON_HEALTH = 3 * HEALTH_UNIT;
    // bomber skeleton states:
    static private final int BOMBER_SKELETON_SPEED = SPEED_UNIT;
    static private final int BOMBER_SKELETON_HEALTH = HEALTH_UNIT;
    static private final int BOMBER_SKELETON_DAMAGE = 4 * BOMB_DAMAGE_UNIT;
    static private final int BOMBER_SKELETON_RADIUS = 5 * UNIT_SIZE;
    static private final int BOMBER_SKELETON_COOL_DOWN = 5000;
    // walls' states:
    static private final int AMAZING_WALL_HIT_POINT = 3;
    static private final int NORMAL_WALL_HIT_POINT = 2;
    // number of entities:
    private final int GHOST_COUNT;
    private final int SKELETON_COUNT;
    private int BOMBER_SKELETON_COUNT;
    private final int AMAZING_WALL_COUNT;
    private final int NORMAL_WALL_COUNT;
    private boolean running = false;
    Timer gameOn;
    private final Player mainPlayer;
    private SecondPlayer secondPlayer;
    private final Mode MODE;

    public PlayGround(Mode mode) {
        this.MODE = mode;
        // these are starting scales for game, set by the class SettingPanel
        PLAYGROUND_WIDTH = SettingPanel.StartingData.getWIDTH() * UNIT_SIZE;
        PLAYGROUND_HEIGHT = SettingPanel.StartingData.getHEIGHT() * UNIT_SIZE;
        PLAYER_HEALTH = SettingPanel.StartingData.getPlayerHealthUnitsCount() * HEALTH_UNIT;
        PLAYER_BOMB_DAMAGE = SettingPanel.StartingData.getPlayerBombDamageUnitsCount() * BOMB_DAMAGE_UNIT;
        PLAYER_BOMB_RADIUS = SettingPanel.StartingData.getPlayerBombRadiusUnitsCount() * UNIT_SIZE;
        if (SettingPanel.StartingData.getWIDTH() * SettingPanel.StartingData.getHEIGHT() < 50) {
            AMAZING_WALL_COUNT = 2;
            NORMAL_WALL_COUNT = 4;
        } else if (SettingPanel.StartingData.getWIDTH() * SettingPanel.StartingData.getHEIGHT() < 200) {
            AMAZING_WALL_COUNT = 5;
            NORMAL_WALL_COUNT = 10;
        } else {
            AMAZING_WALL_COUNT = 10;
            NORMAL_WALL_COUNT = 30;
        }
        Entity.getEntities().removeAllElements();
        Entity.setEntities(new Vector<>());
        if (mode == Mode.MULTIPLAYER) {
            this.secondPlayer = new SecondPlayer(SettingPanel.StartingData.getGhostCount(), SettingPanel.StartingData.getSkeletonCount(), SettingPanel.StartingData.getBomberSkeletonCount());
            GHOST_COUNT = 0;
            SKELETON_COUNT = 0;
            BOMBER_SKELETON_COUNT = 0;
        } else {
            GHOST_COUNT = SettingPanel.StartingData.getGhostCount();
            SKELETON_COUNT = SettingPanel.StartingData.getSkeletonCount();
            BOMBER_SKELETON_COUNT = SettingPanel.StartingData.getBomberSkeletonCount();
        }
        mainPlayer = new Player(PLAYGROUND_WIDTH - 2 * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, PLAYER_SPEED, PLAYER_HEALTH, PLAYER_BOMB_DAMAGE, PLAYER_BOMB_RADIUS, PLAYER_BOMB_COOL_DOWN, this);
        System.gc();
        startGame();
    }

    // the main method of game that implements the logic of the game
    public void startGame() {
        running = true;
        IN_GAME_CLIP.start();
        IN_GAME_CLIP.loop(Clip.LOOP_CONTINUOUSLY);
        AtomicInteger lastHitTime = new AtomicInteger(((int) (System.currentTimeMillis() % 1000000)));
        gameOn = new Timer(1, e -> {
            for (Entity entity : Entity.getEntities()) {
                if (entity instanceof Potion) { // if players gets affected by effects
                    if (Entity.isColliding(getMainPlayer(), entity)) {
                        ((Potion) entity).enableEffect(getMainPlayer());
                    }
                } else if (entity instanceof AutoMovable) {// if player gets hit by an enemy
                    if (Entity.isColliding(getMainPlayer(), entity)) {
                        if (((int) (System.currentTimeMillis() % 1000000)) - lastHitTime.get() > 1000) {
                            ((Mortal) entity).destroy();
                            getMainPlayer().setHealth(getMainPlayer().getHealth() - HEALTH_UNIT);
                            lastHitTime.set((int) (System.currentTimeMillis() % 1000000));
                        }
                    }
                } else if (entity instanceof Bomb && ((Bomb) entity).isExploded()) { // if a bomb is exploded
                    for (Entity entity2 : Entity.getEntities()) {
                        if (entity2 instanceof Mortal && !((Bomb) entity).getHitMortals().contains(entity2)) { // if the bomb hits a mortal
                            if (((Bomb) entity).isCollidingWithEntity(entity2)) {
                                if (((Bomb) entity).getBOMBER() instanceof Player || !(entity2 instanceof AutoMovable)) {
                                    ((Mortal) entity2).getHit((Bomb) entity);
                                }
                            }
                        }
                    }
                }
            }
            Entity.refreshEntitiesList();
            if (MODE == Mode.MULTIPLAYER) {
                if (secondPlayer.getEnemiesLeft() == 0 && Entity.getEnemiesCount() == 0) {
                    endGame(Result.VICTORY);
                }
            } else {
                if (Entity.getEnemiesCount() == 0) {
                    endGame(Result.VICTORY);
                }
            }

        });
        Entity.refreshEntitiesList();
        setWalls();
        setEnemies(GHOST_COUNT, SKELETON_COUNT, BOMBER_SKELETON_COUNT);
        gameOn.start();
    }

    public void setWalls() {
        for (int i = 0; i < PLAYGROUND_WIDTH / UNIT_SIZE; i++) { // vertical walls
            new HardWall(i * UNIT_SIZE, 0, UNIT_SIZE);
            new HardWall(i * UNIT_SIZE, PLAYGROUND_HEIGHT - UNIT_SIZE, UNIT_SIZE);
        }
        for (int i = 0; i < PLAYGROUND_HEIGHT / UNIT_SIZE; i++) { // horizontal walls
            new HardWall(0, i * UNIT_SIZE, UNIT_SIZE);
            new HardWall(PLAYGROUND_WIDTH - UNIT_SIZE, i * UNIT_SIZE, UNIT_SIZE);
        }
        for (int j = 2; j + 1 < PLAYGROUND_HEIGHT / UNIT_SIZE; j = j + 2) { // intersecting walls
            for (int i = 2; i + 1 < PLAYGROUND_WIDTH / UNIT_SIZE; i = i + 2) {
                new HardWall(i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE);
            }
        }
        Entity.refreshEntitiesList();
        placeRandomEntities(4, AMAZING_WALL_COUNT, (PLAYGROUND_WIDTH / UNIT_SIZE) - 2);
        placeRandomEntities(5, NORMAL_WALL_COUNT, (PLAYGROUND_WIDTH / UNIT_SIZE) - 2);
    }

    public void setEnemies(int ghostsCount, int skeletonsCounts, int bomberSkeletonsCount) {
        placeRandomEntities(1, ghostsCount, (PLAYGROUND_WIDTH / UNIT_SIZE) / 2);
        placeRandomEntities(2, skeletonsCounts, (PLAYGROUND_WIDTH / UNIT_SIZE) / 2);
        placeRandomEntities(3, bomberSkeletonsCount, (PLAYGROUND_WIDTH / UNIT_SIZE / 2));
    }

    public void placeRandomEntities(int typeNumber, int count, int maximumX) { // this method handles the placement for random entities
        int randomX;
        int randomY;
        int maximumY = ((PLAYGROUND_HEIGHT / UNIT_SIZE) - 2);
        for (int i = 0; i < count; i++) {
            randomX = (int) Math.floor(Math.random() * ((maximumX) + 1));
            randomY = (int) Math.floor(Math.random() * (maximumY) + 1);
            while (Entity.isPlacementBlocked(randomX * UNIT_SIZE, randomY * UNIT_SIZE)) {
                randomX = (int) Math.floor(Math.random() * ((maximumX) + 1));
                randomY = (int) Math.floor(Math.random() * (maximumY) + 1);
            }
            switch (typeNumber) {
                case 1 -> new Ghost(randomX * UNIT_SIZE, randomY * UNIT_SIZE, UNIT_SIZE, GHOST_SPEED, GHOST_HEALTH);
                case 2 ->
                        new Skeleton(randomX * UNIT_SIZE, randomY * UNIT_SIZE, UNIT_SIZE, SKELETON_SPEED, SKELETON_HEALTH);
                case 3 ->
                        new BomberSkeleton(randomX * UNIT_SIZE, randomY * UNIT_SIZE, UNIT_SIZE, BOMBER_SKELETON_SPEED, BOMBER_SKELETON_HEALTH, BOMBER_SKELETON_DAMAGE, BOMBER_SKELETON_RADIUS, BOMBER_SKELETON_COOL_DOWN);
                case 4 ->
                        new AmazingWall(randomX * UNIT_SIZE, randomY * UNIT_SIZE, UNIT_SIZE, AMAZING_WALL_HIT_POINT, HEALTH_UNIT, BOMB_DAMAGE_UNIT, UNIT_SIZE, SPEED_UNIT);
                case 5 -> new NormalWall(randomX * UNIT_SIZE, randomY * UNIT_SIZE, UNIT_SIZE, NORMAL_WALL_HIT_POINT);
            }
            Entity.refreshEntitiesList();
        }
    }

    // this method handles the visual tasks of game
    public void drawEntities(Graphics g) {
        if (running) {
//            for (int i = 0; i < PLAYGROUND_HEIGHT / UNIT_SIZE; i++) {
//                g.drawLine(0, i * UNIT_SIZE, PLAYGROUND_WIDTH, i * UNIT_SIZE);
//            }
//            for (int i = 0; i < PLAYGROUND_WIDTH / UNIT_SIZE; i++)
//                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, PLAYGROUND_HEIGHT);
            g.drawImage(GRASS, 0, 0, PLAYGROUND_WIDTH, PLAYGROUND_HEIGHT, null);
            for (Entity entity : Entity.getEntities()) {
                try {
                    entity.draw(g);
                } catch (Exception e) {
                    System.out.println("Entity removed!");
                }
            }
            g.setColor(Color.black);
            g.setFont(new Font("Ink Free", Font.BOLD, UNIT_SIZE / 2));
            g.getFontMetrics(g.getFont());
            g.drawString("HEALTH: " + getMainPlayer().getHealth(), PLAYGROUND_WIDTH, UNIT_SIZE);
            g.drawString("SPEED: " + getMainPlayer().getSpeed(), PLAYGROUND_WIDTH, 2 * UNIT_SIZE);
            g.drawString("BOMB DAMAGE: " + getMainPlayer().getBombDamage(), PLAYGROUND_WIDTH, 3 * UNIT_SIZE);
            g.drawString("BOMB RADIUS: " + getMainPlayer().getBombRadius(), PLAYGROUND_WIDTH, 4 * UNIT_SIZE);
            g.drawString("BOMB COOL-DOWN:", PLAYGROUND_WIDTH, 5 * UNIT_SIZE);
            g.drawString(String.valueOf(getMainPlayer().getBOMB_COOL_DOWN()), PLAYGROUND_WIDTH, 6 * UNIT_SIZE);
            g.drawString("BOMB DELAY: " + getMainPlayer().getBOMB_DELAY(), PLAYGROUND_WIDTH, 7 * UNIT_SIZE);
            g.drawString("BOMBS: " + getMainPlayer().getBombsAvailable(), PLAYGROUND_WIDTH, 8 * UNIT_SIZE);
            g.drawString("ENEMIES ALIVE: " + Entity.getEnemiesCount(), PLAYGROUND_WIDTH, 9 * UNIT_SIZE);
            if (MODE == Mode.MULTIPLAYER) {
                g.drawString("SECOND PLAYER'S ENEMIES LEFT:", PLAYGROUND_WIDTH, 10 * UNIT_SIZE);
                g.drawString(String.valueOf(secondPlayer.getEnemiesLeft()), PLAYGROUND_WIDTH, 11 * UNIT_SIZE);
            }
        } else {
            g.drawImage(gameOverImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
            IN_GAME_CLIP.stop();
            gameOverClip.start();
            g.setColor(Color.black);
            g.setFont(new Font("Ink Free", Font.BOLD, UNIT_SIZE));
            g.getFontMetrics(g.getFont());
            g.drawString("Press Enter To Return to Menu", UNIT_SIZE, 2 * UNIT_SIZE);
        }
    }

    public void endGame(Result result) { // when the game ends
        gameOn.stop();
        gameOn.setRepeats(false);
        running = false;
        if (MODE == Mode.SINGLE_PLAYER) {
            if (result.equals(Result.VICTORY)) {
                gameOverImage = VICTORY_Image;
                gameOverClip = VICTORY_CLIP;
            } else {
                gameOverImage = DEFEAT_Image;
                gameOverClip = DEFEAT_CLIP;
            }
        } else {
            if (result.equals(Result.VICTORY)) {
                gameOverImage = PLAYER_ONE_WIN_IMAGE;
                gameOverClip = VICTORY_CLIP;
            } else {
                gameOverImage = PLAYER_TWO_WIN_IMAGE;
                gameOverClip = DEFEAT_CLIP;
            }
        }
    }

    public void attemptPlantEnemy(int x, int y, int enemyType) { // this method is called by the second player
        if (abs(x - mainPlayer.getXCoordinate()) < 6 * UNIT_SIZE && abs(y - mainPlayer.getYCoordinate()) < 6 * UNIT_SIZE) {
            return;
        }
        getSecondPlayer().plantEnemy(x, y, enemyType, UNIT_SIZE, GHOST_SPEED, GHOST_HEALTH, SKELETON_SPEED, SKELETON_HEALTH, BOMBER_SKELETON_SPEED, BOMBER_SKELETON_HEALTH, BOMBER_SKELETON_DAMAGE, BOMBER_SKELETON_RADIUS, BOMBER_SKELETON_COOL_DOWN);
    }

    public Player getMainPlayer() {
        return mainPlayer;
    }

    public boolean isRunning() {
        return running;
    }


    public SecondPlayer getSecondPlayer() {
        return secondPlayer;
    }
}