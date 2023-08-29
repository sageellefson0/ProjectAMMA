package com.example.projectamma.dao;

/* Imports */
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.projectamma.entities.User;
import java.util.List;


/** The database interface class for users.
 * @author Sage Ellefson */
@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM users ORDER BY userID ASC")
    List<User> getAllUsers();

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    List<User> getUserByUsername(String username);


}