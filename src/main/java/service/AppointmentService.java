package service;
import dao.AppointmentDao;
import dao.JdbcAppointmentDao;
import model.Appointment;
import util.AppException;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.*;

public class AppointmentService {
    private final AppointmentDao dao = new JdbcAppointmentDao();
    private final ConcurrentHashMap<String, Object> locks = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public AppointmentService() {
        // simple scheduled task to demonstrate multithreading: runs every minute and prints upcoming appointments (placeholder)
        scheduler.scheduleAtFixedRate(() -> {
            try {
                // in real app we'd check upcoming appointments and notify users
                System.out.println("[Scheduler] heartbeat: " + System.currentTimeMillis());
            } catch (Throwable t) { t.printStackTrace(); }
        }, 1, 60, TimeUnit.SECONDS);
    }

    private Object lockFor(int doctorId, LocalDate date) {
        String k = doctorId + ":" + date.toString();
        locks.putIfAbsent(k, new Object());
        return locks.get(k);
    }

    public int bookAppointment(Appointment a) throws AppException {
        synchronized (lockFor(a.getDoctorId(), a.getDate())) {
            // check existing appointments
            List<Appointment> list = dao.findByDoctorAndDate(a.getDoctorId(), a.getDate());
            boolean exists = list.stream().anyMatch(x -> x.getTime().equals(a.getTime()));
            if (exists) throw new AppException("Slot already booked");
            return dao.create(a);
        }
    }

    public List<Appointment> getAppointmentsForPatient(int patientId) throws AppException { return dao.findByPatient(patientId); }
    public List<Appointment> getAppointmentsForDoctor(int doctorId, LocalDate date) throws AppException { return dao.findByDoctorAndDate(doctorId, date); }
    public List<Appointment> getAll() throws AppException { return dao.findAll(); }
}
