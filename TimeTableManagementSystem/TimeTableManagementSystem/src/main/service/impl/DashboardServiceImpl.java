package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.Building;
import main.model.Dashboard;
import main.model.Dashboard2;
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

    @Override
    public ArrayList<Dashboard> getEmployeeCount() throws SQLException {
        String SQL = "select faculty,count(employeeId) AS NoOfEmployees from Lecturer group by faculty order by faculty ASC";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<Dashboard> NoEmpList = new ArrayList<>();
        while (rst.next()) {
            Dashboard dashboard = new Dashboard();
            dashboard.setFaculty(rst.getString("faculty"));
            dashboard.setNoOfEmployees(Integer.parseInt(rst.getString("NoOfEmployees")));
            NoEmpList.add(dashboard);
        }
        return NoEmpList;
    }

    @Override
    public ArrayList<Dashboard2> getDesignationCount() throws SQLException {
        String SQL = "select designation,count(designation) AS NoOfdesignation from Lecturer group by designation order by designation ASC";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<Dashboard2> NoDesList = new ArrayList<>();
        while (rst.next()) {
            Dashboard2 dashboard2 = new Dashboard2();
            dashboard2.setDesignation(rst.getString("designation"));
            dashboard2.setNoOfDesig(Integer.parseInt(rst.getString("NoOfdesignation")));
            NoDesList.add(dashboard2);
        }
        return NoDesList;
    }

    @Override
    public ArrayList<Dashboard2> getSubjects() throws SQLException {
        String SQL = "select subName,count(subId) AS NoOfSubjects from Subject group by offeredYearSemId";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<Dashboard2> NoSubList = new ArrayList<>();
        while (rst.next()) {
            Dashboard2 dashboard2 = new Dashboard2();
            dashboard2.setDesignation(rst.getString("subName"));
            dashboard2.setNoOfDesig(Integer.parseInt(rst.getString("NoOfSubjects")));
            NoSubList.add(dashboard2);
        }
        return NoSubList;
    }
}
