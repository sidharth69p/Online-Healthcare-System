package dao;
import model.Appointment;
import util.AppException;
import util.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcAppointmentDao implements AppointmentDao {

    @Override
    public synchronized int create(Appointment a) throws AppException {
        String sql = "INSERT INTO appointments (doctor_id,patient_id,appointment_date,appointment_time,status,notes) VALUES (?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, a.getDoctorId()); ps.setInt(2, a.getPatientId());
            ps.setDate(3, Date.valueOf(a.getDate())); ps.setTime(4, Time.valueOf(a.getTime()));
            ps.setString(5, a.getStatus()); ps.setString(6, a.getNotes());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) { if (keys.next()) return keys.getInt(1); }
        } catch (SQLException e) { throw new AppException("Could not create appointment", e); }
        return -1;
    }

    @Override
    public List<Appointment> findByDoctorAndDate(int doctorId, LocalDate date) throws AppException {
        String sql = "SELECT * FROM appointments WHERE doctor_id=? AND appointment_date=? ORDER BY appointment_time";
        List<Appointment> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, doctorId); ps.setDate(2, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(extract(rs)); }
        } catch (SQLException e) { throw new AppException("Error fetching appointments", e); }
        return list;
    }

    @Override
    public List<Appointment> findByPatient(int patientId) throws AppException {
        String sql = "SELECT * FROM appointments WHERE patient_id=? ORDER BY appointment_date DESC, appointment_time DESC";
        List<Appointment> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(extract(rs)); }
        } catch (SQLException e) { throw new AppException("Error fetching patient appointments", e); }
        return list;
    }

    @Override
    public List<Appointment> findAll() throws AppException {
        String sql = "SELECT * FROM appointments ORDER BY appointment_date DESC, appointment_time DESC";
        List<Appointment> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(extract(rs));
        } catch (SQLException e) { throw new AppException("Error listing all appointments", e); }
        return list;
    }

    private Appointment extract(ResultSet rs) throws SQLException {
        Appointment a = new Appointment();
        a.setId(rs.getInt("id"));
        a.setDoctorId(rs.getInt("doctor_id"));
        a.setPatientId(rs.getInt("patient_id"));
        a.setDate(rs.getDate("appointment_date").toLocalDate());
        a.setTime(rs.getTime("appointment_time").toLocalTime());
        a.setStatus(rs.getString("status"));
        a.setNotes(rs.getString("notes"));
        return a;
    }
}
