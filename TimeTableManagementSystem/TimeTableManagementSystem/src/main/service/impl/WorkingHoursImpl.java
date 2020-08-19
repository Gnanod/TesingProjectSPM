package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.WorkingHoursPerDay;
import main.service.WorkingHoursService;

import java.sql.*;
import java.util.ArrayList;

public class WorkingHoursImpl implements WorkingHoursService {

    private Connection connection;

    public WorkingHoursImpl(){
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean saveWorkingHours(WorkingHoursPerDay workingHours) throws SQLException {
        String SQL = "Insert into workingHoursPerDay Values(?,?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setObject(1, 0);
        stm.setObject(2, workingHours.getWorkingTime());
        stm.setObject(3, workingHours.getTimeSlot());
        int res = stm.executeUpdate();
        return res > 0;
    }

    @Override
    public ArrayList<WorkingHoursPerDay> getAllWorkingHours() throws SQLException {
        String SQL = "Select * from workingHoursPerDay";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<WorkingHoursPerDay> list = new ArrayList<>();
        while(rst.next()){
            WorkingHoursPerDay workingHoursPerDay = new WorkingHoursPerDay(Integer.parseInt(rst.getString("whpId")),
                    rst.getString("workingTime"),
                    rst.getString("timeSlot")
                    );
            list.add(workingHoursPerDay);
        }
        return list;
    }

    @Override
    public boolean updateWorkingHours(WorkingHoursPerDay workingHours) throws SQLException {
        String SQL="Update workingHoursPerDay set workingTime='"+workingHours.getWorkingTime()+"'" +
                ",timeSlot='"+workingHours.getTimeSlot()+"' where " +
                " whpId='"+workingHours.getWhpId()+"'";
        System.out.println(SQL);
        Statement stm=connection.createStatement();
        return stm.executeUpdate(SQL)>0;
    }

    @Override
    public boolean deleteWorkingHours(int key) throws SQLException {
        String SQL = "Delete From workingHoursPerDay where whpId = '"+key+"'";
        Statement stm = connection.createStatement();
        return stm.executeUpdate(SQL)>0;
    }



}
