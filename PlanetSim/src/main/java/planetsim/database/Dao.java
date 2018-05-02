/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetsim.database;

/**
 *
 * @author anttkukk
 */
import java.sql.SQLException;
import java.util.ArrayList;

public interface Dao<T, K> {
    /**
     * Finds one from database with key
     * @param key Key which identifies which to find
     * @return Returns one element that is found
     * @throws SQLException if SQL query fails
     */
    T findOne(K key) throws SQLException;
    
    /**
     * Saves an element to the database
     * @param element Element to be saved to database
     * @throws SQLException if update fails
     */
    void save(T element) throws SQLException;
    
    /**
     * Finds all elements from database
     * @return ArrayList of all elements in database 
     * @throws SQLException if SQL query fails
     */
    ArrayList<T> findAll() throws SQLException;
    /**
     * Deletes element from database with String key
     * @param key Element can be identified from database with String key
     * @throws SQLException if SQL query fails
     */
    void delete(String key) throws SQLException;
}
