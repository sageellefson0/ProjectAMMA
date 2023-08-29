package com.example.projectamma.dao;

/* Imports */
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.projectamma.entities.Appointment;
import java.util.List;


/** The database interface class for appointments.
 * @author Sage Ellefson */
@Dao
public interface AppointmentDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Appointment appointment);

    @Update
    void update(Appointment appointment);

    @Delete
    void delete(Appointment appointment);


    @Query("SELECT * FROM appointments ORDER BY appointmentID ASC")
    List<Appointment> getAllAppointments();

    @Query("SELECT * FROM appointments WHERE appointmentID = :appointmentID")
    Appointment getAppointmentByID(int appointmentID);

    @Query("SELECT * FROM appointments WHERE SUBSTR(appointmentDate, 1, 2) = :selectedMonth")
    List<Appointment> getAppointmentsByMonth(String selectedMonth);

    @Query("SELECT * FROM appointments WHERE selectedClientID = :clientID")
    LiveData<List<Appointment>> getAppointmentsByClientID(int clientID);


}