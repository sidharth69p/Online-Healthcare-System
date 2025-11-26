package ui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import dao.JdbcUserDao;
import dao.UserDao;
import model.Role;
import model.User;
import util.Config;
import util.AppException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    private final JTextField emailField = new JTextField(20);
    private final JPasswordField passField = new JPasswordField(20);
    private final JCheckBox themeToggle = new JCheckBox("Dark theme");
    private final UserDao userDao = new JdbcUserDao();

    public LoginFrame() {
        super("Online Healthcare - Login (v3)"); initUI();
    }

    private void initUI() {
        setSize(420,250); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JPanel center = new JPanel(new GridBagLayout()); GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6); c.anchor = GridBagConstraints.WEST;
        c.gridx=0; c.gridy=0; center.add(new JLabel("Email:"), c);
        c.gridx=1; center.add(emailField, c);
        c.gridx=0; c.gridy=1; center.add(new JLabel("Password:"), c);
        c.gridx=1; center.add(passField, c);
        c.gridx=0; c.gridy=2; center.add(new JLabel(""), c); c.gridx=1; center.add(themeToggle, c);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT)); JButton loginBtn = new JButton("Login"); bottom.add(loginBtn);
        add(center, BorderLayout.CENTER); add(bottom, BorderLayout.SOUTH);

        themeToggle.addActionListener(e -> toggleTheme());
        loginBtn.addActionListener(this::doLogin);
    }

    private void toggleTheme() {
        try { if (themeToggle.isSelected()) FlatDarkLaf.install(); else FlatLightLaf.install(); SwingUtilities.updateComponentTreeUI(this); }
        catch (Exception ex) { System.err.println("Theme toggle failed: " + ex.getMessage()); }
    }

    private void doLogin(ActionEvent e) {
        String email = emailField.getText().trim(); String pass = new String(passField.getPassword());
        SwingWorker<User, Void> w = new SwingWorker<>() {
            protected User doInBackground() throws Exception {
                // try DB login first
                try { return userDao.findByEmailAndPassword(email, pass); } catch (AppException ex) { return null; }
            }
            protected void done() {
                try {
                    User u = get();
                    if (u == null) {
                        // fallback to demo accounts in config.properties
                        String aEmail = Config.get("demo.admin.email", "admin@hms.com"); String aPass = Config.get("demo.admin.password","admin123");
                        String dEmail = Config.get("demo.doctor.email","doctor@hms.com"); String dPass = Config.get("demo.doctor.password","doctor123");
                        String pEmail = Config.get("demo.patient.email","patient@hms.com"); String pPass = Config.get("demo.patient.password","patient123");
                        if (email.equals(aEmail) && pass.equals(aPass)) u = new User(0, "Admin User", aEmail, Role.ADMIN);
                        else if (email.equals(dEmail) && pass.equals(dPass)) u = new User(0, "Doctor", dEmail, Role.DOCTOR);
                        else if (email.equals(pEmail) && pass.equals(pPass)) u = new User(0, "Patient", pEmail, Role.PATIENT);
                    }
                    if (u == null) { JOptionPane.showMessageDialog(LoginFrame.this, "Invalid credentials", "Login failed", JOptionPane.ERROR_MESSAGE); return; }
                    dispose();
                    switch (u.getRole()) {
                        case ADMIN -> new AdminDashboard(u).setVisible(true);
                        case DOCTOR -> new DoctorDashboard(u).setVisible(true);
                        case PATIENT -> new PatientDashboard(u).setVisible(true);
                    }
                } catch (Exception ex) { JOptionPane.showMessageDialog(LoginFrame.this, "Login error: " + ex.getMessage()); }
            }
        }; w.execute();
    }

    public static void showLogin() {
        try { FlatLightLaf.install(); } catch (Throwable t) {}
        SwingUtilities.invokeLater(() -> { LoginFrame f = new LoginFrame(); f.setVisible(true); });
    }
}
