package main.service.impl;

import main.model.Lecturer;
import main.service.LecturerService;
import main.dbconnection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LectureServiceImpl implements LecturerService {
    private Connection connection;

    public LectureServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }
    @Override
    public boolean saveLecturer(Lecturer lecturer) throws SQLException {
        String SQL = "Insert into Lecturer  Values(?,?,?,?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setObject(1, lecturer.getEmpId());
        stm.setObject(2, lecturer.getEmpName());
        stm.setObject(3, lecturer.getFaculty());
        stm.setObject(4, lecturer.getDepartment());
        stm.setObject(5, lecturer.getCenter());
        stm.setObject(6, lecturer.getBuilding());
        stm.setObject(7, lecturer.getLevel());
        stm.setObject(8, lecturer.getRank());
        int res = stm.executeUpdate();
        return res > 0;
    }
}
