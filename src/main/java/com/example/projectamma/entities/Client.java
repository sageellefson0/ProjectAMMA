package com.example.projectamma.entities;

/* Imports */
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/** This class represents a client within the Appointment Management Mobile App.
 * @author Sage Ellefson */
@Entity(tableName="clients")
public abstract class Client {
    @PrimaryKey(autoGenerate = true)

/** The ID for the client. */
    protected int clientID;
    /** The name for the client. */
    private String clientName;
    /** The number for the client. */
    private String clientNumber;
    /** The end date for the client. */
    private String clientEmail;
    /** The address for the client. */
    private String clientAddress;

    /** Constructor of an client instance.
     * @param clientID The ID for the client.
     * @param clientName The name for the client.
     * @param clientNumber The number for the client.
     * @param clientEmail The email for the client.
     * @param clientAddress The address for the client. */
    public Client(int clientID, String clientName, String clientNumber, String clientEmail, String clientAddress) {
        this.clientID = clientID;
        this.clientName = clientName;
        this.clientNumber = clientNumber;
        this.clientEmail = clientEmail;
        this.clientAddress = clientAddress;
    }


    /* Getters */

    /** Getter for the client ID.
     * @return Returns ID of the client. */
    public int getClientID() {
        return clientID;
    }

    /** Getter for the client name.
     * @return Returns name of the client. */
    public String getClientName() {
        return clientName;
    }

    /** Getter for the client number.
     * @return Returns number of the client. */
    public String getClientNumber() {
        return clientNumber;
    }

    /** Getter for the client email.
     * @return Returns email of the client. */
    public String getClientEmail() {
        return clientEmail;
    }

    /** Getter for the client address.
     * @return Returns address of the client. */
    public String getClientAddress() {
        return clientAddress;
    }


    /* Setters */

    /** Setter for the client ID.
     * @param clientID The client ID. */
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    /** Setter for the client name.
     * @param clientName The client name. */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /** Setter for the client number.
     * @param clientNumber The client number. */
    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    /** Setter for the client email.
     * @param clientEmail The client email. */
    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    /** Setter for the client address.
     * @param clientAddress The client address. */
    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }
}
