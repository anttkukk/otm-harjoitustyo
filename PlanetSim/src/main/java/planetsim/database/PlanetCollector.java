/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetsim.database;

import planetsim.domain.Planet;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.paint.Color;

/**
 *
 * @author anttkukk
 */
public class PlanetCollector implements Collector<Planet> {

    @Override

    public Planet collect(ResultSet rs) throws SQLException {
        if (rs.getString("color").equals("r")) {
            return new Planet(rs.getString("name"), rs.getDouble("posx"), rs.getDouble("posy"), rs.getDouble("velx"), rs.getDouble("vely"), rs.getDouble("mass"));
        }
        return new Planet(rs.getString("name"), rs.getDouble("posx"), rs.getDouble("posy"), rs.getDouble("velx"), rs.getDouble("vely"), rs.getDouble("mass"), Color.valueOf(rs.getString("color")), rs.getInt("size"));
    }

}
