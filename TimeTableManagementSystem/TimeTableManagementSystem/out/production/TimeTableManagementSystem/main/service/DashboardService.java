package main.service;

import main.model.Building;
import main.model.Dashboard;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DashboardService {

    ArrayList<Dashboard> getBuildingCount() throws SQLException;
}
