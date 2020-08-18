package main.service;

import main.model.Building;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BuildingService {

    public boolean saveBuildings(Building building) throws SQLException;

    public boolean searchBuilding(String center,String building) throws SQLException;

    ArrayList<Building> getAllDetails() throws SQLException;

    public boolean deleteBuilding(int key) throws SQLException;

    boolean updateBuildingDetails(Building building12) throws SQLException;
}


