package com.example.projectamma.entities;

/* Imports */
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/** This class represents an appointment within the Appointment Management Mobile App.
 * @author Sage Ellefson */
@Entity(tableName="appointments")
public class Appointment {
    @PrimaryKey(autoGenerate = true)

    /** The ID for the appointment. */
    private int appointmentID;
    /** The type for the appointment. */
    private String appointmentType;
    /** The description for the appointment. */
    private String appointmentDescription;
    /** The location for the appointment. */
    private String appointmentLocation;
    /** The date for the appointment. */
    private String appointmentDate;
    /** The selected client ID to be associated with the appointment.*/
    private int selectedClientID;
    /** The start time for the appointment. */
    private String appointmentStartTime;
    /** The end time for the appointment. */
    private String appointmentEndTime;

    /** Constructor of an appointment instance.
     * @param appointmentID The ID for the appointment.
     * @param appointmentType The type for the appointment.
     * @param appointmentDescription The description for the appointment.
     * @param appointmentLocation The location of the appointment.
     * @param appointmentDate The date for the appointment.
     * @param selectedClientID The Id for the selected client associated with the appointment.
     * @param appointmentStartTime The start time for the appointment.
     * @param appointmentEndTime The end time for the appointment. */
    public Appointment(int appointmentID, String appointmentType, String appointmentDescription, String appointmentLocation, String appointmentDate, int selectedClientID,
    String appointmentStartTime, String appointmentEndTime)
    {
        this.appointmentID = appointmentID;
        this.appointmentType = appointmentType;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentDate = appointmentDate;
        this.selectedClientID = selectedClientID;
        this.appointmentStartTime = appointmentStartTime;
        this.appointmentEndTime = appointmentEndTime;
    }


    /* Getters */

    /** Getter for the appointment ID.
     * @return Returns ID of the appointment. */
    public int getAppointmentID() {
        return appointmentID;
    }

    /** Getter for the appointment type.
     * @return Returns type of the appointment. */
    public String getAppointmentType() {
        return appointmentType;
    }

    /** Getter for the appointment description.
     * @return Returns description of the appointment. */
    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    /** Getter for the appointment location.
     * @return Returns location of the appointment. */
    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    /** Getter for the appointment date.
     * @return Returns date of the appointment. */
    public String getAppointmentDate() {
        return appointmentDate;
    }

    /** Getter for the selected client ID.
     * @return Returns the selected client ID. */
    public int getSelectedClientID() {
        return selectedClientID;
    }

    /** Getter for the appointment start time.
     * @return Returns start time of the appointment. */
    public String getAppointmentStartTime() {
        return appointmentStartTime;
    }

    /** Getter for the appointment end time.
     * @return Returns end time of the appointment. */
    public String getAppointmentEndTime() {
        return appointmentEndTime;
    }


    /* Setters */

    /** Setter for the appointment ID.
     * @param appointmentID The appointment ID. */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /** Setter for the appointment type.
     * @param appointmentType The appointment type. */
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    /** Setter for the appointment description.
     * @param appointmentDescription The appointment description. */
    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    /** Setter for the appointment location.
     * @param appointmentLocation The appointment location. */
    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    /** Setter for the appointment date.
     * @param appointmentDate The appointment date. */
    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /** Setter for the selected client ID.
     * @param selectedClientID The client ID. */
    public void setSelectedClientID(int selectedClientID) {
        this.selectedClientID = selectedClientID;
    }

    /** Setter for the appointment start time.
     * @param appointmentStartTime The appointment start time. */
    public void setAppointmentStartTime(String appointmentStartTime) {
        this.appointmentStartTime = appointmentStartTime;
    }

    /** Setter for the appointment end time.
     * @param appointmentEndTime The appointment end time. */
    public void setAppointmentEndTime(String appointmentEndTime) {
        this.appointmentEndTime = appointmentEndTime;
    }

}
