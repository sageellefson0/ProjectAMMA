package com.example.projectamma.UI;

/* Imports */
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projectamma.R;
import com.example.projectamma.database.Repository;


/** MainActivity, starts the program and initializes the screen to display the activity_main layout.
 * Login screen of the application, has a button that allows you to create a user and login.
 * @author Sage Ellefson */
public class MainActivity extends AppCompatActivity {
    public static int numAlert;
    EditText editUsername;
    EditText editPassword;
    String username;
    String password;
    Repository repository;

    /** Called when the activity is starting. This is where the layout is initialized.
    * @param savedInstanceState Bundle containing saved instance. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUsername = findViewById(R.id.textViewUsernameBlank);
        editPassword = findViewById(R.id.textViewPasswordBlank);

        repository = new Repository(getApplication());

        // Button that navigates to the Appointment List view after logging in with a user.
        Button buttonLogin = findViewById(R.id.loginButton);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            /** This method is for the login button.
             * @param view The view. */
            @Override
            public void onClick(View view) {
                username = editUsername.getText().toString();
                password = editPassword.getText().toString();

                if (isLoginValid(username, password)) {
                    Intent intent = new Intent(MainActivity.this, AppointmentList.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Username or password incorrect. Please try again.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Create user button.
        Button buttonCreateUser = findViewById(R.id.createUserButton);
        buttonCreateUser.setOnClickListener(new View.OnClickListener() {
            /** This method is for the create user button.
             * @param view The view. */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateUser.class);
                startActivity(intent);
            }
        });
    }


/////////// Validation methods //////////
    /** This method validates the username and password are located in the database. */
    private boolean isLoginValid(String username, String password) {
        return repository.checkLoginCredentials(username, password);
    }

}