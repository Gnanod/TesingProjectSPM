package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.Building;
import main.model.Department;
import main.service.BuildingService;

import java.sql.*;
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



    @Override
    public boolean saveBuildings(Building building) throws SQLException {

        String SQL = "Insert into building Values(?,?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setObject(1, 0);
        stm.setObject(2, building.getCenter());
        stm.setObject(3, building.getBuilding());

        int res = stm.executeUpdate();
        return res > 0;

    }

    @Override
    public boolean searchBuilding(String center, String building) throws SQLException {

        String SQL = "select bid from building where center = '" + center + "' " +
                "&& building='" + building + "'";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        boolean result = false;
        if (rst.next()) {
            if (rst.getString("bid") != null) {
                result = true;
            } else {
                result = false;
            }
        }
        return result;

    }

    @Override
    public ArrayList<Building> getAllDetails() throws SQLException {
        String SQL ="Select * from building";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<Building> buildingList = new ArrayList<>();
        while(rst.next()){
            Building buildingRows = new Building(Integer.parseInt(rst.getString("bid")),
                    rst.getString("center"),
                    rst.getString("building"));
            buildingList.add(buildingRows);
        }
        return buildingList;
    }

    @Override
    public boolean deleteBuilding(int key) throws SQLException {
        String SQL = "Delete From building where bid = '"+key+"'";
        Statement stm = connection.createStatement();
        return stm.executeUpdate(SQL)>0;
    }

    @Override
    public boolean updateBuildingDetails(Building building12) throws SQLException {
        String SQL="Update building set center='"+building12.getCenter()+"'," +
                "building='"+building12.getBuilding()+"'  " +
                "where bid='"+building12.getBid()+"'";
        Statement stm=connection.createStatement();
        return stm.executeUpdate(SQL)>0;
    }

    @Override
    public ArrayList<Building> searchBuildingDetailsByUsingCenter(String center) throws SQLException {
        String SQL = "Select * from building where center LIKE '%" + center + "%'";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);

        ArrayList<Building> buildingsList = new ArrayList<>();

        while(rst.next()){
            Building building= new Building(Integer.parseInt(rst.getString("bid")),
                    rst.getString("building"),
                    rst.getString("center"));
            buildingsList.add(building);
        }
        return buildingsList;
    }

}
