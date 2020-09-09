package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.Programme;
import main.model.Tag;
import main.service.TagService;

import java.sql.*;
import java.util.ArrayList;

public class TagServiceImpl implements TagService {

    private Connection connection;

    public TagServiceImpl(){
        connection = DBConnection.getInstance().getConnection();
    }
    @Override
    public boolean saveTag(Tag tag) throws SQLException {
        String SQL = "Insert into tag Values(?,?)";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setObject(1, 0);
        stm.setObject(2, tag.getTagName());
        int res = stm.executeUpdate();
        return res > 0;
    }

    @Override
    public boolean searchTag(String name) throws SQLException {
        String SQL = "select tagid from tag where tagName = '" + name + "' ";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        boolean result = false;
        if (rst.next()) {
            if (rst.getString("tagid") != null) {
                result = true;
            } else {
                result = false;
            }
        }
        return result;
    }

    @Override
    public ArrayList<Tag> getAllDetails() throws SQLException {
        String SQL ="Select * from tag";
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<Tag> tagList = new ArrayList<>();
        while(rst.next()){
            Tag t = new Tag(Integer.parseInt(rst.getString("tagid")),rst.getString("tagName"));
            tagList.add(t);

        }
        return tagList;
    }

    @Override
    public boolean updateTag(Tag tag) throws SQLException {
        String SQL="Update tag set tagName='"+tag.getTagName()+"' where tagid='"+tag.getTagId()+"'";
        Statement stm=connection.createStatement();
        return stm.executeUpdate(SQL)>0;
    }

    @Override
    public boolean deleteTag(int key) throws SQLException {
        String SQL = "Delete From tag where tagid = '"+key+"'";
        Statement stm = connection.createStatement();
        return stm.executeUpdate(SQL)>0;    }
}
