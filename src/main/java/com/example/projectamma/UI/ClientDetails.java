package com.example.projectamma.UI;

/* Imports */
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projectamma.R;
import com.example.projectamma.database.Repository;
import com.example.projectamma.entities.Client;
import com.example.projectamma.entities.CorporateClient;
import com.example.projectamma.entities.IndividualClient;


public class ClientDetails extends AppCompatActivity {

    /* Various entry fields. */
    int clientID;
    EditText editName;
    EditText editEmail;
    EditText editNumber;
    EditText editAddress;
    EditText editClientTypeResponse;
    String clientName;
    String clientEmail;
    String clientNumber;
    String clientAddress;
    String clientOccupation;
    String clientIndustry;
    Client client;
    CorporateClient corporateClient;
    IndividualClient individualClient;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sets the view.
        setContentView(R.layout.detailed_view_clients);

        editName = findViewById(R.id.textViewBlankName);
        editEmail = findViewById(R.id.textViewBlankEmail);
        editNumber = findViewById(R.id.textViewBlankNumber);
        editAddress = findViewById(R.id.textViewBlankAddress);
        editClientTypeResponse = findViewById((R.id.textViewChangingBlank));

        RadioGroup radioGroup = findViewById(R.id.radioButtonGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonCorporateClient) {
                    // Changes the text in textViewChanging.
                    TextView textViewChanging = findViewById(R.id.textViewChanging);
                    editClientTypeResponse.setVisibility(View.VISIBLE);
                    editClientTypeResponse.setHint("Industry");
                    textViewChanging.setText("Industry");
                } else if (checkedId == R.id.radioButtonIndividualClient) {
                    // Changes the text in textViewChanging.
                    TextView textViewChanging = findViewById(R.id.textViewChanging);
                    editClientTypeResponse.setVisibility(View.VISIBLE);
                    editClientTypeResponse.setHint("Occupation");
                    textViewChanging.setText("Occupation");
                }
            }
        });

        repository = new Repository(getApplication());

        Intent intent = getIntent();
        clientID = intent.getIntExtra("clientID", -1);


        // Check if it's an existing appointment and loads data from the database.
        if (clientID != -1) {
            radioGroup.setVisibility(View.GONE);
            editClientTypeResponse.setVisibility(View.VISIBLE);
            corporateClient = repository.getCorporateClientByID(clientID);
            individualClient = repository.getIndividualClientByID(clientID);
            TextView textViewChanging = findViewById(R.id.textViewChanging);


            if (corporateClient != null) {
                client = corporateClient;
                textViewChanging.setText("Industry");

                // Load data for CorporateClient
                clientName = corporateClient.getClientName();
                editName.setText(clientName);

                clientEmail = corporateClient.getClientEmail();
                editEmail.setText(clientEmail);

                clientNumber = corporateClient.getClientNumber();
                editNumber.setText(clientNumber);

                clientAddress = corporateClient.getClientAddress();
                editAddress.setText(clientAddress);

                clientIndustry = corporateClient.getClientIndustry();
                editClientTypeResponse.setText(clientIndustry);

            } else if (individualClient != null) {

                client = individualClient;
                textViewChanging.setText("Occupation");

                // Load data for IndividualClient
                clientName = individualClient.getClientName();
                editName.setText(clientName);

                clientEmail = individualClient.getClientEmail();
                editEmail.setText(clientEmail);

                clientNumber = individualClient.getClientNumber();
                editNumber.setText(clientNumber);

                clientAddress = individualClient.getClientAddress();
                editAddress.setText(clientAddress);

                clientOccupation = individualClient.getClientOccupation();
                editClientTypeResponse.setText(clientOccupation);
            }
        } else {
            radioGroup.setVisibility(View.VISIBLE);

            clientID = getIntent().getIntExtra("clientID", -1);

            clientName = getIntent().getStringExtra("clientName");
            editName.setText(clientName);

            clientEmail = getIntent().getStringExtra("clientEmail");
            editEmail.setText(clientEmail);

            clientNumber = getIntent().getStringExtra("clientNumber");
            editNumber.setText(clientNumber);

            clientAddress = getIntent().getStringExtra("clientAddress");
            editAddress.setText(clientAddress);


            if (client instanceof IndividualClient) {
                clientOccupation = getIntent().getStringExtra("clientOccupation");
                editClientTypeResponse.setText(clientOccupation);

            } else if (client instanceof CorporateClient) {
                clientIndustry = getIntent().getStringExtra("clientIndustry");
                editClientTypeResponse.setText(clientIndustry);

            }
        }


        // Save button
        Button button = findViewById(R.id.buttonSaveClient);
        button.setOnClickListener(new View.OnClickListener() {
            /** This method is for the client save button.
             * @param view The view that was clicked. */
            @Override
            public void onClick(View view) {
                RadioGroup radioGroup = findViewById(R.id.radioButtonGroup);
                int checkedID = radioGroup.getCheckedRadioButtonId();

                if (checkedID == -1 && clientID == -1) {
                    Toast.makeText(ClientDetails.this, "Please select whether this is a corporate or individual client.", Toast.LENGTH_LONG).show();
                    return; // Exit the onClick handler
                }

                if (clientID == -1)  {
                    if (isFieldEmpty()) {
                        String clientType = ""; // Initialize the client type

                        if (checkedID == R.id.radioButtonCorporateClient) {
                            clientType = "Corporate"; // Set the client type to Corporate
                            clientIndustry = editClientTypeResponse.getText().toString(); // Retrieve industry value
                        } else if (checkedID == R.id.radioButtonIndividualClient) {
                            clientType = "Individual"; // Set the client type to Individual
                            clientOccupation = editClientTypeResponse.getText().toString(); // Retrieve occupation value
                        }

                        //Create the appropriate type of client based on the selected radio button
                        if (clientType.equals("Corporate")) {
                            corporateClient = new CorporateClient(0, editName.getText().toString(), editNumber.getText().toString(), editEmail.getText().toString(), editAddress.getText().toString(), editClientTypeResponse.getText().toString());
                            repository.insertClient(corporateClient);
                            repository.insertCorporateClient(corporateClient);
                        } else if (clientType.equals("Individual")) {
                            individualClient = new IndividualClient(0, editName.getText().toString(), editNumber.getText().toString(), editEmail.getText().toString(), editAddress.getText().toString(), editClientTypeResponse.getText().toString());
                            repository.insertClient(individualClient);
                            repository.insertIndividualClient(individualClient);
                        }

                        Toast.makeText(ClientDetails.this, "The client has been added.", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(ClientDetails.this, "All fields must be filled out before saving.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (isFieldEmpty()) {
                        String clientType = ""; // Initialize the client type

                        if (client instanceof CorporateClient) {
                            clientType = "Corporate";
                            corporateClient = (CorporateClient) client;
                        } else if (client instanceof IndividualClient) {
                            clientType = "Individual";
                            individualClient = (IndividualClient) client;
                        }

                        // Create the appropriate type of client based on the selected radio button
                        if (clientType.equals("Corporate")) {
                            corporateClient = new CorporateClient(clientID, editName.getText().toString(), editNumber.getText().toString(), editEmail.getText().toString(), editAddress.getText().toString(), editClientTypeResponse.getText().toString());
                            repository.updateCorporateClient(corporateClient);
                        } else if (clientType.equals("Individual")) {
                            individualClient = new IndividualClient(clientID, editName.getText().toString(), editNumber.getText().toString(), editEmail.getText().toString(), editAddress.getText().toString(), editClientTypeResponse.getText().toString());
                            repository.updateIndividualClient(individualClient);
                        }

                        Toast.makeText(ClientDetails.this, "The client has been updated.", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(ClientDetails.this, "All fields must be filled out before saving.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


/////////// Validation methods //////////

    /** This method validates that no field is empty. */
    private boolean isFieldEmpty(){
        String clientName=editName.getText().toString();
        String clientEmail=editEmail.getText().toString();
        String clientNumber=editNumber.getText().toString();
        String clientAddress=editAddress.getText().toString();
        String clientTypeResponse = editClientTypeResponse.getText().toString();

        return !clientName.isEmpty() && !clientEmail.isEmpty() && !clientNumber.isEmpty()
                && !clientAddress.isEmpty() && !clientTypeResponse.isEmpty();
    }


//////// Menu methods ///////////

    /** This method creates the menu options.
     * @param menu_detailed The menu being displayed. */
    public boolean onCreateOptionsMenu(Menu menu_detailed) {
        getMenuInflater().inflate(R.menu.menu_delete_client, menu_detailed);

        return true;
    }

    /** Called to prepare the options menu before it is displayed.
     * @param menu_detailed The options menu. */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu_detailed) {
        if (clientID != -1) {
            menu_detailed.findItem(R.id.menuDeleteClient).setVisible(true);
        }
        else {
            menu_detailed.findItem(R.id.menuDeleteClient).setVisible(false);

        }
        return super.onPrepareOptionsMenu(menu_detailed);
    }

    /** This method handles the different menu selections.
     * @param menu_selection The item being selected from the menu.
     * @return Returns the selected menu item.*/
    @Override
    public boolean onOptionsItemSelected(MenuItem menu_selection) {
        if (menu_selection.getItemId() == R.id.menuDeleteClient) {
            if (clientID != -1) {
                repository.getAppointmentsByClientID(clientID).observe(this, appointments -> {
                    if (appointments != null && !appointments.isEmpty()) {
                        Toast.makeText(this, "Client cannot be deleted as it is associated with an appointment. Please remove the client from the appointment and try again.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (client instanceof CorporateClient) {
                            repository.deleteCorporateClient(corporateClient);
                            Toast.makeText(this, "The client has been deleted.", Toast.LENGTH_SHORT).show();
                        } else if (client instanceof IndividualClient) {
                            repository.deleteIndividualClient(individualClient);
                            Toast.makeText(this, "The client has been deleted.", Toast.LENGTH_SHORT).show();
                        }
                        // Navigates to previous page.
                        finish();
                    }
                });
            } else {
                Toast.makeText(this, "The client could not be deleted at this time.", Toast.LENGTH_SHORT).show();
                // Navigates to previous page.
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(menu_selection);
    }
}



