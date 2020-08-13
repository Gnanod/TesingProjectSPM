package main.service;

import main.model.Lecturer;
import main.model.MainGroup;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LecturerService {
    public boolean saveLecturer(Lecturer lecturer) throws SQLException;
    public ArrayList<Lecturer> getAllLecturerDetails() throws SQLException ;
    public ArrayList<Lecturer> searchLecturerDetails(String name) throws SQLException ;
    public void deleteLecturerDetails(int id) throws SQLException ;
    public boolean updateLecturer(Lecturer lecturer) throws SQLException ;
}
