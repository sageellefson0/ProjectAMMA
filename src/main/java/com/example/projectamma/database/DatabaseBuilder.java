package com.example.projectamma.database;

/* Imports */
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.projectamma.dao.AppointmentDAO;
import com.example.projectamma.dao.ClientDAO;
import com.example.projectamma.dao.UserDAO;
import com.example.projectamma.entities.Appointment;
import com.example.projectamma.entities.Client;
import com.example.projectamma.entities.CorporateClient;
import com.example.projectamma.entities.IndividualClient;
import com.example.projectamma.entities.User;


/** The database builder class.
 * @author Sage Ellefson */
@Database(entities = {Appointment.class, Client.class, IndividualClient.class, CorporateClient.class, User.class}, version = 2,exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {

    /** Gets the DAO for appointment. */
    public abstract AppointmentDAO appointmentDAO();

    /** Gets the DAO for client. */
    public abstract ClientDAO clientDAO();

    /** Gets the DAO for the user.*/
    public abstract UserDAO userDAO();

    private static volatile DatabaseBuilder INSTANCE;

    /** Gets the instance of the database.
     * @param context The application context.
     * @return The DatabaseBuilder instance. */
    static DatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "AMMADatabaseBuilder.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

