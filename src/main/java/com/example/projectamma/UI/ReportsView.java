package com.example.projectamma.UI;

/* Imports */
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectamma.R;
import com.example.projectamma.database.Repository;
import com.example.projectamma.entities.Appointment;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** This activity is for the report view.
 * Generates reports for appointments by type and total number of appointments for a specific type of appointment.
 * @author Sage Ellefson */
public class ReportsView extends AppCompatActivity {

    TextView totalAppointments;
    Spinner spinnerMonth;
    Repository repository;
    private AppointmentAdapter appointmentAdapter;

    /** Called when the activity is starting. This is where the layout is initialized for the report view page.
     * @param savedInstanceState Bundle containing saved instance. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.reports_view);

        totalAppointments = findViewById(R.id.textViewTotalCount);

        repository = new Repository(getApplication());

        appointmentAdapter = new AppointmentAdapter(this);

        // Spinner
        spinnerMonth = findViewById(R.id.spinnerMonth);
        populateSpinner();
    }

    /** This method populates the spinner and sets the months list. */
    private void populateSpinner() {
        List<String> months  = new ArrayList<>();

        // Adds the "Select Month" option and the 12 months of the year.
        months.add("Select Month");
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

        Map<String, String> monthNameToFormatMap = new HashMap<>();
        monthNameToFormatMap.put("January", "01");
        monthNameToFormatMap.put("February", "02");
        monthNameToFormatMap.put("March", "03");
        monthNameToFormatMap.put("April", "04");
        monthNameToFormatMap.put("May", "05");
        monthNameToFormatMap.put("June", "06");
        monthNameToFormatMap.put("July", "07");
        monthNameToFormatMap.put("August", "08");
        monthNameToFormatMap.put("September", "09");
        monthNameToFormatMap.put("October", "10");
        monthNameToFormatMap.put("November", "11");
        monthNameToFormatMap.put("December", "12");

        NoSelectionSpinnerAdapter adapter = new NoSelectionSpinnerAdapter(this, android.R.layout.simple_spinner_item, months);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerMonth.setAdapter(adapter);

        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedMonthName = parent.getItemAtPosition(position).toString();

                if (!selectedMonthName.equals("Select Month")) {
                    String selectedMonthFormat = monthNameToFormatMap.get(selectedMonthName);
                    List<Appointment> appointments = repository.getAppointmentsByMonth(selectedMonthFormat);


                    RecyclerView recyclerView=findViewById(R.id.recyclerViewAppointmentsReports);
                    appointmentAdapter = new AppointmentAdapter(ReportsView.this);
                    recyclerView.setAdapter(appointmentAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ReportsView.this));
                    appointmentAdapter.setAppointments(appointments);

                    // Update the format of the date-time stamp as needed (e.g., "MM/dd/yyyy HH:mm")
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

                    // Create a list of strings that contains the formatted date-time stamps
                    List<String> dateTimeStamps = new ArrayList<>();
                    for (Appointment appointment : appointments) {
                        String dateTimeStamp = appointment.getAppointmentDate() + " " + appointment.getAppointmentStartTime();
                        dateTimeStamps.add(dateTimeStamp);
                    }


                    if (appointments.isEmpty()) {
                        Toast.makeText(ReportsView.this, "There were no appointments found for the selected month.", Toast.LENGTH_LONG).show();
                    }
                    int totalAppointmentsCount = appointments.size();
                    totalAppointments.setText("Total Appointments For " + selectedMonthName + ": " + totalAppointmentsCount);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }
}

