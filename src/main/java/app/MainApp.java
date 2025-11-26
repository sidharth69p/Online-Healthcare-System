package app;

import ui.LoginFrame;
import util.Config;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        Config.load();
        SwingUtilities.invokeLater(() -> LoginFrame.showLogin());
    }
}
