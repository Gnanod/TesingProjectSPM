package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.MainGroup;
import main.model.MainGroupCount;
import main.model.Tag;
import main.service.MainGroupService;

import java.sql.*;
import java.util.ArrayList;

public class MainGroupServiceImpl implements MainGroupService {

    private Connection connection;

    public MainGroupServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }


    @Override
    public boolean saveMainGroupId(MainGroup mainGroup) throws SQLException {
        String SQL = "Insert into maingroup Values(?,?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setObject(1, 0);
        stm.setObject(2, mainGroup.getMgroupName());
        stm.setObject(3, mainGroup.getGroupNumber());
        stm.setObject(4, mainGroup.getGroupid());
        stm.setObject(5, mainGroup.getProgrammeid());
        stm.setObject(6, mainGroup.getSemid());
        int res = stm.executeUpdate();
        return res > 0;
    }

    @Override
    public int getCountAccordingToName(String s) throws SQLException {
        String SQL = "select count(id) from maingroup where mgroupName ='" + s + "' group by mgroupName";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        int result = 0;
        if (rst.next()) {
            if (rst.getString("count(id)") != null) {
                result = Integer.parseInt(rst.getString("count(id)"));
            } else {
                result = 0;
            }
        }
        return result;
    }

    @Override
    public ArrayList<MainGroupCount> getAllGroupCount(int yearAndSem, int programme) throws SQLException {

        String yearSemSql = "";
        String programmeSql = "";
        if (yearAndSem != 0) {
            yearSemSql = " and m.semid LIKE '%" + yearAndSem + "%'";
        }
        if (programme != 0) {
            programmeSql = " and m.programmeid LIKE '%" + programme + "%'";
        }
        String SQL = "select count(m.id),a.fullName,p.programmeName " +
                     "from maingroup m,programme p,academicYearAndSemester a " +
                     "where m.programmeid=p.programmeid and m.semid=a.id "+yearSemSql+" "+programmeSql +" group by mgroupName";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<MainGroupCount> main = new ArrayList<>();
        while(rst.next()){
            MainGroupCount mainGroupCount = new MainGroupCount(rst.getString("fullName"),rst.getString("programmeName"),rst.getString("count(m.id)"));
            main.add(mainGroupCount);
        }
        return main;
    }

    @Override
    public ArrayList<MainGroup> getAllGroupDetails(int id) throws SQLException {
        String idSql = "";
        if (id != 0) {
            idSql = " where m.groupNumber LIKE '%" + id + "%'";
        }
        String SQL = "Select id,groupNumber,groupid from maingroup m "+idSql;
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<MainGroup> main = new ArrayList<>();
        while(rst.next()){
            MainGroup mainGroup = new MainGroup();
            mainGroup.setId(Integer.parseInt(rst.getString("id")));
            mainGroup.setGroupNumber(Integer.parseInt(rst.getString("groupNumber")));
            mainGroup.setGroupid(rst.getString("groupid"));
            main.add(mainGroup);
        }
        return main;
    }

    @Override
    public boolean searchMainGroup(String newGroupId) throws SQLException {
        String SQL = "select id from maingroup where groupid = '" + newGroupId + "'";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        boolean result = false;
        if (rst.next()) {
            if (rst.getString("id") != null) {
                result = true;
            } else {
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean updateGroupNumber(MainGroup m) throws SQLException {
        String SQL="Update maingroup set groupNumber='"+m.getGroupNumber()+"',groupid='"+m.getGroupid()+"' where id='"+m.getId()+"'";
        Statement stm=connection.createStatement();
        return stm.executeUpdate(SQL)>0;
    }

    @Override
    public ArrayList<MainGroup> getAllMainGroupDetails() throws SQLException {
        String SQL = "select * from maingroup ";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<MainGroup> main = new ArrayList<>();
        while(rst.next()){
            MainGroup mainGroup = new MainGroup();
            mainGroup.setId(Integer.parseInt(rst.getString("id")));
            mainGroup.setGroupid(rst.getString("groupid"));
            main.add(mainGroup);
        }
        return main;
    }
}
