package front;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(MenuPanel menuPanel) {
        setContentPane(menuPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("BOMBER FRIENDS");
    }
}
