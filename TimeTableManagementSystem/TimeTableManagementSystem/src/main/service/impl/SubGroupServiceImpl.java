package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.SubGroup;
import main.model.SubGroupCount;
import main.service.SubGroupService;

import java.sql.*;
import java.util.ArrayList;

public class SubGroupServiceImpl implements SubGroupService {

    private Connection connection;

    public SubGroupServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean saveDetails(SubGroup sub) throws SQLException {
        String SQL = "Insert into subgroup Values(?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setObject(1, 0);
        stm.setObject(2, sub.getSubgroupid());
        stm.setObject(3, sub.getSubgroupnumber());
        stm.setObject(4, sub.getMaingroupid());
        int res = stm.executeUpdate();
        return res > 0;
    }

    @Override
    public int subGroupCountAccordingToId(int mainid) throws SQLException {
        String SQL = "select count(id) from subgroup where maingroupid ='" + mainid + "' group by maingroupid";
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
    public ArrayList<SubGroupCount> getAllSubGroupCount(int mainGroupId) throws SQLException {
        String sql = "";
        if (mainGroupId != 0) {
            sql = " and s.maingroupid ='" + mainGroupId + "' ";
        }
        String SQL = "select count(s.id),m.groupid from subgroup s,maingroup m " +
                " where s.maingroupid=m.id " + sql +
                " group by maingroupid";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<SubGroupCount> main = new ArrayList<>();
        while (rst.next()) {
            SubGroupCount subGroup = new SubGroupCount();
            subGroup.setGroupId(rst.getString("groupid"));
            subGroup.setSubGroupCount(Integer.parseInt(rst.getString("count(s.id)")));
            main.add(subGroup);
        }
        return main;
    }

    @Override
    public ArrayList<SubGroup> getAllSubGroupDetails(int id) throws SQLException {
        String sql="";
        if(id!=0){
            sql=" where subgroupnumber Like '%"+id+"%'";
        }
        String SQL = "Select * from subgroup "+sql;
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<SubGroup> subGroupList = new ArrayList<>();
        while (rst.next()) {
            SubGroup s = new SubGroup(Integer.parseInt(rst.getString("id")),
                    rst.getString("subgroupid"),
                    Integer.parseInt(rst.getString("subgroupnumber")),
                    Integer.parseInt(rst.getString("maingroupid")));
            subGroupList.add(s);
        }
        return subGroupList;
    }

    @Override
    public boolean searchSubGroup(String updateGroupId) throws SQLException {
        String SQL = "select id from subgroup where subgroupid = '" + updateGroupId + "'";
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
    public boolean updateGroupNumber(SubGroup m) throws SQLException {
        String SQL="Update subgroup set subgroupnumber='"+m.getSubgroupnumber()+"',subgroupid='"+m.getSubgroupid()+"' where id='"+m.getId()+"'";
        Statement stm=connection.createStatement();
        return stm.executeUpdate(SQL)>0;
    }

    @Override
    public boolean deleteSubGroup(int id) throws SQLException {
        String SQL = "Delete From subgroup where id = '"+id+"'";
        Statement stm = connection.createStatement();
        return stm.executeUpdate(SQL)>0;
    }

    @Override
    public int getMainGroup(int id) throws SQLException {
        String SQL = "select maingroupid from subgroup where id='"+id+"'";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        int result = 0;
        if (rst.next()) {
            if (rst.getString("maingroupid") != null) {
                result = Integer.parseInt(rst.getString("maingroupid"));
            } else {
                result = 0;
            }
        }
        return result;
    }
}
