package com.example.projectamma.dao;

/* Imports */
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import com.example.projectamma.entities.Client;
import com.example.projectamma.entities.CorporateClient;
import com.example.projectamma.entities.IndividualClient;
import java.util.ArrayList;
import java.util.List;


/** The database interface class for clients.
 * @author Sage Ellefson */
@Dao
public interface ClientDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertClient(Client client);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertIndividualClient(IndividualClient individualClient);

    @Update
    void updateIndividualClient(IndividualClient individualClient);

    @Delete
    void deleteIndividualClient(IndividualClient individualClient);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCorporateClient(CorporateClient corporateClient);

    @Update
    void updateCorporateClient(CorporateClient corporateClient);

    @Delete
    void deleteCorporateClient(CorporateClient corporateClient);

    @Transaction
    @Query("SELECT * FROM individual_clients")
    List<IndividualClient> getAllIndividualClients();

    @Transaction
    @Query("SELECT * FROM corporate_clients")
    List<CorporateClient> getAllCorporateClients();

    @Transaction
    @Query("SELECT * FROM individual_clients UNION SELECT * FROM corporate_clients")
    default List<Client> getAllClients() {
        ArrayList<Client> result = new ArrayList<>();
        result.addAll(getAllIndividualClients());
        result.addAll(getAllCorporateClients());
        return result;
    }

    @Query("SELECT MAX(clientID) FROM (SELECT clientID FROM individual_clients UNION SELECT clientID FROM corporate_clients)")
    int getHighestClientID();

    @Query("SELECT * FROM individual_clients WHERE clientID = :clientID")
    IndividualClient getIndividualClientByID(int clientID);

    @Query("SELECT * FROM corporate_clients WHERE clientID = :clientID")
    CorporateClient getCorporateClientByID(int clientID);


}