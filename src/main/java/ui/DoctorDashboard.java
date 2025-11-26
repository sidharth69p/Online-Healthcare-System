package ui;
import model.User;
import javax.swing.*;
import java.awt.*;

public class DoctorDashboard extends JFrame {
    private final User doctor;
    public DoctorDashboard(User doctor) {
        super("Doctor Dashboard - " + doctor.getName()); this.doctor = doctor; init();
    }
    private void init() {
        setSize(900,600); setLocationRelativeTo(null); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Doctor Dashboard", SwingConstants.CENTER); title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);
        JPanel center = new JPanel(new GridLayout(2,1,10,10));
        center.add(createPanel("Today's Schedule", "Calendar / list of today's appointments"));
        center.add(createPanel("Patient Records", "Open and update medical records"));
        add(center, BorderLayout.CENTER);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logout = new JButton("Logout"); logout.addActionListener(e -> { dispose(); ui.LoginFrame.showLogin(); });
        bottom.add(logout); add(bottom, BorderLayout.SOUTH);
    }
    private JPanel createPanel(String title, String desc) { JPanel p = new JPanel(new BorderLayout()); p.setBorder(BorderFactory.createTitledBorder(title)); JTextArea ta = new JTextArea(desc); ta.setEditable(false); p.add(ta, BorderLayout.CENTER); return p; }
}
