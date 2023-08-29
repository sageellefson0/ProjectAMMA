package com.example.projectamma.entities;

/* Imports */
import androidx.room.Entity;
import androidx.room.ForeignKey;


/** This class represents an extension of the client class within the Appointment Management Mobile App.
 * @author Sage Ellefson */
@Entity(tableName="corporate_clients",
        foreignKeys = @ForeignKey(entity = Client.class,
                parentColumns = "clientID",
                childColumns = "clientID"))

public class CorporateClient extends Client {

    /** The industry of the client. */
    private String clientIndustry;

    /** Constructor of a client instance.
     * @param clientID The ID for the client.
     * @param clientName The name for the client.
     * @param clientEmail The end date for the client.
     * @param clientNumber The number for the client.
     * @param clientAddress The address for the client.
     * @param clientIndustry The industry of the client. */
    public CorporateClient(int clientID, String clientName, String clientNumber, String clientEmail,String clientAddress,  String clientIndustry) {
        super(clientID, clientName, clientNumber, clientEmail, clientAddress);
        this.clientIndustry = clientIndustry; }

    /** Getter for the clients industry.
     * @return Returns the industry of the client. */
    public String getClientIndustry(){
        return clientIndustry;
    }

    /** Setter for industry of the client.
     * @param clientIndustry The industry of the client. */
    public void setClientIndustry(String clientIndustry){
        this.clientIndustry = clientIndustry;
    }
}