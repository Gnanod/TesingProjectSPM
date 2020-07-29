package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.YearAndSemester;
import main.service.YearandSemesterService;

import java.sql.*;
import java.util.ArrayList;


public class YearAndServiceImpl implements YearandSemesterService {

    private Connection connection;

    public YearAndServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean saveAcademiceYear(YearAndSemester yearAndSemester) throws SQLException {
        String SQL = "Insert into academicYearAndSemester Values(?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setObject(1, 0);
        stm.setObject(2, yearAndSemester.getYearName());
        stm.setObject(3, yearAndSemester.getSemesterName());
        stm.setObject(4, yearAndSemester.getFullName());
        int res = stm.executeUpdate();
        return res > 0;
    }

    @Override
    public boolean searchYearAndSemester(String year, String semester) throws SQLException {
        String SQL = "select id from academicYearAndSemester where yearName = '" + year + "' && semesterName='" + semester + "'";
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
    public ArrayList<YearAndSemester> getAllDetails() throws SQLException {
        String SQL ="Select * from academicYearAndSemester";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<YearAndSemester> yearAndSemesterList = new ArrayList<>();
        while(rst.next()){
            YearAndSemester yearAndSemester = new YearAndSemester(Integer.parseInt(rst.getString("id")),rst.getString("yearName"),rst.getString("semesterName"),rst.getString("fullName"));
            yearAndSemesterList.add(yearAndSemester);
        }
        return yearAndSemesterList;
    }
}
