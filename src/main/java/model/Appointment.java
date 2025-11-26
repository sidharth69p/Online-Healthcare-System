package model;
import java.time.LocalDate;
import java.time.LocalTime;
public class Appointment {
    private int id;
    private int doctorId;
    private int patientId;
    private LocalDate date;
    private LocalTime time;
    private String status;
    private String notes;
    // getters & setters
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public int getDoctorId(){return doctorId;} public void setDoctorId(int doctorId){this.doctorId=doctorId;}
    public int getPatientId(){return patientId;} public void setPatientId(int patientId){this.patientId=patientId;}
    public LocalDate getDate(){return date;} public void setDate(LocalDate date){this.date=date;}
    public LocalTime getTime(){return time;} public void setTime(LocalTime time){this.time=time;}
    public String getStatus(){return status;} public void setStatus(String status){this.status=status;}
    public String getNotes(){return notes;} public void setNotes(String notes){this.notes=notes;}
}
