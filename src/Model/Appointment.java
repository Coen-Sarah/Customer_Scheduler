package Model;

import java.time.LocalDateTime;

/**
 * Provides storage for appointment information
 * */
public class Appointment {
    /**
     * The appointment identifier
     * */
    int AppointmentId;
    /**
     * The title of the appointment
     * */
    String title;
    /**
     * A description of the appointment
     * */
    String description;
    /**
     * Where the appointment will be held
     * */
    String location;
    /**
     * The appointment type
     * */
    String type;

    /**
     * The appointment start time
     * */
    LocalDateTime startTime;

    /**
     * The appointment end time
     * */
    LocalDateTime endTime;

    /**
     * Constructor to instantiate an Appointment object.
     * */
    public Appointment(int appointmentId, String title, String description, String location, String type, LocalDateTime startTime, LocalDateTime endTime) {
        AppointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * @return the appointmentID
     * */
    public int getAppointmentId() {
        return AppointmentId;
    }

    /**
     * @param appointmentId sets the appointmentID
     * */
    public void setAppointmentId(int appointmentId) {
        AppointmentId = appointmentId;
    }

    /**
     * @return the appointment title
     * */
    public String getTitle() {
        return title;
    }

    /**
     * @param title sets the appointment title
     * */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the appointment description
     * */
    public String getDescription() {
        return description;
    }

    /**
     * @param description sets the appointment description
     * */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the appointment location
     * */
    public String getLocation() {
        return location;
    }

    /**
     * @param location sets the appointment location
     * */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the appointment type
     * */
    public String getType() {
        return type;
    }

    /**
     * @param type sets the appointment type
     * */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the appointment's start time
     * */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * @param startTime sets the appointment's start time
     * */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the appointment's end time
     * */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * @param endTime sets the appointment's end time
     * */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
