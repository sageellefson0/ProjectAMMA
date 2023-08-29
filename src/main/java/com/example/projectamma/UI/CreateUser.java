package com.example.projectamma.UI;

/* Imports */
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projectamma.R;
import com.example.projectamma.database.Repository;
import com.example.projectamma.entities.User;


/** This activity is for the creation of a user entity.
 * A username and password is entered and validated and then saved as a user that can access the application.
 * @author Sage Ellefson */
public class CreateUser extends AppCompatActivity {

    EditText editUsername;
    EditText editPassword;
    String username;
    User user;
    Repository repository;

    /** Called when the activity is starting. This is where the layout is initialized for the create user page.
     * @param savedInstanceState Bundle containing saved instance. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sets the content view.
        setContentView(R.layout.create_user_page);

        editUsername = findViewById(R.id.textViewBlankUsername);
        editPassword = findViewById(R.id.textViewBlankPassword);

        repository = new Repository(getApplication());

        // Save button for the user.
        Button button = findViewById(R.id.buttonSaveUser);
        button.setOnClickListener(new View.OnClickListener() {

            /** This method is for the user save button.
             * @param view The view. */
            @Override
            public void onClick(View view) {

                if (doesUsernameExist()) {

                    if (isFieldValid()) {
                        user = new User(0, editUsername.getText().toString().trim(), editPassword.getText().toString().trim());
                        repository.insertUser(user);
                        Toast.makeText(CreateUser.this, "The user has been created.", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(CreateUser.this, "All fields must be filled out before saving. The password must be 8 or more characters and include a number. The username must be 5 or more characters.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(CreateUser.this, "Username already taken, please choose a different username.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


/////////// Validation methods //////////

    /** This method validates that no field is empty and that the username is 5 or more characters, and the password has a number and is more than 8 characters.*/
    public boolean isFieldValid() {
        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();

        boolean hasNumber = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasNumber = true;
                break;
            }
        }
        return !username.isEmpty() && !password.isEmpty() && password.length() >= 8 && username.length() >= 5 && hasNumber;
    }

    /** This method validates the username entered does not match that of any other username in the database. */
    private boolean doesUsernameExist() {
            username = editUsername.getText().toString().trim(); //
            return repository.checkUsernameExists(username);
    }
}
