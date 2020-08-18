package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.Building;
import main.model.Dashboard;
import main.service.DashboardService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DashboardServiceImpl implements DashboardService {

    private Connection connection;

    public DashboardServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public ArrayList<Dashboard> getBuildingCount() throws SQLException {
        String SQL = "select center,count(building) AS NoOfBuildings from building group by center order by center ASC";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<Dashboard> NoBuildingList = new ArrayList<>();
        while (rst.next()) {
            Dashboard dashboard = new Dashboard();
            dashboard.setCenter(rst.getString("center"));
            dashboard.setNoOfBuildings(Integer.parseInt(rst.getString("NoOfBuildings")));
            NoBuildingList.add(dashboard);
        }
        return NoBuildingList;
    }
}
