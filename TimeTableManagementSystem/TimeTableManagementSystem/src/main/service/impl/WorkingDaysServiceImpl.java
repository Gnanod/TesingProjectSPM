package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.Tag;
import main.model.WorkingDaysMain;
import main.model.WorkingDaysSub;
import main.service.WorkingDaysService;

import java.sql.*;
import java.util.ArrayList;

public class WorkingDaysServiceImpl implements WorkingDaysService {

    private Connection connection;

    public WorkingDaysServiceImpl(){
        connection = DBConnection.getInstance().getConnection();
    }
    @Override
    public int addWorkingDays(WorkingDaysMain workingDaysMain) throws SQLException {
        String SQL = "Insert into WorkingDaysMain Values(?,?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setObject(1, 0);
        stm.setObject(2, workingDaysMain.getType());
        stm.setObject(3, workingDaysMain.getNoOfDays());
        int res = stm.executeUpdate();
        int lastId=0;
        if(res>0){
            String sql = "select workingId from WorkingDaysMain order by 1 desc limit 1";
            Statement stm1 = connection.createStatement();
            ResultSet rst = stm1.executeQuery(sql);
            System.out.println("lastId"+lastId);
            if (rst.next()) {
                lastId = Integer.parseInt(rst.getString("workingId"));
            }
        }
        return lastId;
    }

    @Override
    public boolean addWorkingDaysSub(WorkingDaysSub workingDaysSub) throws SQLException {
        String SQL = "Insert into WorkingDaysSub Values(?,?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setObject(1, 0);
        stm.setObject(2, workingDaysSub.getWorkingId());
        stm.setObject(3, workingDaysSub.getWorkingday());
        int res = stm.executeUpdate();
        return res>0;
    }

    @Override
    public ArrayList<WorkingDaysMain> getAllNoOfWorkingDays() throws SQLException {
        String SQL ="Select * from WorkingDaysMain";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<WorkingDaysMain> list = new ArrayList<>();
        while(rst.next()){
            WorkingDaysMain  m1 = new WorkingDaysMain(Integer.parseInt(rst.getString("workingId")),
                    rst.getString("type"),Integer.parseInt(rst.getString("noOfDays")));
            list.add(m1);
        }
        return list;
    }

    @Override
    public ArrayList<String> getWorkingDaysAccordingId(int workingId) throws SQLException {
        String SQL ="select workingday from WorkingDaysSub where workingId='"+workingId+"'";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<String> list = new ArrayList<>();
        while(rst.next()){
            String day = rst.getString("workingday");
            list.add(day);
        }
        return list;
    }

    @Override
    public boolean deleteWorkingDay(int workingId) throws SQLException {
        String SQL = "Delete From WorkingDaysMain where workingId = '"+workingId+"'";
        Statement stm = connection.createStatement();
        return stm.executeUpdate(SQL)>0;
    }

    @Override
    public boolean deleteWorkingDaysfromSub(int updateId) throws SQLException {
        String SQL = "Delete From WorkingDaysSub where workingId = '"+updateId+"'";
        Statement stm = connection.createStatement();
        return stm.executeUpdate(SQL)>0;
    }

    @Override
    public boolean updateNoOfWorkingDays(WorkingDaysMain workingDaysMain) throws SQLException {
        String SQL="Update WorkingDaysMain set type='"+workingDaysMain.getType()+"'," +
                "noOfDays='"+workingDaysMain.getNoOfDays()+"' " +
                "where workingId='"+workingDaysMain.getWorkingId()+"'";
        Statement stm=connection.createStatement();
        return stm.executeUpdate(SQL)>0;
    }

    @Override
    public ArrayList<WorkingDaysSub> getAllSubDetails() throws SQLException {
        String SQL ="Select * from WorkingDaysSub";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<WorkingDaysSub> list = new ArrayList<>();
        while(rst.next()){
            WorkingDaysSub  m1 = new WorkingDaysSub();
            m1.setSubId(Integer.parseInt(rst.getString("subId")));
            m1.setWorkingday(rst.getString("workingday"));
            m1.setWorkingId(Integer.parseInt(rst.getString("workingId")));
            list.add(m1);
        }
        return list;
    }

    @Override
    public boolean deleteWorkingDaysSub(int id,int workingId) throws SQLException {
        String SQL = "Delete From WorkingDaysSub where subId = '"+id+"'";
        Statement stm = connection.createStatement();
        int delete = stm.executeUpdate(SQL);
        int count=0;
        int update =0;
        if(delete>0){
            String sqlCount = "select count(workingday) from WorkingDaysSub where workingId ='"+workingId+"' ";
            Statement stm1 = connection.createStatement();
            ResultSet rst = stm1.executeQuery(sqlCount);
            if (rst.next()) {
                count = Integer.parseInt(rst.getString("count(workingday)"));
            }
            String sqlUpdate="update WorkingDaysMain set noOfDays = '"+count+"' where workingId ='"+workingId+"'";
            update= stm1.executeUpdate(sqlUpdate);
        }
        return update>0;
    }

    @Override
    public boolean checkWeekDayOrWeekEndIsAdded(String selectedType) throws SQLException {
        String SQL = "select workingId from WorkingDaysMain where type = '" + selectedType + "' ";;
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        boolean result = false;
        if (rst.next()) {
            if (rst.getString("workingId") != null) {
                result = true;
            } else {
                result = false;
            }
        }
        return result;
    }
}
