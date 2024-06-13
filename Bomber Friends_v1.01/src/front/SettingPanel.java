package front;

import javax.swing.*;

public class SettingPanel extends JPanel {


    public JPanel mainPanel;
    private JTextField WIDTHTextField;
    private JTextField PLAYERHEALTHTextField;
    private JTextField PLAYERBOMBDAMAGETextField;
    private JTextField PLAYERBOMBRADIUSTextField;
    private JTextField GHOSTCOUNTTextField;
    private JTextField SKELETONCOUNTTextField;
    private JTextField BOMBERSKELETONCOUNTTextField;
    private JSlider widthSlider;
    private JSlider playerHealthSlider;
    private JSlider playerBombDamageSlider;
    private JSlider playerBombRadiusSlider;
    private JSlider ghostSlider;
    private JSlider skeletonSlider;
    private JSlider bomberSkeletonSlider;
    private JButton SAVEButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField HEIGHTTextField;
    private JSlider heightSlider;


    public SettingPanel(MainFrame mainFrame) {
        setVisible(true);
        textField2.setText(String.valueOf(playerHealthSlider.getValue()));
        textField3.setText(String.valueOf(playerBombDamageSlider.getValue()));
        textField4.setText(String.valueOf(playerBombRadiusSlider.getValue()));
        textField5.setText(String.valueOf(ghostSlider.getValue()));
        textField6.setText(String.valueOf(skeletonSlider.getValue()));
        textField7.setText(String.valueOf(bomberSkeletonSlider.getValue()));
        textField1.setText(String.valueOf(widthSlider.getValue() * 2 + 11));
        textField8.setText(String.valueOf(heightSlider.getValue() * 2 + 3));
        widthSlider.addChangeListener(e -> {
            textField1.setText(String.valueOf(widthSlider.getValue() * 2 + 11));
        });
        heightSlider.addChangeListener(e -> {
            textField8.setText(String.valueOf(heightSlider.getValue() * 2 + 3));
        });
        playerHealthSlider.addChangeListener(e -> {
            textField2.setText(String.valueOf(playerHealthSlider.getValue()));
        });
        playerBombDamageSlider.addChangeListener(e -> {
            textField3.setText(String.valueOf(playerBombDamageSlider.getValue()));
        });
        playerBombRadiusSlider.addChangeListener(e -> {
            textField4.setText(String.valueOf(playerBombRadiusSlider.getValue()));
        });
        ghostSlider.addChangeListener(e -> {
            textField5.setText(String.valueOf(ghostSlider.getValue()));
        });
        skeletonSlider.addChangeListener(e -> {
            textField6.setText(String.valueOf(skeletonSlider.getValue()));
        });
        bomberSkeletonSlider.addChangeListener(e -> {
            textField7.setText(String.valueOf(bomberSkeletonSlider.getValue()));
        });
        SAVEButton.addActionListener(e -> {
            if (skeletonSlider.getValue() + bomberSkeletonSlider.getValue() + ghostSlider.getValue() == 0) {
                JOptionPane.showMessageDialog(this, "number of robots can't be zero");
            } else {
                StartingData.setUnitsSize((widthSlider.getValue() * 2 + 11), (heightSlider.getValue() * 2 + 3), playerHealthSlider.getValue(), playerBombDamageSlider.getValue(), playerBombRadiusSlider.getValue(), ghostSlider.getValue(), skeletonSlider.getValue(), bomberSkeletonSlider.getValue());
                new MainFrame(new MenuPanel());
            }
        });

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static class StartingData {
        private static int WIDTH = 21;
        private static int HEIGHT = 13;
        private static int PLAYER_HEALTH_UNITS_COUNT = 3;
        private static int PLAYER_BOMB_DAMAGE_UNITS_COUNT = 3;
        private static int PLAYER_BOMB_RADIUS_UNITS_COUNT = 3;
        private static int GHOST_COUNT = 3;
        private static int SKELETON_COUNT = 2;
        private static int BOMBER_SKELETON_COUNT = 1;

        public static void setUnitsSize(int width, int height, int playerHealthUnitsCount, int playerBombDamageUnitsCount, int playerBombRadiusUnitsCount, int ghostCount, int skeletonCount, int bomberSkeletonCount) {
            WIDTH = width;
            HEIGHT = height;
            PLAYER_HEALTH_UNITS_COUNT = playerHealthUnitsCount;
            PLAYER_BOMB_DAMAGE_UNITS_COUNT = playerBombDamageUnitsCount;
            PLAYER_BOMB_RADIUS_UNITS_COUNT = playerBombRadiusUnitsCount;
            GHOST_COUNT = ghostCount;
            SKELETON_COUNT = skeletonCount;
            BOMBER_SKELETON_COUNT = bomberSkeletonCount;
        }

        public static int getWIDTH() {
            return WIDTH;
        }

        public static int getHEIGHT() {
            return HEIGHT;
        }

        public static int getPlayerHealthUnitsCount() {
            return PLAYER_HEALTH_UNITS_COUNT;
        }

        public static int getPlayerBombDamageUnitsCount() {
            return PLAYER_BOMB_DAMAGE_UNITS_COUNT;
        }

        public static int getPlayerBombRadiusUnitsCount() {
            return PLAYER_BOMB_RADIUS_UNITS_COUNT;
        }

        public static int getGhostCount() {
            return GHOST_COUNT;
        }

        public static int getSkeletonCount() {
            return SKELETON_COUNT;
        }

        public static int getBomberSkeletonCount() {
            return BOMBER_SKELETON_COUNT;
        }


    }
}
