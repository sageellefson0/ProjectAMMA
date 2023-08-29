package com.example.projectamma;

import com.example.projectamma.entities.Appointment;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class AppointmentTest {
        private Appointment appointment;

        @Before
        public void setUp() {
            appointment = new Appointment(1, "TestType", "TestDesc", "TestLocation", "08-23-2023", 1, "11:00:00", "12:00:00"
            );
        }
        @Test
        public void testGetters() {
            assertEquals(1, appointment.getAppointmentID());
            assertEquals("TestType", appointment.getAppointmentType());
            assertEquals("TestDesc", appointment.getAppointmentDescription());
            assertEquals("TestLocation", appointment.getAppointmentLocation());
            assertEquals("08-23-2023", appointment.getAppointmentDate());
            assertEquals(1, appointment.getSelectedClientID());
            assertEquals("11:00:00", appointment.getAppointmentStartTime());
            assertEquals("12:00:00", appointment.getAppointmentEndTime());
        }

        @Test
        public void testSetters() {
            appointment.setAppointmentID(2);
            appointment.setAppointmentType("TestType");
            appointment.setAppointmentDescription("TestDesc");
            appointment.setAppointmentLocation("TestLocation");
            appointment.setAppointmentDate("08-23-2023");
            appointment.setSelectedClientID(1);
            appointment.setAppointmentStartTime("11:00:00");
            appointment.setAppointmentEndTime("12:00:00");

            assertEquals(2, appointment.getAppointmentID());
            assertEquals("TestType", appointment.getAppointmentType());
            assertEquals("TestDesc", appointment.getAppointmentDescription());
            assertEquals("TestLocation", appointment.getAppointmentLocation());
            assertEquals("08-23-2023", appointment.getAppointmentDate());
            assertEquals(1, appointment.getSelectedClientID());
            assertEquals("11:00:00", appointment.getAppointmentStartTime());
            assertEquals("12:00:00", appointment.getAppointmentEndTime());
        }
    }
