package com.example.projectamma.UI;

/* Imports */
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projectamma.R;
import com.example.projectamma.database.Repository;
import com.example.projectamma.entities.Appointment;
import com.example.projectamma.entities.Client;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/** This activity is for the appointment detail view of an appointment entity.
 * Fields are validated for null and logic before saving or updating an appointment.
 * @author Sage Ellefson */
public class AppointmentDetails extends AppCompatActivity {

    /* Various entry fields. */
    int appointmentID;
    EditText editType;
    EditText editDescription;
    EditText editLocation;
    EditText editDate;
    EditText editStartTime;
    EditText editEndTime;
    DatePickerDialog.OnDateSetListener datePicker;
    final Calendar myCalendarDatePicker = Calendar.getInstance();
    final Calendar myCalendarStartTime = Calendar.getInstance();
    final Calendar myCalendarEndTime = Calendar.getInstance();
    Spinner spinnerClientDropDown;
    String appointmentType;
    String appointmentDescription;
    String appointmentLocation;
    String appointmentDate;
    String appointmentStartTime;
    String appointmentEndTime;
    Appointment appointment;
    Repository repository;

    /** Called when the activity is starting. This is where the layout is initialized for the appointment detail page.
     * @param savedInstanceState Bundle containing saved instance. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sets the content view.
        setContentView(R.layout.detailed_view_appointments);

        editType = findViewById(R.id.textViewTypeBlank);
        editDescription = findViewById(R.id.textViewDescriptionBlank);
        editLocation = findViewById(R.id.textViewLocationBlank);
        editDate = findViewById(R.id.textViewDateBlank);
        editStartTime = findViewById(R.id.textViewStartTimeBlank);
        editEndTime = findViewById(R.id.textViewEndTimeBlank);

        // Key listeners for the date and times - forces the user to pick a date and time rather than enter one.
        editDate.setKeyListener(null);
        editStartTime.setKeyListener(null);
        editEndTime.setKeyListener(null);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdfDate = new SimpleDateFormat(myFormat, Locale.US);
        editDate.setText(sdfDate.format(Calendar.getInstance().getTime()));

        String myFormatTime = "HH:mm:ss";
        SimpleDateFormat sdfTime = new SimpleDateFormat(myFormatTime, Locale.US);
        editStartTime.setText(sdfTime.format(Calendar.getInstance().getTime()));
        editEndTime.setText(sdfTime.format(Calendar.getInstance().getTime()));

        repository = new Repository(getApplication());

        Intent intent = getIntent();
        appointmentID = intent.getIntExtra("appointmentID", -1);

        // Spinner
        spinnerClientDropDown = findViewById(R.id.spinnerClientDropDown);
        populateSpinner();

        // Checks if it's an existing appointment.
        if (appointmentID != -1) {
            appointment = repository.getAppointmentByID(appointmentID);
            if (appointment != null) {
                appointmentType = appointment.getAppointmentType();
                editType.setText(appointmentType);

                appointmentDescription = appointment.getAppointmentDescription();
                editDescription.setText(appointmentDescription);

                appointmentLocation = appointment.getAppointmentLocation();
                editLocation.setText(appointmentLocation);

                appointmentDate = appointment.getAppointmentDate();
                editDate.setText(appointmentDate);

                appointmentStartTime = appointment.getAppointmentStartTime();
                editStartTime.setText(appointmentStartTime);

                appointmentEndTime = appointment.getAppointmentEndTime();
                editEndTime.setText(appointmentEndTime);

                populateSpinner();

            } else {
                appointmentID = getIntent().getIntExtra("appointmentID", -1);

                appointmentType = getIntent().getStringExtra("appointmentType");
                editType.setText(appointmentType);

                appointmentDescription = getIntent().getStringExtra("appointmentDescription");
                editDescription.setText(appointmentDescription);

                appointmentLocation = getIntent().getStringExtra("appointmentLocation");
                editLocation.setText(appointmentLocation);

                appointmentDate = getIntent().getStringExtra("appointmentDate");
                editDate.setText(appointmentDate);

                appointmentStartTime = getIntent().getStringExtra("appointmentStartTime");
                editStartTime.setText(appointmentStartTime);

                appointmentEndTime = getIntent().getStringExtra("appointmentEndTime");
                editEndTime.setText(appointmentEndTime);
            }
        }

        // Save button for the appointment.
        Button button = findViewById(R.id.buttonSaveAppointment);
        button.setOnClickListener(new View.OnClickListener() {
            /** This method is for the appointment save button.
             * @param view The view that was clicked. */
            @Override
            public void onClick(View view) {
               // Client selectedClient = (Client) spinnerClientDropDown.getSelectedItem();
                String selectedClientName = spinnerClientDropDown.getSelectedItem().toString();

                if (selectedClientName.equals("Select Client")) {
                    Toast.makeText(AppointmentDetails.this, "Please select a client before saving.", Toast.LENGTH_LONG).show();
                    return;
                }
                // Retrieves the list of clients from the repository.
                List<Client> clients = repository.getAllClients();

                // Finds the selected client by name.
                Client selectedClient = null;
                for (Client client : clients) {
                    if (client.getClientName().equals(selectedClientName)) {
                        selectedClient = client;
                        break;
                    }
                }

                if (appointmentID == -1) {
                    if (isFieldEmpty()) {
                        if (isEndTimeValid()) {

                            appointment = new Appointment(0, editType.getText().toString(), editDescription.getText().toString(), editLocation.getText().toString(), editDate.getText().toString(), selectedClient.getClientID(), editStartTime.getText().toString(), editEndTime.getText().toString());

                            repository.insertAppointment(appointment);
                            Toast.makeText(AppointmentDetails.this, "The appointment has been added.", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(AppointmentDetails.this, "The appointment end time must be at least 15 minutes after the start time.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(AppointmentDetails.this, "All fields must be filled out before saving.", Toast.LENGTH_LONG).show();
                    }

                } else {
                    if (isFieldEmpty()) {
                        if (isEndTimeValid()) {
                        appointment = new Appointment(appointmentID, editType.getText().toString(), editDescription.getText().toString(), editLocation.getText().toString(), editDate.getText().toString(), selectedClient.getClientID(), editStartTime.getText().toString(), editEndTime.getText().toString());
                            repository.updateAppointment(appointment);
                            Toast.makeText(AppointmentDetails.this, "The appointment has been updated.", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(AppointmentDetails.this, "The appointment end time must be at least 15 minutes after the start time.", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(AppointmentDetails.this, "All fields must be filled out before saving.", Toast.LENGTH_LONG).show();
                    }
                }
            }

        });


/////////////// Date and Time Code ///////////////

        // For the date picker.
        editDate.setOnClickListener(new View.OnClickListener() {

            /** Called when the user clicks the start date EditText.
             * Displays a DatePickerDialog to select a start date.
             * @param v The clicked on view. */
            @Override
            public void onClick(View v) {
                String info = editDate.getText().toString();
                if (info.equals("")) {
                    myCalendarDatePicker.setTimeInMillis(System.currentTimeMillis());
                }
                try {
                    myCalendarDatePicker.setTime(sdfDate.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AppointmentDetails.this, datePicker, myCalendarDatePicker
                        .get(Calendar.YEAR), myCalendarDatePicker.get(Calendar.MONTH),
                        myCalendarDatePicker.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // For the start time picker.
        editStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = myCalendarStartTime.get(Calendar.HOUR_OF_DAY);
                int minute = myCalendarStartTime.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AppointmentDetails.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                myCalendarStartTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                myCalendarStartTime.set(Calendar.MINUTE, minute);
                                updateLabelStartTime();
                            }
                        },
                        hour,
                        minute,
                        true
                );
                timePickerDialog.show();
            }
        });


        // For the end time picker.
        editEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = myCalendarEndTime.get(Calendar.HOUR_OF_DAY);
                int minute = myCalendarEndTime.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AppointmentDetails.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                myCalendarEndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                myCalendarEndTime.set(Calendar.MINUTE, minute);
                                updateLabelEndTime();
                            }
                        },
                        hour,
                        minute,
                        true
                );
                timePickerDialog.show();
            }
        });


        // Date picker.
        datePicker = new DatePickerDialog.OnDateSetListener() {
            /** Called when the user sets a date in the DatePickerDialog - for the start date.
             * Updates the calendar with the selected date.
             * @param view The DatePicker view.
             * @param year The selected year.
             * @param monthOfYear The selected month.
             * @param dayOfMonth  The selected day. */
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarDatePicker.set(Calendar.YEAR, year);
                myCalendarDatePicker.set(Calendar.MONTH, monthOfYear);
                myCalendarDatePicker.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDate();
            }
        };
    }

    /** Updates the editDate field with the date from the myCalendarStartDate instance. */
    private void updateLabelDate() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdfDate = new SimpleDateFormat(myFormat, Locale.US);
        editDate.setText(sdfDate.format(myCalendarDatePicker.getTime()));
    }

    /** Updates the editStartTime field with the date from the myCalendarStartDate instance. */
    private void updateLabelStartTime() {
        String myFormat = "HH:mm:ss";
        SimpleDateFormat sdfTime = new SimpleDateFormat(myFormat, Locale.US);
        editStartTime.setText(sdfTime.format(myCalendarStartTime.getTime()));
    }

    /** Updates the editEndTime field with the date from the myCalendarStartDate instance. */
    private void updateLabelEndTime() {
        String myFormat = "HH:mm:ss";
        SimpleDateFormat sdfTime = new SimpleDateFormat(myFormat, Locale.US);
        editEndTime.setText(sdfTime.format(myCalendarEndTime.getTime()));
    }


    /** This method populates the spinner for the clients. */
    private void populateSpinner() {
        List<Client> clients = repository.getAllClients();
        List<String> clientNames = new ArrayList<>();

        clientNames.add("Select Client");

        for (Client client : clients) {
            clientNames.add(client.getClientName());
        }

        NoSelectionSpinnerAdapter adapter = new NoSelectionSpinnerAdapter(this, android.R.layout.simple_spinner_item, clientNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerClientDropDown.setAdapter(adapter);

        if (appointment != null) {
            int selectedClientID = appointment.getSelectedClientID();
            for (int i = 0; i < clients.size(); i++) {
                if (clients.get(i).getClientID() == selectedClientID) {
                    spinnerClientDropDown.setSelection(i + 1);
                    break;
                }
            }
        }
    }


/////////// Validation methods //////////

    /** This method validates that no field is empty. */
    private boolean isFieldEmpty() {
        String appointmentType = editType.getText().toString();
        String appointmentDescription = editDescription.getText().toString();
        String appointmentLocation = editLocation.getText().toString();
        String appointmentDate = editDate.getText().toString();
        String appointmentStartTime = editStartTime.getText().toString();
        String appointmentEndTime = editStartTime.getText().toString();

        return !appointmentType.isEmpty() && !appointmentDescription.isEmpty() && !appointmentLocation.isEmpty() && !appointmentDate.isEmpty() && !appointmentStartTime.isEmpty() && !appointmentEndTime.isEmpty();
    }

    /** This method validates that the end time is at least 15 minutes after the start time of the appointment. */
    private boolean isEndTimeValid() {
        try {
            SimpleDateFormat sdfValid = new SimpleDateFormat("HH:mm:ss", Locale.US);
            Date startTime = sdfValid.parse(editStartTime.getText().toString());
            Date endTime = sdfValid.parse(editEndTime.getText().toString());

            Calendar calendarStart = Calendar.getInstance();
            Calendar calendarEnd = Calendar.getInstance();
            calendarStart.setTime(startTime);
            calendarEnd.setTime(endTime);

            long differenceInMilliseconds = calendarEnd.getTimeInMillis() - calendarStart.getTimeInMillis();
            long differenceInMinutes = differenceInMilliseconds / (60 * 1000);

            return differenceInMinutes >= 15;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }


//////// Menu methods ///////////

    /** This method creates the menu options.
     * @param menu_detailed The menu being displayed. */
    public boolean onCreateOptionsMenu(Menu menu_detailed) {
        getMenuInflater().inflate(R.menu.menu_notify_appointment, menu_detailed);
        getMenuInflater().inflate(R.menu.menu_share_appointment, menu_detailed);
        getMenuInflater().inflate(R.menu.menu_delete_appointment, menu_detailed);
        return true;
    }

    /** Called to prepare the options menu before it is displayed.
     * @param menu_detailed The options menu. */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu_detailed) {
        if (appointmentID != -1) {
            menu_detailed.findItem(R.id.menuNotifyAppointment).setVisible(true);
            menu_detailed.findItem(R.id.menuShareAppointment).setVisible(true);
            menu_detailed.findItem(R.id.menuDeleteAppointment).setVisible(true);
        }
        else {
            menu_detailed.findItem(R.id.menuNotifyAppointment).setVisible(false);
            menu_detailed.findItem(R.id.menuShareAppointment).setVisible(false);
            menu_detailed.findItem(R.id.menuDeleteAppointment).setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu_detailed);
    }


    /** This method handles the different menu selections.
     * @param menu_selection The item being selected from the menu.
     * @return Returns the selected menu item.*/
    @Override
    public boolean onOptionsItemSelected(MenuItem menu_selection) {
        if (menu_selection.getItemId() == R.id.menuShareAppointment) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            String appointment =
                    "Appointment: " + editType.getText().toString() + " Location: " + editLocation.getText().toString() +
                            " Date: " + editDate.getText().toString();
            sendIntent.putExtra(Intent.EXTRA_TEXT, appointment);
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Appointment Management Application");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }

        if (menu_selection.getItemId() == R.id.menuNotifyAppointment) {
            String dateFromDateField = editDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdfDate = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdfDate.parse(dateFromDateField);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate.getTime();
            Intent intent = new Intent(AppointmentDetails.this, MyReceiver.class);
            intent.putExtra("key", "Appointment Date: " + appointmentType + " starting on " + dateFromDateField);
            PendingIntent sender = PendingIntent.getBroadcast(AppointmentDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
        }

        if (menu_selection.getItemId() == R.id.menuDeleteAppointment) {
            if (appointmentID != -1) {
                repository.deleteAppointment(appointment);
                Toast.makeText(this, "The appointment has been deleted.", Toast.LENGTH_SHORT).show();
                // Navigates to previous page.
                finish();
            }
            else {
                Toast.makeText(this, "The appointment could not be deleted at this time.", Toast.LENGTH_SHORT).show();
                // Navigates to previous page.
                finish();
            }
        return true;
    }
        return super.onOptionsItemSelected(menu_selection);
    }
}
