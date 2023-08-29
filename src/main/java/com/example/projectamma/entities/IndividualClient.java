package com.example.projectamma.entities;

/* Imports */
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;


/** This class represents an extension of the client class within the Appointment Management Mobile App.
 * @author Sage Ellefson */
@Entity(tableName="individual_clients",
        foreignKeys = @ForeignKey(entity = Client.class,
                parentColumns = "clientID",
                childColumns = "clientID"))

public class IndividualClient extends Client {

    /** The occupation of the client. */
    private String clientOccupation;

    /** Constructor of a client instance.
     * @param clientID     The ID for the client.
     * @param clientName   The name for the client.
     * @param clientNumber The number for the client.
     * @param clientEmail  The end date for the client.
     * @param clientAddress The address for the client.
     * @param clientOccupation The occupation of the client. */
    public IndividualClient(int clientID, String clientName, String clientNumber, String clientEmail, String clientAddress, String clientOccupation) {
        super(clientID, clientName, clientNumber, clientEmail,clientAddress);
        this.clientOccupation = clientOccupation; }

    /** Getter for the clients occupation.
     * @return Returns the occupation of the client. */
    public String getClientOccupation(){
        return clientOccupation;
    }

    /** Setter for the clients occupation.
     * @param clientOccupation The occupation of the client. */
    public void setClientOccupation(String clientOccupation){
        this.clientOccupation = clientOccupation;
    }
}