package com.example.projectamma.UI;

/* Imports */
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectamma.R;
import com.example.projectamma.database.Repository;
import com.example.projectamma.entities.Client;
import java.util.List;


/** This activity is for the client list view.
 * Sets the recycler view to display a list of clients and includes menu options to add clients.
 * @author Sage Ellefson */
public class ClientsList extends AppCompatActivity {
    private Repository repository;

    /** Called when the activity is starting. This is where the layout is initialized for the client list.
     * @param savedInstanceState Bundle containing saved instance. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sets the content view.
        setContentView(R.layout.list_of_clients);
        repository=new Repository(getApplication());

        // Creates a list of all clients.
        List<Client> allClients=repository.getAllClients();

        // Sets the recycler view.
        RecyclerView recyclerView=findViewById(R.id.recyclerViewClients);
        final ClientAdapter clientAdapter = new ClientAdapter(this);
        recyclerView.setAdapter(clientAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        clientAdapter.setClients(allClients);

        // Button to take you to the appointments list from the clients list page.
        Button buttonClients = findViewById(R.id.buttonViewAppointments);
        buttonClients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientsList.this, AppointmentList.class);
                startActivity(intent);
            }
        });
    }


//////// Menu methods ///////////

    /** This method creates the menu options.
     * @param menu_add The menu being displayed. */
    public boolean onCreateOptionsMenu(Menu menu_add){
        getMenuInflater().inflate(R.menu.menu_add_client, menu_add);
        return true;
    }

    /** This method handles the different menu selections.
     * @param item The item being selected from the menu.
     * @return Returns the selected menu item.*/
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuAddClient) {
            Intent addClientIntent = new Intent(this, ClientDetails.class);
            startActivity(addClientIntent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /** This method handles the resuming of the recycler view to keep it up to date. */
    @Override
    protected void onResume() {
        super.onResume();
        List<Client> allClients=repository.getAllClients();
        RecyclerView recyclerView=findViewById(R.id.recyclerViewClients);
        final ClientAdapter clientAdapter=new ClientAdapter(this);
        recyclerView.setAdapter(clientAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        clientAdapter.setClients(allClients);
    }
}
