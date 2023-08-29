package com.example.projectamma.entities;

/* Imports */
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/** This class represents a user within the Appointment Management Mobile App.
 * @author Sage Ellefson */
@Entity(tableName="users")
public class User {
    @PrimaryKey(autoGenerate = true)

    /** The user ID. */
    private int userID;
    /** The username for an account. */
    private String username;
    /** The password for an account. */
    private String password;

    /** This is the constructor for a user instance.
     * @param userID The user ID for a user.
     * @param username The username for a user.
     * @param password The password for a user. */
    public User(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    /* Getters */

    /** Getter for the user ID.
     * @return Returns the user ID. */
    public int getUserID() {
        return userID;
    }

    /** Getter for the username.
     * @return Returns the username. */
    public String getUsername() {
        return username;
    }

    /** Getter for the user password.
     * @return Returns the user password. */
    public String getPassword() {
        return password;
    }


    /* Setters */

    /** Setter for the user ID.
     * @param userID The user ID. */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /** Setter for the username.
     * @param username The username. */
    public void setUsername(String username) {
        this.username = username;
    }

    /** Setter for the user password.
     * @param password The user password. */
    public void setPassword(String password) {
        this.password = password;
    }
}


