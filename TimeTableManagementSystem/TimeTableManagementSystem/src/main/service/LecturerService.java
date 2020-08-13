package main.service;

import main.model.Lecturer;
import main.model.MainGroup;

import java.sql.SQLException;

public interface LecturerService {
    public boolean saveLecturer(Lecturer lecturer) throws SQLException;
}
