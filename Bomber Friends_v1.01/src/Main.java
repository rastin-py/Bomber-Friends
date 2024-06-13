
import front.MainFrame;
import front.MenuPanel;


public class Main {

    public static void main(String[] args) {
        MenuPanel menuPanel = new MenuPanel();
        MainFrame mainFrame = new MainFrame(menuPanel);
        menuPanel.setMainFrame(mainFrame);
    }
}
