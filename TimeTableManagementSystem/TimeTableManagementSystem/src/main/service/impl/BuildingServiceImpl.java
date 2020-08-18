package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.Building;
import main.model.Department;
import main.service.BuildingService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BuildingServiceImpl implements BuildingService {
    private Connection connection;
    public BuildingServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public ArrayList<Building> searchBuildingDetailsByCenter(String name) throws SQLException {
        String SQL = "Select * from building where center LIKE '%" + name + "%'";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<Building> buildingsList = new ArrayList<>();
        while(rst.next()){
            Building building= new Building(Integer.parseInt(rst.getString("bid")),
                    rst.getString("building"),rst.getString("center"));
            buildingsList.add(building);
        }
        return buildingsList;
    }
}
