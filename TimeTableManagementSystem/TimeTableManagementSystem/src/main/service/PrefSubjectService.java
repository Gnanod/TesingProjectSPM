package main.service;

import main.model.prefSubject;

import java.sql.SQLException;

public interface PrefSubjectService {

    String getSubIdFromSubjects(String subject) throws SQLException;

    boolean savePrefSubjectRoom(prefSubject prefSub) throws SQLException;
}
