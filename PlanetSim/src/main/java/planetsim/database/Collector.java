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
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Collector<T> {
    /**
     * Collects a needed values for element T and returns a new T element
     * @param rs ResultSet from which collector creates T element
     * @return A T element
     * @throws SQLException If SQL query fails
     */
    T collect(ResultSet rs) throws SQLException;
}
