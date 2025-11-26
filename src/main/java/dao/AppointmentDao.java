package dao;
import model.Appointment;
import util.AppException;
import java.time.LocalDate;
import java.util.List;

public interface AppointmentDao {
    int create(Appointment a) throws AppException;
    List<Appointment> findByDoctorAndDate(int doctorId, LocalDate date) throws AppException;
    List<Appointment> findByPatient(int patientId) throws AppException;
    List<Appointment> findAll() throws AppException;
}
