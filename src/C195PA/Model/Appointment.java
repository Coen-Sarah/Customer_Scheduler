package C195PA.Model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Provides storage for appointment information
 * */
public class Appointment {
    /**
     * The appointment identifier
     * */
    int appointmentId;
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

    int customerId;
    int contactId;
    int userId;

    /**
     * Constructor to instantiate an Appointment object.
     * */
    public Appointment(int appointmentId,
                       String title,
                       String description,
                       String location,
                       String type,
                       LocalDateTime startTime,
                       LocalDateTime endTime,
                       int customerId,
                       int contactId,
                       int userId) {
        this.appointmentId = appointmentId;
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
        return appointmentId;
    }

    /**
     * @param appointmentId sets the appointmentID
     * */
    public void setAppointmentId(int appointmentId) {
        appointmentId = appointmentId;
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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullDescription(){
        String fullDescription = "Description: " + description + "\n" +
                                 "Location: " + location + "\n" +
                                 "Type: " + type + "\n";
        return fullDescription;

    }
    public String getInviteList(){
        String inviteList = "Customer ID: " + customerId + "\n" +
                            "Contact ID: " + contactId + "\n" +
                            "User ID " + userId + "\n";
        return inviteList;
    }

    public String getFormattedStartTime(){
        ZonedDateTime utcTime = startTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime userZonedTime = utcTime.withZoneSameInstant(ZoneId.systemDefault());
        String formattedStartTime = userZonedTime.format(DateTimeFormatter.ofPattern("MM/dd/YY\nHH:mm zz"));

        return formattedStartTime;
    }
    public String getFormattedEndTime(){
        ZonedDateTime utcTime = endTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime userZonedTime = utcTime.withZoneSameInstant(ZoneId.systemDefault());
        String formattedEndTime = userZonedTime.format(DateTimeFormatter.ofPattern("MM/dd/YY\nHH:mm zz"));

        return formattedEndTime;
    }
}
