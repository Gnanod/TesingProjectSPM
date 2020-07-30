package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.Programme;
import main.model.YearAndSemester;
import main.service.ProgrammeService;

import java.sql.*;
import java.util.ArrayList;

public class ProgrammeServiceImpl implements ProgrammeService {

    private Connection connection;

    public ProgrammeServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }
    @Override
    public boolean saveProgramme(Programme programme) throws SQLException {
        String SQL = "Insert into programme Values(?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setObject(1, 0);
        stm.setObject(2, programme.getProgrammeName());
        int res = stm.executeUpdate();
        return res > 0;
    }

    @Override
    public boolean searchProgramme(String name) throws SQLException {
        String SQL = "select programmeid from programme where programmeName = '" + name + "' ";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        boolean result = false;
        if (rst.next()) {
            if (rst.getString("programmeid") != null) {
                result = true;
            } else {
                result = false;
            }
        }
        return result;
    }

    @Override
    public ArrayList<Programme> getAllDetails() throws SQLException {
        String SQL ="Select * from programme";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<Programme> programmes = new ArrayList<>();
        while(rst.next()){
            Programme pr = new Programme(Integer.parseInt(rst.getString("programmeid")),rst.getString("programmeName"));
            programmes.add(pr);
        }
        return programmes;
    }

    @Override
    public boolean updateProgramme(Programme programme) throws SQLException {
        String SQL="Update programme set programmeName='"+programme.getProgrammeName()+"' where programmeid='"+programme.getProgrammeId()+"'";
        Statement stm=connection.createStatement();
        return stm.executeUpdate(SQL)>0;
    }

    @Override
    public boolean deleteProgramme(int key) throws SQLException {
        String SQL = "Delete From programme where programmeid = '"+key+"'";

        Statement stm = connection.createStatement();
        return stm.executeUpdate(SQL)>0;
    }
}
