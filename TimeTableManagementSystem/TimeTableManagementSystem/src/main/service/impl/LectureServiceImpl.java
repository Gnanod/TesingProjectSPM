package main.service.impl;

import main.model.Lecturer;
import main.model.MainGroup;
import main.model.NotAvailableGroup;
import main.model.NotAvailableLecturer;
import main.service.LecturerService;
import main.dbconnection.DBConnection;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LectureServiceImpl implements LecturerService {
    private Connection connection;

    public LectureServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean saveLecturer(Lecturer lecturer) throws SQLException {
        String SQL = "Insert into Lecturer  Values(?,?,?,?,?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setObject(1, lecturer.getEmpId());
        stm.setObject(2, lecturer.getEmpName());
        stm.setObject(3, lecturer.getFaculty());
        stm.setObject(4, lecturer.getDepartment());
        stm.setObject(5, lecturer.getDesignation());
        stm.setObject(6, lecturer.getCenter());
        stm.setObject(7, lecturer.getBuilding());
        stm.setObject(8, lecturer.getLevel());
        stm.setObject(9, lecturer.getRank());
        int res = stm.executeUpdate();
        return res > 0;
    }

    @Override
    public ArrayList<Lecturer> getAllLecturerDetails() throws SQLException {
        String SQL = "Select * from Lecturer";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<Lecturer> lecturers = new ArrayList<>();
        while (rst.next()) {
            Lecturer lecturer = new Lecturer();
            lecturer.setEmpId(Integer.parseInt(rst.getString("employeeId")));
            lecturer.setEmpName(rst.getString("employeeName"));
            lecturer.setFaculty(rst.getString("faculty"));
            lecturer.setDepartment(Integer.parseInt(rst.getString("departmentId")));
            lecturer.setCenter(rst.getString("center"));
            lecturer.setBuilding(Integer.parseInt(rst.getString("buildingId")));
            lecturer.setDesignation(rst.getString("designation"));
            lecturer.setLevel(Integer.parseInt(rst.getString("level")));
            lecturer.setRank(rst.getString("ranks"));
            lecturers.add(lecturer);
        }
        return lecturers;
    }

    @Override
    public ArrayList<Lecturer> searchLecturerDetails(String name) throws SQLException {
        String empName = name;
        String SQL = "Select * from Lecturer where employeeName LIKE '%" + empName + "%'";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<Lecturer> lecturers = new ArrayList<>();
        while (rst.next()) {
            Lecturer lecturer = new Lecturer();
            lecturer.setEmpId(Integer.parseInt(rst.getString("employeeId")));
            lecturer.setEmpName(rst.getString("employeeName"));
            lecturer.setFaculty(rst.getString("faculty"));
            lecturer.setDepartment(Integer.parseInt(rst.getString("departmentId")));
            lecturer.setDesignation(rst.getString("designation"));
            lecturer.setCenter(rst.getString("center"));
            lecturer.setBuilding(Integer.parseInt(rst.getString("buildingId")));
            lecturer.setLevel(Integer.parseInt(rst.getString("level")));
            lecturer.setRank(rst.getString("ranks"));
            lecturers.add(lecturer);
        }
        return lecturers;
    }

    @Override
    public void deleteLecturerDetails(int id) throws SQLException {
        String sql = "delete from Lecturer where employeeId LIKE '%" + id + "%'";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
    }

    @Override
    public boolean updateLecturer(Lecturer lecturer) throws SQLException {
        lecturer.setBuilding(1);
        lecturer.setDepartment(1);
        System.out.print(lecturer.getDesignation());
        String SQL = "Update Lecturer set employeeName='" + lecturer.getEmpName() + "',faculty='" + lecturer.getFaculty() + "',departmentId='" + lecturer.getDepartment() + "' ,center='" + lecturer.getCenter() + "',designation='" + lecturer.getDesignation() + "',level='" + lecturer.getLevel() + "'" +
                "where employeeId='" + lecturer.getEmpId() + "'";
        Statement stm = connection.createStatement();
        return stm.executeUpdate(SQL) > 0;
    }

    @Override
    public boolean addNotAvailableLectures(NotAvailableLecturer nal) throws SQLException {
        String SQL = "Insert into notAvailableLecture  Values(?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setObject(1, 0);
        stm.setObject(2, nal.getDay());
        stm.setObject(3, nal.getToTime());
        stm.setObject(4, nal.getFromTime());
        stm.setObject(5, nal.getLectureId());
        int res = stm.executeUpdate();
        return res > 0;
    }

    @Override
    public boolean deleteNotAvailableLecturer(int id) throws SQLException {
        String sql = "delete from notAvailableLecture where id LIKE '%" + id + "%'";
        Statement stmt = connection.createStatement();
        int res =stmt.executeUpdate(sql);
        return res>0;
    }

    @Override
    public List<NotAvailableLecturer> getAllNotAvailableLecturers(String name) throws SQLException {
        String SQL ="";
        if(name.isEmpty()){
            System.out.println("LLLLLLLLL");
            SQL = "select * from notAvailableLecture nal ,Lecturer l where nal.lectureId=l.employeeId ";
        }else{
            SQL ="select * from notAvailableLecture nal,Lecturer l where nal.lectureId=l.employeeId and " +
                    "l.employeeName LIKE '%"+name+"%' ";
        }
        System.out.println(SQL);
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<NotAvailableLecturer> main = new ArrayList<>();
        while(rst.next()){
            NotAvailableLecturer nal = new NotAvailableLecturer();
            nal.setId(Integer.parseInt(rst.getString("id")));
            nal.setToTime(rst.getString("toTime"));
            nal.setFromTime(rst.getString("fromTime"));
            nal.setDay(rst.getString("day"));
            nal.setLectureName(rst.getString("employeeName"));
            main.add(nal);
        }
        return main;
    }
}
