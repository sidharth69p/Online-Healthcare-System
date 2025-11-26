package ui;
import model.User;
import model.Appointment;
import service.AppointmentService;
import util.AppException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.apache.pdfbox.pdmodel.*; // for PDF export
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.File;

public class PatientDashboard extends JFrame {
    private final User patient;
    private final AppointmentService svc = new AppointmentService();
    private final JTable table = new JTable();

    public PatientDashboard(User patient) {
        super("Patient Dashboard - " + patient.getName());
        this.patient = patient;
        init();
    }

    private void init() {
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Patient Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refresh = new JButton("Refresh");
        JButton book = new JButton("Book Appointment");
        JButton export = new JButton("Export Appointments PDF");
        top.add(refresh); top.add(book); top.add(export);
        add(top, BorderLayout.SOUTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refresh.addActionListener(e -> loadAppointments());
        book.addActionListener(e -> openBookingDialog());
        export.addActionListener(e -> exportPdf());

        loadAppointments();
    }

    private void loadAppointments() {
        SwingWorker<List<Appointment>, Void> w = new SwingWorker<>() {
            protected List<Appointment> doInBackground() throws Exception {
                return svc.getAppointmentsForPatient(patient.getId());
            }
            protected void done() {
                try {
                    List<Appointment> list = get();
                    DefaultTableModel m = new DefaultTableModel(new Object[]{"ID","DoctorID","Date","Time","Status","Notes"},0);
                    for (Appointment a: list) m.addRow(new Object[]{a.getId(), a.getDoctorId(), a.getDate(), a.getTime(), a.getStatus(), a.getNotes()});
                    table.setModel(m);
                } catch (Exception e) { JOptionPane.showMessageDialog(PatientDashboard.this, "Error: " + e.getMessage()); }
            }
        }; w.execute();
    }

    private void openBookingDialog() {
        JPanel p = new JPanel(new GridLayout(5,2));
        JTextField doctorId = new JTextField(); JTextField date = new JTextField(LocalDate.now().toString()); JTextField time = new JTextField(LocalTime.of(10,0).toString());
        JTextArea notes = new JTextArea(3,20);
        p.add(new JLabel("Doctor ID:")); p.add(doctorId);
        p.add(new JLabel("Date (YYYY-MM-DD):")); p.add(date);
        p.add(new JLabel("Time (HH:MM):")); p.add(time);
        p.add(new JLabel("Notes:")); p.add(new JScrollPane(notes));

        int res = JOptionPane.showConfirmDialog(this, p, "Book Appointment", JOptionPane.OK_CANCEL_OPTION);
        if (res==JOptionPane.OK_OPTION) {
            SwingWorker<Void,Void> w = new SwingWorker<>() {
                protected Void doInBackground() throws Exception {
                    try {
                        Appointment a = new Appointment();
                        a.setDoctorId(Integer.parseInt(doctorId.getText().trim()));
                        a.setPatientId(patient.getId());
                        a.setDate(LocalDate.parse(date.getText().trim()));
                        a.setTime(LocalTime.parse(time.getText().trim()));
                        a.setStatus("SCHEDULED"); a.setNotes(notes.getText());
                        svc.bookAppointment(a);
                    } catch (AppException ex) { throw ex; }
                    return null;
                }
                protected void done() {
                    try { get(); JOptionPane.showMessageDialog(PatientDashboard.this, "Appointment booked"); loadAppointments(); }
                    catch (Exception ex) { JOptionPane.showMessageDialog(PatientDashboard.this, "Booking failed: " + ex.getMessage()); }
                }
            }; w.execute();
        }
    }

    private void exportPdf() {
        try {
            File f = new File("appointments_" + patient.getId() + ".pdf");
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage(PDRectangle.LETTER);
            doc.addPage(page);
            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 14);
                cs.newLineAtOffset(50, 700);
                cs.showText("Appointments for " + patient.getName());
                cs.endText();

                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, 10);
                cs.newLineAtOffset(50, 680);
                for (int r=0; r < table.getRowCount(); r++) {
                    String line = table.getValueAt(r,0) + " | " + table.getValueAt(r,2) + " " + table.getValueAt(r,3) + " | " + table.getValueAt(r,4);
                    cs.showText(line);
                    cs.newLineAtOffset(0, -12);
                }
                cs.endText();
            }
            doc.save(f);
            doc.close();
            JOptionPane.showMessageDialog(this, "Exported to " + f.getAbsolutePath());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "PDF export failed: " + e.getMessage());
        }
    }
}
