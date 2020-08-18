package main.service.impl;

import main.model.Lecturer;
import main.model.Subject;
import main.service.SubjectService;
import main.dbconnection.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class SubjectServiceImpl implements SubjectService {
    private Connection connection;
    public SubjectServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean saveSubject(Subject subject) throws SQLException {
        String SQL = "Insert into Subject  Values(?,?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setObject(1, subject.getSubId());
        stm.setObject(2, subject.getSubName());
        stm.setObject(3, subject.getOfferedYearSem());
        stm.setObject(4, subject.getNoLecHrs());
        stm.setObject(5, subject.getNoTutHrs());
        stm.setObject(6, subject.getNoEvalHrs());
        int res = stm.executeUpdate();
        return res > 0;
    }

    @Override
    public ArrayList<Subject> getAllSubjectDetails() throws SQLException {
        String SQL = "Select * from Subject";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<Subject>  subjects = new ArrayList<>();
        while(rst.next()){
            Subject subject=new Subject();
            subject.setSubId(rst.getString("subId"));
            subject.setSubName(rst.getString("subName"));
            subject.setOfferedYearSem(Integer.parseInt(rst.getString("offeredYearSemId")));
            subject.setNoLecHrs(Integer.parseInt(rst.getString("noLecHrs")));
            subject.setNoTutHrs(Integer.parseInt(rst.getString("noTutHrs")));
            subject.setNoEvalHrs(Integer.parseInt(rst.getString("noEvalHrs")));
            subjects.add(subject);
        }
        return subjects;
    }

    @Override
    public void deleteSubjectDetails(String id) throws SQLException {
        String sql = "delete from Subject where subId LIKE '%" + id + "%'";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
    }

    @Override
    public boolean updateSubject(Subject subject) throws SQLException {
        String SQL="Update Subject set subName='"+subject.getSubName()+"',offeredYearSemId='"+subject.getOfferedYearSem()+"',noLecHrs='"+subject.getNoLecHrs()+"' ,noTutHrs='"+subject.getNoTutHrs()+"',noEvalHrs='"+subject.getNoEvalHrs()+"'" +
                "where subId='"+subject.getSubId()+"'";
        Statement stm=connection.createStatement();
        return stm.executeUpdate(SQL)>0;
    }

    @Override
    public ArrayList<Subject> searchSubjectDetails(String name) throws SQLException {
        return null;
    }
}
