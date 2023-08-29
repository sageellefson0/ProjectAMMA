package com.example.projectamma;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.example.projectamma.UI.AppointmentList;
import com.example.projectamma.database.Repository;
import com.example.projectamma.entities.Appointment;
import java.util.ArrayList;
import java.util.List;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class AppointmentListTest {

    @Mock
    private Repository mockRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPerformSearch() {
        List<Appointment> allAppointments = new ArrayList<>();
        allAppointments.add(new Appointment(1, "Type1", "Description1", "Location1", "08-23-2023", 1, "11:00:00", "12:00:00"));
        allAppointments.add(new Appointment(2, "Type2", "Description2", "Location2", "08-23-2023", 2, "11:00:00", "12:00:00"));
        allAppointments.add(new Appointment(3, "Type3", "Description3", "Location3", "08-23-2023", 3, "11:00:00", "12:00:00"));
        Mockito.when(mockRepository.getAllAppointments()).thenReturn(allAppointments);

        // Launches the AppointmentList activity
        ActivityScenario.launch(AppointmentList.class);

        // Perform a search using espresso
        onView(withId(R.id.textViewSearch)).perform(typeText("Type1"));
        onView(withId(R.id.buttonSearch)).perform(click());

    }
}
