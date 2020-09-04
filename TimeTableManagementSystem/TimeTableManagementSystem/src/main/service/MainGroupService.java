package main.service;

import main.model.MainGroup;
import main.model.MainGroupCount;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MainGroupService {

    public boolean saveMainGroupId(MainGroup mainGroup) throws SQLException;

    int getCountAccordingToName(String s)throws SQLException;

    ArrayList<MainGroupCount> getAllGroupCount(int yearAndSem, int programme) throws SQLException;

    ArrayList<MainGroup> getAllGroupDetails(int id)throws  SQLException;

    boolean searchMainGroup(String newGroupId) throws SQLException;

    boolean updateGroupNumber(MainGroup m) throws SQLException;

    ArrayList<MainGroup> getAllMainGroupDetails() throws SQLException;

    boolean deleteMainGroup(int id) throws SQLException;
}
