package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.Department;
import main.model.Tag;
import main.service.DepartmentService;

import java.sql.*;
import java.util.ArrayList;

public class DepartmentServiceImpl implements DepartmentService {

    private Connection connection;
    static String departmentName;
    public DepartmentServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean searchDepartment(String departmentName) throws SQLException {
        String SQL = "select dId from department where departmentName = '" + departmentName + "' ";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        boolean result = false;
        if (rst.next()) {
            if (rst.getString("dId") != null) {
                result = true;
            } else {
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean saveDepartment(Department department) throws SQLException {
        String SQL = "Insert into department Values(?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setObject(1, 0);
        stm.setObject(2, department.getDepartmentName());
        int res = stm.executeUpdate();
        return res > 0;
    }

    @Override
    public boolean updateDepartment(Department department) throws SQLException {
        String SQL="Update department set departmentName='"+department.getDepartmentName()+"' where " +
                   "dId='"+department.getDepartmentId()+"'";
        Statement stm=connection.createStatement();
        return stm.executeUpdate(SQL)>0;
    }

    @Override
    public ArrayList<Department> getAllDetails() throws SQLException {
        String SQL ="Select * from department";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<Department> dptList = new ArrayList<>();
        while(rst.next()){
            Department t = new Department(Integer.parseInt(rst.getString("dId")),
                                          rst.getString("departmentName"));
            dptList.add(t);
        }
        return dptList;
    }

    @Override
    public String searchDepartmentName(int id) throws SQLException {
        String SQL = "select departmentName  from department where dId = '" + id + "' ";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);

        while(rst.next()) {

            departmentName=rst.getString("departmentName");
        }
       return departmentName;

    }
}
