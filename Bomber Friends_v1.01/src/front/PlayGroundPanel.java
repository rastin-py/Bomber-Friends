package front;

import back.Mover;
import back.PlayGround;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayGroundPanel extends JPanel {
    static private final int SCREEN_WIDTH = 1600;
    static private final int SCREEN_HEIGHT = 780;
    PlayGround playGround;

    public PlayGroundPanel(MainFrame mainFrame, PlayGround.Mode mode) {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        playGround = new PlayGround(mode);
        setVisible(true);
        setLayout(null);
        new Timer(20, e -> repaint()).start();
        setFocusable(true);
        addKeyListener(new KeyListener() { // input handler for the main player
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> playGround.getMainPlayer().move(Mover.Direction.UP);
                    case KeyEvent.VK_S -> playGround.getMainPlayer().move(Mover.Direction.DOWN);
                    case KeyEvent.VK_A -> playGround.getMainPlayer().move(Mover.Direction.LEFT);
                    case KeyEvent.VK_D -> playGround.getMainPlayer().move(Mover.Direction.RIGHT);
                    case KeyEvent.VK_E -> playGround.getMainPlayer().plantBomb();
                    case KeyEvent.VK_ENTER -> {
                        if (!playGround.isRunning()) {
                            mainFrame.dispose();
                            System.gc();
                            MenuPanel menuPanel = new MenuPanel();
                            MainFrame mainFrame = new MainFrame(menuPanel);
                            mainFrame.setContentPane(menuPanel);
                            menuPanel.setMainFrame(mainFrame);
                        }
                    }
                }

            }

            public void keyReleased(KeyEvent e) {
                playGround.getMainPlayer().move(Mover.Direction.IDLE);
            }

            public void keyTyped(KeyEvent e) {

            }
        });
        if (mode.equals(PlayGround.Mode.MULTIPLAYER)) { // input handler for second player
            addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    switch (e.getButton()) {
                        case MouseEvent.BUTTON1 ->
                                playGround.attemptPlantEnemy(getMousePosition().x, getMousePosition().y, 1);
                        case MouseEvent.BUTTON2 ->
                                playGround.attemptPlantEnemy(getMousePosition().x, getMousePosition().y, 2);
                        case MouseEvent.BUTTON3 ->
                                playGround.attemptPlantEnemy(getMousePosition().x, getMousePosition().y, 3);
                    }
                }

                public void mousePressed(MouseEvent e) {

                }

                public void mouseReleased(MouseEvent e) {

                }

                public void mouseEntered(MouseEvent e) {

                }
                public void mouseExited(MouseEvent e) {

                }
            });
        }
    }
    // calls the main method for refreshing the game screen
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        playGround.drawEntities(g);
    }
}
