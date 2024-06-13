package front;

import back.PlayGround;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MenuPanel extends JPanel {
    private static final int SCREEN_WIDTH = 1100;
    private static final int SCREEN_HEIGHT = 780;
    private static Image backgroundImage;
    private static MainFrame mainFrame;

    public MenuPanel() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setVisible(true);
        setLayout(null);
        Image singlePlayerButtonIcon;
        Image multiplayerButtonIcon;
        Image settingButtonIcon;
        try {
            backgroundImage = ImageIO.read(new File("rsc\\sky.jpg"));
            backgroundImage = backgroundImage.getScaledInstance(SCREEN_WIDTH, SCREEN_HEIGHT, Image.SCALE_SMOOTH);
            singlePlayerButtonIcon = ImageIO.read(new File("rsc\\single-player.png"));
            singlePlayerButtonIcon = singlePlayerButtonIcon.getScaledInstance(200, 120, Image.SCALE_SMOOTH);
            multiplayerButtonIcon = ImageIO.read(new File("rsc\\multiplayer.png"));
            multiplayerButtonIcon = multiplayerButtonIcon.getScaledInstance(200, 120, Image.SCALE_SMOOTH);
            settingButtonIcon = ImageIO.read(new File("rsc\\setting.png"));
            settingButtonIcon = settingButtonIcon.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JButton singlePlayerButton = new JButton(new ImageIcon(singlePlayerButtonIcon));
        singlePlayerButton.setBounds(SCREEN_WIDTH / 2 - 100, SCREEN_HEIGHT - SCREEN_HEIGHT / 2, 200, 120);
        singlePlayerButton.addActionListener(e -> {
            mainFrame.setContentPane(new PlayGroundPanel(mainFrame, PlayGround.Mode.SINGLE_PLAYER));
            mainFrame.pack();
            mainFrame.setVisible(false);
            mainFrame.setVisible(true);
        });
        add(singlePlayerButton);
        JButton multiplayerButton = new JButton(new ImageIcon(multiplayerButtonIcon));
        multiplayerButton.setBounds(SCREEN_WIDTH / 2 - 100, singlePlayerButton.getY() + 130, 200, 120);
        multiplayerButton.addActionListener(e -> {
            mainFrame.setContentPane(new PlayGroundPanel(mainFrame, PlayGround.Mode.MULTIPLAYER));
            mainFrame.pack();
            mainFrame.setVisible(false);
            mainFrame.setVisible(true);
        });
        add(multiplayerButton);
        JButton settingButton = new JButton(new ImageIcon(settingButtonIcon));
        settingButton.setBounds(5, 5, 100, 100);
        settingButton.addActionListener(e -> {
            mainFrame.setContentPane(new SettingPanel(mainFrame).getMainPanel());
            mainFrame.pack();
            mainFrame.setVisible(false);
            mainFrame.setVisible(true);
        });
        add(settingButton);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
    }

    public void setMainFrame(MainFrame mainFrame) {
        MenuPanel.mainFrame = mainFrame;
    }
}

