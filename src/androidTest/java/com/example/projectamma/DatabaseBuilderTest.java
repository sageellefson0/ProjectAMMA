package com.example.projectamma;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import com.example.projectamma.dao.AppointmentDAO;
import com.example.projectamma.dao.UserDAO;
import com.example.projectamma.database.DatabaseBuilder;
import com.example.projectamma.entities.Appointment;
import com.example.projectamma.entities.User;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.io.IOException;
import java.util.List;


@RunWith(AndroidJUnit4.class)
public class DatabaseBuilderTest {
        private UserDAO userDAO;
        private AppointmentDAO appointmentDAO;
        private DatabaseBuilder database;

        @Before
        public void createDB() {
                Context context = ApplicationProvider.getApplicationContext();
                database = Room.inMemoryDatabaseBuilder(context, DatabaseBuilder.class).build();
                userDAO = database.userDAO();
                appointmentDAO = database.appointmentDAO();

        }

        @Test
        public void insertUserIntoDatabase() throws Exception {
                // Create a new user
                User user = new User(1, "testUser", "testPassword");

                // Insert the user
                userDAO.insertUser(user);

                // Retrieve the user by username
                List<User> byName = userDAO.getUserByUsername("testUser");

                // Check if the retrieved user matches
                assertThat(byName.size(), equalTo(1));
                assertThat(byName.get(0).getUsername(), equalTo("testUser"));
                assertThat(byName.get(0).getPassword(), equalTo("testPassword"));
        }

        @Test
        public void insertAppointmentIntoDatabase() throws Exception {
                // Create a new appointment
                Appointment appointment = new Appointment(1, "TestType", "TestDescription", "TestLocation",
                        "08/27/2023", 1, "11:00:00", "12:00:00");

                // Insert the appointment
                appointmentDAO.insert(appointment);

                // Retrieve the appointment by ID
                Appointment retrievedAppointment = appointmentDAO.getAppointmentByID(1);

                assertNotNull(retrievedAppointment);
                assertEquals(appointment.getAppointmentID(), retrievedAppointment.getAppointmentID());
                assertEquals(appointment.getAppointmentType(), retrievedAppointment.getAppointmentType());
                assertEquals(appointment.getAppointmentDescription(), retrievedAppointment.getAppointmentDescription());
                assertEquals(appointment.getAppointmentLocation(), retrievedAppointment.getAppointmentLocation());
                assertEquals(appointment.getAppointmentDate(), retrievedAppointment.getAppointmentDate());
                assertEquals(appointment.getSelectedClientID(), retrievedAppointment.getSelectedClientID());
                assertEquals(appointment.getAppointmentStartTime(), retrievedAppointment.getAppointmentStartTime());
                assertEquals(appointment.getAppointmentEndTime(), retrievedAppointment.getAppointmentEndTime());
        }

        @Test
        public void deleteAppointmentFromDatabase() throws Exception {
                // Create a new appointment
                Appointment appointment = new Appointment(1, "TestType", "TestDescription", "TestLocation",
                        "08/27/2023", 1, "11:00:00", "12:00:00");

                // Insert the appointment
                appointmentDAO.insert(appointment);

                assertNotNull(appointmentDAO.getAppointmentByID(1));

                // Delete the appointment
                appointmentDAO.delete(appointment);

                // Try to get the deleted appointment
                Appointment deletedAppointment = appointmentDAO.getAppointmentByID(1);

                // Check if the appointment is null after deletion
                assertNull(deletedAppointment);
        }

        @Test
        public void editAppointmentInDatabase() throws Exception {
                // Create a new appointment
                Appointment originalAppointment = new Appointment(1, "TestType", "TestDescription", "TestLocation",
                        "08/27/2023", 1, "11:00:00", "12:00:00");

                // Insert the original appointment
                appointmentDAO.insert(originalAppointment);

                // Retrieve the original appointment by ID
                Appointment retrievedAppointment = appointmentDAO.getAppointmentByID(1);

                assertNotNull(retrievedAppointment);
                assertEquals(originalAppointment.getAppointmentType(), retrievedAppointment.getAppointmentType());
                assertEquals(originalAppointment.getAppointmentDescription(), retrievedAppointment.getAppointmentDescription());
                assertEquals(originalAppointment.getAppointmentLocation(), retrievedAppointment.getAppointmentLocation());
                assertEquals(originalAppointment.getAppointmentDate(), retrievedAppointment.getAppointmentDate());
                assertEquals(originalAppointment.getSelectedClientID(), retrievedAppointment.getSelectedClientID());
                assertEquals(originalAppointment.getAppointmentStartTime(), retrievedAppointment.getAppointmentStartTime());
                assertEquals(originalAppointment.getAppointmentEndTime(), retrievedAppointment.getAppointmentEndTime());

                // Modify the appointment
                retrievedAppointment.setAppointmentType("UpdatedType");
                retrievedAppointment.setAppointmentDescription("UpdatedDescription");
                retrievedAppointment.setAppointmentLocation("UpdatedLocation");
                retrievedAppointment.setAppointmentDate("09/01/2023");
                retrievedAppointment.setSelectedClientID(2);
                retrievedAppointment.setAppointmentStartTime("13:00:00");
                retrievedAppointment.setAppointmentEndTime("14:00:00");

                appointmentDAO.update(retrievedAppointment);

                Appointment editedAppointment = appointmentDAO.getAppointmentByID(1);

                assertNotNull(editedAppointment);
                assertEquals("UpdatedType", editedAppointment.getAppointmentType());
                assertEquals("UpdatedDescription", editedAppointment.getAppointmentDescription());
                assertEquals("UpdatedLocation", editedAppointment.getAppointmentLocation());
                assertEquals("09/01/2023", editedAppointment.getAppointmentDate());
                assertEquals(2, editedAppointment.getSelectedClientID());
                assertEquals("13:00:00", editedAppointment.getAppointmentStartTime());
                assertEquals("14:00:00", editedAppointment.getAppointmentEndTime());
        }

        @After
        public void closeDb() throws IOException {
                database.close();
        }
}
