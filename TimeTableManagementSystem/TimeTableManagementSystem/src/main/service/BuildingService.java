package main.service;

import main.model.Building;
import main.model.Lecturer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BuildingService {
    public ArrayList<Building> searchBuildingDetailsByCenter(String name) throws SQLException;
}
