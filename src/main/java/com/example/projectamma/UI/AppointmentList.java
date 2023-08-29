package com.example.projectamma.UI;

/* Imports */
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectamma.R;
import com.example.projectamma.database.Repository;
import com.example.projectamma.entities.Appointment;
import com.example.projectamma.entities.Client;
import java.util.ArrayList;
import java.util.List;


/** This activity is for the appointment list view.
 * Sets the recycler view to display a list of appointment and includes menu options to add appointments.
 * @author Sage Ellefson */
public class AppointmentList extends AppCompatActivity {
    public Repository repository;
    EditText searchText;
    Button buttonSearch;
    public AppointmentAdapter appointmentAdapter;

    /** Called when the activity is starting. This is where the layout is initialized for the appointment list.
     * @param savedInstanceState Bundle containing saved instance. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sets the content view.
        setContentView(R.layout.list_of_appointments);
        repository=new Repository(getApplication());

        // Creates a list of all appointments.
        List<Appointment> allAppointments=repository.getAllAppointments();
        appointmentAdapter = new AppointmentAdapter(this);

        searchText = findViewById(R.id.textViewSearch);
        buttonSearch = findViewById(R.id.buttonSearch);

        // Sets the recycler view.
        RecyclerView recyclerView=findViewById(R.id.recyclerViewAppointments);
        appointmentAdapter = new AppointmentAdapter(this);
        recyclerView.setAdapter(appointmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        appointmentAdapter.setAppointments(allAppointments);

        // Button to take you to the clients list from the appointments list page.
        Button buttonClients = findViewById(R.id.buttonViewClients);
        buttonClients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppointmentList.this, ClientsList.class);
                startActivity(intent);
            }
        });

        // Button to take you to the report page from the appointments list page.
        Button buttonReports = findViewById(R.id.buttonViewReportsAppointments);
        buttonReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppointmentList.this, ReportsView.class);
                startActivity(intent);
            }
        });

        // Button to take you to search the appointment list.
        Button buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchStringAppointment = searchText.getText().toString();
                performSearch(searchStringAppointment);
            }
        });
    }

    /** This method performs a search of the appointment recycler view.
     * @param query The query being entered. */
    public void performSearch(String query) {
        List<Appointment> allAppointments = repository.getAllAppointments();
        List<Appointment> searchResults = new ArrayList<>();

        for (Appointment appointment : allAppointments) {
            if (String.valueOf(appointment.getAppointmentType()).contains(query)) {
                searchResults.add(appointment);
            }
        }

        if (searchResults.isEmpty()) {
            Toast.makeText(this, "No appointment with that type can be found.", Toast.LENGTH_SHORT).show();
            appointmentAdapter.setAppointments(allAppointments);
            searchText.setText(""); // Clear the search text
        } else {
            appointmentAdapter.setAppointments(searchResults);
        }
    }


//////// Menu methods ///////////

    /** This method creates the menu options.
     * @param menu_add The menu being displayed. */
    public boolean onCreateOptionsMenu(Menu menu_add){
        getMenuInflater().inflate(R.menu.menu_add_appointment, menu_add);
        return true;
    }

    /** This method handles the different menu selections.
     * @param item The item being selected from the menu.
     * @return Returns the selected menu item.*/
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuAddAppointment) {
            List<Client> clients = repository.getAllClients();
            if (clients.isEmpty()) {
                Toast.makeText(this, "Please add a client before adding an appointment.", Toast.LENGTH_LONG).show();
            } else {
                Intent addAppointmentIntent = new Intent(this, AppointmentDetails.class);
                startActivity(addAppointmentIntent);
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    /** This method handles the resuming of the recycler view to keep it up to date. */
    @Override
    protected void onResume() {
        super.onResume();
        List<Appointment> allAppointments=repository.getAllAppointments();
        RecyclerView recyclerView=findViewById(R.id.recyclerViewAppointments);
        appointmentAdapter.setAppointments(allAppointments);
    }
}
