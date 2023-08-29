package com.example.projectamma.database;

/* Imports */
import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.projectamma.dao.AppointmentDAO;
import com.example.projectamma.dao.ClientDAO;
import com.example.projectamma.dao.UserDAO;
import com.example.projectamma.entities.Appointment;
import com.example.projectamma.entities.Client;
import com.example.projectamma.entities.CorporateClient;
import com.example.projectamma.entities.IndividualClient;
import com.example.projectamma.entities.User;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;


/** Repository class that allows the application to interact with the DAO files.
 * @author Sage Ellefson */
public class Repository {
    private AppointmentDAO mAppointmentDAO;
    private ClientDAO mClientDAO;
    private UserDAO mUserDAO;
    private List<Appointment> mAllAppointments;
    private List<Client> mAllClients;
    private List<User> mAllUsers;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /** Constructor for the Repository class. Initializes the DAOs for appointment, client, and user entities.
     * @param application The application context. */
    public Repository(Application application){
        DatabaseBuilder db=DatabaseBuilder.getDatabase(application);
        mAppointmentDAO=db.appointmentDAO();
        mClientDAO=db.clientDAO();
        mUserDAO=db.userDAO();
    }

// Future enhancement: replace Thread.sleep(1000) with a different execution.

////////// Appointment actions ////////////
    /** This method allows the user to get all appointments from the database. */
    public List<Appointment>getAllAppointments(){
        databaseExecutor.execute(()-> mAllAppointments=mAppointmentDAO.getAllAppointments());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAppointments;
    }

    /** This method allows the user to get an appointment by the appointment ID from the database. */
    public Appointment getAppointmentByID(int appointmentID) {
        AtomicReference<Appointment> appointmentReference = new AtomicReference<>(null);
        databaseExecutor.execute(() -> {
            Appointment appointment = mAppointmentDAO.getAppointmentByID(appointmentID);
            appointmentReference.set(appointment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return appointmentReference.get();
    }

    /** This method allows the user to get an appointment by month from the database. */
    public List<Appointment> getAppointmentsByMonth(String selectedMonth) {
        final List<Appointment>[] appointments = new List[]{null};
        CountDownLatch latch = new CountDownLatch(1);

        databaseExecutor.execute(() -> {
            appointments[0] = mAppointmentDAO.getAppointmentsByMonth(selectedMonth);
            latch.countDown();
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return appointments[0];
    }

    /** This method allows the user to get an appointment by client ID. */
    public LiveData<List<Appointment>> getAppointmentsByClientID(int clientID) {
        return mAppointmentDAO.getAppointmentsByClientID(clientID);
    }

    /** This method allows the user to insert an appointment into the database. */
    public void insertAppointment(Appointment appointment){
        databaseExecutor.execute(()-> mAppointmentDAO.insert(appointment));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** This method allows the user to update an appointment in the database. */
    public void updateAppointment(Appointment appointment){
        databaseExecutor.execute(()-> mAppointmentDAO.update(appointment));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** This method allows the user to delete an appointment in the database. */
    public void deleteAppointment(Appointment appointment){
        databaseExecutor.execute(()-> mAppointmentDAO.delete(appointment));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


////////// Client actions ////////////
    /** This method allows the user to get all clients in the database. */
    public List<Client>getAllClients(){
        databaseExecutor.execute(()-> mAllClients= mClientDAO.getAllClients());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllClients;
    }

    /** This method allows the user to get an individual client by the client ID from the database. */
    public IndividualClient getIndividualClientByID(int clientID) {
        AtomicReference<IndividualClient> individualClientReference = new AtomicReference<>(null);
        databaseExecutor.execute(() -> {
            IndividualClient client = mClientDAO.getIndividualClientByID(clientID);
            individualClientReference.set(client);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return individualClientReference.get();
    }

    /** This method allows the user to get an corporate client by the client ID from the database. */
    public CorporateClient getCorporateClientByID(int clientID) {
        AtomicReference<CorporateClient> corporateClientReference = new AtomicReference<>(null);
        databaseExecutor.execute(() -> {
            CorporateClient client = mClientDAO.getCorporateClientByID(clientID);
            corporateClientReference.set(client);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return corporateClientReference.get();
    }

    /** This method allows the user to insert a client into the database. */
    public void insertClient(Client client) {
        databaseExecutor.execute(() -> mClientDAO.insertClient(client));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** This method allows the user to insert an individual client into the database. */
    public void insertIndividualClient(IndividualClient individualClient) {
        databaseExecutor.execute(() -> {
            int highestIndividualClientID = mClientDAO.getHighestClientID();
            individualClient.setClientID(highestIndividualClientID + 1);
            mClientDAO.insertIndividualClient(individualClient);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** This method allows the user to insert a corporate client into the database. */
    public void insertCorporateClient(CorporateClient corporateClient) {
        databaseExecutor.execute(() -> {
            int highestCorporateClientID = mClientDAO.getHighestClientID();
            corporateClient.setClientID(highestCorporateClientID + 1);
            mClientDAO.insertCorporateClient(corporateClient);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** This method allows the user to update an individual client in the database. */
    public void updateIndividualClient(Client individualClient){
        databaseExecutor.execute(()-> mClientDAO.updateIndividualClient((IndividualClient) individualClient));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** This method allows the user to delete an individual client in the database. */
    public void deleteIndividualClient(Client individualClient){
        databaseExecutor.execute(()-> mClientDAO.deleteIndividualClient((IndividualClient) individualClient));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** This method allows the user to update a corporate client in the database. */
    public void updateCorporateClient(Client corporateClient){
        databaseExecutor.execute(()-> mClientDAO.updateCorporateClient((CorporateClient) corporateClient));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** This method allows the user to delete a corporate client in the database. */
    public void deleteCorporateClient(Client corporateClient){
        databaseExecutor.execute(()-> mClientDAO.deleteCorporateClient((CorporateClient) corporateClient));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


////////// User actions ////////////
    /** This method allows the user to get all users in the database. */
    public List<User>getAllUsers(){
        databaseExecutor.execute(()-> mAllUsers= mUserDAO.getAllUsers());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllUsers;
    }

    /** This method checks if the username entered already exists in the database. */
    public boolean checkUsernameExists(String username) {
        List<User> allUsers = getAllUsers();
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    /** This method checks the username and password entered to ensure it exists in the database. */
    public boolean checkLoginCredentials(String username, String password) {
        List<User> allUsers = getAllUsers();
        for (User user : allUsers) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /** This method allows the user to insert a user into the database. */
    public void insertUser(User user){
        databaseExecutor.execute(()-> mUserDAO.insertUser(user));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Future implementations.
    /** This method allows the user to update a user in the database. */
    public void updateUser(User user){
        databaseExecutor.execute(()-> mUserDAO.updateUser(user));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** This method allows the user to delete a user in the database. */
    public void deleteUser(User user){
        databaseExecutor.execute(()-> mUserDAO.deleteUser(user));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<User> getUserByUsername(String username) {
        return mUserDAO.getUserByUsername(username);
    }





}

