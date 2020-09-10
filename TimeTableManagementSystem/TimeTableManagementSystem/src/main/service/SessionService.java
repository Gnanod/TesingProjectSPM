package main.service;

import main.model.NotAvailableSession;

import java.sql.SQLException;

public interface SessionService {
    int searchSession(int lecId, String subId, int tagId, int subGroupId, int mainGroupId) throws SQLException;

    boolean saveDetails(NotAvailableSession nas) throws SQLException;
}
